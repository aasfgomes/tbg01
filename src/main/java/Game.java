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
    private List<AlienBullet> alienBullets; // Nova lista para armazenar as balas dos aliens
    private List<Barrier> barriers;

    public Game(Screen screen) {
        this.screen = screen;
        this.aliens = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.alienBullets = new ArrayList<>(); // Inicializa a nova lista
        this.barriers = new ArrayList<>();

        final int spaceshipWidth = 7;
    }

    public boolean start() throws IOException {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        int screenWidth = screen.getTerminalSize().getColumns();
        int screenHeight = screen.getTerminalSize().getRows();

        int spaceshipWidth = 4;
        int spaceshipX = (screenWidth - spaceshipWidth) / 2;
        int spaceshipY = screenHeight - 6;

        spaceship = new Spaceship(spaceshipX, spaceshipY);
        spaceship.draw(textGraphics);

        int numAliens = 11;
        int alienWidth = 7;
        int totalAlienWidth = numAliens * alienWidth;
        int totalSpaces = numAliens + 1;
        int spaceWidth = (screenWidth - totalAlienWidth) / totalSpaces;
        int numAlienRows = 3; // Número de linhas de aliens
        int alienHeight = 3; // Altura do alien
        

        for (int row = 0; row < numAlienRows; row++) {
            for (int i = 0; i < numAliens; i++) {
                int x = spaceWidth + i * (alienWidth + spaceWidth);
                int y = (row + 1) * alienHeight; // Começa na segunda linha e continua para baixo
                Alien alien = new Alien(x, y);
                aliens.add(alien);
                alien.draw(textGraphics);
    }
}

        
        // Adiciona 4 barreiras ao jogo
        int numBarriers = 4; // Número de barreiras
        int barrierWidth = 14; // largura da barreira para 18 caracteres
        int totalBarrierWidth = numBarriers * barrierWidth; // Largura das barreiras
        int totalSpacing = screenWidth - totalBarrierWidth; // Espaço total disponível para espaçamento
        int barrierSpacing = totalSpacing / (numBarriers + 1); // Espaçamento entre as barreiras e as margens
        int barrierY = spaceshipY - 5; // A posição y das barreiras é 5 linhas acima da nave

        int marginSpace = barrierSpacing; // Espaço de margem é igual ao espaçamento entre as barreiras

        // Calcular a posição inicial x de cada barreira
        int barrierXStart = marginSpace; // A posição x inicial é após o primeiro espaçamento

        for (int i = 0; i < numBarriers; i++) {
            int xPosition = barrierXStart + i * (barrierWidth + barrierSpacing);
            barriers.add(new Barrier(xPosition, barrierY));
            
            barriers.get(i).draw(textGraphics);
        }
        
        screen.refresh();

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

            // Dispara os tiros dos aliens 
            if (counter % 400 == 0) {
                for (Alien alien : aliens) {
                    AlienBullet alienBullet = alien.shoot();
                    alienBullets.add(alienBullet);
                }
            }

            // Move as balas dos aliens para baixo
            for (AlienBullet alienBullet : alienBullets) {
                alienBullet.moveDown();

                if (spaceship.isHit(alienBullet)) {
                   
                }
            }


            if (counter % 5 == 0) {
                List<Bullet> bulletsToRemove = new ArrayList<>();
                List<Alien> aliensToRemove = new ArrayList<>();

                    for (Bullet bullet : bullets) {
                        bullet.moveUp();

                        // int alienHeight = 5; // Altura do alien

                        for (Alien alien : aliens) {
                            if (bullet.getX() >= alien.getX() && bullet.getX() <= alien.getX() + alienWidth &&
                                bullet.getY() >= alien.getY() && bullet.getY() <= alien.getY() + alienHeight) {
                                bulletsToRemove.add(bullet);
                                aliensToRemove.add(alien);
                                break;
                            }
                        }

                    // Verifique se qualquer bala atinge as barreiras
                    for (Barrier barrier : barriers) {
                        if (barrier.isHit(bullet)) {
                            bulletsToRemove.add(bullet);
                            break;
                        }
                    }
                }

                bullets.removeAll(bulletsToRemove);
                aliens.removeAll(aliensToRemove);
            }

            screen.clear();
            spaceship.draw(textGraphics);
            
            for (AlienBullet alienBullet : alienBullets) {
               alienBullet.draw(textGraphics);
            }
            for (Alien alien : aliens) {
                alien.draw(textGraphics);
            }
            for (Bullet bullet : bullets) {
                bullet.draw(textGraphics);
            }
            for (Barrier barrier : barriers) {
                barrier.draw(textGraphics);
            }
            
            
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
            screen.refresh();

            counter++;
        }
    }
}
