import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Game {
    private Clip backgroundSoundClip;
    private Screen screen;
    private Spaceship spaceship;
    private List<Alien> aliens;
    private List<Bullet> bullets;
    private boolean movingRight = true;
    private int edgePadding = 2;
    private int score = 0;
    private int lives = 3;
    private Random random = new Random();

    public Game(Screen screen) {
        this.screen = screen;
        this.aliens = new ArrayList<>();
        this.bullets = new ArrayList<>();
    }

    private void playSound(String soundFileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundSound(String soundFileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            backgroundSoundClip = AudioSystem.getClip(); // Inicialize a variável aqui
            backgroundSoundClip.open(audioIn);
            backgroundSoundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void moveAliens() {
        int screenWidth = screen.getTerminalSize().getColumns();

        boolean reachedEdge = false;
        for (Alien alien : aliens) {
            if ((movingRight && alien.getX() >= screenWidth - edgePadding) ||
                    (!movingRight && alien.getX() <= edgePadding)) {
                reachedEdge = true;
                break;
            }
        }

        if (reachedEdge) {
            movingRight = !movingRight;
            for (Alien alien : aliens) {
                alien.moveDown();
            }
        } else {
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
        playBackgroundSound("soundtrack.wav");  // Toca o som de fundo
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        int screenWidth = screen.getTerminalSize().getColumns();
        int screenHeight = screen.getTerminalSize().getRows();

        int spaceshipWidth = 4;
        int spaceshipX = (screenWidth - spaceshipWidth) / 2;
        int spaceshipY = screenHeight - 3;

        int alienHeight = 1;
        spaceship = new Spaceship(spaceshipX, spaceshipY);
        spaceship.draw(textGraphics);

        int numAliensPerRow = 12;
        int numAlienRows = 3;
        int alienWidth = 1;
        int spaceWidth = 1;

        int totalAlienBlockWidth = (numAliensPerRow * alienWidth) + ((numAliensPerRow - 1) * spaceWidth);
        int initialX = (screenWidth - totalAlienBlockWidth) / 2;

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
                        System.out.println("ESC key pressed");
                        if (backgroundSoundClip != null) {
                            backgroundSoundClip.stop();
                        }
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
                        playSound("shoot.wav");
                    }
                }

                if (counter % 20 == 0) {
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

                for (Alien alien : aliens) {
                    if (alien.getY() >= spaceship.getY()) {
                        lives--;
                        score = 0;
                        aliens.clear();
                        bullets.clear();
                        int messageX = (screenWidth - "Aliens got you!".length()) / 2;
                        textGraphics.putString(messageX, screenHeight / 2, "Aliens got you!");
                        screen.refresh();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        screen.clear();
                        for (int row = 0; row < numAlienRows; row++) {
                            for (int col = 0; col < numAliensPerRow; col++) {
                                int x = initialX + col * (alienWidth + spaceWidth);
                                int y = (row + 1) * alienHeight;
                                Alien newAlien = new Alien(x, y);
                                aliens.add(newAlien);
                            }
                        }
                        break;
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

                if (aliens.size() < 10) {
                    for (int i = 0; i < 3; i++) {
                        int x = random.nextInt(screenWidth - alienWidth);
                        int y = 0;
                        Alien newAlien = new Alien(x, y);
                        aliens.add(newAlien);
                    }
                }

                if (aliens.size() < 5) {
                    for (int i = 0; i < 6; i++) {
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

                textGraphics.putString(screenWidth - 10, 0, "lives: " + lives);
                textGraphics.putString(0, 0, "points:" + score);

                screen.refresh();

                counter++;
            }
        }
    }
}
