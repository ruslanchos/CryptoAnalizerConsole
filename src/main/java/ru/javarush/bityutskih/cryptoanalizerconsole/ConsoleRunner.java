package ru.javarush.bityutskih.cryptoanalizerconsole;

import java.util.List;
import java.util.Locale;

public class ConsoleRunner {
    static String ALPHABET = "абвгдежзиклмнопрстуфхцчшщъыьэя";

    public static String encoding(String printText, int Key) {
        printText = printText.toUpperCase();
        StringBuilder cryptoText = new StringBuilder();
        for (int i = 0; i < printText.length(); i++) {
            int charIndex = ALPHABET.indexOf(printText.charAt(i));
            int newIndex = (charIndex + Key) % 30;
            char cipherChar = ALPHABET.charAt(newIndex);
            cryptoText.insert(0, cipherChar);
        }
        return cryptoText.toString();

    }
}
