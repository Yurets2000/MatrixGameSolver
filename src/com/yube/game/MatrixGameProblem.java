package com.yube.game;

import com.yube.common.Utils;

public class MatrixGameProblem {

    private int m, n;
    private double[][] paymentMatrix;

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[][] getPaymentMatrix() {
        return paymentMatrix;
    }

    public void setPaymentMatrix(double[][] paymentMatrix) {
        this.paymentMatrix = paymentMatrix;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Кількість стратегій гравця A: %d\n", m));
        result.append(String.format("Кількість стратегій гравця B: %d\n", n));
        result.append(String.format("Платіжна матриця: \n%s\n", Utils.matrixToString(paymentMatrix)));
        return result.toString();
    }
}
