
import com.example.mygame.AlienBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlienBulletTest {
    private AlienBullet bullet;

    @BeforeEach
    void setUp() {
        // cria uma nova bala de alien na posição (5,5).
        bullet = new AlienBullet(5, 5);
    }
    @Test
    void testInitialPosition() {
        // Teste para verificar se a bala é inicializada com as coordenadas corretas.
        assertEquals(5, bullet.getX());
        assertEquals(5, bullet.getY());
    }

    @Test
    void testMoveDown() {
        // Teste para verificar o movimento da bala para baixo.
        bullet.moveDown();
        assertEquals(5, bullet.getX()); // A posição X não deve mudar.
        assertEquals(6, bullet.getY()); // A posição Y deve incrementar em 1.
    }

    @Test
    void testGetXGetY() {
        // Teste para verificar se os métodos get retornam as coordenadas corretas.
        assertEquals(5, bullet.getX());
        assertEquals(5, bullet.getY());
    }
}
