/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: CordenarEnvio
*Funcao...........: coordenar o envio de pacotes entre roteadores, controlando animacao,
*                   contadores globais e sincronizacao de envio em ambiente concorrente
**************************************************************************************************/

package Model;

import Model.Posicao;
import Controller.ControllerDeMover;
import Model.Roteador;
import Model.Pacote;
import java.util.concurrent.Semaphore;


public class CordenarEnvio extends Thread {
     private ControllerDeMover controllerMover;
     private Posicao posicaoInicial; private Posicao posicaoFinal;
     private Roteador Origem; private Roteador Destino;
     private static int IDRotadorEnvio;
     private static Integer contadorPacotes = 0;
     private static Integer PacotesPendentes = 0;
     private static boolean flag;
     private Pacote pacote;  
     private static Semaphore mutex;
     private static Semaphore mutexPacotesPendentes;
        
     static {
         mutex = new Semaphore(1);
         mutexPacotesPendentes = new  Semaphore(1);
     }     
     
     /********************************************************************************************
     * Metodo: CordenarEnvio
     * Funcao: inicializar o controlador de envio com referencia ao controller de movimento
     * Parametros: ControllerDeMover controllerMover
     * Retorno: nenhum
     ********************************************************************************************/ 
     public CordenarEnvio(ControllerDeMover controllerMover) {
          this.controllerMover = controllerMover;
     }
     
     /********************************************************************************************
     * Metodo: run
     * Funcao: executar o fluxo completo de envio do pacote (controle, animacao e entrega)
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     @Override
     public void run() {
          controlarContador();
          conatarPacotesPendentes();
          animarEnviarPacote(posicaoInicial, posicaoFinal); 
          sinalizarChegada();
          decrementarContador();
          administrarPacotes(); 
          
     }
     
     /********************************************************************************************
     * Metodo: enviarPacote
     * Funcao: configurar os dados necessarios para envio do pacote entre roteadores
     * Parametros: Roteador Origem, Roteador Destino, Pacote pacote
     * Retorno: nenhum
     ********************************************************************************************/
     public void enviarPacote(Roteador Origem, Roteador Destino, Pacote pacote) {
               posicaoInicial = Origem.getPosicao();
               posicaoFinal = Destino.getPosicao();
               this.Origem = Origem; this.Destino = Destino; 
               this.pacote = pacote; 
     }
     
     /********************************************************************************************
     * Metodo: decrementarContador
     * Funcao: decrementar o numero de pacotes pendentes com controle de concorrencia
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/ 
     private void decrementarContador() {
        DownMutex(mutexPacotesPendentes); 
          PacotesPendentes--; 
        UpMutex(mutexPacotesPendentes);
     }
     
     
     /********************************************************************************************
     * Metodo: administrarPacotes
     * Funcao: verificar se nao ha mais pacotes pendentes e liberar interface
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     private void administrarPacotes() {
              if(PacotesPendentes == 0) {
                 controllerMover.reabilitarButao();  
                 contadorPacotes = 0;
              } 
     }
     
     /********************************************************************************************
     * Metodo: controlarContador
     * Funcao: incrementar contador global de pacotes enviados
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     private void controlarContador() {
        
        DownMutex(mutex); 
         contadorPacotes++;
         controllerMover.atualizarContador(contadorPacotes);
        UpMutex(mutex);  
     }
     
     /********************************************************************************************
     * Metodo: conatarPacotesPendentes
     * Funcao: incrementar o numero de pacotes pendentes
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     private void conatarPacotesPendentes() {
           DownMutex(mutexPacotesPendentes);
             PacotesPendentes++;   
          UpMutex(mutexPacotesPendentes); 
     }
     
     /********************************************************************************************
     * Metodo: animarEnviarPacote
     * Funcao: animar o movimento do pacote entre duas posicoes usando interpolacao
     * Parametros: Posicao p0, Posicao p
     * Retorno: nenhum
     ********************************************************************************************/
     private void animarEnviarPacote(Posicao p0, Posicao p) {
            
            funcaoMovimentoParametrico f = t -> { 
               double x0 = Projecao(0,p0); double x = Projecao(0,p);
               double y0 = Projecao(1,p0); double y = Projecao(1,p);
               return new Posicao(x0 + t*(x-x0), y0 + t*(y-y0));
            };
            controllerMover.Mover(f);
     }    
      
     /********************************************************************************************
     * Metodo: sinalizarChegada
     * Funcao: informar ao roteador destino que o pacote chegou
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     private void sinalizarChegada() {
            Destino.sinalizarEventoDeChegada(pacote);
            
     }
     
     /********************************************************************************************
     * Metodo: setIDEnvio
     * Funcao: definir identificador de envio global
     * Parametros: int x
     * Retorno: nenhum
     ********************************************************************************************/
     public static void setIDEnvio(int x) {
            IDRotadorEnvio = x;
     }
     
     /********************************************************************************************
     * Metodo: Projecao
     * Funcao: obter coordenada X ou Y de uma posicao
     * Parametros: int tipo, Posicao p
     * Retorno: double
     ********************************************************************************************/
     public double Projecao(int tipo, Posicao p) {
           
           if(tipo == 0) {
               return p.getX();
           }
               return p.getY();
     }
     
     /********************************************************************************************
     * Metodo: DownMutex
     * Funcao: adquirir semaforo
     * Parametros: Semaphore semaphore
     * Retorno: nenhum
     ********************************************************************************************/
     private void DownMutex(Semaphore semaphore) {
         try {
               semaphore.acquire();
         } catch(Exception ex) {
               ex.printStackTrace();
         }
     }
     
     /********************************************************************************************
     * Metodo: UpMutex
     * Funcao: liberar semaforo
     * Parametros: Semaphore semaphore
     * Retorno: nenhum
     ********************************************************************************************/
     private void UpMutex(Semaphore semaphore) {
         try {
              semaphore.release();
         } catch(Exception ex) {
               ex.printStackTrace();
         }
     }
}
