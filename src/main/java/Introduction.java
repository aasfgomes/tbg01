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

    public void display() throws IOException {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();
        drawTextBox(textGraphics);
        screen.refresh();

        KeyStroke keyStroke = screen.readInput();
        while (keyStroke != null && keyStroke.getKeyType() != KeyType.Escape) {
            keyStroke = screen.readInput();
        }
        screen.clear();
    }

    private void drawTextBox(TextGraphics textGraphics) {
        String[] textBox = {
                "+-----------------------------+",
                "|         lntroduction        |",
                "+-----------------------------+",
                "|                             |",
                "|    Objective of the game    |",
                "|                             |",
                "|  welcome to Space Invaders! |",
                "|       Kill the aliens       |",
                "|     and be the conqueror    |",
                "|        of the world!        |",
                "|                             |",
                "|         How to play         |",
                "|                             |",
                "| Arrow left  --> turns left  |",
                "| Arrow right --> turns right |",
                "| Space bar   --> shooting    |",
                "| ESC         --> quit game   |",
                "|                             |",
                "|                             |",
                "|     press esc to go back    |",
                "|                             |",
                "+-----------------------------+"
        };

        int boxWidth = textBox[0].length();
        int boxHeight = textBox.length;

        int boxStartX = (screen.getTerminalSize().getColumns() - boxWidth) / 2;
        int boxStartY = (screen.getTerminalSize().getRows() - boxHeight) / 2;

        for (int i = 0; i < textBox.length; i++) {
            String line = textBox[i];
            if (line.contains("lntroduction") || line.contains("How to play") || line.contains("Press esc to go back")) {
                // Divide a linha em três partes: a "parede" esquerda, o texto e a "parede" direita
                String leftWall = line.substring(0, 1);
                String text = line.substring(1, line.length() - 1);
                String rightWall = line.substring(line.length() - 1);

                // Desenha a "parede" esquerda
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(boxStartX, boxStartY + i, leftWall);

                // Desenha o texto
                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                textGraphics.putString(boxStartX + 1, boxStartY + i, text);

                // Desenha a "parede" direita
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(boxStartX + line.length() - 1, boxStartY + i, rightWall);
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.putString(boxStartX, boxStartY + i, line);
            }
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