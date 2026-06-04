/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Pacote
*Funcao...........: representar um pacote trafegando na rede, contendo informacoes sobre
*                   emissor, receptor, roteador anterior e controle de tempo de vida (TTL)
**************************************************************************************************/


package Model;

public class Pacote {
     private Roteador emissor;
     private Roteador receptor;
     private Roteador roteadorAnterior;
     private int TTL= 5;
     
     /********************************************************************************************
     * Metodo: Pacote (construtor)
     * Funcao: inicializa o pacote com valores padrao
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     public Pacote() {
     }
     
     /********************************************************************************************
     * Metodo: setRoteador
     * Funcao: define o roteador anterior pelo qual o pacote passou
     * Parametros: roteador - ultimo roteador visitado
     * Retorno: vazio
     ********************************************************************************************/
     public void setRoteador(Roteador roteador) {
         this.roteadorAnterior = roteador;
     }  

     /********************************************************************************************
     * Metodo: setRoteador
     * Funcao: define o roteador anterior pelo qual o pacote passou
     * Parametros: roteador - ultimo roteador visitado
     * Retorno: vazio
     ********************************************************************************************/
     public void setTTL(int x) {
         this.TTL = x;
     }
     
     /********************************************************************************************
     * Metodo: getEmissor
     * Funcao: retorna o roteador emissor do pacote
     * Parametros: nenhum
     * Retorno: objeto Roteador
     ********************************************************************************************/
     public Roteador getEmissor() {
          return emissor;
     }

     /********************************************************************************************
     * Metodo: getRecptor
     * Funcao: retorna o roteador receptor do pacote
     * Parametros: nenhum
     * Retorno: objeto Roteador
     ********************************************************************************************/
     public Roteador getRecptor() {
          return receptor;
     }
       
     /********************************************************************************************
     * Metodo: decrementarTTL
     * Funcao: reduz o valor do TTL em uma unidade
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     public void decrementarTTL() {
             this.TTL--;
     } 
      
     /********************************************************************************************
     * Metodo: roteadorAnterior
     * Funcao: retorna o ultimo roteador visitado pelo pacote
     * Parametros: nenhum
     * Retorno: objeto Roteador
     ********************************************************************************************/ 
     public Roteador roteadorAnterior() {
         return this.roteadorAnterior;
     }
     
     /********************************************************************************************
     * Metodo: getTTL
     * Funcao: retorna o valor atual do TTL do pacote
     * Parametros: nenhum
     * Retorno: inteiro
     ********************************************************************************************/
     public int getTTL() {
            return this.TTL;
     }
}
