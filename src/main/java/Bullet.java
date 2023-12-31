import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class Bullet {
    private int x, y;
    private int width;

    // COntrutor com x e y 
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Bullet shoot() {
        int bulletX = this.x + this.width / 2; // Centra o tiro na nave (não está a funcionar)
        int bulletY = this.y;
        return new Bullet(bulletX, bulletY);
    }   

    public void draw(TextGraphics textGraphics) {
        TextColor originalColor = textGraphics.getForegroundColor(); // Guardar a cor original

        textGraphics.setForegroundColor(Color.Orange.getColor()); // Colocar cor vermelho 
        textGraphics.putString(x, y, "|"); // Desenhar o tiro

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
