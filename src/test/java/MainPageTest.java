import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class MainPageTest {

    private MainPage mainPage;
    private Screen mockScreen;

    @BeforeEach
    public void setUp() throws IOException {
        mainPage = new MainPage();
        mainPage.initScreen(); // Inicializa o terminal aqui
        mockScreen = Mockito.mock(Screen.class);
    }

    @Test
    public void testDrawMethodDoesNotThrowException() {
        // Este teste  verifica se o método draw não lança uma exceção
        assertDoesNotThrow(() -> mainPage.draw());
    }
}

