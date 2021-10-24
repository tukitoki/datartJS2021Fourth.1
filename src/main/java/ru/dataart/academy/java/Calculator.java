package ru.dataart.academy.java;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Calculator {
    /**
     * @param zipFilePath -  path to zip archive with text files
     * @param character   - character to find
     * @return - how many times character is in files
     */
    public Integer getNumberOfChar(String zipFilePath, char character) {
        int countOfCharacter = 0;
        try (ZipFile zipFile = new ZipFile(zipFilePath)){
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                String fileContent = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))
                        .lines()
                        .collect(Collectors.joining(" "));
                List<Character> charText = fileContent.chars()
                        .filter(symbol -> symbol != ' ')
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList());
                countOfCharacter += charText.stream().
                        filter(symbol -> symbol == character)
                        .count();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countOfCharacter;
    }

    /**
     * @param zipFilePath - path to zip archive with text files
     * @return - max length
     */

    public Integer getMaxWordLength(String zipFilePath) {
        int maxWordLength = 0;
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                String fileContent = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))
                        .lines()
                        .collect(Collectors.joining(" "));
                List<String> listOfWords = new ArrayList<>(Arrays.asList(fileContent.split(" ")));
                maxWordLength = listOfWords.stream()
                        .max(Comparator.comparing(String::length))
                        .get()
                        .length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxWordLength;
    }
}
