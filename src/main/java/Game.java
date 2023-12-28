import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Game {
    private Screen screen;
    private Spaceship spaceship;
    private List<Alien> aliens;
    private List<Bullet> bullets;

    public Game(Screen screen) {
        this.screen = screen;
        this.aliens = new ArrayList<>();
        this.bullets = new ArrayList<>();
    }

    public boolean start() throws IOException {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics(); 

        // Obtemos o tamanho do terminal
        int screenWidth = screen.getTerminalSize().getColumns();
        int screenHeight = screen.getTerminalSize().getRows();

        // Calcula a posição da nave
        int spaceshipWidth = 4; // largura da nave
        int spaceshipX = (screenWidth - spaceshipWidth) / 2;
        int spaceshipY = screenHeight - 6; // Coloca a nave X linhas acima do fundo da terminal

        spaceship = new Spaceship(spaceshipX, spaceshipY);
        spaceship.draw(textGraphics);

        // Cria uma lista de aliens
        int numAliens = 15;
        int alienWidth = 5; // largura real alien
        int totalAlienWidth = numAliens * alienWidth;
        int totalSpaces = numAliens + 1; // Espaços entre aliens e também nas bordas
        int spaceWidth = (screenWidth - totalAlienWidth) / totalSpaces;
        

        for (int i = 0; i < numAliens; i++) {
            int x = spaceWidth + i * (alienWidth + spaceWidth); // Começa com um espaço e adiciona o índice do alien * (largura do alien + largura do espaço)
            Alien alien = new Alien(x, 0); // todos os aliens começam na linha 0
            aliens.add(alien);
            alien.draw(textGraphics);
        }

        screen.refresh();

        int counter = 0;
        while (true) {
            KeyStroke keyStroke = screen.pollInput();

            // Se o user pressionar ESC, sai do jogo
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    return false;
                }

                // Move a nave para a esquerda se a tecla esquerda for pressionada
                if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                    spaceship.moveLeft();
                }

                // Move a nave para a direita se a tecla direita for pressionada
                if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                    spaceship.moveRight(screenWidth);
                }

                // Dispara um tiro se a tecla espaço for pressionada
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ') {
                    Bullet bullet = spaceship.shoot();
                    bullets.add(bullet);
                }
            }

            // Move cada tiro para cima a cada 5 ciclos do loop
            if (counter % 5 == 0) {
                List<Bullet> bulletsToRemove = new ArrayList<>();
                List<Alien> aliensToRemove = new ArrayList<>();

                for (Bullet bullet : bullets) {
                    bullet.moveUp();

                    // Verifica se o tiro atingiu algum alien
                    for (Alien alien : aliens) {
                        if (bullet.getX() == alien.getX() && bullet.getY() == alien.getY()) {
                            // Se um tiro atingiu um alien, adiciona o tiro e o alien às listas de remoção
                            bulletsToRemove.add(bullet);
                            aliensToRemove.add(alien);
                            break;
                        }
                    }
                }

                // Remove os tiros e aliens que foram atingidos
                bullets.removeAll(bulletsToRemove);
                aliens.removeAll(aliensToRemove);
            }

            // Redesenhe o estado do jogo
            screen.clear();
            spaceship.draw(textGraphics);
            for (Alien alien : aliens) {
                alien.draw(textGraphics);
            }
            for (Bullet bullet : bullets) {
                bullet.draw(textGraphics);
            }
            screen.refresh();

            counter++;
        }
    }
}