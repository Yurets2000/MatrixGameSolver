/*
 * Copyright (c) 2020. Yurii Bezliudnyi.
 * Copying without author notice is prohibited.
 *
 */

package com.yube.game;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final String doubleNumberPatternString = "-?\\d+(\\.\\d+)?";
    private final String vectorPatternString = "\\[((" + doubleNumberPatternString + ",\\s+)+" + doubleNumberPatternString + ")?\\]";
    Pattern vectorPattern = Pattern.compile(vectorPatternString);
    Pattern doubleNumberPattern = Pattern.compile(doubleNumberPatternString);

    public static void main(String[] args) {
        Main main = new Main();
        main.process();
    }

    public void process() {
        MatrixGameProblem matrixGameProblem = readMatrixGameProblem();
        MatrixGameSolver matrixGameSolver = new MatrixGameSolver();
        matrixGameSolver.solve(matrixGameProblem);
    }

    private MatrixGameProblem readMatrixGameProblem() {
        int m = Integer.parseInt(read("Введіть кількість стратегій гравця A:", "\\d{1,2}"));
        int n = Integer.parseInt(read("Введіть кількість стратегій гравця B:", "\\d{1,2}"));
        if (m < 1 || n < 1) {
            throw new IllegalArgumentException("Кількість стратегій не може бути меншою за 1");
        }
        double[][] paymentMatrix;
        GenerationType generationType = readGenerationType();
        if (generationType.equals(GenerationType.RANDOM)) {
            paymentMatrix = generateRandomMatrix(m, n, 10);
        } else {
            paymentMatrix = readMatrix("Платіжна матриця", m, n);
        }
        MatrixGameProblem problem = new MatrixGameProblem();
        problem.setM(m);
        problem.setN(n);
        problem.setPaymentMatrix(paymentMatrix);
        return problem;
    }

    private double[][] generateRandomMatrix(int m, int n, int amplitude) {
        double[][] matrix = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 2 * amplitude * Math.random() - amplitude;
            }
        }
        return matrix;
    }

    private String read(String question, String pattern) {
        while (true) {
            System.out.println(question);
            String line = scanner.nextLine().trim();
            if (line.matches(pattern)) return line;
            System.out.println("Неправильно введене значення, спробуйте знову.");
        }
    }

    private GenerationType readGenerationType() {
        String generationTypeString = read("Введіть тип заповнення платіжної матриці (user/random):", "(user|random)");
        if (generationTypeString.equals("user")) {
            return GenerationType.USER;
        } else if (generationTypeString.equals("random")) {
            return GenerationType.RANDOM;
        } else {
            throw new IllegalArgumentException("Неможливо зчитати тип заповнення платіжної матриці");
        }
    }

    private double[][] readMatrix(String label, int columns, int rows) {
        double[][] result = new double[columns][rows];
        System.out.printf("Введіть значення матриці '%s'\n", label);
        for (int i = 0; i < columns; i++) {
            System.out.printf("Введіть значення %d-го рядка матриці:\n", i);
            result[i] = readVector(rows);
        }
        return result;
    }

    private double[] readVector(int size) {
        double[] result = new double[size];
        boolean flag = false;
        String vector = null;
        while (!flag) {
            String line = scanner.nextLine();
            if (!line.trim().isEmpty()) {
                if (vectorPattern.matcher(line).matches()) {
                    vector = line;
                    flag = true;
                } else {
                    System.out.println("Ви неправильно ввели значення вектору, спробуйте знову:");
                    flag = false;
                }
            }
        }

        int i = 0;
        Matcher numberMatcher = doubleNumberPattern.matcher(vector);
        while (numberMatcher.find()) {
            String number = numberMatcher.group();
            result[i++] = Double.parseDouble(number);
        }
        return result;
    }
}
