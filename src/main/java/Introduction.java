import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Introduction {
    private Screen screen;

    public Introduction(Screen screen) {
        this.screen = screen;
    }

    // Método para desenhar a caixa de texto
    public void display() throws IOException {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();
        drawTextBox(textGraphics);
        screen.refresh();

        // Espera que o user carregue em  ESC para ir para o menu
        KeyStroke keyStroke = screen.readInput();
        while (keyStroke != null && keyStroke.getKeyType() != KeyType.Escape) {
            keyStroke = screen.readInput();
        }
        screen.clear();
    }

    private void drawTextBox(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);

        String[] textBox = {
                "+-----------------------------+",
                "|         INTRODUCTION        |",
                "+-----------------------------+",
                "|                             |",
                "|    OBJECTIVE OF THE GAME    |",
                "|                             |",
                "|  Welcome to Space Invaders! |",
                "|       Kill the aliens       |",
                "|     and be the conqueror    |",
                "|        of the world!        |",
                "|                             |",
                "|         HOW TO PLAY         |",
                "|                             |",
                "| ARROW LEFT  --> TURNS LEFT  |",
                "| ARROW RIGHT --> TURNS RIGHT |",
                "| SPACE BAR   --> SHOOTING    |",
                "| ESC         --> QUIT GAME   |",
                "|                             |",
                "|                             |",
                "|     PRESS ESC TO GO BACK    |",
                "|                             |",
                "+-----------------------------+"
        };

        int boxWidth = textBox[0].length();
        int boxHeight = textBox.length;

        int boxStartX = (screen.getTerminalSize().getColumns() - boxWidth) / 2;
        int boxStartY = (screen.getTerminalSize().getRows() - boxHeight) / 2;

        for (int i = 0; i < textBox.length; i++) {
            textGraphics.putString(boxStartX, boxStartY + i, textBox[i]);
        }
    }

    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            Introduction intro = new Introduction(screen);
            intro.display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}