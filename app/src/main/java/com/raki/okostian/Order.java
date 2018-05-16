package com.raki.okostian;

public class Order {
    Lobster lobster;
    float weights;

    public Order(Lobster lobster, float weights) {
        this.lobster = lobster;
        this.weights = weights;
    }

    public Lobster getLobster() {
        return lobster;
    }

    public void setLobster(Lobster lobster) {
        this.lobster = lobster;
    }

    public float getWeights() {
        return weights;
    }

    public void setWeights(float weights) {
        this.weights = weights;
    }
}
