import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.Font;
import java.io.IOException;

public class MainPage {

    private Screen screen;
    private int selectedMenuItemIndex = 0;
    private String[] menuItems = {"CLICK HERE TO PLAY", "LEADERBOARD", "INTRODUCTION", "EXIT"};

    public void initScreen() throws IOException {
        // Ajustar aqui o tamanho da fonte para as opções do menu aqui!!!!!
        Font font = new Font("Monospaced", Font.BOLD, 18);
        SwingTerminalFontConfiguration fontConfig = SwingTerminalFontConfiguration.newInstance(font);

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setTerminalEmulatorFontConfiguration(fontConfig);
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
                textGraphics.setBackgroundColor(TextColor.ANSI.BLACK); // Resetar a cor de fundo
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
                    selectedMenuItemIndex = (selectedMenuItemIndex - 1 + menuItems.length) % menuItems.length;
                    draw();
                    break;
                case ArrowDown:
                    selectedMenuItemIndex = (selectedMenuItemIndex + 1) % menuItems.length;
                    draw();
                    break;
                case Enter:
                    // PARA FAZER
                    System.out.println("Selected: " + menuItems[selectedMenuItemIndex]);

                    return;
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
