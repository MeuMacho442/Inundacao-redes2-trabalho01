/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Animacao
*Funcao...........: controlar a animacao de um pacote no painel grafico por meio de uma funcao
*                   parametrica, permitindo representar movimentos ao longo de curvas
**************************************************************************************************/


package View;

import View.Janela;
import Model.funcaoMovimentoParametrico;
import Model.Posicao;
import javafx.animation.AnimationTimer;

public class Animacao {
      private Janela janela;
      /********************************************************************************************
      * Metodo: Animacao (construtor)
      * Funcao: inicializa o controlador de animacoes associado a janela principal
      * Parametros: janela - referencia da interface principal
      * Retorno: nenhum
      ********************************************************************************************/
      public Animacao(Janela janela) {
           this.janela = janela;
      }
      
      /********************************************************************************************
      * Metodo: AnimarPonto
      * Funcao: executa a animacao de um ponto ao longo de uma curva parametrica definida por uma
      *         funcao de alta ordem
      * Parametros: f - funcao parametrica que define a trajetoria do movimento
      * Retorno: vazio
      ********************************************************************************************/
      public void AnimarPonto(funcaoMovimentoParametrico f) {//funcao de alta ordem, permite qualquer movimento sobre curvas parametricas
                PacoteView pacoteView = new PacoteView(janela);
                int FPS = 320; 
                for(double t = 0; t <= 1; t += 0.001) {
                      double x =  f.implementarAcao(t).getX();  double y =  f.implementarAcao(t).getY();
                      pacoteView.escreverPacoteView(x,y);
                      controlarTaxaFPS(FPS);
                }
                pacoteView.removerNodePonto();
      } //fim do metodo AnimarPonto

      /********************************************************************************************
      * Metodo: controlarTaxaFPS
      * Funcao: regula a velocidade da animacao por meio de pausas na execucao da thread
      * Parametros: FPS - quadros por segundo desejados
      * Retorno: vazio
      ********************************************************************************************/
      private void controlarTaxaFPS(int FPS) {
           try {
              Thread.sleep((long)(1000/FPS)); 
           } catch(Exception ex) {
              ex.printStackTrace();
           } 
      }//fim do metodo controlarTaxaFPS
}
