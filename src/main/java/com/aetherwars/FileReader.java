package com.aetherwars;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    // Buat bonus import deck
    public void readFile(File file) {
        try {

            Scanner reader = new Scanner(file);
            int cardCount, idCard;
            cardCount = Integer.parseInt(reader.nextLine());

            for (int i = 0;i < cardCount; i++) {
                idCard = Integer.parseInt(reader.nextLine());
            }


        } catch (FileNotFoundException e) {
            System.out.println("File tidak ada.");
        }
    }

}
