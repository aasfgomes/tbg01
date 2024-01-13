package spaceinvaiders;

import spaceinvaiders.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BulletTest {
    private Bullet bullet;

    @BeforeEach
    void setUp() {
        // cria um novo Bullet na posição (5,5).
        bullet = new Bullet(5, 5);
    }

    @Test
    void testInitialPosition() {
        // Teste para verificar se o Bullet é inicializado com as coordenadas corretas.
        assertEquals(5, bullet.getX());
        assertEquals(5, bullet.getY());
    }

    @Test
    void testShoot() {
        // verificar a funcionalidade do método shoot.
        // Espera-se que um novo Bullet seja criado uma posição acima.
        Bullet newBullet = bullet.shoot();
        assertNotNull(newBullet); // Verifica se a Bullet é criada.
        assertEquals(5, newBullet.getX()); // A posição X deve ser a mesma.
        assertEquals(4, newBullet.getY()); // A posição Y deve ser uma unidade acima.
    }

    @Test
    void testMoveUpAndDown() {
        // Teste para verificar o movimento do Bullet para cima e para baixo.
        bullet.moveUp();
        assertEquals(4, bullet.getY()); // Depois de mover para cima, a posição Y deve diminuir.
        bullet.moveDown();
        assertEquals(5, bullet.getY()); // Depois de mover para baixo, a posição Y deve voltar ao original.
    }

    @Test
    void testGetXGetY() {
        // Teste para verificar se os métodos get retornam as coordenadas corretas.
        assertEquals(5, bullet.getX());
        assertEquals(5, bullet.getY());
    }
}
