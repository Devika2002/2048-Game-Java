package com.devika.game2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game2048 extends JPanel implements KeyListener {
    private final Board board;
    private boolean gameOver = false;

    public Game2048(int size) {
        board = new Board(size);
        setFocusable(true);
        addKeyListener(this);
    }

    //
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }



    private void drawBoard(Graphics g) {
        g.setColor(new Color(187, 173, 160));
        g.fillRect(0, 0, 500, 500);

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                int value = board.getTile(i, j).getValue();
                int x = j * 120 + 20;
                int y = i * 120 + 20;

                g.setColor(getTileColor(value));
                g.fillRoundRect(x, y, 100, 100, 15, 15);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                if (value != 0) {
                    g.drawString(String.valueOf(value), x + 35, y + 55);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + board.getScore(), 20, 480);

        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", 150, 250);
        }
    }

    private Color getTileColor(int value) {
        switch (value) {
            case 2: return new Color(238, 228, 218);
            case 4: return new Color(237, 224, 200);
            case 8: return new Color(242, 177, 121);
            case 16: return new Color(245, 149, 99);
            case 32: return new Color(246, 124, 95);
            case 64: return new Color(246, 94, 59);
            case 128: return new Color(237, 207, 114);
            case 256: return new Color(237, 204, 97);
            case 512: return new Color(237, 200, 80);
            case 1024: return new Color(237, 197, 63);
            case 2048: return new Color(237, 194, 46);
            default: return new Color(205, 193, 180);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        boolean moved = false;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> moved = board.moveLeft();
            case KeyEvent.VK_RIGHT -> moved = board.moveRight();
            case KeyEvent.VK_UP -> moved = board.moveUp();
            case KeyEvent.VK_DOWN -> moved = board.moveDown();
            case KeyEvent.VK_R -> {
                board.reset();
                gameOver = false;
                repaint();
                return;
            }
        }

        if (moved) repaint();
        if (!board.canMove()) gameOver = true;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 Game - Devika");
        Game2048 game = new Game2048(4);
        frame.add(game);
        frame.setSize(520, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
