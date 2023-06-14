package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class TextovaPerzistence {

    public static <E extends Letadlo> void ulozDoSouboru(String jmenoSouboru, SpojovySeznam<E> seznam) {
        if (seznam == null) {
            throw new IllegalArgumentException("Seznam nemuze byt NULL");
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jmenoSouboru), StandardCharsets.UTF_8))) {
            List<String> radky = seznam.stream()
                    .map(prvek -> {
                        TypyLetadel typ = prvek.getTyp();
                        switch (typ) {
                            case DOPRAVNI_LETADLO -> {
                                DopravniLetadlo dopravniLetadlo = (DopravniLetadlo) prvek;
                                return dopravniLetadlo.getId() + "; " + dopravniLetadlo.getTyp() + "; " + dopravniLetadlo.getRychlost() + "; " +
                                        dopravniLetadlo.getPocetSedadel() + "; " + dopravniLetadlo.getDolet();
                            }
                            case SOUKROME_LETADLO -> {
                                SoukromeLetadlo soukromeLetadlo = (SoukromeLetadlo) prvek;
                                return soukromeLetadlo.getId() + "; " + soukromeLetadlo.getTyp() + "; " + soukromeLetadlo.getRychlost() + "; " +
                                        soukromeLetadlo.getBarva().toString();
                            }
                            case SPORTOVNI_LETADLO -> {
                                SportovniLetadlo sportovniLetadlo = (SportovniLetadlo) prvek;
                                return sportovniLetadlo.getId() + "; " + sportovniLetadlo.getTyp() + "; " + sportovniLetadlo.getRychlost() + "; " +
                                        sportovniLetadlo.getTypMotoru() + "; " + sportovniLetadlo.getVzletovaDraha();
                            }
                            default -> throw new IllegalArgumentException("Nespravny typ Letadlo: " + typ);
                        }
                    })
                    .collect(Collectors.toList());

            writer.write(String.join(System.lineSeparator(), radky));
        } catch (IOException e) {
            throw new RuntimeException("Nepodarilo se ulozit do souboru: " + jmenoSouboru, e);
        }
    }

    public static SpojovySeznam<Letadlo> nactiZeSouboru(String jmenoSouboru) {
        SpojovySeznam<Letadlo> seznam = new SpojovySeznam<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(jmenoSouboru), StandardCharsets.UTF_8))) {
            List<String> radky = reader.lines().toList();
            for (String radek : radky) {
                String[] pole = radek.split("; ");

                String id = pole[0];
                TypyLetadel typ = TypyLetadel.fromString(pole[1]);
                int rychlost = Integer.parseInt(pole[2]);

                Letadlo letadlo;
                switch (typ) {
                    case DOPRAVNI_LETADLO -> {
                        int pocetSedadel = Integer.parseInt(pole[3]);
                        int dolet = Integer.parseInt(pole[4]);
                        letadlo = new DopravniLetadlo(id, rychlost, pocetSedadel, dolet);
                    }
                    case SOUKROME_LETADLO -> {
                        Barva barva = Barva.fromString(pole[3]);
                        letadlo = new SoukromeLetadlo(id, rychlost, barva);
                    }
                    case SPORTOVNI_LETADLO -> {
                        String typMotoru = pole[3];
                        int vzletovaDraha = Integer.parseInt(pole[4]);
                        letadlo = new SportovniLetadlo(id, rychlost, typMotoru, vzletovaDraha);
                    }
                    default -> throw new IllegalArgumentException("Nespravny typ Letadlo: " + typ);
                }
                seznam.vlozPosledni(letadlo);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Nepodařilo se načíst seznam ze souboru " + jmenoSouboru);
        }
        return seznam;
    }
}
