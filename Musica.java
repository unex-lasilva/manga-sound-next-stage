public class Musica {
    private String titulo;  // titulo: nome da música
    private int duracao; // duracao: duração da música em segundos (inicialmente 0)
    private String path; // path: caminho do arquivo de áudio no sistema
    private String artista; // artista: nome do artista que interpreta a música

    // Construtor padrão
    public Musica() {
    }
    // Construtor que inicializa os campos principais
    public Musica(String titulo, String artista, String path) {
        this.titulo = titulo;
        this.artista = artista;
        this.path = path;
        this.duracao = 0;  
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getPath() {
        return path;
    }

    public String getArtista() {
        return artista;
    }
    
  
}
