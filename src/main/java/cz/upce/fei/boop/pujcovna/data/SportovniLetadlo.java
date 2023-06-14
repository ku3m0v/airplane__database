package cz.upce.fei.boop.pujcovna.data;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class SportovniLetadlo extends Letadlo implements Serializable {

    private String typMotoru;
    private int vzletovaDraha;

    public SportovniLetadlo(String id, int rychlost, String typMotoru, int vzletovaDraha) throws IllegalArgumentException {
        super(id, TypyLetadel.SPORTOVNI_LETADLO, rychlost);
        if (typMotoru == null || typMotoru.equals("")) {
            throw new IllegalArgumentException("Nespravny typ motoru!");
        }
        if (vzletovaDraha <= 0) {
            throw new IllegalArgumentException("Nespravna vzletova draha!");
        }
        this.typMotoru = typMotoru;
        this.vzletovaDraha = vzletovaDraha;
    }

    public String getTypMotoru() {
        return typMotoru;
    }

    public int getVzletovaDraha() {
        return vzletovaDraha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SportovniLetadlo that = (SportovniLetadlo) o;

        if (vzletovaDraha != that.vzletovaDraha) return false;
        return Objects.equals(typMotoru, that.typMotoru);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (typMotoru != null ? typMotoru.hashCode() : 0);
        result = 31 * result + vzletovaDraha;
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, typ: %s, rychlost: %d km/h, typ motoru: %s, vzletova draha: %d m", getId(),
                getTyp().toString(), getRychlost(), typMotoru, vzletovaDraha);
    }

}
