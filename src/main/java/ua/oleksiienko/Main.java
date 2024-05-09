package ua.oleksiienko;

import com.github.javafaker.Faker;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ERR_INPUT_MISMATCH = "Found invalid value in input - expected integer 0 < x <= 1000.\nPlease try again:";

    public static void main(String[] args) {
        int capacity = extractFromSTDIN("the storage capacity", true);
        int totalItems = extractFromSTDIN("the total number of items", true);
        int consumers = extractFromSTDIN("the number of consumers", true);
        int producers = extractFromSTDIN("the number of producers", true);
        boolean fair = extractFromSTDIN("fair (1) or unfair (0) simulation", false) != 0;
        Storage storage = new Storage(capacity, 1, fair);
        Faker faker = new Faker();
        int leftToDistribute = totalItems;
        for (int i = 0; i < consumers; i++) {
            int items = faker.random().nextInt(1, leftToDistribute - consumers + i + 2);
            if (i == consumers - 1) {
                items = leftToDistribute;
            }
            leftToDistribute -= items;
            new Thread(new Consumer(items, faker.funnyName().name(), storage)).start();
        }
        leftToDistribute = totalItems;
        for (int i = 0; i < producers; i++) {
            int items = faker.random().nextInt(1, leftToDistribute - producers + i + 2);
            if (i == producers - 1) {
                items = leftToDistribute;
            }
            leftToDistribute -= items;
            new Thread(new Producer(items, faker.company().name(), storage)).start();
        }
    }

    private static int extractFromSTDIN(String ask, boolean validate) {
        System.out.printf("Please enter %s:\n", ask);
        while (true) {
            try {
                int input = scanner.nextInt();
                if (validate && (input < 1 || input > 1000)) {
                    System.err.println(ERR_INPUT_MISMATCH);
                    continue;
                }
                return input;
            } catch (InputMismatchException e) {
                scanner.next();
                System.err.println(ERR_INPUT_MISMATCH);
            }
        }
    }
}