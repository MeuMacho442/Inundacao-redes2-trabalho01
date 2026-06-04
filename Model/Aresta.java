package Model;

public class Aresta<T,K> {
     int peso;
     Node<T, K> node;
     public Aresta(Node<T, K> node, int peso) {
          this.peso = peso;
          this.node = node;
     }
     
     protected Node<T, K> getNode() {
        return node;
     }
     
     protected int getPeso() {
        return peso;
     }
}
