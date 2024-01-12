import com.example.mygame.Spaceship;
import com.example.mygame.Bullet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class SpaceshipTest {
    private Spaceship spaceship;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.initMocks(this);

        // Cria uma nova instância de Spaceship para ser usada em cada teste.
        spaceship = new Spaceship(5, 5);
    }

    // Testa se o método getX() da Spaceship retorna o valor correto.
    @Test
    public void testGetX() {

        assertEquals(5, spaceship.getX());
    }

    // Testa se o método getY() da Spaceship retorna o valor correto.
    @Test
    public void testGetY() {

        assertEquals(5, spaceship.getY());
    }

    // Testa se o método moveLeft() atualiza corretamente a posição X da Spaceship.
    @Test
    public void testMoveLeft() {
        spaceship.moveLeft();
        assertEquals(4, spaceship.getX());
    }

    // Testa se o método moveRight() atualiza corretamente a posição X da Spaceship, considerando a largura da tela.
    @Test
    public void testMoveRight() {
        int screenWidth = 10;
        spaceship.moveRight(screenWidth);
        assertEquals(6, spaceship.getX());
    }

    // Testa se o método shoot() da Spaceship cria um objeto Bullet na posição correta.
    @Test
    public void testShoot() {
        Bullet bullet = spaceship.shoot();
        assertEquals(5, bullet.getX());
        assertEquals(4, bullet.getY());
    }

}
