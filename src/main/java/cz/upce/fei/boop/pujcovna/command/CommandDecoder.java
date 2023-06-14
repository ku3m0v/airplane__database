package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.spravce.OvladaniException;
import cz.upce.fei.boop.pujcovna.spravce.SpravaLetadel;

import java.util.ArrayList;

public class CommandDecoder {

    private final ArrayList<String> commandList;
    Reader reader = new Reader();
    private SpravaLetadel seznamLetadel = new SpravaLetadel();

    public CommandDecoder() {
        commandList = new ArrayList<>();
        commandList.add("help, h     - výpis příkazů");
        commandList.add("novy,no     - vytvoř novou instanci a vlož data za aktuální prvek");
        commandList.add("najdi,na,n  - najdi v seznamu data podle hodnoty nějakém atributu");
        commandList.add("odeber,od   - odeber data ze seznamu podle nějaké hodnoty atributu");
        commandList.add("dej         - zobraz aktuální data v seznamu");
        commandList.add("edituj,edit - edituj aktuální data v seznamu");
        commandList.add("vyjmi       - vyjmi aktuální data ze seznamu");
        commandList.add("prvni,pr    - nastav jako aktuální první data v seznamu");
        commandList.add("dalsi,da    - přejdi na další data");
        commandList.add("posledni,po - přejdi na poslední data");
        commandList.add("pocet       - zobraz počet položek v seznamu");
        commandList.add("obnov       - obnov seznam data z binárního souboru");
        commandList.add("zalohuj     - zálohuj seznam dat do binárního souboru");
        commandList.add("vypis       - zobraz seznam dat");
        commandList.add("nactitext,nt- načti seznam data z textového souboru");
        commandList.add("uloztext,ut - ulož seznam data do textového souboru");
        commandList.add("generuj,g   - generuj náhodně data pro testování");
        commandList.add("zrus        - zruš všechny data v seznamu");
        commandList.add("exit        - ukončení programu");
    }

    public void executeCommand(String command) {

        command = command.trim();

        switch (command) {
            case "help":
            case "h":
                displayCommands();
                break;
            case "novy":
            case "no":
                createNewInstance();
                break;
            case "najdi":
            case "na":
            case "n":
                findData();
                break;
            case "odeber":
            case "od":
                removeData();
                break;
            case "dej":
                displayCurrentData();
                break;
            case "edituj":
            case "edit":
                editCurrentData();
                break;
            case "vyjmi":
                removeCurrentData();
                break;
            case "prvni":
            case "pr":
                setFirstData();
                break;
            case "dalsi":
            case "da":
                moveToNextData();
                break;
            case "posledni":
            case "po":
                moveToLastData();
                break;
            case "pocet":
                displayNumberOfItems();
                break;
            case "obnov":
                restoreDataListFromBinaryFile();
                break;
            case "zalohuj":
                backupDataListToBinaryFile();
                break;
            case "vypis":
                displayDataList();
                break;
            case "nactitext":
            case "nt":
                loadDataListFromTextFile();
                break;
            case "uloztext":
            case "ut":
                saveDataListToTextFile();
                break;
            case "generuj":
            case "g":
                generateRandomData();
                break;
            case "zrus":
                deleteAllData();
                break;
            case "exit":
                break;
            default:
                System.out.println("Invalid command!");
        }
    }

    private void displayCommands() {
        System.out.println("List of commands:");
        for (String command : commandList) {
            System.out.println("- " + command);
        }
    }

    private void createNewInstance() {

        try {
            String id;

            int typ = reader.readInt("""
                    1 - Dopravni letadlo
                    2 - Soukrome letadlo
                    3 - Sportovni letadlo
                    Napiste cislo typu letadla:\s""", "typ letadla");

            int rychlost = reader.readInt("Napiste rychlost: ", "rychlost");

            switch (typ) {
                case 1 -> {
                    id = seznamLetadel.generujId(TypyLetadel.DOPRAVNI_LETADLO);
                    int pocetSedadel = reader.readInt("Napiste pocet sedadel: ", "pocet sedadel");
                    int dolet = reader.readInt("Napiste dolet: ", "dolet");
                    seznamLetadel.vlozPolozku(new DopravniLetadlo(id, rychlost, pocetSedadel, dolet));
                }
                case 2 -> {
                    id = seznamLetadel.generujId(TypyLetadel.SOUKROME_LETADLO);
                    int length = Barva.values().length;
                    for (int i = 0; i < length; i++) {
                        System.out.println(i + 1 + " - " + Barva.values()[i].toString());
                    }
                    int cisloBarvy = reader.readInt("Napiste cislo barvy: ", "cislo barvy");
                    if (cisloBarvy <= 0 || cisloBarvy > length) {
                        throw new IllegalArgumentException("Cislo barvy musi byt od 1 do " + length);
                    }
                    seznamLetadel.vlozPolozku(new SoukromeLetadlo(id, rychlost, Barva.values()[cisloBarvy - 1]));
                }
                case 3 -> {
                    id = seznamLetadel.generujId(TypyLetadel.SPORTOVNI_LETADLO);
                    String typMotoru = reader.readString("Napis typ motoru: ", "typ motoru");
                    int vzletovaDraha = reader.readInt("Napis vzletovou drahu: ", "vzletova draha");
                    seznamLetadel.vlozPolozku(new SportovniLetadlo(id, rychlost, typMotoru, vzletovaDraha));
                }
                default -> System.out.println("Zadan neni spravny typ!");
            }
        } catch (IllegalArgumentException | OvladaniException ex) {
            System.err.println(ex.getMessage());
        }


    }

