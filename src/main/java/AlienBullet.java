import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class AlienBullet {
    private int x, y;
    private int moveCounter = 0; // Novo campo para contar as chamadas para moveDown()

    // Construtor com x e y
    public AlienBullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Guardar a cor original

        textGraphics.setForegroundColor(Color.Orange.getColor()); // Colocar cor vermelho 
        textGraphics.putString(x, y, "."); // Desenhar o tiro

        textGraphics.setForegroundColor(originalColor); // Reset para a cor original
    } 

    public void moveDown() {
        moveCounter++;
        if (moveCounter % 2 == 0) { // Mover a bala para baixo a cada duas chamadas para moveDown()
            y += 1;
        }
    }

    // Métodos get para x e y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}