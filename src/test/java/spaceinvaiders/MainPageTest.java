package spaceinvaiders;

import spaceinvaiders.MainPage;

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

import java.awt.*;
import java.io.IOException;

class MainPageTest {

    @Mock
    private Screen screen;

    @Mock
    private TextGraphics textGraphics;

    private MainPage mainPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(screen.newTextGraphics()).thenReturn(textGraphics);
        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        mainPage = new MainPage();
        mainPage.setScreen(screen);
    }

    @Test
    void testDrawMenu() throws IOException {
        // Simula a interação com o Screen
        mainPage.draw();
        verify(screen, times(1)).clear();
        verify(screen, times(1)).refresh();
    }

    @Test
    void testRunAndSelectLeaderboard() throws IOException {
        // Configura entradas de teclado simuladas para selecionar a opção de leaderboard
        when(screen.readInput())
                .thenReturn(new KeyStroke(KeyType.ArrowDown)) // Move para baixo no menu
                .thenReturn(new KeyStroke(KeyType.ArrowDown)) // Move para baixo novamente para selecionar "leaderboard"
                .thenReturn(new KeyStroke(KeyType.Enter)) // Seleciona a opção "leaderboard"
                .thenReturn(new KeyStroke(KeyType.Escape)); // Sai do menu

        mainPage.run();

        // Verifica se os métodos necessários para mostrar o leaderboard foram chamados
        // Como o método é privado, verificamos o comportamento esperado, como limpar a tela e refrescar
        verify(screen, atLeastOnce()).clear();
        verify(screen, atLeastOnce()).refresh();
    }

    @Test
    void testRunAndSelectMenuItem() throws IOException {
        // Configura entradas de teclado simuladas
        when(screen.readInput())
                .thenReturn(new KeyStroke(KeyType.ArrowDown)) // Move para baixo no menu
                .thenReturn(new KeyStroke(KeyType.Enter)) // Seleciona a opção
                .thenReturn(new KeyStroke(KeyType.Escape)); // Sai do menu

        mainPage.run();

        // Verifica se os métodos necessários foram chamados
        verify(screen, atLeastOnce()).readInput();
        verify(screen, atLeastOnce()).refresh();
    }

    @Test
    void testChangeFont() {
        Font expectedFont = mainPage.changeFont("src/main/resources/SpaceInvadersFont.ttf", 18);
        assertNotNull(expectedFont); // Verifica se a fonte foi alterada corretamente
    }

    @Test
    void testPlaySound() {
        // Simula a execução do método playSound sem verificar o efeito sonoro
        mainPage.playSound("src/main/resources/sound.wav");
        // Não é possível verificar diretamente se o som foi tocado, mas garante que não há exceções
    }

    void testInitScreen() throws IOException {
        // Mock do Screen é injetado em mainPage
        mainPage.setScreen(screen);

        // Chama o método que deve iniciar a tela
        mainPage.initScreen();

        // Verifica se o método startScreen foi chamado no mock do Screen
        verify(screen).startScreen();
    }

    @Test
    void testSetAndGetScreen() {
        Screen newScreen = mock(Screen.class);
        mainPage.setScreen(newScreen);
        assertEquals(newScreen, mainPage.getScreen());
    }

    @Test
    void testDrawTitle() throws IOException {
        mainPage.draw();
        verify(textGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        // Verifica se o texto do título foi desenhado
    }
    @Test
    void testDrawMenuItems() throws IOException {
        mainPage.draw();
        verify(textGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
        // Verifica se os itens do menu foram desenhados
    }



}
