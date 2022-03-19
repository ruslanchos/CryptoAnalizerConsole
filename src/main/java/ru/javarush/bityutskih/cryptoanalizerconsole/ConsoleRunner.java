package ru.javarush.bityutskih.cryptoanalizerconsole;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class ConsoleRunner {
    static String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\\\":-!? ";

/*    public static String encoding(String printText, int Key) {
        printText = printText.toLowerCase();
        String  cryptoText = "";
        for (int i = 0; i < printText.length(); i++) {
            int charIndex = ALPHABET.indexOf(printText.charAt(i));
            int newIndex = (charIndex + Key) % 33;
            char cipherChar = ALPHABET.charAt(newIndex);
            cryptoText = cryptoText + cipherChar;
        }
        return cryptoText;

    }*/

    public void encryptFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВДИТЕ ПУТЬ К ФАЙЛУ >>");
        String inputFileName = scanner.nextLine();
        System.out.println("ВВЕДИТЕ КЛЮЧ >>:");
        int key = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ВВЕДИТЕ ПУТЬ К ШИФРОВАННОМУ ФАЙЛУ >>");
        String outputFileName = scanner.nextLine();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName)); BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))) {
            ArrayList<String> data = new ArrayList<>();

            while (bufferedReader.ready()) {
                String string = bufferedReader.readLine();
                char[] chars = string.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int index = ALPHABET.indexOf(Character.toLowerCase(chars[i]));
                    if (index == -1) {
                        continue;
                    }
                    int shift = (index + key) % ALPHABET.length();
                    if (shift < 0) shift = shift + ALPHABET.length();
                    chars[i] = ALPHABET.charAt(shift);
                }
                data.add(new String(chars));
            }

            for (String string : data) {
                bufferedWriter.write(string + "\n");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static String decryptFile(String cryptoText, int Key) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВДИТЕ ПУТЬ К ШИФРОВАННОМУ ФАЙЛУ >>");
        String inputFileName = scanner.nextLine();
        System.out.println("ВВЕДИТЕ КЛЮЧ >>:");
        int key = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ВВЕДИТЕ ПУТЬ ДЛЯ РАСШИФРОВАННОГО ФАЙЛА >>");
        String outputFileName = scanner.nextLine();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName)); BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))) {
            ArrayList<String> data = new ArrayList<>();
            while (bufferedReader.ready()) {
                String string = bufferedReader.readLine();
                char[] chars = string.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int index = ALPHABET.indexOf(Character.toLowerCase(chars[i]));
                    if (index == -1) {
                        continue;
                    }

                    int shift;
                    if (key > 0) {
                        shift = (index - key) % ALPHABET.length();
                    } else shift = (index + key) % ALPHABET.length();
                    if (shift < 0) shift = shift + ALPHABET.length();
                    chars[i] = ALPHABET.charAt(shift);
                }
                data.add(new String(chars));
            }

            for (String string : data) {
                bufferedWriter.write(string + "\n");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static String decryptBrutForce(String cryptoText, int Key) {

    }

    public static String decryptStatAnalyse(String cryptoText, int Key) {

    }


/*    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ВВЕДИТЕ ТЕКСТ >>");
        String text = scanner.nextLine();

        System.out.println("ВВЕДИТЕ КЛЮЧ  >>");
        int Key = scanner.nextInt();*/

    //String cipherText = encoding(text,Key);
    //System.out.println("ЗАШИФРОВАННЫЙ ТЕКСТ:" + cipherText);

    // System.out.println("РАСШИФРОВКА СООБЩЕНИЯ >>" + decoding(cipherText,Key));


    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        String menu = "Программа выполняет следующие функции:\n" +
                "1) Шифровка текста\n" +
                "2) Расшифровка текста с помощью ключа\n" +
                "3) Расшифровка текста с помощью brute force\n" +
                "4) Расшифровка текста с помощью статистического анализа.\n";
        System.out.println(menu);
        System.out.println("ENTER NUMBER:");

        ConsoleRunner cryptoAnalyzer = new ConsoleRunner();

        switch (scanner.nextLine()) {
            case "1" -> cryptoAnalyzer.encryptFile();
            case "2" -> cryptoAnalyzer.decryptFile();
            case "3" -> cryptoAnalyzer.decryptBrutForce();
            case "4" -> cryptoAnalyzer.decryptStatAnalyse();
        }
    }
}
