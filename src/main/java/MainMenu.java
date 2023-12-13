
// Para efeitos de teste existe um painel mas tenho de imprimir as opções de escolha diretas no terminal ao abrir

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.IOException;

public class MainMenu {

    private Screen screen;
    private WindowBasedTextGUI gui;

    public MainMenu() {
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            BasicWindow window = new BasicWindow();
            window.setHints(java.util.Collections.singletonList(Window.Hint.CENTERED));

            gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.DEFAULT));

            // Cria um painel -- Tenta alterar isto e imprimir direto no terminal
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL)); // Usa um Panel como conteúdo da janela

            // Título "Space Invaders" no painel
            Label title = new Label("Space Invaders");
            title.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            title.setSize(new TerminalSize(100, 5)); // Ajuste o tamanho da fonte aqui
            panel.addComponent(title);

            // Adicione espaço entre o título e a primeira opção
            panel.addComponent(new EmptySpace(new TerminalSize(0, 2))); // Espaço de 2 linhas

            window.setComponent(panel);

            buildUI(panel);

            gui.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildUI(Panel panel) {
        // Adiciona os botões no painel
        Button playButton = createButton("CLICK HERE TO PLAY", this::startGame);
        playButton.setPreferredSize(new TerminalSize(30, 3));
        panel.addComponent(playButton);

        Button leaderboardButton = createButton("LEADERBOARD", this::showLeaderboard);
        leaderboardButton.setPreferredSize(new TerminalSize(30, 3));
        panel.addComponent(leaderboardButton);

        Button introductionButton = createButton("INTRODUCTION", this::showIntroduction);
        introductionButton.setPreferredSize(new TerminalSize(30, 3));
        panel.addComponent(introductionButton);

        Button exitButton = createButton("EXIT", this::exitGame);
        exitButton.setPreferredSize(new TerminalSize(30, 3));
        panel.addComponent(exitButton);
    }

    private Button createButton(String title, Runnable action) {
        return new Button(title, action);
    }

    private void startGame() {

    }

    private void showLeaderboard() {

    }

    private void showIntroduction() {

    }

    private void exitGame() {
        try {
            if (screen != null) {
                screen.stopScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
