package Model;


import Model.ListaDupla.Element;


public class InicializarSubRede {
      private Grafo<Roteador, Posicao> grafo;
      private Node<Roteador, Posicao>[] listaNode;
      private int idEmissor, idReceptor;
      private enum EstadoRoteador {INICIADO, NAOINICIADO}
      private EstadoRoteador estado;


      public InicializarSubRede(Grafo<Roteador, Posicao> grafo) {
            this.grafo = grafo;
            estado = EstadoRoteador.NAOINICIADO;
            this.listaNode = grafo.getConjuntoVertice();
      }
      
      public void iniciarSimulacao(int idEmissor, int idReceptor,  int versao, int ttl) { 
         try {
            
            for(int i = 0; i < listaNode.length; i++) {
                 
                (listaNode[i].getValor()).definirVersao(versao);
                (listaNode[i].getValor()).definirTTL(ttl);
                (listaNode[i].getValor()).remarcarEstado();
                if((listaNode[i].getValor()).getId_identificao() == idEmissor-1) {
                       (listaNode[i].getValor()).iniciarEnvio();
                }

                if(estado == EstadoRoteador.NAOINICIADO) {
                   (listaNode[i].getValor()).setPosicao(listaNode[i].getSegundoValor());
                   (listaNode[i].getValor()).definirAdjacencia(new RoteadoresAdjacentes(grafo.getAdjacencia(listaNode[i].getChave())));
                   (listaNode[i].getValor()).start();   
                }                 
                
            }
            estado = EstadoRoteador.INICIADO;
         } catch(Exception ex) {
             ex.printStackTrace();
         }
      }
      
}
