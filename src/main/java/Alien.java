import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Alien {
    private int x, y;
    private boolean movingRight;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
        this.movingRight = true; // Defina a direção inicial como direita
    }

    public AlienBullet shoot() {
        return new AlienBullet(x, y + 1); // Create a new AlienBullet at the position of the alien
    }

    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Guarda a cor original

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN); // Colocar cor verde
        textGraphics.putString(x, y, "W"); // Desenha o alien

        textGraphics.setForegroundColor(originalColor); // Reset para a cor original
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft() {
        if (movingRight) {
            // Se estava se movendo para a direita, agora move para a esquerda
            movingRight = false;
        }
        this.x -= 1;
    }

    public void moveRight() {
        if (!movingRight) {
            // Se estava se movendo para a esquerda, agora move para a direita
            movingRight = true;
        }
        this.x += 1;
    }

    public void moveDown() {
        this.y += 1;
    }
}


