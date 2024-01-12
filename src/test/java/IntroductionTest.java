import com.example.mygame.Introduction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

class IntroductionTest {

    @Mock
    private Screen screen; // Cria um mock do objeto Screen
    @Mock
    private TextGraphics textGraphics; // Cria um mock do objeto TextGraphics
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks

        // Configura o comportamento do mock 'screen'
        // para retornar um tamanho de terminal específico quando é chamado
        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        // Configura o comportamento do mock 'screen'
        // para retornar o mock de 'textGraphics' quando o método newTextGraphics() for chamado
        when(screen.newTextGraphics()).thenReturn(textGraphics);
    }

    @Test
    void testDisplay() throws IOException {
        // Configura o mock 'screen' para retornar um KeyStroke do tipo ESCAPE quando readInput() for chamado
        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Escape));

        // Cria uma instância de Introduction passando o mock 'screen'
        Introduction intro = new Introduction(screen);
        intro.display(); // Chama o método display()

        // Verifica se o método clear() foi chamado pelo menos uma vez no mock 'screen'
        verify(screen, atLeastOnce()).clear();
        // Verifica se o método refresh() foi chamado exatamente uma vez no mock 'screen'
        verify(screen, times(1)).refresh();
    }

    @Test
    void testIntroductionConstructor() {
        Introduction intro = new Introduction(screen);
        assertNotNull(intro); // Verifica se a instância não é nula
        assertEquals(screen, intro.getScreen()); // Verifica se o screen está definido corretamente
    }

}
