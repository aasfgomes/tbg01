import com.example.mygame.Game;
import com.example.mygame.Alien;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.sound.sampled.Clip;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    @TempDir
    Path tempDir; // Cria um path temporário para os testes
    @Test
    void GameOverTest() throws Exception {
        // Cria uma instância da classe Game, passando 'null' para o construtor, teste não depende da Screen.
        Game game = new Game(null);
        // Configura o campo privado 'lives' para 0 usando reflexão.
        setPrivateField(game, "lives", 0);
        // Obtém o método privado 'isGameOver' da classe Game usando reflexão.
        Method method = Game.class.getDeclaredMethod("isGameOver");
        // Torna o método acessível para que possa ser chamado.
        method.setAccessible(true);
        // Invoca o método 'isGameOver' na instância criada de Game e armazena o resultado.
        boolean result = (boolean) method.invoke(game);
        assertTrue(result);
    }
    // Método auxiliar para configurar campos privados num objeto usando reflexão.
    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        // Obtém o campo do objeto com o nome especificado.
        java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
        // Torna o campo acessível.
        field.setAccessible(true);
        // Define um novo valor para o campo.
        field.set(object, value);
    }
    // Método auxiliar para obter o valor de um campo privado num  objeto usando reflexão.
    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    @Test
    void stopBackgroundMusicTest() throws Exception {
        // Criar mock do Clip
        Clip mockClip = mock(Clip.class);

        // Criar instância de Game
        Game game = new Game(null);

        // Usar reflexão para definir o Clip mock e o estado da música
        setPrivateField(game, "backgroundSoundClip", mockClip);
        setPrivateField(game, "isBackgroundMusicPlaying", true);

        // Chamar stopBackgroundMusic
        game.stopBackgroundMusic();

        // Verificar se o método stop foi chamado no mock do Clip
        verify(mockClip).stop();

        // Verificar se isBackgroundMusicPlaying é false
        boolean isMusicPlaying = (boolean) getPrivateField(game, "isBackgroundMusicPlaying");
        assertFalse(isMusicPlaying);
    }
    @Test
    void moveAliensTest() throws Exception {
        // Criar um mock para Screen
        Screen mockScreen = mock(Screen.class);
        TerminalSize mockSize = new TerminalSize(80, 24); // Use um tamanho de terminal que nao é real
        when(mockScreen.getTerminalSize()).thenReturn(mockSize);

        // Criar instância de Game com mock de Screen
        Game game = new Game(mockScreen);

        // Iniciar a lista de aliens
        List<Alien> aliens = new ArrayList<>();
        aliens.add(new Alien(0, 1)); // Supondo que 0, 1 sejam coordenadas que são válidas para o alien

        // Configurar campos privados usando reflexão
        setPrivateField(game, "aliens", aliens);
        setPrivateField(game, "movingRight", true);

        // Invocar o método moveAliens
        Method method = Game.class.getDeclaredMethod("moveAliens");
        method.setAccessible(true);
        method.invoke(game);

        boolean movingRight = (boolean) getPrivateField(game, "movingRight");

    }

    @Test
    void aliensShootTest() throws Exception {
        Game game = new Game(null);

        // Configurar o estado do jogo
        ArrayList<Alien> aliens = new ArrayList<>();
        aliens.add(new Alien(5, 5)); // Adiciona um alien numa posição
        setPrivateField(game, "aliens", aliens);
        setPrivateField(game, "lastShotTime", System.currentTimeMillis() - 1001); // garantir que o tempo passou

        // Invocar o método aliensShoot
        Method method = Game.class.getDeclaredMethod("aliensShoot");
        method.setAccessible(true);
        method.invoke(game);

        // Verificar se um AlienBullet foi adicionado à lista alienBullets
        ArrayList<?> alienBullets = (ArrayList<?>) getPrivateField(game, "alienBullets");
        assertFalse(alienBullets.isEmpty());
    }

    @Test
    void playBulletSoundTest() throws Exception {
        Game game = new Game(null);
        Method method = Game.class.getDeclaredMethod("playBulletSound", String.class);
        method.setAccessible(true);

        assertDoesNotThrow(() -> method.invoke(game, "shoot.wav"));
    }

    @Test
    void playBackgroundSoundTest() throws Exception {
        Game game = new Game(null);
        Method method = Game.class.getDeclaredMethod("playBackgroundSound", String.class);
        method.setAccessible(true);

        assertDoesNotThrow(() -> method.invoke(game, "soundtrack.wav"));
    }

    @Test
    void addEntryToLeaderboardTest() throws Exception {
        Game game = new Game(null);

        // Criar um ficheiro de leaderboard temporário
        Path tempLeaderboard = Files.createTempFile("leaderboard", ".txt");
        // Escrever conteúdo inicial no arquivo
        Files.write(tempLeaderboard, List.of("-------", "1 - André - 150 points", "2 - Marta - 100 points"));

        // Invocar o método addEntryToLeaderboard
        Method method = Game.class.getDeclaredMethod("addEntryToLeaderboard", String.class, int.class);
        method.setAccessible(true);
        assertDoesNotThrow(() -> method.invoke(game, "TestPlayer", 200));

    }

}







