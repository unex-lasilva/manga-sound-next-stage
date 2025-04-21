import javax.sound.sampled.*;
import java.io.File;

public class ReprodutorLista {
    private ListaReproducao listaReproducao; // listaReproducao: lista atual sendo reproduzida
    private String status; // status: estado atual da reprodução ("tocando", "pausado", "parado")
    private Clip clip; // clip: recurso de áudio usado para tocar os arquivos .wav
    private int indiceMusicaAtual; // indiceMusicaAtual: posição da música atual dentro da lista
    private long posicaoPausada; // posicaoPausada: posição em microssegundos onde a música foi pausada
    private Thread playbackThread; // playbackThread: thread usada para executar a lista de reprodução
    private volatile boolean manualSkip = false; // manualSkip: flag que indica se o usuário solicitou pular música manualmente

    public ReprodutorLista() {
        this.listaReproducao = null;
        this.status = "parado";
        this.indiceMusicaAtual = 0;
    }

    public void setListaReproducao(ListaReproducao lista) {
        this.listaReproducao = lista;
        this.indiceMusicaAtual = 0;
        this.status = "parado";
    }

    public void play() {
        if (listaReproducao == null || listaReproducao.isVazia()) {
            System.out.println("Lista de reprodução vazia ou não definida.");
            return;
        }
        // Retomar se estiver pausado
        if ("pausado".equals(status) && clip != null) {
            clip.setMicrosecondPosition(posicaoPausada);
            clip.start();
            status = "tocando";
            System.out.println("Retomando: " + listaReproducao.obterMusica(indiceMusicaAtual).getTitulo());
            return;
        }
        // Não inicia nova thread se já estiver tocando
        if (playbackThread != null && playbackThread.isAlive()) {
            return;
        }
        // Inicia thread de reprodução contínua
        playbackThread = new Thread(() -> {
            status = "tocando";
            while (indiceMusicaAtual < listaReproducao.tamanho()) {
                Musica musica = listaReproducao.obterMusica(indiceMusicaAtual); // musica: música atual a ser tocada
                tocarMusica(musica);
                // Sai se pausou, parou ou pular manual
                if (!"tocando".equals(status) || manualSkip) {
                    manualSkip = false;
                    break;
                }
                indiceMusicaAtual++;
            }
            // Fim natural da lista
            if (indiceMusicaAtual >= listaReproducao.tamanho() && !manualSkip) {
                System.out.println("Fim da lista de reprodução.");
                stop();
            }
        });
        playbackThread.start();
    }
    //Abre o arquivo .wav da música, inicia o Clip e espera até o término da faixa. 
    private void tocarMusica(Musica musica) { 
        try {
            if (clip != null && clip.isOpen()) {  // Fecha clip anterior, se estiver aberto
                clip.stop();
                clip.close();
            }
            File file = new File(musica.getPath());  // file: arquivo de áudio a ser reproduzido
            if (!file.exists()) {
                System.out.println("Arquivo não encontrado: " + musica.getPath());
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);  // audioStream: fluxo de áudio lido do arquivo
            clip = AudioSystem.getClip(); 
            clip.open(audioStream);
            clip.start();
            status = "tocando";
            posicaoPausada = 0;
            System.out.println("Tocando: " + musica.getTitulo() + " - " + musica.getArtista());
            // Espera terminar musica
            while (clip.isRunning()) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir música: " + e.getMessage());
            e.printStackTrace();
        }
    }
  //Pausa a reprodução atual, armazenando a posição para retomar depois.
    public void pause() {
        if (clip != null && clip.isRunning()) {
            posicaoPausada = clip.getMicrosecondPosition();
            clip.stop();
            status = "pausado";
            System.out.println("Música pausada.");
        }
    }
    //Reinicia a música atual desde o começo.
    public void restartMusica() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            clip.start();
            status = "tocando";
            System.out.println("Música reiniciada.");
        }
    }
    //Reinicia toda a lista de reprodução, fechando o clip e resetando índices.
    public void restartLista() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
        }
        manualSkip = true;
        indiceMusicaAtual = 0;
        status = "parado";
        play();
    }
    //Para completamente a reprodução e fecha o clip.
    public void stop() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
        }
        status = "parado";
        System.out.println("Reprodução parada.");
    }
    //Avança manualmente para a próxima música da lista.
    public void passarMusica() {
        if (listaReproducao == null) return;
        if (indiceMusicaAtual < listaReproducao.tamanho() - 1) {
            manualSkip = true;
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }
            indiceMusicaAtual++;
            status = "parado";
            play();
        } else {
            System.out.println("Esta é a última música da lista.");
        }
    }
 //Retorna para o início da faixa atual ou para a faixa anterior, dependendo da posição do usuário.
    public void voltarMusical() {
        if (listaReproducao == null) return;
        manualSkip = true;
        if (posicaoPausada > 10_000_000) { //Se a música já estiver avançada além de 10 segundos, reinicia a mesma
            restartMusica();
        } else if (indiceMusicaAtual > 0) {
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }
            indiceMusicaAtual--;
            status = "parado";
            play();
        } else {
            System.out.println("Já estamos na primeira música.");
        }
    }
}
