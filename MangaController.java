import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

public class MangaController {
    // Repositórios e listas (utilizamos a ListaEncadeada implementada) // Lista com todas as músicas adicionadas ao repositório
    private ListaEncadeada repositorioMusica;  // Lista com todas as músicas adicionadas ao repositório
    private ListaEncadeada listasReproducao;  // Lista com todas as listas de reprodução criadas
    private ListaEncadeada artistas;           // Lista de artistas associados às músicas
    private ReprodutorLista reprodutorLista;   // Reprodutor responsável pela execução das listas de reprodução

    public MangaController() {
        repositorioMusica = new ListaEncadeada();
        listasReproducao = new ListaEncadeada();
        artistas = new ListaEncadeada();
        reprodutorLista = new ReprodutorLista();
        // Cria a pasta "repositório" se não existir.
        File repositorioDir = new File("repositorio"); // repositorioDir: referência à pasta de armazenamento
        if(!repositorioDir.exists()){
            repositorioDir.mkdir();
        }
    }

    // 1. Adicionar Música ao Repositório  
    public void adicionarMusica(String titulo, String pathOrigem, String nomeArtista) {
        // Copiar o arquivo para a pasta "repositorio"
        File origem = new File(pathOrigem); // origem: arquivo de origem informado pelo usuário
        if(!origem.exists() || !origem.getName().endsWith(".wav")){
            System.out.println("Arquivo inválido ou não existe.");
            return;
        }
        File destino = new File("repositorio" + File.separator + origem.getName()); // destino: local para onde a música será copiada
        try {
            Files.copy(origem.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Música copiada para o repositório.");
        } catch (IOException e) {
            System.out.println("Erro ao copiar arquivo: " + e.getMessage());
            return;
        }
        // Cria objeto Musica e adiciona ao repositório
        Musica musica = new Musica(titulo, nomeArtista, destino.getPath()); // musica: objeto criado a partir do arquivo copiado
        repositorioMusica.append(musica);
        // Adiciona o artista caso não esteja na lista de artistas
        if(!artistas.contains(nomeArtista)){
            artistas.append(nomeArtista);
        }
    }

    // 2. Criar Lista de Reprodução
    public void criarListaReproducao(String titulo) {
        ListaReproducao lista = new ListaReproducao(titulo); // lista: nova lista de reprodução
        listasReproducao.append(lista);
        System.out.println("Lista de reprodução \"" + titulo + "\" criada.");
    }

    // Excluir lista de reprodução (opcional)
    public void excluirListaReproducao(String titulo) {
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao lista = (ListaReproducao) listasReproducao.get(i);
            if(lista.getTitulo().equalsIgnoreCase(titulo)){
                listasReproducao.remove(i);
                System.out.println("Lista de reprodução \"" + titulo + "\" removida.");
                return;
            }
        }
        System.out.println("Lista não encontrada.");
    }

    // 4. Adicionar Música à Lista de Reprodução (adiciona no final)
    public void adicionarMusicaListaReproducao(String tituloMusica, String tituloLista) {
        Musica musica = buscarMusicaNoRepositorio(tituloMusica); // musica: música a ser adicionada
        if(musica == null){
            System.out.println("Música não encontrada no repositório.");
            return;
        }
        ListaReproducao lista = buscarListaPorTitulo(tituloLista); // lista: lista de reprodução alvo
        if(lista == null){
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }
        lista.addMusica(musica);
        System.out.println("Música adicionada à lista \"" + tituloLista + "\".");
    }

    // 4. Adicionar Música à Lista de Reprodução em uma posição específica
    public void adicionarMusicalistaReproducaoEmPosicao(String tituloMusica, String tituloLista, int posicao) {
        Musica musica = buscarMusicaNoRepositorio(tituloMusica);
        if(musica == null){
            System.out.println("Música não encontrada no repositório.");
            return;
        }
        ListaReproducao lista = buscarListaPorTitulo(tituloLista);
        if(lista == null){
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }
        if(posicao < 0 || posicao > lista.tamanho()){
            System.out.println("Posição inválida.");
            return;
        }
        // Inserção utilizando o método da lista encadeada
        lista.inserirMusicaEm(posicao, musica);
        System.out.println("Música adicionada na posição " + posicao + " na lista \"" + tituloLista + "\".");
    }

