/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Roteador
*Funcao...........: representar um roteador em uma simulacao de rede utilizando threads, 
*                   com controle de envio de pacotes por algoritmo de inundacao
**************************************************************************************************/

package Model;

import java.util.concurrent.Semaphore;
import Controller.ControllerDeMover;

public class Roteador extends Thread {
   
   private Roteador roteadorOrigem; //registra o roteador de cheagada do pacote
   private Fila<Pacote> fila; //fila de pacote  
   private Semaphore mutex;// controle de exclusao mutua
   private Semaphore mutexFila;// controle de sincronizacao da fila
   private EstadoDeEventos evento;// estado atual do roteador
   private RoteadoresAdjacentes interfaceSaida;// roteadores adjacentes
   private Posicao localizacao;// posicao no espaco
   private int id_identificao;// identificador do roteador
   private ControllerDeMover ponteiro;// controlador de envio
   private Integer TTL;// tempo de vida do pacote
   private enum EstadoRoteador {VISITADO, DESCONHECIDO} 
   private EstadoRoteador estadoRoteador;// estado do roteador
   private int versao;// versao do algoritmo de inundacao
   
   /********************************************************************************************
   * Metodo: Roteador
   * Funcao: inicializar o roteador com seus atributos basicos
   * Parametros: int id_identificao - identificador do roteador
   * Retorno: nenhum
   ********************************************************************************************/ 
   public Roteador(int id_identificao) {
        this.mutex = new  Semaphore(1);
        this.fila = new Fila();
        this.mutexFila = new Semaphore(0);
        this.evento = evento.inicio;
        this.id_identificao = id_identificao;
        this.estadoRoteador =  EstadoRoteador.DESCONHECIDO;
   } 
   
