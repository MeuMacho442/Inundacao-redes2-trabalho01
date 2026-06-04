/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Grafo
*Funcao...........: representar um grafo generico utilizando lista de adjacencia, permitindo
*                   insercao de vertices, arestas e operacoes de busca
**************************************************************************************************/

package Model;

import Model.ListaDupla.Element;

public class Grafo<T,K> {
     private int tamanho;
     private Node<T,K>[] listaNos; 
     private int numeroAresta = 0;
     private int chave = 0;
     private ListaDupla<Aresta<T,K>>[] listaAdjacencia;    
     
     /********************************************************************************************
     * Metodo: Grafo (construtor)
     * Funcao: inicializa a estrutura do grafo com tamanho fixo
     * Parametros: tamanho - numero maximo de vertices
     * Retorno: nenhum
     ********************************************************************************************/
     @SuppressWarnings("unchecked")
     public Grafo(int tamanho) {
         this.tamanho = tamanho;
         listaNos = new Node[tamanho];
         listaAdjacencia = new ListaDupla[tamanho];
     }
      
     /********************************************************************************************
     * Metodo: inserirVertice
     * Funcao: adiciona um novo vertice ao grafo
     * Parametros: no - vertice a ser inserido
     * Retorno: vazio
     ********************************************************************************************/
     @SuppressWarnings("unchecked")
     public void inserirVertice(Node no) throws Exception {
         if(!(chave < tamanho)) {
             throw new Exception("excessao do tamanho");
         }

         if(procurarNode(no)) {
            throw new Exception("No existente");
         }
         no.setChave(chave);  
         listaNos[chave] = no;
         listaAdjacencia[chave] = new ListaDupla();
         chave++; 
     }
     
     /********************************************************************************************
     * Metodo: Grafo (construtor)
     * Funcao: inicializa a estrutura do grafo com tamanho fixo
     * Parametros: tamanho - numero maximo de vertices
     * Retorno: nenhum
     ********************************************************************************************/
     public Node<T, K>[] getConjuntoVertice() {
           return listaNos;
     }
       
    /********************************************************************************************
     * Metodo: inserirAresta
     * Funcao: conecta dois vertices no grafo (grafo nao direcionado)
     * Parametros: w, r - vertices
     *             peso - peso da aresta
     * Retorno: vazio
     ********************************************************************************************/ 
     public void inserirAresta(Node<T,K> w, Node<T,K> r, int Peso) throws Exception {
         if(!procurarNode(w) || !procurarNode(r)) {
              throw new Exception("insercao invalida");
         }
         Aresta<T,K> arestaW = new Aresta(w, Peso);
         Aresta<T,K> arestaR = new Aresta(r, Peso);
         listaAdjacencia[w.getChave()].inserirFim(arestaR);
         listaAdjacencia[r.getChave()].inserirFim(arestaW);
         numeroAresta++;
     }
     
     /********************************************************************************************
      * Metodo: getConjuntoVertice
      * Funcao: retorna o conjunto de vertices armazenados no grafo
      * Parametros: nenhum
      * Retorno: vetor de nodes
      ********************************************************************************************/  
     public ListaDupla<Aresta<T,K>> getAdjacencia(int chave) throws Exception {
            if(chave >= tamanho) {
                throw new Exception("excessao do tamanho");
            }
            
            return listaAdjacencia[chave];
     }

      /********************************************************************************************
      * Metodo: mostrarGrafo
      * Funcao: gera uma representacao textual do grafo, listando vertices e suas adjacencias
      *  Parametros: comprimento - quantidade de vertices a serem exibidos
      * Retorno: string representando o grafo
      ********************************************************************************************/
     public String mostrarGrafo(int comprimento) {
           String res = "";
           for(int i = 0; i < comprimento; i++) {
                  res += String.valueOf(listaNos[i].getValor()) + " -> " + listarAdjacencia(listaAdjacencia[listaNos[i].getChave()]) +"\n";
           } 
          return res;           
     }
     
     /********************************************************************************************
     * Metodo: listarAdjacencia
     * Funcao: percorre uma lista de adjacencia e retorna uma representacao textual das arestas
     * Parametros: lista - lista de arestas
     * Retorno: string com os elementos da lista
     ********************************************************************************************/
     private String listarAdjacencia(ListaDupla<Aresta<T,K>> lista) {
            Element ptr;
            String res = "";
            for(ptr = lista.getHead(); ptr != null; ptr = ptr.getNext()) {
                  res += String.valueOf(((Aresta<T,K>)ptr.getValor()).getNode().getValor()) + " | " +  String.valueOf(((Aresta<T,K>)ptr.getValor()).getPeso()) + " -> ";
            }
            return res;
     }
     
     /********************************************************************************************
     * Metodo: procurarNode
     * Funcao: verifica se um determinado node existe no grafo
     * Parametros: no - node a ser verificado
     * Retorno: booleano indicando existencia
     ********************************************************************************************/
     //eu poderia usar alguma estrutura de dados como AVL para aumentar a efetividade de busca.
     public boolean procurarNode(Node no) {
           for(int i = 0; i < tamanho && listaNos[i] != null; i++) {
               if(no == listaNos[i]) {
                     return true;
               }
           }
           return false;
     }
     
     /********************************************************************************************
      * Metodo: procurarNodeChave
      * Funcao: verifica se existe um vertice com determinado valor associado
      * Parametros: valor - valor a ser buscado
      * Retorno: booleano indicando existencia
      ********************************************************************************************/
     public boolean procurarNodeChave(T valor) {
           for(int i = 0; i < tamanho && listaNos[i] != null; i++) {
               if(valor.equals(listaNos[i].getValor())) {
                    return true;
               }
           }
          return false;
     }
     /********************************************************************************************
     * Metodo: procurarNodeChave
     * Funcao: verifica se existe um vertice com determinado valor associado
     * Parametros: valor - valor a ser buscado
     * Retorno: booleano indicando existencia
     ********************************************************************************************/
     public Node<T,K> getNode(int chave) {
           Node<T,K> T = null;
           try {
               T = listaNos[chave];
           } catch(Exception ex) {
              System.out.println("tamanho do array excedido");
           }
           return T; 
     }
  
}