    // Remover Música da Lista de Reprodução (por título)
    public void removerMusicalistaReproducao(String tituloMusica, String tituloLista) {
        ListaReproducao lista = buscarListaPorTitulo(tituloLista);
        if(lista == null){
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }
        int posicao = -1; // posicao: índice onde a música foi encontrada
        for (int i = 0; i < lista.tamanho(); i++) {
            Musica musica = lista.obterMusica(i);
            if(musica.getTitulo().equalsIgnoreCase(tituloMusica)){
                posicao = i;
                break;
            }
        }
        if(posicao == -1){
            System.out.println("Música não encontrada na lista.");
            return;
        }
        lista.removerMusica(posicao);
        System.out.println("Música removida da lista \"" + tituloLista + "\".");
    }

    // Remover Música da Lista de Reprodução por posição
    public void removerMusicaListaReproducaoEmPosicao(String tituloLista, int posicao) {
        ListaReproducao lista = buscarListaPorTitulo(tituloLista);
        if(lista == null){
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }
        if(lista.removerMusica(posicao)){
            System.out.println("Música removida da posição " + posicao + " na lista \"" + tituloLista + "\".");
        } else {
            System.out.println("Posição inválida.");
        }
    }

    // Executa a lista de reprodução escolhida.
    // Em MangaController.java

public void reproduzirListaDeReproducao(String tituloLista) {
    ListaReproducao lista = buscarListaPorTitulo(tituloLista);
    if (lista == null) {
        System.out.println("Lista de reprodução não encontrada.");
        return;
    }
    reprodutorLista.setListaReproducao(lista);
    // Dispara a reprodução em background
    Thread playbackThread = new Thread(() -> reprodutorLista.play()); // playbackThread: thread que executa a reprodução em segundo plano
    playbackThread.start();
}


    // Métodos para controlar a reprodução
    public void pausarMusical() {
        reprodutorLista.pause();
    }

    public void executarMusica() {
        reprodutorLista.play();
    }

    public void voltarMusical() {
        reprodutorLista.voltarMusical();
    }

    public void passarMusica() {
        reprodutorLista.passarMusica();
    }

    public void reiniciarLista() {
        reprodutorLista.restartLista();
    }

    public void pararLista() {
        reprodutorLista.stop();
    }
    
    // Métodos auxiliares para busca de música e listas
    private Musica buscarMusicaNoRepositorio(String tituloMusica) {
        for (int i = 0; i < repositorioMusica.size(); i++) {
            Musica musica = (Musica) repositorioMusica.get(i);
            if(musica.getTitulo().equalsIgnoreCase(tituloMusica)){
                return musica;
            }
        }
        return null;
    }
    
    private ListaReproducao buscarListaPorTitulo(String tituloLista) {
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao lista = (ListaReproducao) listasReproducao.get(i);
            if(lista.getTitulo().equalsIgnoreCase(tituloLista)){
                return lista;
            }
        }
        return null;
    }
    // dentro de MangaController, ao lado dos outros métodos de edição
public void reposicionarMusicaListaReproducao(String tituloMusica,
String tituloLista,
int posDestino) {
ListaReproducao lista = buscarListaPorTitulo(tituloLista);
if (lista == null) {
System.out.println("Lista de reprodução não encontrada.");
return;
}
// encontra a posição atual
int posOrigem = -1; // posOrigem: posição atual da música
for (int i = 0; i < lista.tamanho(); i++) {
if (lista.obterMusica(i).getTitulo()
.equalsIgnoreCase(tituloMusica)) {
posOrigem = i;
break;
}
}
if (posOrigem == -1) {
System.out.println("Música não encontrada na lista.");
return;
}
// chama nosso novo moverMusica
lista.moverMusica(posOrigem, posDestino);
}

}
