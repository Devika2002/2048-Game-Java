package com.devika.game2048;

import java.util.*;

public class Board {
    private final int size;
    private final Tile[][] tiles;
    private int score;

//


    public Board(int size) {
        this.size = size;
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile();
            }
        }
        addRandomTile();
        addRandomTile();
    }

    public int getSize() {
        return size;
    }

    public int getScore() {
        return score;
    }

    public Tile getTile(int r, int c) {
        return tiles[r][c];
    }

    public void reset() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                tiles[i][j].setValue(0);
        score = 0;
        addRandomTile();
        addRandomTile();
    }

    public void addRandomTile() {
        List<int[]> emptyTiles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].isEmpty()) {
                    emptyTiles.add(new int[]{i, j});
                }
            }
        }
        if (!emptyTiles.isEmpty()) {
            int[] pos = emptyTiles.get(new Random().nextInt(emptyTiles.size()));
            tiles[pos[0]][pos[1]].setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }

    private boolean slideLeft() {
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            int[] row = new int[size];
            int index = 0;
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].getValue() != 0) {
                    row[index++] = tiles[i][j].getValue();
                }
            }

            for (int j = 0; j < size - 1; j++) {
                if (row[j] != 0 && row[j] == row[j + 1]) {
                    row[j] *= 2;
                    score += row[j];
                    row[j + 1] = 0;
                }
            }

            int[] newRow = new int[size];
            index = 0;
            for (int val : row) {
                if (val != 0) newRow[index++] = val;
            }

            for (int j = 0; j < size; j++) {
                if (tiles[i][j].getValue() != newRow[j]) moved = true;
                tiles[i][j].setValue(newRow[j]);
            }
        }
        return moved;
    }

    private void rotateClockwise() {
        int[][] newBoard = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                newBoard[j][size - 1 - i] = tiles[i][j].getValue();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                tiles[i][j].setValue(newBoard[i][j]);
    }

    public boolean moveLeft() {
        boolean moved = slideLeft();
        if (moved) addRandomTile();
        return moved;
    }

    public boolean moveRight() {
        rotateClockwise();
        rotateClockwise();
        boolean moved = slideLeft();
        rotateClockwise();
        rotateClockwise();
        if (moved) addRandomTile();
        return moved;
    }

    public boolean moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        boolean moved = slideLeft();
        rotateClockwise();
        if (moved) addRandomTile();
        return moved;
    }

    public boolean moveDown() {
        rotateClockwise();
        boolean moved = slideLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        if (moved) addRandomTile();
        return moved;
    }

    public boolean canMove() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tiles[i][j].isEmpty())
                    return true;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size - 1; j++)
                if (tiles[i][j].getValue() == tiles[i][j + 1].getValue())
                    return true;

        for (int j = 0; j < size; j++)
            for (int i = 0; i < size - 1; i++)
                if (tiles[i][j].getValue() == tiles[i + 1][j].getValue())
                    return true;

        return false;
    }
}
