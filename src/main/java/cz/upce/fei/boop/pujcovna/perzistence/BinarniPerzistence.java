package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;

public class BinarniPerzistence {

    public BinarniPerzistence() {
    }


    public static <E> void uloz(String jmenoSouboru, SpojovySeznam<E> seznam)
            throws IOException {
        Objects.requireNonNull(seznam);
        ObjectOutputStream vystup;
        vystup = new ObjectOutputStream(
                new FileOutputStream(jmenoSouboru));
        vystup.writeInt(seznam.size());
        for (Object o : seznam) {
            vystup.writeObject(o);
        }

    }

    public <E> void nacti(String jmenoSouboru, SpojovySeznam<E> seznam)
            throws IOException {
        try {
            Objects.requireNonNull(seznam);
            try (ObjectInputStream vstup
                         = new ObjectInputStream(
                    new FileInputStream(jmenoSouboru))) {
                seznam.zrus();
                int pocet = vstup.readInt();
                for (int i = 0; i < pocet; i++) {
                    seznam.vlozPosledni((E) vstup.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new IOException(ex);
        }
    }
}
