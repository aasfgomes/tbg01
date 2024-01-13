package spaceinvaiders;
import com.googlecode.lanterna.graphics.TextGraphics;

public class BonusPower {
    private int x, y;

    public BonusPower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveDown() {
        y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(TextGraphics textGraphics) {
        textGraphics.putString(x, y, "T");
    }

}
