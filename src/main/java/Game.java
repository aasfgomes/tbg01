import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final int INITIAL_SHOOT_INTERVAL = 400; // Intervalo inicial entre os tiros dos aliens
    private int shootInterval = INITIAL_SHOOT_INTERVAL; // Intervalo atual entre os tiros dos aliens
    List<Alien> aliensToRemove = new ArrayList<>();
    private int score = 0;
    private int lives = 3; // Número de vidas do jogador
    
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
        int numBarriers = 5; // Número de barreiras
        int barrierWidth = 8; // largura da barreira 
        int totalBarrierWidth = numBarriers * barrierWidth; // Largura das barreiras
        int totalSpacing = screenWidth - totalBarrierWidth; // Espaço total disponível para espaçamento
        int barrierSpacing = totalSpacing / (numBarriers + 1); // Espaçamento entre as barreiras e as margens
        int barrierY = spaceshipY - 4; // A posição y das barreiras é 5 linhas acima da nave

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
            if (counter % shootInterval == 0 && !aliens.isEmpty()) {
                Random random = new Random();
                Alien shootingAlien = aliens.get(random.nextInt(aliens.size()));
                AlienBullet alienBullet = shootingAlien.shoot();
                alienBullets.add(alienBullet);

                // Diminui o intervalo entre os tiros dos aliens à medida que mais aliens são mortos
                shootInterval = INITIAL_SHOOT_INTERVAL - aliens.size() * 10; // Isso foi mudado de aliens.size()
                if (shootInterval < 50) { // Garante que o intervalo nunca seja menor que 50
                    shootInterval = 50;
                }
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

                    // Verifica se qualquer bala atinge as barreiras
                    for (Barrier barrier : barriers) {
                        if (barrier.isHit(bullet)) {
                            bulletsToRemove.add(bullet);
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
            
            // Move os aliens para cima se não houver nenhum alien acima deles
            List<Alien> newAliens = new ArrayList<>();
            for (Alien alien : aliens) {
                if (!aliensToRemove.contains(alien)) {
                    newAliens.add(alien);
                }
            }
            aliens = newAliens;
            int spaceshipHeight = 30;

            // Indica a linha que contém o espaço vazio ( não funciona, tentar corrigir)
            int emptyRow = -1;
            for (int i = 0; i < numAliens; i++) {
                boolean rowIsEmpty = true;
                for (int j = 0; j < numAliens; j++) {
                    int index = i * numAliens + j;
                    if (index >= aliens.size()) {
                        break;
                    }
                    Alien alien = aliens.get(index);
                    if (!aliensToRemove.contains(alien)) {
                        rowIsEmpty = false;
                        break;
                    }
                }
                if (rowIsEmpty) {
                    emptyRow = i;
                    break;
                }
            }

            // Mova os aliens que estão acima da linha vazia para baixo
            for (int i = 0; i < emptyRow; i++) {
                for (int j = 0; j < numAliens; j++) {
                    int index = i * numAliens + j;
                    if (index >= aliens.size()) {
                        break;
                    }
                    Alien alien = aliens.get(index);
                    if (alien.getY() < screenHeight - alienHeight - spaceshipHeight) {
                        alien.moveDown();
                    }
                }
            }

            screen.clear();
            List<AlienBullet> alienBulletsToRemove = new ArrayList<>();

            spaceship.draw(textGraphics);
            for (AlienBullet alienBullet : alienBullets) {
                if (spaceship.isHit(alienBullet)) {
                    lives--;
                    alienBulletsToRemove.add(alienBullet);
                    if (lives <= 0) {
                        return false; // Termina o jogo se o jogador ficar sem vidas
                    }
                }
            }
            for (AlienBullet bullet : alienBullets) {
                bullet.moveDown();
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

            textGraphics.putString(screenWidth - 10, 0, "Lives: " + lives);
            textGraphics.putString(0, 0, "Points:" + score);
            
            screen.refresh();

            counter++;
        }
    }
}
