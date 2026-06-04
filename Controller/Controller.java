/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Controller
*Funcao...........: coordenar a inicializacao do sistema, integrando a configuracao da rede,
*                   gerenciamento de eventos e controle de animacoes
**************************************************************************************************/


package Controller;

import View.Janela;
import Model.Operadores;
import Model.LerArquivo;
import Controller.Excessao.ExcessaoIncosistenciaArquivo;
import Model.Operadores;

public class Controller {
      private AlocarNode alocarNode;
      private GerenciadorEventos eventos;
      private ControllerDeMover controleMover;
      private Janela janela; 
      
      /********************************************************************************************
      * Metodo: Controller (construtor)
      * Funcao: inicializa os componentes principais de controle do sistema
      * Parametros: janela - referencia da interface principal
      * Retorno: nenhum
      ********************************************************************************************/
      public Controller(Janela janela) {
          this.janela = janela;
          this.eventos = new GerenciadorEventos(this.janela);
          this.alocarNode = new AlocarNode(this.janela);
          this.controleMover = new ControllerDeMover(this.janela);
      }
      
      /********************************************************************************************
      * Metodo: iniciar
      * Funcao: realiza a inicializacao da rede e registra os eventos principais do sistema
      * Parametros: nenhum
      * Retorno: vazio
      ********************************************************************************************/
      public void iniciar() {
          try { 
            alocarNode.setNode();
            alocarNode.setAresta();
            eventos.start(alocarNode);
          } catch(ExcessaoIncosistenciaArquivo ex) {
            System.out.println(ex.getMessage());
          }
      } //fim do metodo inicia
}
