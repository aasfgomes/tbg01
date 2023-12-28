import com.googlecode.lanterna.graphics.TextGraphics;

public class Spaceship {
    private int x, y;

    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(TextGraphics textGraphics) {
        String[] spaceship = {
            "  /\\  ",
            " (  ) ",
            " (  ) ",
            "/|/\\|\\",
            "||||||"
        };

        for (int i = 0; i < spaceship.length; i++) {
            textGraphics.putString(x, y + i, spaceship[i]);
        }
    }

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
        return new Bullet(x, y - 1); // Cria um novo tiro na posição atual da nave (não funciona)
    }
}