package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUIDialog {

    private final Stage stage;
    private final Scene scene;
    private final GridPane gridPane;
    private TextField rychlostField;
    private TextField pocetSedadelField;
    private TextField doletField;
    private TextField typMotoruField;
    private TextField vzletovaDrahaField;
    private ComboBox<Barva> barvaComboBox;

    Letadlo letadlo = null;

    public GUIDialog(String typ, String id) {
        stage = new Stage();
        stage.setTitle("Prace s letadlem");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        zakladniNastaveni(id);
        switch (typ) {
            case "Dopravní letadlo" -> dopravniNastaveni();
            case "Soukromé letadlo" -> soukromeNastaveni();
            case "Sportovní letadlo" -> sportovniNastaveni();
        }
        HBox buttonBox = new HBox();
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> stage.close());
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                int rychlost = Integer.parseInt(rychlostField.getText());
                switch (typ) {
                    case "Dopravní letadlo" -> {
                        int pocetSedadel = Integer.parseInt(pocetSedadelField.getText());
                        int dolet = Integer.parseInt(doletField.getText());
                        letadlo = new DopravniLetadlo(id, rychlost, pocetSedadel, dolet);
                    }
                    case "Soukromé letadlo" -> {
                        Barva barva = barvaComboBox.getValue();
                        letadlo = new SoukromeLetadlo(id, rychlost, barva);
                    }
                    case "Sportovní letadlo" -> {
                        String typMotoru = typMotoruField.getText();
                        int vzletovaDraha = Integer.parseInt(vzletovaDrahaField.getText());
                        letadlo = new SportovniLetadlo(id, rychlost, typMotoru, vzletovaDraha);
                    }
                }
                stage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Neni spravne vyplneno!");
                alert.showAndWait();
            }
        });
        buttonBox.getChildren().addAll(cancelButton, okButton);
        buttonBox.setSpacing(10);
        gridPane.add(buttonBox, 1, 5);

        scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public GUIDialog(Letadlo let) {
        stage = new Stage();
        stage.setTitle("Prace s letadlem");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        zakladniNastaveni(let);
        switch (let.getTyp()) {
            case DOPRAVNI_LETADLO -> dopravniNastaveni((DopravniLetadlo) let);
            case SOUKROME_LETADLO -> soukromeNastaveni((SoukromeLetadlo) let);
            case SPORTOVNI_LETADLO -> sportovniNastaveni((SportovniLetadlo) let);
        }
        HBox buttonBox = new HBox();
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> stage.close());
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                int rychlost = Integer.parseInt(rychlostField.getText());
                switch (let.getTyp()) {
                    case DOPRAVNI_LETADLO -> {
                        int pocetSedadel = Integer.parseInt(pocetSedadelField.getText());
                        int dolet = Integer.parseInt(doletField.getText());
                        letadlo = new DopravniLetadlo(let.getId(), rychlost, pocetSedadel, dolet);
                    }
                    case SOUKROME_LETADLO -> {
                        Barva barva = barvaComboBox.getValue();
                        letadlo = new SoukromeLetadlo(let.getId(), rychlost, barva);
                    }
                    case SPORTOVNI_LETADLO -> {
                        String typMotoru = typMotoruField.getText();
                        int vzletovaDraha = Integer.parseInt(vzletovaDrahaField.getText());
                        letadlo = new SportovniLetadlo(let.getId(), rychlost, typMotoru, vzletovaDraha);
                    }
                }
                stage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Neni spravne vyplneno!");
                alert.showAndWait();
            }
        });
        buttonBox.getChildren().addAll(cancelButton, okButton);
        buttonBox.setSpacing(10);
        gridPane.add(buttonBox, 1, 5);

        scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void zakladniNastaveni(String id){
        Label idLabel = new Label("ID:");
        gridPane.add(idLabel, 0, 0);

        Label idField = new Label(id);
        gridPane.add(idField, 1, 0);

        Label rychlostLabel = new Label("Rychlost:");
        gridPane.add(rychlostLabel, 0, 1);

        rychlostField = new TextField();
        gridPane.add(rychlostField, 1, 1);
    }

    private void zakladniNastaveni(Letadlo let){
        Label idLabel = new Label("ID:");
        gridPane.add(idLabel, 0, 0);

        Label idField = new Label(let.getId());
        gridPane.add(idField, 1, 0);

        Label rychlostLabel = new Label("Rychlost:");
        gridPane.add(rychlostLabel, 0, 1);

        rychlostField = new TextField();
        rychlostField.setText(String.valueOf(let.getRychlost()));
        gridPane.add(rychlostField, 1, 1);
    }

    private void dopravniNastaveni() {
        Label pocetSedadelLabel = new Label("Počet sedadel:");
        gridPane.add(pocetSedadelLabel, 0, 2);

        pocetSedadelField = new TextField();
        gridPane.add(pocetSedadelField, 1, 2);

        Label doletLabel = new Label("Dolet:");
        gridPane.add(doletLabel, 0, 3);

        doletField = new TextField();
        gridPane.add(doletField, 1, 3);
    }

    private void dopravniNastaveni(DopravniLetadlo let) {
        Label pocetSedadelLabel = new Label("Počet sedadel:");
        gridPane.add(pocetSedadelLabel, 0, 2);

        pocetSedadelField = new TextField();
        pocetSedadelField.setText(String.valueOf(let.getPocetSedadel()));
        gridPane.add(pocetSedadelField, 1, 2);

        Label doletLabel = new Label("Dolet:");
        gridPane.add(doletLabel, 0, 3);

        doletField = new TextField();
        doletField.setText(String.valueOf(let.getDolet()));
        gridPane.add(doletField, 1, 3);
    }

    private void soukromeNastaveni() {
        Label barvaLabel = new Label("Barva:");
        gridPane.add(barvaLabel, 0, 3);

        barvaComboBox = new ComboBox<>();
        barvaComboBox.getItems().addAll(Barva.values());
        barvaComboBox.getSelectionModel().selectFirst();
        gridPane.add(barvaComboBox, 1, 3);
    }

    private void soukromeNastaveni(SoukromeLetadlo let) {
        Label barvaLabel = new Label("Barva:");
        gridPane.add(barvaLabel, 0, 3);

        barvaComboBox = new ComboBox<>();
        barvaComboBox.getItems().addAll(Barva.values());
        barvaComboBox.getSelectionModel().select(let.getBarva());
        gridPane.add(barvaComboBox, 1, 3);
    }

    private void sportovniNastaveni() {
        Label typMotoruLabel = new Label("Typ motoru:");
        gridPane.add(typMotoruLabel, 0, 3);

        typMotoruField = new TextField();
        gridPane.add(typMotoruField, 1, 3);

        Label vzletovaDrahaLabel = new Label("Vzletová dráha:");
        gridPane.add(vzletovaDrahaLabel, 0, 4);

        vzletovaDrahaField = new TextField();
        gridPane.add(vzletovaDrahaField, 1, 4);
    }

    private void sportovniNastaveni(SportovniLetadlo let) {
        Label typMotoruLabel = new Label("Typ motoru:");
        gridPane.add(typMotoruLabel, 0, 3);

        typMotoruField = new TextField();
        typMotoruField.setText(let.getTypMotoru());
        gridPane.add(typMotoruField, 1, 3);

        Label vzletovaDrahaLabel = new Label("Vzletová dráha:");
        gridPane.add(vzletovaDrahaLabel, 0, 4);

        vzletovaDrahaField = new TextField();
        vzletovaDrahaField.setText(String.valueOf(let.getVzletovaDraha()));
        gridPane.add(vzletovaDrahaField, 1, 4);
    }

    public Letadlo getLetadlo() {
        return letadlo;
    }

}
