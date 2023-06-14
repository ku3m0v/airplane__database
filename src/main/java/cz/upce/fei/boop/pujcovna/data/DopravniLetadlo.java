package cz.upce.fei.boop.pujcovna.data;

import java.io.Serializable;
import java.util.Locale;

public class DopravniLetadlo extends Letadlo implements Serializable {

    private int pocetSedadel;
    private int dolet;

    public DopravniLetadlo(String id, int rychlost, int pocetSedadel, int dolet) {
        super(id, TypyLetadel.DOPRAVNI_LETADLO, rychlost);
        if (pocetSedadel <= 0) {
            throw new IllegalArgumentException("Nespravny pocet sedadel!");
        }
        if (dolet <= 0) {
            throw new IllegalArgumentException("Nespravny dolet!");
        }
        this.pocetSedadel = pocetSedadel;
        this.dolet = dolet;
    }

    public int getPocetSedadel() {
        return pocetSedadel;
    }

    public int getDolet() {
        return dolet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DopravniLetadlo that = (DopravniLetadlo) o;

        if (pocetSedadel != that.pocetSedadel) return false;
        return dolet == that.dolet;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pocetSedadel;
        result = 31 * result + dolet;
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, typ: %s, rychlost: %d km/h, pocet sedadel: %d, dolet: " +
                "%d km", getId(), getTyp().toString(), getRychlost(), pocetSedadel, dolet);
    }

}
