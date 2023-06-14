package cz.upce.fei.boop.pujcovna.kolekce;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SpojovySeznam<E> implements Seznam<E>, Serializable {

    private Prvek<E> prvni;
    private Prvek<E> posledni;
    private Prvek<E> aktualni;
    private int pocet = 0;

    private static class Prvek<E> {

        private Prvek<E> dalsi;
        private final E data;

        public Prvek(E data, Prvek<E> dalsi) {
            this.dalsi = dalsi;
            this.data = data;
        }

        public Prvek(E data) {
            this.data = data;
        }

    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        aktualni = prvni;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        aktualni = posledni;
    }

    @Override
    public void dalsi() throws KolekceException {
        if (!jeDalsi()) {
            throw new KolekceException("Neni dalsi prvek!");
        }
        aktualni = aktualni.dalsi;
    }

    @Override
    public boolean jeDalsi() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        if (aktualni == null) {
            throw new KolekceException("Nenastaven aktualni prvek!");
        }
        return aktualni != null && aktualni.dalsi != null;
    }

    @Override
    public void vlozPrvni(E data) {
        if (data == null) {
            throw new NullPointerException();
        }

        pocet++;

        if (jePrazdny()) {
            prvni = new Prvek<>(data);
            posledni = prvni;
        } else {
            prvni = new Prvek<>(data, prvni);
        }
    }

    @Override
    public void vlozPosledni(E data) {
        if (data == null) {
            throw new NullPointerException();
        }

        if (jePrazdny()) {
            vlozPrvni(data);
        } else {
            Prvek prvek = new Prvek(data);
            posledni.dalsi = prvek;
            posledni = prvek;
            pocet++;
        }
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if (data == null) {
            throw new NullPointerException();
        }
        if (aktualni == null) {
            throw new KolekceException("Nenastaven aktualni prvek!");
        }

        if (aktualni == posledni) {
            vlozPosledni(data);
        } else {
            Prvek prvek = new Prvek(data);
            prvek.dalsi = aktualni.dalsi;
            aktualni.dalsi = prvek;
            pocet++;
        }
    }

    @Override
    public boolean jePrazdny() {
        return prvni == null;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        return prvni.data;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        return posledni.data;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Nenastaven aktualni prvek!");
        }
        return aktualni.data;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        if (aktualni == null) {
            throw new KolekceException("Nenastaven aktualni prvek!");
        }
        if (jeDalsi()) {
            return aktualni.dalsi.data;
        } else {
            throw new KolekceException("Neni dalsi prvek!");
        }
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }

        if (prvni == posledni) {
            E temp = prvni.data;
            zrus();
            return temp;
        } else {
            E temp = prvni.data;
            if (aktualni == prvni) {
                aktualni = null;
            }
            prvni = prvni.dalsi;
            pocet--;
            return temp;
        }
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        if (prvni == posledni) {
            E temp = prvni.data;
            zrus();
            return temp;
        } else {
            Prvek<E> predposledni = prvni;
            while (predposledni.dalsi != posledni) {
                predposledni = predposledni.dalsi;
            }
            E temp = posledni.data;
            if (aktualni == posledni) {
                aktualni = null;
            }
            posledni = predposledni;
            posledni.dalsi = null;
            pocet--;
            return temp;
        }
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prazdny!");
        }
        if (aktualni == null) {
            throw new KolekceException("Nenastaven aktualni prvek!");
        }

        E temp = aktualni.data;

        if (aktualni == prvni) {
            odeberPrvni();
        } else if (aktualni == posledni) {
            odeberPosledni();
        } else {
            Prvek<E> predchozi = prvni;
            while (predchozi.dalsi != aktualni) {
                predchozi = predchozi.dalsi;
            }
            predchozi.dalsi = aktualni.dalsi;
            if (aktualni == posledni) {
                posledni = predchozi;
            }
        }

        aktualni = null;
        pocet--;

        return temp;
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Nenastaven aktualni prvek!");
        }
        if (aktualni.dalsi == null) {
            throw new KolekceException("Aktualni prvek je posledni!");
        }

        E data = aktualni.dalsi.data;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        pocet--;

        return data;
    }

    @Override
    public int size() {
        return pocet;
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        pocet = 0;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {

            private Prvek<E> iteratorAktualni = prvni;
            private Prvek<E> iteratorPredchozi = null;

            @Override
            public boolean hasNext() {
                return iteratorAktualni != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                iteratorPredchozi = iteratorAktualni;
                E data = iteratorAktualni.data;
                iteratorAktualni = iteratorAktualni.dalsi;
                return data;
            }

            @Override
            public void remove() throws RuntimeException {

                E temp = iteratorPredchozi.data;

                if (iteratorPredchozi == prvni) {
                    try {
                        odeberPrvni();
                    } catch (KolekceException e) {
                        throw new RuntimeException(e);
                    }
                } else if (iteratorPredchozi == posledni) {
                    try {
                        odeberPosledni();
                    } catch (KolekceException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Prvek<E> prepredchozi = prvni;
                    while (prepredchozi.dalsi != iteratorPredchozi) {
                        prepredchozi = prepredchozi.dalsi;
                    }
                    prepredchozi.dalsi = iteratorPredchozi.dalsi;
                }
                pocet--;
            }
        };
    }

}
