package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.generator.ObjectGenerator;
import cz.upce.fei.boop.pujcovna.kolekce.KolekceException;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import cz.upce.fei.boop.pujcovna.perzistence.BinarniPerzistence;
import cz.upce.fei.boop.pujcovna.perzistence.TextovaPerzistence;

import java.io.IOException;
import java.util.Iterator;

public class SpravaLetadel implements Ovladani {

    private SpojovySeznam<Letadlo> seznamLetadel;

    private static final ObjectGenerator objectGenerator = new ObjectGenerator();
    private static final BinarniPerzistence bp = new BinarniPerzistence();
    private static final TextovaPerzistence tp = new TextovaPerzistence();

    public SpravaLetadel() {
        this.seznamLetadel = new SpojovySeznam<>();
    }

    public String generujId(TypyLetadel typ) {
        return objectGenerator.generujID(typ);
    }

    public void vlozPolozku(Letadlo data) throws OvladaniException {
        if (data == null) {
            throw new OvladaniException("Data nesmi byt null!");
        }
        seznamLetadel.vlozPosledni(data);
    }

    public Letadlo najdi(String id) throws OvladaniException {
        if (id == null || id.isEmpty()) {
            throw new OvladaniException("Id nesmi byt null!");
        }
        Iterator iterator = iterator();
        Letadlo average = null;
        while (iterator.hasNext()) {
            Letadlo temp = (Letadlo) iterator.next();
            if (temp.getId().equals(id)) {
                average = temp;
            }
        }
        if (average == null) {
            throw new OvladaniException("Letadlo s id " + id + " nebylo nalezeno!");
        } else {
            return average;
        }
    }

    public Letadlo odeber(String id) throws OvladaniException {
        if (id == null || id.isEmpty()) {
            throw new OvladaniException("Id nesmi byt null!");
        }
        Letadlo removed = null;
        Iterator iterator = seznamLetadel.iterator();
        while (iterator.hasNext()) {
            Letadlo temp = (Letadlo) iterator.next();
            if (temp.getId().equals(id)) {
                removed = temp;
                iterator.remove();
            }
        }
        if (removed == null) {
            throw new OvladaniException("Letadlo s id " + id + " nebylo nalezeno!");
        } else {
            return removed;
        }
    }



    public Letadlo dej() throws OvladaniException {
        Letadlo average = null;
        try {
            average = seznamLetadel.dejAktualni();
        } catch (KolekceException ex) {
            throw new OvladaniException(ex.getMessage());
        }
        return average;
    }

    public void editAktualni(Letadlo data) throws OvladaniException {
        if (data == null) {
            throw new OvladaniException("Data nesmi byt null!");
        }
        try {
            seznamLetadel.vlozZaAktualni(data);
            seznamLetadel.odeberAktualni();
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
    }

    public void update(String id, Letadlo letadlo) throws OvladaniException {
        if (id == null || id.isEmpty()) {
            throw new OvladaniException("Id nesmi byt null!");
        }
        if (letadlo == null) {
            throw new OvladaniException("Letadlo nesmi byt null!");
        }
        boolean updated = false;
        try {
            nastavPrvni();
            while (seznamLetadel.jeDalsi()) {
                if (dej().getId().equals(id)) {
                    editAktualni(letadlo);
                    updated = true;
                    break;
                }
                seznamLetadel.dalsi();
            }
            if (!updated && dej().getId().equals(id)) {
                editAktualni(letadlo);
                updated = true;
            }
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
        if (!updated) {
            throw new OvladaniException("Letadlo s id " + id + " nebylo nalezeno!");
        }
    }




    public Letadlo vyjmi() throws OvladaniException {
        Letadlo average = null;
        try {
            average = seznamLetadel.odeberAktualni();
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
        return average;
    }

    public void nastavPrvni() throws OvladaniException {
        try {
            seznamLetadel.nastavPrvni();
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
    }

    public void dalsi() throws OvladaniException {
        try {
            seznamLetadel.dalsi();
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
    }

    public void nastavPosledni() throws OvladaniException {
        try {
            seznamLetadel.nastavPosledni();
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
    }

    public int pocet() {
        return seznamLetadel.size();
    }

    public void obnov() throws OvladaniException {
        try {
            seznamLetadel = new SpojovySeznam();
            bp.nacti("test.bin", seznamLetadel);
        } catch (IOException e) {
            throw new OvladaniException("Chyba při čtení ze souboru test.bin!");
        }
    }

    public void zalohuj() throws OvladaniException {
        try {
            bp.uloz("test.bin", seznamLetadel);
        } catch (IOException e) {
            throw new OvladaniException("Nepodařilo se zapsat seznam do souboru test.bin!");
        }
    }

    public SpojovySeznam<Letadlo> dejSeznam() throws OvladaniException {
        if (seznamLetadel.size() == 0) {
            throw new OvladaniException("Seznam je prazdny");
        }
        return seznamLetadel;
    }

    public void nactiText() throws OvladaniException {
        SpojovySeznam<Letadlo> temp = TextovaPerzistence.nactiZeSouboru("test.txt");
        if (temp.size() > 0) {
            seznamLetadel = temp;
        } else {
            throw new OvladaniException("Nepodarilo se nacist seznam ze souboru");
        }
    }
    public void ulozText() {
        tp.ulozDoSouboru("test.txt", seznamLetadel);
    }

    public void generuj(int pocet) {
        seznamLetadel = objectGenerator.generateObjects(pocet);
    }

    public void zrus() {
        seznamLetadel.zrus();
    }

    @Override
    public Iterator<Letadlo> iterator() {
        return seznamLetadel.iterator();
    }

}
