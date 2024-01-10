import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class AlienBullet {
    private int x, y;

    // Construtor para inicializar a posição da bala de alien
    public AlienBullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Método para mover a bala para baixo (em direção à nave)
    public void moveDown() {
        y++;
    }

    // Método para desenhar a bala na tela
    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor();
        textGraphics.setForegroundColor(TextColor.ANSI.RED); // Escolha a cor desejada
        textGraphics.putString(x, y, "."); // Caractere representando a bala
        textGraphics.setForegroundColor(originalColor);
    }

    // Métodos get para x e y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
