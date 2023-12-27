import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;


public class Game {
    private Screen screen;
    private Font font;

    public Game(Screen screen) {
        this.screen = screen;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
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

    public void createTerminal(int width, int height) {
        setFont(changeFont("src/main/resources/fonts/Square-Regular.ttf", 20));
        AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(true,
            AWTTerminalFontConfiguration.BoldMode.NOTHING, getFont());
        try {
            Terminal terminal = new DefaultTerminalFactory()
                .setForceAWTOverSwing(true)
                .setInitialTerminalSize(new TerminalSize(width, height))
                .setTerminalEmulatorFontConfiguration(cfg)
                .createTerminal();
            // ...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            screen.clear();
            TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(10, 10, "Game started!", SGR.BOLD);
            screen.refresh();
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}