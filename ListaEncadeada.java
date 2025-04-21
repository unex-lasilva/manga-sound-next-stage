public class ListaEncadeada {
    private No cabeca; //Referencia para o primeiro nó (cabeca) da lista
    private int tamanho; //Quantidade de elementos atualmente armazenados na lista. 

    public ListaEncadeada() {
        cabeca = null;
        tamanho = 0;
    }

    // Adiciona o elemento no final da lista.
   
    public void append(Object value) {
        No novo = new No(value); //nó contendo o valor a ser adicionado
        if(isEmpty()){
            cabeca = novo;
        } else {
            No atual = cabeca;
            while(atual.getProx() != null){
                atual = atual.getProx();
            }
            atual.setProx(novo);
        }
        tamanho++;
    }

    // Remove o elemento na posição indicada (iniciando em 0).
   
    public boolean remove(int position) {
        if(position < 0 || position >= tamanho) {
            return false;
        }
        if(position == 0) {
            cabeca = cabeca.getProx();
        } else {
            No anterior = getNo(position - 1); //anterior: nó antes do que será removido
            No atual = anterior.getProx();     // atual: nó que será removido
            anterior.setProx(atual.getProx());
        }
        tamanho--;
        return true;
    }

    // Insere o elemento na posição indicada (iniciando em 0).
    public void insertAt(int position, Object value) {
        if(position < 0 || position > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        No novo = new No(value); // novo: nó a ser inserido na posição desejada
        if(position == 0) {
            novo.setProx(cabeca);
            cabeca = novo;
        } else {
            No anterior = getNo(position - 1); // anterior: nó anterior à posição de inserção
            novo.setProx(anterior.getProx());
            anterior.setProx(novo);
        }
        tamanho++;
    }

    // Retorna se a lista está vazia.
    public boolean isEmpty() {
        return tamanho == 0;
    }

    // Retorna o tamanho da lista.
    public int size() {
        return tamanho;
    }

    // Adiciona todos os elementos de outra lista a esta lista.
    public void addAll(ListaEncadeada list) {
        for (int i = 0; i < list.size(); i++){ //i: índice de iteração sobre a lista fornecida
            this.append(list.get(i));
        }
    }

    // Retorna o índice do elemento, ou -1 se não encontrado.
    public int indexOf(Object value) {
        No atual = cabeca; //atual: percorre os nós da lista
        int indice = 0; //indice: contador de posição
        while(atual != null){
            if(atual.getValor().equals(value)){
                return indice;
            }
            indice++;
            atual = atual.getProx();
        }
        return -1;
    }

    // Verifica se a lista contém o elemento.
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    // Limpa a lista.
    public boolean clear() {
        cabeca = null;
        tamanho = 0;
        return true;
    }

    // Retorna o elemento na posição indicada.
    public Object get(int position) {
        if(position < 0 || position >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        No node = getNo(position); //node: nó localizado na posição desejada
        return node.getValor();
    }
    
    // Método auxiliar que retorna o nó em determinada posição.
    private No getNo(int position) { //atual: percorre a lista até a posição indicada
        No atual = cabeca;
        for (int i = 0; i < position; i++) {
            atual = atual.getProx();
        }
        return atual;
    }
}
