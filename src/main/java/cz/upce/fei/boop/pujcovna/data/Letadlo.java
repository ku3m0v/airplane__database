package cz.upce.fei.boop.pujcovna.data;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public abstract class Letadlo implements Serializable {

    private String id;
    TypyLetadel typ;
    private int rychlost;

    public Letadlo(String id, TypyLetadel typ, int rychlost) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Nespravny id!");
        }
        if (rychlost <= 0) {
            throw new IllegalArgumentException("Nespravna rychlost!");
        }
        this.id = id;
        this.typ = typ;
        this.rychlost = rychlost;
    }

    public void setId(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Nespravny id!");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public TypyLetadel getTyp() {
        return typ;
    }

    public int getRychlost() {
        return rychlost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Letadlo letadlo = (Letadlo) o;

        if (rychlost != letadlo.rychlost) return false;
        if (!Objects.equals(id, letadlo.id)) return false;
        return typ == letadlo.typ;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typ != null ? typ.hashCode() : 0);
        result = 31 * result + rychlost;
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, typ: %s, rychlost: %d", id, typ.toString(), rychlost);
    }

}
