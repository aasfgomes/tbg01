import com.example.mygame.BonusPower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BonusPowerTest {
    private BonusPower bonusPower;

    @BeforeEach
    void setUp() {
        // cria um novo BonusPower na posição (5,5).
        bonusPower = new BonusPower(5, 5);
    }

    @Test
    void testInitialPosition() {
        // Teste para verificar se o BonusPower é inicializado com as coordenadas corretas.
        assertEquals(5, bonusPower.getX());
        assertEquals(5, bonusPower.getY());
    }

    @Test
    void testMoveDown() {
        // Teste para verificar o movimento do BonusPower para baixo.
        bonusPower.moveDown();
        assertEquals(5, bonusPower.getX()); // A posição X não deve mudar.
        assertEquals(6, bonusPower.getY()); // A posição Y deve incrementar em 1.
    }

    @Test
    void testGetXGetY() {
        // Teste para verificar se os métodos get retornam as coordenadas corretas.
        assertEquals(5, bonusPower.getX());
        assertEquals(5, bonusPower.getY());
    }
}
