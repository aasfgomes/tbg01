import com.googlecode.lanterna.graphics.TextGraphics;

public class Alien {
    private int x, y;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(TextGraphics textGraphics) {
        String[] smallAlien = {
                " (o.o) ",
                "  \\|/  ",
                "   V   "
        };

        for (int i = 0; i < smallAlien.length; i++) {
            textGraphics.putString(x, y + i, smallAlien[i]);
        }
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
