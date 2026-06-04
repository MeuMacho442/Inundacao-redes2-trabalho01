/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ArestaView
*Funcao...........: representar visualmente uma aresta de um grafo por meio de uma linha
*                   definida por coordenadas iniciais e finais
**************************************************************************************************/

package View;

import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class ArestaView {

     private double X0, Y0;
     private double X, Y;
     
     /********************************************************************************************
     * Metodo: ArestaView (construtor)
     * Funcao: inicializa a estrutura da aresta visual
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     public ArestaView() {
     }

     /********************************************************************************************
     * Metodo: criarAresta
     * Funcao: instancia uma linha representando uma aresta entre dois pontos no plano
     * Parametros: X0, Y0 - coordenadas iniciais
     *             X, Y   - coordenadas finais
     * Retorno: objeto Line representando a aresta
     ********************************************************************************************/
     public Line criarAresta(double X0, double Y0, double X, double Y) {
            Line linha = new Line(X0, Y0, X, Y);
            linha.setStroke(Color.BLACK);  
            return linha;
     } //fim do metodo criarAresta
}