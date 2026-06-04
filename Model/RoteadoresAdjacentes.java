package Model;


public class RoteadoresAdjacentes {
     private ListaDupla<Aresta<Roteador, Posicao>> interfaceSaida;
     private ListaDupla.Element ponteiro;

     public RoteadoresAdjacentes(ListaDupla<Aresta<Roteador, Posicao>> interfaceSaida) {
            this.interfaceSaida = interfaceSaida;
            ponteiro = interfaceSaida.getHead();           
     }

     public boolean next() {

          if(ponteiro.getNext() != null) {
               ponteiro = ponteiro.getNext();
               return true; 
          } else {
               ponteiro = interfaceSaida.getHead();
               return false;
          } 
     }

     
     
     public Roteador getRoteadorAtual() throws Exception {
           if(ponteiro == null) {
              throw new Exception("");
           }
           return (((Aresta<Roteador, Posicao>)(ponteiro.getValor())).getNode()).getValor();
     }
     
}
