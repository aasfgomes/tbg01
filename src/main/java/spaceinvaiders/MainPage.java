package spaceinvaiders;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



public class MainPage {

    private Screen screen;
    private int selectedMenuItemIndex = 0;
    private String[] menuItems = { "Click here to play", "leaderboard", "lntroduction", "Exit" };
    private Font font;

    public Font getFont() {

        return font;
    }


    public void setFont(Font font) {

        this.font = font;
    }

    private List<String> readLeaderboard() throws IOException {
        return Files.readAllLines(Paths.get("src/main/resources/leaderboard.txt"));
    }

    private void showLeaderboard() throws IOException {
        List<String> leaderboardLines = readLeaderboard();
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        int screenWidth = screen.getTerminalSize().getColumns();
        int row = 3; // Iniciar a partir de uma linha específica

        for (String line : leaderboardLines) {
            int col = (screenWidth - line.length()) / 2; // Centraliza cada linha
            textGraphics.putString(col, row++, line);
        }

        screen.refresh();
        screen.readInput();
        draw(); // Redesenha o menu principal após exibir o leaderboard
    }

    public Font changeFont(String path, int size){
        File fontFile = new File(path);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,fontFile);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font loaded = font.deriveFont(Font.PLAIN,size);
        return loaded;
    }

    public void playSound(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return this.screen;
    }

    public void initScreen() throws IOException {
        setFont(changeFont("src/main/resources/SpaceInvadersFont.ttf", 18));
        AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(true,
                AWTTerminalFontConfiguration.BoldMode.NOTHING, getFont());

        TerminalSize size = new TerminalSize(80, 24); // Defina o tamanho desejado aqui

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setForceAWTOverSwing(true)
                .setTerminalEmulatorFontConfiguration(cfg)
                .setInitialTerminalSize(size); // Adicione esta linha

        Terminal terminal = terminalFactory.createTerminal();

        screen = new TerminalScreen(terminal);
        screen.startScreen();
    }


    public void draw() throws IOException {
        screen.clear();

        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        drawTitle(textGraphics);
        drawMenuItems(textGraphics);

        screen.refresh();
    }

    private void drawTitle(TextGraphics textGraphics) {
        // Titulo em ASCII para "Space Invaders"
        String[] titleArt = {
                "                        _                     _               ",
                "                         (_)                   | |              ",
                " ___ _ __   __ _  ___ ___ _ _ ____   ____ _  __| | ___ _ __ ___ ",
                "/ __| '_ \\ / _` |/ __/ _ \\ | '_ \\ \\ / / _` |/ _` |/ _ \\ '__/ __|",
                "\\__ \\ |_) | (_| | (_|  __/ | | | \\ V / (_| | (_| |  __/ |  \\__ \\",
                "|___/ .__/ \\__,_|\\___\\___|_|_| |_|\\_/ \\__,_|\\__,_|\\___|_|  |___/",
                "    | |                                                          ",
                "    |_|                                                          "
        };

        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        int titleRow = 1; // Mete o título para cima
        for (String line : titleArt) {
            int titleCol = (screen.getTerminalSize().getColumns() - line.length()) / 2;
            textGraphics.putString(titleCol, titleRow++, line);
        }
    }

    private void drawMenuItems(TextGraphics textGraphics) {
        int menuItemRow = 10; // Mete as opções no topo

        for (int i = 0; i < menuItems.length; i++) {
            String menuItem = menuItems[i];
            int menuCol = (screen.getTerminalSize().getColumns() - menuItem.length()) / 2;

            // Opções do menu não selecionadas
            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.putString(menuCol, menuItemRow, menuItem);

            // Opção do menu selecionada
            if (i == selectedMenuItemIndex) {
                textGraphics.setBackgroundColor(TextColor.ANSI.GREEN);
                textGraphics.putString(menuCol, menuItemRow, " ".repeat(menuItem.length()));
                textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
                textGraphics.putString(menuCol, menuItemRow, menuItem);
                textGraphics.setBackgroundColor(TextColor.ANSI.BLACK); // Reset a cor de fundo
            }

            menuItemRow += 2; // Aumenta o espaçamento entre as opções do menu
        }
    }

public void run() throws IOException {
    draw();
    KeyStroke keyStroke;

    do {
        keyStroke = screen.readInput();
        switch (keyStroke.getKeyType()) {
            case ArrowUp:
                playSound("src/main/resources/selected.wav");
                selectedMenuItemIndex = (selectedMenuItemIndex - 1 + menuItems.length) % menuItems.length;
                draw();
                break;
            case ArrowDown:
                playSound("src/main/resources/selected.wav");
                selectedMenuItemIndex = (selectedMenuItemIndex + 1) % menuItems.length;
                draw();
                break;
            case Enter:
                if (selectedMenuItemIndex == 0) { // Verifica se a opção selecionada é "CLICK HERE TO PLAY"
                    Game game = new Game(screen); // Cria uma nova instância de Game
                    game.start(); // Inicia o jogo
                    draw(); // Redesenha o menu principal após o jogo
                } else if (selectedMenuItemIndex == 2) { // Verifica se a opção selecionada é "INTRODUCTION"
                    Introduction introduction = new Introduction(screen); // Passa 'screen' como argumento
                    introduction.display();
                    draw(); // Redesenha o menu principal após a introdução
                } else if (selectedMenuItemIndex == 1) {
                    showLeaderboard();
                } else if (selectedMenuItemIndex == 3) { // Verifica se a opção selecionada é "EXIT"
                    screen.stopScreen(); // Fecha a janela
                                
                    return; 
                }
                break;
            default:
                break;
        }
    } while (keyStroke.getKeyType() != KeyType.Escape);

    screen.stopScreen();
}
    public static void main(String[] args) {
        MainPage menuScreen = new MainPage();
        try {
            menuScreen.initScreen();
            menuScreen.run();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (menuScreen.screen != null) {
                    menuScreen.screen.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
