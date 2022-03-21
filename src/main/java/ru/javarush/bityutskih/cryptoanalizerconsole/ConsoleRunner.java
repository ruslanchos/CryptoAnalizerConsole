package ru.javarush.bityutskih.cryptoanalizerconsole;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class ConsoleRunner {
    static String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\\\":-!? ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String menu = "Программа выполняет следующие функции:\n" +
                "1) Шифровка текста\n" +
                "2) Расшифровка текста с помощью ключа\n" +
                "3) Расшифровка текста с помощью brute force\n" +
                "4) Расшифровка текста с помощью статистического анализа.\n";

        System.out.println(menu);
        System.out.println("ENTER NUMBER:");

        ConsoleRunner consoleRunner = new ConsoleRunner();

        switch (scanner.nextLine()) {
            case "1" -> consoleRunner.encryptFile();
            case "2" -> consoleRunner.decryptFile();
            case "3" -> consoleRunner.decryptBrutForce();
            case "4" -> consoleRunner.decryptStatAnalyse();

        }
    }

    public void encryptFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВДИТЕ ПУТЬ К ФАЙЛУ >>");
        String inputFileName = scanner.nextLine();
        System.out.println("ВВЕДИТЕ КЛЮЧ >>:");
        int key = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ВВЕДИТЕ ПУТЬ К ШИФРОВАННОМУ ФАЙЛУ >>");
        String outputFileName = scanner.nextLine();

        //try (BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\text.txt"))) {
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
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    private void decryptFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВДИТЕ ПУТЬ К ШИФРОВАННОМУ ФАЙЛУ >>");
        String inputFileName = scanner.nextLine();
        System.out.println("ВВЕДИТЕ КЛЮЧ >>:");
        int key = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ВВЕДИТЕ ПУТЬ ДЛЯ РАСШИФРОВАННОГО ФАЙЛА >>");
        String outputFileName = scanner.nextLine();
        //try (BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\decrypt.txt"))) {
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
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public void decryptBrutForce() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВДИТЕ ПУТЬ К ШИФРОВАННОМУ ФАЙЛУ >>");
        String inputFileName = scanner.nextLine();
        System.out.println("ВВВЕДИТЕ ПУТЬ ДЛЯ РАСШИФРОВАННОГО BRUT-FORCE ФАЙЛА >>");
        String outputFileName = scanner.nextLine();

        //try (BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\encrypt.txt"))) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName)); BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))) {
            ArrayList<String> inputData = new ArrayList<>();
            ArrayList<String> outputData = new ArrayList<>();

            while (bufferedReader.ready()) {
                inputData.add(bufferedReader.readLine());
            }

            for (int key = 1; key < ALPHABET.length(); key++) {
                for (String string : inputData) {
                    char[] chars = string.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        int index = ALPHABET.indexOf(Character.toLowerCase(chars[i]));
                        if (index == -1) {
                            continue;
                        }

                        int shift = (index - key) % ALPHABET.length();
                        if (shift < 0) shift = shift + ALPHABET.length();
                        chars[i] = ALPHABET.charAt(shift);
                    }
                    outputData.add(new String(chars));
                }

                boolean isCorrectLength = true;
                boolean isCorrectPunt = true;
                int notCorrectPunch = 0;
                int countWords = 0;

                for (String string : outputData) {
                    if (string.matches("(.*)[a-zA-Z](.*)")) {
                        //if (string.matches("(.*)[а-яА-Я](.*)")) {  не работает
                        continue;
                    }

                    String[] stringsLength = string.split(" ");
                    for (String s : stringsLength) {
                        if (s.length() > 25) {
                            isCorrectLength = false;
                            break;
                        }
                    }

                    String[] stringsPunt = string.split("[?!.]");
                    for (String s : stringsPunt) {
                        if (stringsPunt.length == 1 | s.length() == 1 | s.isEmpty()) {
                            break;
                        }
                        if (!s.startsWith(" ")) {
                            notCorrectPunch++;
                        }
                    }
                }

                for (String string : outputData) {
                    String[] words = string.split(" ");
                    countWords += words.length;
                }

                isCorrectPunt = notCorrectPunch <= countWords / 10;
                //isCorrectPunt = notCorrectPunch > countWords / 10 ? false : true;

                if (isCorrectLength & isCorrectPunt) {
                    System.out.println("ПОДОБРАННЫЙ КЛЮЧ >>" + key);
                    break;
                }
                outputData.clear();
            }

            for (String string : outputData) {
                bufferedWriter.write(string + "\n");
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public void  decryptStatAnalyse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ВВДИТЕ ПУТЬ К ШИФРОВАННОМУ ФАЙЛУ >>");
        String inputFileName = scanner.nextLine();
        System.out.println("ВВДИТЕ ПУТЬ К ФАЙЛУ СТАТИСТИКИ >>");
        String inputStatFileName = scanner.nextLine();
        System.out.println("ВВВЕДИТЕ ПУТЬ ДЛЯ РАСШИФРОВАННОГО ФАЙЛА СТАТИСТИЧЕСКИМ АНАЛИЗОМ >>");
        String outputFileName = scanner.nextLine();

        //try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\text2.txt"))) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName))){

            List<String> inputData = Files.readAllLines(Path.of(inputFileName));
            List<String> statData = Files.readAllLines(Path.of(inputStatFileName));
            ArrayList<String> outputData = new ArrayList<>();

            HashMap<Character, Integer> inputChars = new HashMap<>();
            HashMap<Character, Integer> statChars = new HashMap<>();

            for (String string : inputData) {
                char[] chars = string.toLowerCase().toCharArray();
                for (char ch : chars) {
                    if (inputChars.get(ch) == null) {
                        inputChars.put(ch, 1);
                    } else inputChars.put(ch, inputChars.get(ch) + 1);
                }
            }

            for (String string : statData) {
                char[] chars = string.toLowerCase().toCharArray();
                for (char ch : chars) {
                    if (statChars.get(ch) == null) {
                        statChars.put(ch, 1);
                    } else statChars.put(ch, statChars.get(ch) + 1);
                }
            }

            ArrayList<Map.Entry<Character, Integer>> inputList = new ArrayList(inputChars.entrySet());
            inputList.sort(new Comparator<Map.Entry<Character, Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                    return (int) (o2.getValue() - o1.getValue());
                }
            });
            System.out.println(inputList);

            ArrayList<Map.Entry<Character, Integer>> statList = new ArrayList<>(statChars.entrySet());
            statList.sort(new Comparator<Map.Entry<Character, Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                    return (int) (o2.getValue() - o1.getValue());
                }
            });
            System.out.println("ДАННЫЕ СТАТИСТИЧЕСКОГО АНАЛИЗА" + statList);

            HashMap<Character, Character> totalMap = new HashMap<>();

            for (int i = 0; i < inputList.size(); i++) {
                totalMap.put(inputList.get(i).getKey(), statList.get(i).getKey());
            }

            for (String string : inputData) {
                char[] chars = string.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (totalMap.containsKey(chars[i])) {
                        chars[i] = totalMap.get(chars[i]);
                    }
                }
                outputData.add(new String(chars));
            }

            for (String string : outputData) {
                writer.write(string + "\n");
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}