    private void findData() {
        String id = reader.readString("Napiste ID letadla, ktere hledate: ", "ID");
        Letadlo temp = null;
        try {
            temp = seznamLetadel.najdi(id);
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Nasli jsme tyto data: " + temp);
    }

    private void removeData() {
        String id = reader.readString("Napiste ID letadla, ktere chcete odebrat: ", "ID");
        Letadlo temp = null;
        try {
            temp = seznamLetadel.odeber(id);
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Odebrali jsme tyto data: " + temp);
    }

    private void displayCurrentData(){
        Letadlo temp = null;
        try {
            temp = seznamLetadel.dej();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Aktualni prvek je: " + temp);
    }

    private void editCurrentData(){

        try {
            String id = seznamLetadel.dej().getId();

            TypyLetadel typ = seznamLetadel.dej().getTyp();

            if (typ != null) {

                int rychlost = reader.readInt("Napiste novou rychlost: ", "rychlost");

                switch (typ) {
                    case DOPRAVNI_LETADLO -> {
                        int pocetSedadel = reader.readInt("Napiste novy pocet sedadel: ", "pocet sedadel");
                        int dolet = reader.readInt("Napiste novy dolet: ", "dolet");
                        seznamLetadel.editAktualni(new DopravniLetadlo(id, rychlost, pocetSedadel, dolet));
                    }
                    case SOUKROME_LETADLO -> {
                        int length = Barva.values().length;
                        for (int i = 0; i < length; i++) {
                            System.out.println(i + 1 + " - " + Barva.values()[i].toString());
                        }
                        int cisloBarvy = reader.readInt("Napiste nove cislo barvy: ", "cislo barvy");
                        if (cisloBarvy <= 0 || cisloBarvy > length) {
                            throw new IllegalArgumentException("Cislo barvy musi byt od 1 do " + length);
                        }
                        Barva barva = Barva.values()[cisloBarvy - 1];
                        seznamLetadel.editAktualni(new SoukromeLetadlo(id, rychlost, barva));
                    }
                    case SPORTOVNI_LETADLO -> {
                        String typMotoru = reader.readString("Napis novy typ motoru: ", "typ motoru");
                        int vzletovaDraha = reader.readInt("Napis vzletovou drahu: ", "vzletova draha");
                        seznamLetadel.editAktualni(new SportovniLetadlo(id, rychlost, typMotoru, vzletovaDraha));
                    }
                    default -> System.out.println("Zadan neni spravny typ!");
                }
            }
        } catch (IllegalArgumentException | OvladaniException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void removeCurrentData() {
        Letadlo temp = null;
        try {
            temp = seznamLetadel.vyjmi();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Odebrano aktualni letadlo: " + temp);
    }

    private void setFirstData() {
        try {
            seznamLetadel.nastavPrvni();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void moveToNextData() {
        try {
            seznamLetadel.dalsi();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void moveToLastData() {
        try {
            seznamLetadel.nastavPosledni();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void displayNumberOfItems() {
        System.out.println(seznamLetadel.pocet());
    }

    private void restoreDataListFromBinaryFile() {
        try {
            seznamLetadel.obnov();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void backupDataListToBinaryFile() {
        try {
            seznamLetadel.zalohuj();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void displayDataList() {
        try {
            for(Letadlo l : seznamLetadel.dejSeznam()){
                System.out.println(l);
            }
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadDataListFromTextFile() {
        try {
            seznamLetadel.nactiText();
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private void saveDataListToTextFile() {
        seznamLetadel.ulozText();
    }

    private void generateRandomData() {
        int pocet = reader.readInt("Zadejte pocet hodnot: ", "pocet");
        seznamLetadel.generuj(pocet);
    }

    private void deleteAllData() {
        seznamLetadel.zrus();
    }

}
