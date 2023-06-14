package cz.upce.fei.boop.pujcovna.generator;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;

import java.util.Random;

public class ObjectGenerator {

    private final Random random;
    private int pocetID = 0;

    public ObjectGenerator() {
        this.random = new Random();
    }

    public SpojovySeznam<Letadlo> generateObjects(int pocet) {

        pocetID = pocet;
        SpojovySeznam<Letadlo> seznam = new SpojovySeznam<>();
        for(int i = 0; i < pocet; i++) {
            int type = random.nextInt(3);
            switch (type) {
                case 0 -> seznam.vlozPosledni(generateDopravniL("DL" + i));
                case 1 -> seznam.vlozPosledni(generateSoukromeL("SoL" + i));
                case 2 -> seznam.vlozPosledni(generateSportovniL("SpL" + i));
            }
        }
        return seznam;

    }

    private DopravniLetadlo generateDopravniL(String id) {
        int rychlost = random.nextInt(1000) + 1;
        int pocetSedadel = random.nextInt(51) + 1;
        int dolet = random.nextInt(2001) + 1;
        return new DopravniLetadlo(id, rychlost, pocetSedadel, dolet);
    }

    private SoukromeLetadlo generateSoukromeL(String id) {
        int rychlost = random.nextInt(1000) + 1;
        Barva[] barva = Barva.values();
        return new SoukromeLetadlo(id, rychlost, barva[random.nextInt(barva.length)]);
    }

    private SportovniLetadlo generateSportovniL(String id) {
        int rychlost = random.nextInt(1000) + 1;
        String typMotoru = "MS" +  random.nextInt(200) + 1;
        int vzletovaDraha = random.nextInt(500) + 1;
        return new SportovniLetadlo(id, rychlost, typMotoru, vzletovaDraha);
    }

    public String generujID(TypyLetadel typ) {
        switch (typ) {
            case DOPRAVNI_LETADLO -> {
                return "DL" + pocetID++;
            }
            case SOUKROME_LETADLO -> {
                return "SoL" + pocetID++;
            }
            case SPORTOVNI_LETADLO -> {
                return "SpL" + pocetID++;
            }
            default -> {
                return null;
            }
        }
    }

}
