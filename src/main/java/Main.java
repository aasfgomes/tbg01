import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.doResizeIfNecessary();

            KeyStroke keyStroke;
            do {
                keyStroke = screen.readInput();
            } while (keyStroke == null || keyStroke.getKeyType() != KeyType.Escape);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
