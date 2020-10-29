package com.yube.game;

import com.yube.common.Utils;

public class MatrixGameSolution {

    private double gamePrice;
    private double modifiedGamePrice;
    private double[] p;
    private double alpha, beta;
    private boolean modified;

    public double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public double getModifiedGamePrice() {
        return modifiedGamePrice;
    }

    public void setModifiedGamePrice(double modifiedGamePrice) {
        this.modifiedGamePrice = modifiedGamePrice;
    }

    public double[] getP() {
        return p;
    }

    public void setP(double[] p) {
        this.p = p;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Нижня ціна гри: %.3f\n", alpha));
        result.append(String.format("Верхня ціна гри: %.3f\n", beta));
        if (modified) result.append(String.format("Ціна гри модифікованої матриці: %.3f\n", modifiedGamePrice));
        result.append(String.format("Ціна гри: %.3f\n", gamePrice));
        result.append(String.format("Розподіл ймовірностей оптимальної змішаної стратегії гравця А:\n%s\n", Utils.arrayToString(p)));
        return result.toString();
    }
}
