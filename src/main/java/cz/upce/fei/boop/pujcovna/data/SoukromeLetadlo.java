package cz.upce.fei.boop.pujcovna.data;


import java.io.Serializable;
import java.util.Locale;

public class SoukromeLetadlo extends Letadlo implements Serializable {

    private Barva barva;

    public SoukromeLetadlo(String id, int rychlost, Barva barva) {
        super(id, TypyLetadel.SOUKROME_LETADLO, rychlost);
        this.barva = barva;
    }

    public Barva getBarva() {
        return barva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SoukromeLetadlo that = (SoukromeLetadlo) o;

        return barva == that.barva;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (barva != null ? barva.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, typ: %s, rychlost: %d km/h, barva: %s", getId(),
                getTyp().toString(), getRychlost(), barva.toString());
    }

}
