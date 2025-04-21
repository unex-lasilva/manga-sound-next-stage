public class ListaReproducao {
    private String titulo; // Armazena o título/nome da lista de reprodução
    private ListaEncadeada lista; // Lista encadeada que contém os objetos do tipo Musica

    public ListaReproducao(String titulo) {
        this.titulo = titulo; // Inicializa o título da lista
        this.lista = new ListaEncadeada(); // Inicializa a lista encadeada vazia
    }

    // Adiciona uma música à lista.
    public void addMusica(Musica musica) {
        lista.append(musica);
    }

    // Remove a música na posição informada (posição inicia em 0).
    public boolean removerMusica(int posicao) {
        return lista.remove(posicao);
    }
    
    // Insere a música em uma posição específica (sobrescreve a posição, ou insere deslocando os demais)
    public void inserirMusicaEm(int posicao, Musica musica) {
        lista.insertAt(posicao, musica);
    }

    public boolean isVazia() {
        return lista.isEmpty();
    }

    public int tamanho() {
        return lista.size();
    }

    // Cria uma nova lista de reprodução a partir de outra (cópia dos elementos).
    public void criarListaApartirDelista(ListaReproducao outra) {
        this.titulo = outra.getTitulo();
        this.lista.clear();
        for (int i = 0; i < outra.tamanho(); i++) {
            Musica musica = (Musica) outra.obterMusica(i); // musica: elemento atual da iteração
            this.addMusica(musica);
        }
    }

    // Retorna a posição da música dentro da lista; retorna -1 se não encontrada.
    public int posicaoDa(Musica musica) {
        return lista.indexOf(musica);
    }

    // Verifica se a música está contida na lista.
    public boolean contemMusica(Musica musica) {
        return lista.contains(musica);
    }

    // Limpa a lista de reprodução.
    public boolean limparLista() {
        return lista.clear();
    }

    // Retorna a música na posição desejada.
    public Musica obterMusica(int posicao) {
        return (Musica) lista.get(posicao);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    // Método para acessar a lista encadeada caso necessário.
    public ListaEncadeada getLista() {
        return lista;
    }
    

 //Move a música da posição posOrigem para a posição posDestino.
  //Se posOrigem < posDestino, o destino é ajustado automaticamente
 // após a remoção para não ultrapassar o tamanho.
 
public void moverMusica(int posOrigem, int posDestino) {
    int tamanhoAtual = lista.size(); // tamanhoAtual: quantidade de músicas na lista
    if (posOrigem < 0 || posOrigem >= tamanhoAtual) {
        System.out.println("Posição de origem inválida.");
        return;
    }
    if (posDestino < 0 || posDestino >= tamanhoAtual) {
        System.out.println("Posição de destino inválida.");
        return;
    }
    // captura a música antes de remover
    Musica m = (Musica) lista.get(posOrigem); // m: música que será movida
    // remove da origem
    lista.remove(posOrigem);
    // insere exatamente no destino solicitado
    lista.insertAt(posDestino, m);
    System.out.println(
        "Música \"" + m.getTitulo() + 
        "\" movida de " + posOrigem + " para " + posDestino + "."
    );
}

}
