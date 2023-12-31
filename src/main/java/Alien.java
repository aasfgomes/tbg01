import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Alien {
    private int x, y;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Save the original color

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN); // Set the color to green

        String[] smallAlien = {
            " (o.o) ",
            "   V   "
        };

        for (int i = 0; i < smallAlien.length; i++) {
            textGraphics.putString(x, y + i, smallAlien[i]);
        }

        textGraphics.setForegroundColor(originalColor); // Reset the color to the original color
    }    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public AlienBullet shoot() {
        return new AlienBullet(x + 3, y + 3); // Cria um novo tiro na posição atual do alien
    }
}