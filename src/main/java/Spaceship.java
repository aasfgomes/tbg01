import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class Spaceship {
    private int x, y;

    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
    }
    

    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Cor original

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        String[] spaceship = {
            "   .  ",
            "  / \\  ",
            " (   ) ",
            " (   ) ",
            "/|/|\\|\\",
            "|||||||"
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
        return new Bullet(x + (spaceshipWidth/2), y - 1); // Cria um novo tiro na posição atual da nave ( corrigido)
    }

    public boolean isHit(AlienBullet alienBullet) {
        // Verifica se a bala de alienígena atingiu a nave
        return this.x == alienBullet.getX() && this.y == alienBullet.getY();
    }
}