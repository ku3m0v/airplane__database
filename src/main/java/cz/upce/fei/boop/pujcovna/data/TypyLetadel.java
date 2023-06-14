package cz.upce.fei.boop.pujcovna.data;

public enum TypyLetadel {

    DOPRAVNI_LETADLO ("Dopravní letadlo"),
    SOUKROME_LETADLO ("Soukromé letadlo"),
    SPORTOVNI_LETADLO ("Sportovní letadlo");

    private final String nazev;

    TypyLetadel(String nazev) {
        this.nazev = nazev;
    }

    public static TypyLetadel fromString(String s) {
        return switch (s) {
            case "Dopravní letadlo" -> DOPRAVNI_LETADLO;
            case "Soukromé letadlo" -> SOUKROME_LETADLO;
            case "Sportovní letadlo" -> SPORTOVNI_LETADLO;
            default -> throw new IllegalArgumentException("Invalid string representation of TypyLetadel: " + s);
        };
    }

    @Override
    public String toString() {
        return nazev;
    }

}
