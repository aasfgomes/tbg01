import com.googlecode.lanterna.graphics.TextGraphics;

public class Barrier {
    private int x;
    private int y;
    private String[] design = {
        "--------------",
        "|            |",
        "|            |",
        
    };

    public Barrier(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(TextGraphics textGraphics) {
        for (int i = 0; i < design.length; i++) {
            for (int j = 0; j < design[i].length(); j++) {
                if (design[i].charAt(j) != ' ') {
                    textGraphics.putString(x + j, y + i, String.valueOf(design[i].charAt(j)));
                }
            }
        }
    }

    public boolean isHit(Bullet bullet) {
        // Verifica se a bala atingiu a barreira
        return bullet.getX() >= x && bullet.getX() < x + design[0].length() && bullet.getY() >= y && bullet.getY() < y + design.length;
    }
}
