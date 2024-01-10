import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Game {

    private int minAliensCount = 10;
    private Screen screen;
    private Spaceship spaceship;
    private List<Alien> aliens;
    private List<Bullet> bullets;
    private boolean movingRight = true; // Começa movendo para a direita
    private int edgePadding = 2; // Espaço que os aliens devem manter da borda da tela
    List<Alien> aliensToRemove = new ArrayList<>();
    private int score = 0;
    private int lives = 3; // Número de vidas do jogador

    private Random random = new Random();

    public Game(Screen screen) {
        this.screen = screen;
        this.aliens = new ArrayList<>();
        this.bullets = new ArrayList<>();
    }

    // Método para mover os aliens
    private void moveAliens() {
        int screenWidth = screen.getTerminalSize().getColumns();

        // Verifica se algum alien atingiu a borda da tela
        boolean reachedEdge = false;
        for (Alien alien : aliens) {
            if ((movingRight && alien.getX() >= screenWidth - edgePadding) ||
                    (!movingRight && alien.getX() <= edgePadding)) {
                reachedEdge = true;
                break;
            }
        }
        // Se algum alien atingiu a borda, muda a direção e move todos os aliens para baixo
        if (reachedEdge) {
            movingRight = !movingRight; // Muda a direção
            for (Alien alien : aliens) {
                alien.moveDown(); // Move os aliens para baixo
            }
        } else {
            // Se não atingiram a borda, move todos os aliens na direção atual
            for (Alien alien : aliens) {
                if (movingRight) {
                    alien.moveRight();
                } else {
                    alien.moveLeft();
                }
            }
        }
    }

    public boolean start() throws IOException {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        int screenWidth = screen.getTerminalSize().getColumns();
        int screenHeight = screen.getTerminalSize().getRows();

        int spaceshipWidth = 4;
        int spaceshipX = (screenWidth - spaceshipWidth) / 2;
        int spaceshipY = screenHeight - 6;
        int alienHeight = 3;

        spaceship = new Spaceship(spaceshipX, spaceshipY);
        spaceship.draw(textGraphics);

        int numAliensPerRow = 12; // Número de aliens por linha
        int numAlienRows = 3; // Número de linhas de aliens
        int alienWidth = 1; // Largura de um alien
        int spaceWidth = 1; // Largura do espaço entre dois aliens

        // A largura total do bloco de aliens é a largura de todos os aliens mais os espaços entre eles
        int totalAlienBlockWidth = (numAliensPerRow * alienWidth) + ((numAliensPerRow - 1) * spaceWidth);

        // A posição X inicial para centralizar o bloco de aliens
        int initialX = (screenWidth - totalAlienBlockWidth) / 2;

        // Desenha os aliens usando 'initialX' para a posição X do primeiro alien
        for (int row = 0; row < numAlienRows; row++) {
            for (int col = 0; col < numAliensPerRow; col++) {
                int x = initialX + col * (alienWidth + spaceWidth);
                int y = (row + 1) * alienHeight;
                Alien alien = new Alien(x, y);
                aliens.add(alien);
                alien.draw(textGraphics);
            }
        }

        while (true) {
            int counter = 0;
            while (true) {
                KeyStroke keyStroke = screen.pollInput();

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Escape) {
                        return false;
                    }

                    if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        spaceship.moveLeft();
                    }

                    if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                        spaceship.moveRight(screenWidth);
                    }

                    if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ') {
                        Bullet bullet = spaceship.shoot();
                        bullets.add(bullet);
                    }
                }

                if (counter % 10 == 0) {
                    moveAliens();
                }

                if (counter % 5 == 0) {
                    List<Bullet> bulletsToRemove = new ArrayList<>();
                    List<Alien> aliensToRemove = new ArrayList<>();

                    for (Bullet bullet : bullets) {
                        bullet.moveUp();

                        for (Alien alien : aliens) {
                            if (bullet.getX() >= alien.getX() && bullet.getX() <= alien.getX() + alienWidth &&
                                    bullet.getY() >= alien.getY() && bullet.getY() <= alien.getY() + alienHeight) {
                                bulletsToRemove.add(bullet);
                                aliensToRemove.add(alien);
                                break;
                            }
                        }
                    }

                    bullets.removeAll(bulletsToRemove);
                    aliens.removeAll(aliensToRemove);
                    for (Alien alien : aliensToRemove) {
                        score += 10;
                    }
                }

                screen.clear();

                spaceship.draw(textGraphics);
                for (Alien alien : aliens) {
                    alien.draw(textGraphics);
                }
                for (Bullet bullet : bullets) {
                    bullet.draw(textGraphics);
                }

                // Verifica o número de aliens restantes e cria novos se for menor que 10
                if (aliens.size() < minAliensCount) {
                    for (int i = 0; i < 5; i++) {
                        int x = random.nextInt(screenWidth - alienWidth);
                        int y = 0;
                        Alien newAlien = new Alien(x, y);
                        aliens.add(newAlien);
                    }
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Exibe informações de vidas e pontuação na tela
                textGraphics.putString(screenWidth - 10, 0, "lives: " + lives);
                textGraphics.putString(0, 0, "points:" + score);

                screen.refresh();

                counter++;
            }
        }
    }
}
