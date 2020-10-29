package com.yube.game;

import com.yube.common.Utils;
import com.yube.simplex.*;

import java.util.Arrays;

public class MatrixGameSolver {

    public MatrixGameSolution solve(MatrixGameProblem problem) {
        System.out.println(Utils.getEnvelope("Задача матричної гри", problem.toString()));
        double[][] paymentMatrix = problem.getPaymentMatrix();
        double alpha = findBottomPrice(paymentMatrix);
        double beta = findUpperPrice(paymentMatrix);
        if (alpha > beta) {
            System.out.println("В задачі наявні сідлові точки");
        } else {
            System.out.println("В задачі немає сідлових точок");
        }
        double min = Utils.min(paymentMatrix);
        if (min < 0) {
            System.out.println("В платіжній матриці наявні від'ємні елементи, виконуємо модифікацію платіжної матриці");
            paymentMatrix = Utils.add(paymentMatrix, -min);
            System.out.printf("Модифікована платіжна матриця: \n%s\n", Utils.matrixToString(paymentMatrix));
        }
        SimplexProblem simplexProblem = constructSimplexProblem(paymentMatrix);
        SimplexProblemSolver simplexProblemSolver = new SimplexProblemSolver();
        SimplexProblemSolution simplexProblemSolution = simplexProblemSolver.solveSimplexProblem(simplexProblem);
        double modifiedGamePrice = 1 / simplexProblemSolution.getValue();
        double gamePrice = modifiedGamePrice + min;
        double[] p = Utils.multiplyArray(simplexProblemSolution.getX(), modifiedGamePrice);
        MatrixGameSolution matrixGameSolution = new MatrixGameSolution();
        matrixGameSolution.setModifiedGamePrice(modifiedGamePrice);
        matrixGameSolution.setModified(min < 0);
        matrixGameSolution.setGamePrice(gamePrice);
        matrixGameSolution.setAlpha(alpha);
        matrixGameSolution.setBeta(beta);
        matrixGameSolution.setP(p);
        System.out.println(Utils.getEnvelope("Розв'язок матричної гри", matrixGameSolution.toString()));
        return matrixGameSolution;
    }

    private SimplexProblem constructSimplexProblem(double[][] paymentMatrix) {
        int m = paymentMatrix.length;
        int n = paymentMatrix[0].length;
        double[][] transposedPaymentMatrix = Utils.transposeMatrix(paymentMatrix);
        double[] function = new double[m];
        Arrays.fill(function, 1.0);
        Equation[] equations = new Equation[n];
        for (int i = 0; i < n; i++) {
            Equation equation = new Equation();
            equation.setCoefficients(transposedPaymentMatrix[i]);
            equation.setSymbol(Symbol.GREATER);
            equation.setBound(1.0);
            equations[i] = equation;
        }
        SimplexProblem problem = new SimplexProblem();
        problem.setEquationsCount(n);
        problem.setVariablesCount(m);
        problem.setEquationSystem(equations);
        problem.setFunction(function);
        problem.setType(SimplexProblemType.MIN);
        return problem;
    }

    private double findBottomPrice(double[][] paymentMatrix) {
        int m = paymentMatrix.length;
        double[] minArr = new double[m];
        for (int i = 0; i < m; i++) {
            minArr[i] = Utils.min(paymentMatrix[i]);
        }
        return Utils.max(minArr);
    }

    private double findUpperPrice(double[][] paymentMatrix) {
        int m = paymentMatrix.length;
        int n = paymentMatrix[0].length;
        double[] maxArr = new double[n];
        for (int i = 0; i < n; i++) {
            double[] column = new double[m];
            for (int j = 0; j < m; j++) {
                column[j] = paymentMatrix[j][i];
            }
            maxArr[i] = Utils.max(column);
        }
        return Utils.min(maxArr);
    }
}
