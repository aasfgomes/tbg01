package com.example.mygame;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class Spaceship {
    private int x, y;

    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y; // Supondo que 'y' seja a posição Y da nave
    }

     public int getX() {
        return x;
    }

    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Cor original

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        String[] spaceship = {
            "Z"
        };

        for (int i = 0; i < spaceship.length; i++) {
            textGraphics.putString(x, y + i, spaceship[i]);
        }

        textGraphics.setForegroundColor(originalColor); // Reset para a cor original ( anteriormente ficava da cor dos tiros)
    }

    private static final int spaceshipWidth = 7;

    public void moveLeft() {
        if (x > 0) { // Verifica se a nave não está na borda esquerda da tela
            x--;
        }
    }

    public void moveRight(int screenWidth) {
        if (x < screenWidth - 1) { // Verifica se a nave não está na borda direita da tela
            x++;
        }
    }

    public Bullet shoot() {
        return new Bullet(this.x, this.y - 1); // A bala é criada acima da nave
    }

}