   /********************************************************************************************
   * Metodo: run
   * Funcao: iniciar a execucao da thread do roteador
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   @Override
   public void run() {
         System.out.println("Roteador " + id_identificao + " inicializado");
         algoritimoInundacao();
   }      
   
   /********************************************************************************************
   * Metodo: algoritimoInundacao
   * Funcao: selecionar qual versao do algoritmo de inundacao sera executada
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   private void algoritimoInundacao() {
       try {
         switch(versao) {
             case 1:
             algoritimoDeInundacaoVersao1(); 
             break;
             case 2:
             algoritimoDeInundacaoVersao2();
             break;
             case 3:
             algoritimoDeInundacaoPorFSM(); 
             break;
             case 4:
             algoritimoDeInundacaoVersao4();
             break;
         }
       } catch(Exception ex) {
           ex.printStackTrace();
       }
   }
    
   /********************************************************************************************
   * Metodo: algoritimoDeInundacaoVersao1
   * Funcao: implementar o algoritmo de inundacao simples (envia para todos)
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/   
   private void algoritimoDeInundacaoVersao1() throws Exception {
        Pacote pacote = null;
        while(true) {
          switch(evento) {
               case inicio:
                    this.evento = evento.pacoteChegou;
               break;
               case EnviarPacote:
                    do {
                          Pacote pacoteEnvio = new Pacote();
                          pacoteEnvio.setRoteador(this);
                          CordenarEnvio enviar =  new CordenarEnvio(ponteiro); 
                          enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(), pacoteEnvio);
                          enviar.start(); 
                          controlarEnvio();
                    } while(interfaceSaida.next());
                    this.evento = evento.pacoteChegou;
               break;
               case pacoteChegou:
                  DownMutex(mutexFila);
                     DownMutex(mutex);
                        pacote = fila.desenfileirar();
                  UpMutex(mutex);   
                  
                   this.evento = evento.EnviarPacote;
               break;
         }  
            
        }
   }
   
   /********************************************************************************************
   * Metodo: algoritimoDeInundacaoVersao2
   * Funcao: evitar envio para o roteador de origem do pacote
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   private void algoritimoDeInundacaoVersao2() throws Exception {
        Pacote pacote = null;
        while(true) {
          switch(evento) {
               case inicio:
                    this.evento = evento.pacoteChegou;
               break;
               case EnviarPacote:
                    do {
                      if(interfaceSaida.getRoteadorAtual() != pacote.roteadorAnterior()) { 
                          Pacote pacoteEnvio = new Pacote();
                          pacoteEnvio.setRoteador(this);
                          CordenarEnvio enviar =  new CordenarEnvio(ponteiro); 
                          enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(), pacoteEnvio);
                          enviar.start(); 
                          controlarEnvio();
                       }   
                    } while(interfaceSaida.next());
                    this.evento = evento.pacoteChegou;
               break;
               case pacoteChegou:
                  DownMutex(mutexFila);
                     DownMutex(mutex);
                        pacote = fila.desenfileirar();
                  UpMutex(mutex);   
                  
                   this.evento = evento.EnviarPacote;
               break;
         }  
            
        }
   }
  
   /********************************************************************************************
   * Metodo: algoritimoDeInundacaoPorFSM , versao 3
   * Funcao: implementar inundacao com controle de TTL (tempo de vida do pacote)
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   private void algoritimoDeInundacaoPorFSM() throws Exception {
        Pacote pacote = null;
        while(true) {
          switch(evento) {
               case inicio:
                    this.evento = evento.pacoteChegou;
               break;
               case EnviarPacote:
                    do {
                      if(interfaceSaida.getRoteadorAtual() != pacote.roteadorAnterior()) { 
                          Pacote pacoteEnvio = new Pacote();
                          pacoteEnvio.setTTL(pacote.getTTL());
                          pacoteEnvio.setRoteador(this);
                          CordenarEnvio enviar =  new CordenarEnvio(ponteiro); 
                          enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(), pacoteEnvio);
                          enviar.start(); 
                          controlarEnvio();
                       }   
                    } while(interfaceSaida.next());
                    this.evento = evento.pacoteChegou;
               break;
               case pacoteChegou:
                     DownMutex(mutexFila);
                     DownMutex(mutex);
                        pacote = fila.desenfileirar();
                     UpMutex(mutex);   
                  
                 if(pacote.getTTL() > 0) { 
                    this.evento = evento.EnviarPacote;
                    pacote.decrementarTTL();
                 }  else {
                    this.evento = evento.pacoteChegou;
                 }
               break;
         }  
            
        }
   }
 
 /********************************************************************************************
   * Metodo: algoritimoDeInundacaoVersao4
   * Funcao: implementar inundacao com TTL e controle de visitacao do roteador
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/  
 private void algoritimoDeInundacaoVersao4() throws Exception {
         Pacote pacote = null;
     while(true) {
         switch(evento) {
             case inicio:
                 this.evento = evento.pacoteChegou;
             break;
             case EnviarPacote:
                    do {
                      if(interfaceSaida.getRoteadorAtual() != pacote.roteadorAnterior()) { 
                          Pacote pacoteEnvio = new Pacote();
                          pacoteEnvio.setTTL(pacote.getTTL());
                          pacoteEnvio.setRoteador(this);
                          CordenarEnvio enviar =  new CordenarEnvio(ponteiro); 
                          enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(), pacoteEnvio);
                          enviar.start(); 
                          controlarEnvio();
                       }   
                    } while(interfaceSaida.next());
                    this.evento = evento.pacoteChegou;
             break;
             case pacoteChegou:
                
                DownMutex(mutexFila);
                DownMutex(mutex);
                    pacote = fila.desenfileirar();
                UpMutex(mutex);
                
               
                if(pacote.getTTL() > 0 && this.estadoRoteador == EstadoRoteador.DESCONHECIDO) { 
                    this.evento = evento.EnviarPacote;
                    pacote.decrementarTTL();
                } else {
                    this.evento = evento.pacoteChegou;
                }
                this.estadoRoteador = EstadoRoteador.VISITADO;
             break;
         }
     }
 }
 
  /********************************************************************************************
   * Metodo: iniciarEnvio
   * Funcao: iniciar o envio de um pacote com TTL definido
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   public void iniciarEnvio() {
       this.estadoRoteador = EstadoRoteador.DESCONHECIDO;
       Pacote pacote = new Pacote();
       pacote.setTTL(TTL);
       enfileirarPacote(pacote);
   }
   
   /********************************************************************************************
   * Metodo: enfileirarPacote
   * Funcao: inserir pacote na fila com controle de concorrencia
   * Parametros: Pacote pacote
   * Retorno: nenhum
   ********************************************************************************************/
   public void enfileirarPacote(Pacote pacote) {    
           try { 
            DownMutex(mutex);
             if(!fila.cheia()) {
                 fila.enfileirar(pacote);
                 UpMutex(mutex);
                 UpMutex(mutexFila);
             } else {
               UpMutex(mutex);
             }
           } catch(Exception ex) {
              ex.printStackTrace();
           }
   }
   
   
   public void sinalizarEventoDeChegada(Pacote pacote) {
           enfileirarPacote(pacote);
   }


   public void definirAdjacencia(RoteadoresAdjacentes interfaceSaida) {
           this.interfaceSaida = interfaceSaida;
   }
  
  /********************************************************************************************
   * Metodo: DownMutex
   * Funcao: adquirir o semaforo
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
   * Funcao: liberar o semaforo
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

   public int getId_identificao() {
         return id_identificao;
   }

   public void setPosicao(Posicao xs) {
          this.localizacao = xs;
   }

   public Posicao getPosicao() {
          return localizacao;
   }

   public void setControllerDeMover(ControllerDeMover ponteiro) {
          this.ponteiro = ponteiro;
   } 

   public void setEmissor() {
         this.evento = evento.RoteadorEmissor;
   } 

   public void remarcarEstado() {
        estadoRoteador = EstadoRoteador.DESCONHECIDO;
   }
   
   public void controlarEnvio() {
        try {
            Thread.sleep(104);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
   }
   
   public void definirVersao(int b) {
         this.versao = b;
   }
   
   public void definirTTL(int x) {
       this.TTL = x;
   }
}
