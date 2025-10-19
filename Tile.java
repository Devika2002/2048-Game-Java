package com.devika.game2048;

public class Tile {
    private int value;

    public Tile() {
        this.value = 0;
    }

//

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == 0;
    }
}
