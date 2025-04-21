import java.util.Scanner;

public class MangaSoundApplication {
    public static void main(String[] args) {
        MangaController controller = new MangaController();  // controller: objeto responsável por manipular as funções principais do sistema
        Scanner scanner = new Scanner(System.in);  // scanner: objeto usado para capturar entrada do usuário
        int opcao = 0; // opcao: armazena a opção escolhida pelo usuário no menu principal

        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    // Adicionar Música ao Repositório
                    System.out.print("Informe o título da música: ");
                    String tituloMusica = scanner.nextLine();  // tituloMusica: título informado pelo usuário
                    System.out.print("Informe o caminho do arquivo (*.wav): ");
                    String caminho = scanner.nextLine(); // caminho: caminho do arquivo de música
                    System.out.print("Informe o nome do artista: ");
                    String nomeArtista = scanner.nextLine(); // nomeArtista: nome do artista da música
                    controller.adicionarMusica(tituloMusica, caminho, nomeArtista);
                    break;
                case 2:
                    // Criar Lista de Reprodução
                    System.out.print("Informe o nome da nova lista de reprodução: ");
                    String tituloLista = scanner.nextLine();
                    controller.criarListaReproducao(tituloLista); // tituloLista: nome da nova lista de reprodução
                    break;
                case 3:
                    // Adicionar Música à Lista de Reprodução
                    System.out.print("Informe o nome da lista de reprodução: ");
                    String listaParaAdicionar = scanner.nextLine();  // listaParaAdicionar: lista onde a música será adicionada
                    System.out.print("Informe o título da música que deseja adicionar: ");
                    String musicaParaAdicionar = scanner.nextLine(); // musicaParaAdicionar: música que será adicionada
                    controller.adicionarMusicaListaReproducao(musicaParaAdicionar, listaParaAdicionar);
                    break;
                case 4:
                    // Editar Lista de Reprodução (reposicionamento de uma música)
                    
                    System.out.print("Informe o nome da lista de reprodução a editar: ");
                    String listaEditar = scanner.nextLine(); // listaEditar: lista onde a música será movida
                    System.out.print("Informe o título da música a reposicionar: ");
                    String musicaEditar = scanner.nextLine(); // musicaEditar: música que será movida
                    System.out.print("Informe a nova posição (0 até tamanho-1): ");
                    int posicaoDestino = Integer.parseInt(scanner.nextLine()); // posicaoDestino: nova posição da música
                    controller.reposicionarMusicaListaReproducao(
                        musicaEditar, listaEditar, posicaoDestino
                    );
                    break;
                
                case 5:
                    // Executar Lista de Reprodução
                    System.out.print("Informe o nome da lista de reprodução a executar: ");
                    String listaExecutar = scanner.nextLine(); // listaExecutar: nome da lista que será reproduzida
                    controller.reproduzirListaDeReproducao(listaExecutar);
                    // Abre o sub-menu de controle da reprodução
                    controleReproducao(controller, scanner);
                    break;
                case 6:
                    System.out.println("Saindo do MangaSound. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 6);

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n==== MangaSound ====");
        System.out.println("1. Adicionar Música ao Repositório");
        System.out.println("2. Criar Lista de Reprodução");
        System.out.println("3. Adicionar Música à Lista de Reprodução");
        System.out.println("4. Editar Lista de Reprodução");
        System.out.println("5. Executar Lista de Reprodução");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Sub-menu para controle da reprodução durante a execução da lista
    public static void controleReproducao(MangaController controller, Scanner scanner) {
        int opcao = 0; // opcao: armazena a opção de controle selecionada
        do {
            System.out.println("\n--- Controle de Reprodução ---");
            System.out.println("1. Pausar Música");
            System.out.println("2. Voltar Música");
            System.out.println("3. Passar Música");
            System.out.println("4. Reiniciar Lista");
            System.out.println("5. Parar Lista e voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, tente novamente.");
                continue;
            }
            switch (opcao) {
                case 1:
                    controller.pausarMusical();
                    break;
                case 2:
                    controller.voltarMusical();
                    break;
                case 3:
                    controller.passarMusica();
                    break;
                case 4:
                    controller.reiniciarLista();
                    break;
                case 5:
                    controller.pararLista();
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 5);
    }
}
