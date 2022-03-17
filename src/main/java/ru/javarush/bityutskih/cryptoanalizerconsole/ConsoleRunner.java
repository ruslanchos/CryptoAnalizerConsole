package ru.javarush.bityutskih.cryptoanalizerconsole;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleRunner {
    static String ALPHABET = "абвгдежзиклмнопрстуфхцчшщъыьэя";

    public static String encoding(String printText, int Key) {
        printText = printText.toLowerCase();
        String  cryptoText = "";
        for (int i = 0; i < printText.length(); i++) {
            int charIndex = ALPHABET.indexOf(printText.charAt(i));
            int newIndex = (charIndex + Key) % 30;
            char cipherChar = ALPHABET.charAt(newIndex);
            cryptoText = cryptoText + cipherChar;
        }
        return cryptoText;

    }
    public static String decoding(String cryptoText, int Key){
        cryptoText = cryptoText.toLowerCase();
        String printText = "";
        for (int i = 0; i <cryptoText.length() ; i++) {
            int charIndex = ALPHABET.indexOf(cryptoText.charAt(i));
            int newIndex = (charIndex - Key) % 30;
            if (newIndex < 0) {
                newIndex = ALPHABET.length() + newIndex;
            }
            char plainChar = ALPHABET.charAt(newIndex);
            printText = printText + plainChar;
        }
        return printText;

        }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ВВЕДИТЕ ТЕКСТ >>");
        String text = scanner.nextLine();

        System.out.println("ВВЕДИТЕ КЛЮЧ  >>");
        int Key = scanner.nextInt();

        String cipherText = encoding(text,Key);
        System.out.println("ЗАШИФРОВАННЫЙ ТЕКСТ:" + cipherText);

        System.out.println("РАСШИФРОВКА СООБЩЕНИЯ >>" + decoding(cipherText,Key));

    }

}
