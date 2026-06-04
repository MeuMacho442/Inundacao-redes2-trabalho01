/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ControllerDeMover
*Funcao...........: atuar como intermediador entre a logica de movimentacao e a interface
*                   grafica, delegando a execucao da animacao e atualizacao de elementos visuais
**************************************************************************************************/

package Controller;

import View.Janela;
import Model.*;
import View.Animacao;

public class ControllerDeMover {

       private Janela janela;
       private Animacao anima;

       /********************************************************************************************
       * Metodo: ControllerDeMover (construtor)
       * Funcao: inicializa o controlador de movimentacao com referencia da janela e do modulo
       *         de animacao
       * Parametros: janela - referencia da interface principal
       * Retorno: nenhum
       ********************************************************************************************/
       public ControllerDeMover(Janela janela) {
              this.janela = janela;
              this.anima = new Animacao(janela);
       } 
       
       /********************************************************************************************
       * Metodo: Mover
       * Funcao: delega a execucao da animacao de um ponto com base em uma funcao parametrica
       * Parametros: f - funcao que define o movimento
       * Retorno: vazio
       ********************************************************************************************/
       public void Mover(funcaoMovimentoParametrico f) {
             anima.AnimarPonto(f);
       } //fim do metodo Move

       /********************************************************************************************
       * Metodo: atualizarContador
       * Funcao: atualiza o contador de pacotes exibido na interface
       * Parametros: contador - quantidade atual de pacotes
       * Retorno: vazio
       ********************************************************************************************/
       public void atualizarContador(Integer contador) {
            janela.editarTexto(contador);
       }//fim do metodo atualizarContador
       
       /********************************************************************************************
       * Metodo: reabilitarButao
       * Funcao: reativa o botao principal da interface apos execucao
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/
       public void reabilitarButao() {
           janela.rabilitarButao();
       }//fim do metodo reabilitarButao
}