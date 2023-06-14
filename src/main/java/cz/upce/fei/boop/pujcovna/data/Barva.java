package cz.upce.fei.boop.pujcovna.data;

import java.awt.Color;

public enum Barva {

    BILA(Color.WHITE, "bílá"),
    CERNA(Color.BLACK, "černá"),
    CERVENA(Color.RED, "červená"),
    ZELENA(Color.GREEN, "zelená"),
    MODRA(Color.BLUE, "modrá");

    private final Color color;
    private final String nazev;

    Barva(Color color, String cesky) {
        this.color = color;
        this.nazev = cesky;
    }

    public static Barva fromString(String s) {
        return switch (s) {
            case "bílá" -> BILA;
            case "černá" -> CERNA;
            case "červená" -> CERVENA;
            case "zelená" -> ZELENA;
            case "modrá" -> MODRA;
            default -> throw new IllegalArgumentException("Invalid string representation of TypyLetadel: " + s);
        };
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return nazev;
    }

}
