package com.example.mygame;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class Bullet {
    private int x, y;
    private int width;

    // Contrutor com x e y
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Bullet shoot() {
        int bulletX = this.x; // A posição X do tiro é a mesma da nave
        int bulletY = this.y - 1; // A posição Y do tiro é um acima da nave
        return new Bullet(bulletX, bulletY);
    }
    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Guardar a cor original

        textGraphics.setForegroundColor(TextColor.ANSI.RED); // Escolha a cor desejada
        textGraphics.putString(x, y, "."); // Desenhar o tiro

        textGraphics.setForegroundColor(originalColor); // Reset par a cor original
    }
    public void moveUp() {
        y--;
    }
    // Métodos get para x e y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void moveDown() {
    y++;
}
    
}
