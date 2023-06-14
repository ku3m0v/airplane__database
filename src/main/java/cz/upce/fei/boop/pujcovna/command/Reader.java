package cz.upce.fei.boop.pujcovna.command;

import java.util.Scanner;

public class Reader {

    private final Scanner sc;

    public Reader() {
        sc = new Scanner(System.in);
    }

    public String readString(String prompt, String name) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public int readInt(String prompt, String name) {
        System.out.print(prompt);
        int temp;
        try {
            temp = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hodnota " + name + " musi byt celym cislem!");
        }
        return temp;
    }

}