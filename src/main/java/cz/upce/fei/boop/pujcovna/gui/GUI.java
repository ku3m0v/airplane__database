package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.Letadlo;
import cz.upce.fei.boop.pujcovna.data.TypyLetadel;
import cz.upce.fei.boop.pujcovna.spravce.OvladaniException;
import cz.upce.fei.boop.pujcovna.spravce.SpravaLetadel;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.*;

public class GUI extends Application {

    private final SpravaLetadel seznamLetadel = new SpravaLetadel();
    private final ListView<String> list = newListView(14, 43, 537, 317);
    ComboBox<String> comboBoxNoveLetadlo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        AnchorPane root = new AnchorPane();
        ObservableList<Node> children = root.getChildren();
        Scene scene = new Scene(root, 650, 420);
        primaryStage.setTitle("Evidence letadel - Evgeny Kuzmov");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        children.add(newButton("Generuj", 14, 378, generujData));
        children.add(newButton("Uloz", 80, 378, ulozText));
        children.add(newButton("Nacti", 130, 378, nactiText));
        comboBoxNoveLetadlo = newComboBox("Novy", 180, 378, TypyLetadel.values(), zadejNoveLetadlo);
        children.add(comboBoxNoveLetadlo);
        children.add(newButton("Zrus",325,378, zrusSeznam));
        ComboBox<String> comboBoxFiltr = newComboBox("Filtr", 370, 378, TypyLetadel.values(), nastavFiltr);
        comboBoxFiltr.getItems().add(0,"No filtr");
        children.add(comboBoxFiltr);
        children.add(newButton("Zalohuj", 520, 378, handlerZalohuj));
        children.add(newButton("Obnov", 590, 378, handlerObnov));

        children.add(newButton("Prvni", 560, 43, nastavPrvni));
        children.add(newButton("Dalsi", 560, 73, nastavDalsi));
        children.add(newButton("Posledni", 560, 103, nastavPosledni));

        children.add(newButton("Edituj", 560, 170, editLetadlo));
        children.add(newButton("Vyjmi", 560, 200, vyjmiLetadlo));
        children.add(newButton("Zobraz", 560, 230, zobrazLetadla));
        children.add(newButton("Clear", 560, 260, clearList));

        children.add(list);
        primaryStage.show();
    }

    private static Button newButton(String text, int x, int y, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

    private ListView<String> newListView(int x1, int y1, int x2, int y2) {
        ListView<String> listView = new ListView<>();
        listView.setLayoutX(x1);
        listView.setLayoutY(y1);
        listView.setPrefSize(x2, y2);
        return listView;
    }

    private ComboBox<String> newComboBox(String text, int x, int y, TypyLetadel[] items, EventHandler<ActionEvent> handler) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(text);
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        for (TypyLetadel item : items) {
            comboBox.getItems().add(item.toString());
        }
        comboBox.setOnAction(handler);
        return comboBox;
    }

    private final EventHandler<ActionEvent> generujData = actionEvent -> {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("GENEROVÁNÍ");
        textInputDialog.setHeaderText("Počet letadel:");
        Optional<String> pocet = textInputDialog.showAndWait();
        if (pocet.isPresent()) {
            try {
                int pocetDP = Integer.parseInt(textInputDialog.getEditor().getText());
                seznamLetadel.generuj(pocetDP);
                obnovList();
            } catch (NumberFormatException e) {
                showError("Zadejte prosím číslo!");
            }
        }
    };

    private final EventHandler<ActionEvent> ulozText = actionEvent -> {
        seznamLetadel.ulozText();
        showInfo("Ukladani probehlo uspesne!");
    };

    private final EventHandler<ActionEvent> nactiText = actionEvent -> {
        try {
            seznamLetadel.nactiText();
            obnovList();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> zadejNoveLetadlo = actionEvent -> {
        String typ = ((ComboBox) actionEvent.getSource()).getSelectionModel().getSelectedItem().toString();
        String id = seznamLetadel.generujId(TypyLetadel.fromString(typ));
        try {
            GUIDialog dialog = new GUIDialog(typ, id);
            seznamLetadel.vlozPolozku(dialog.getLetadlo());
            obnovList();

        } catch (OvladaniException e) {
            showError(e.getMessage());
        }

    };

    private final EventHandler<ActionEvent> zrusSeznam = actionEvent -> {
        seznamLetadel.zrus();
        list.getItems().clear();
    };

    private final EventHandler<ActionEvent> nastavFiltr = actionEvent -> {
        list.getItems().clear();
        try {
            String typ = ((ComboBox) actionEvent.getSource()).getSelectionModel().getSelectedItem().toString();
            switch (typ) {
                case "No filtr" -> obnovList();
                case "Dopravní letadlo" -> {
                    for (Letadlo letadlo : seznamLetadel.dejSeznam()) {
                        if (letadlo.getTyp().equals(TypyLetadel.DOPRAVNI_LETADLO)) {
                            list.getItems().add(letadlo.toString());
                        }
                    }
                }
                case "Soukromé letadlo" -> {
                    for (Letadlo letadlo : seznamLetadel.dejSeznam()) {
                        if (letadlo.getTyp().equals(TypyLetadel.SOUKROME_LETADLO)) {
                            list.getItems().add(letadlo.toString());
                        }
                    }
                }
                case "Sportovní letadlo" -> {
                    for (Letadlo letadlo : seznamLetadel.dejSeznam()) {
                        if (letadlo.getTyp().equals(TypyLetadel.SPORTOVNI_LETADLO)) {
                            list.getItems().add(letadlo.toString());
                        }
                    }
                }
            }
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> handlerZalohuj = actionEvent -> {
        try {
            seznamLetadel.zalohuj();
            showInfo("Zalohovani probehlo uspesne!");

        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> handlerObnov = actionEvent -> {
        try {
            seznamLetadel.obnov();
            obnovList();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavPrvni = actionEvent -> {
        try {
            seznamLetadel.nastavPrvni();
            list.getSelectionModel().selectFirst();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavDalsi = actionEvent -> {
        try {
            seznamLetadel.dalsi();
            list.getSelectionModel().selectNext();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavPosledni = actionEvent -> {
        try {
            seznamLetadel.nastavPosledni();
            list.getSelectionModel().selectLast();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> editLetadlo = actionEvent -> {
        String selectedItem = list.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showError("Neni vybrano zadne letadlo");
            return;
        }
        int commaIndex = selectedItem.indexOf(",");
        String id = selectedItem.substring(0, commaIndex);
        try {
            Letadlo letadlo = seznamLetadel.najdi(id);

            GUIDialog dialog = new GUIDialog(letadlo);

            if (dialog.getLetadlo() != null) {
                seznamLetadel.update(id, dialog.getLetadlo());
                obnovList();
            }

        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

        private final EventHandler<ActionEvent> vyjmiLetadlo = actionEvent -> {
        String temp = list.getSelectionModel().getSelectedItem();
        if (temp == null) {
            showError("Neni vybrano zadne letadlo");
            return;
        }
        int commaIndex = temp.indexOf(",");
        String id = temp.substring(0, commaIndex);
        try {
            seznamLetadel.odeber(id);
            obnovList();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> zobrazLetadla = actionEvent -> {
        obnovList();
    };

    private final EventHandler<ActionEvent> clearList = actionEvent -> {
        list.getItems().clear();
    };

    private void obnovList() {
        try {
            list.getItems().clear();
            for (Letadlo letadlo : seznamLetadel.dejSeznam()) {
                list.getItems().add(letadlo.toString());
            }
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
