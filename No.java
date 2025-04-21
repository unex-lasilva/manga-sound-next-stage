public class No {
    private Object valor; // valor: conteúdo armazenado no nó (genérico)
    private No prox;  // prox: referência para o próximo nó da lista encadeada

    public No() {
        this.valor = null;
        this.prox = null;
    }

    public No(Object valor) {
        this.valor = valor;
        this.prox = null;
    }

    public Object getValor() {
        return valor;
    }

    public No getProx() {
        return prox;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
