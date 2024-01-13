package spaceinvaiders;

import spaceinvaiders.Alien;
import spaceinvaiders.AlienBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlienTest {
    private Alien alien;

    @BeforeEach
    void setUp() {
        // cria um novo Alien na posição (5,5).
        alien = new Alien(5, 5);
    }

    @Test
    void testInitialPosition() {
        // Teste para verificar se o Alien é inicializado com as coordenadas corretas.
        // as coordenadas X e Y sejam as mesmas definidas no construtor.
        assertEquals(5, alien.getX());
        assertEquals(5, alien.getY());
    }

    @Test
    void testShoot() {
        // Teste para verificar a funcionalidade de disparo do Alien.
        // Ao disparar, deve ser criado um AlienBullet na posição correta.
        AlienBullet bullet = alien.shoot();
        assertNotNull(bullet); // Verifica se o bullet realmente foi criado.
        assertEquals(5, bullet.getX()); // A posição X do bullet deve ser igual à do Alien.
        assertEquals(6, bullet.getY()); // A posição Y do bullet deve ser uma unidade abaixo do Alien.
    }

    @Test
    void testMoveLeftAndRight() {
        // Teste para verificar o movimento do Alien para a direita e esquerda.
        alien.moveRight();
        assertEquals(6, alien.getX()); // Depois de mover para a direita, a posição X deve incrementar.
        alien.moveLeft();
        assertEquals(5, alien.getX()); // Depois de mover para a esquerda, a posição X deve voltar ao original.
    }

    @Test
    void testMoveDown() {
        // Teste para verificar o movimento do Alien para baixo.
        alien.moveDown();
        assertEquals(6, alien.getY()); // Depois de mover para baixo, a posição Y deve incrementar.
    }
}
