import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager {
    private Font font;

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

 Font changeFont(String path, int size){
    File fontFile = new File("src/main/resources/SpaceInvadersFont.ttf");
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
}