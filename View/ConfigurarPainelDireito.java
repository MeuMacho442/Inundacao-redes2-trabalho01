/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ConfigurarPainelDireito
*Funcao...........: configurar e gerenciar o painel direito da interface grafica, sendo 
*                   responsavel pela manipulacao de elementos visuais como textos e 
*                   propriedades estruturais do painel
**************************************************************************************************/


package View;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.application.Platform;

public class ConfigurarPainelDireito {
      private Pane painel;
      private static final int larguraPanelDireito = 800;
      private static final int alturaPanelDireito = 650;
       
      /********************************************************************************************
      * Metodo: ConfigurarPainelDireito (construtor)
      * Funcao: inicializa o painel direito aplicando suas configuracoes iniciais de layout e estilo
      * Parametros: painel - referencia ao painel que sera configurado
      * Retorno: nenhum
      ********************************************************************************************/
      public ConfigurarPainelDireito(Pane painel) {
            this.painel = painel;
            rearranjarPainel();
      }  //fim do metodo ConfigurarPainelDireito
      
      /********************************************************************************************
      * Metodo: adicionarTexto
      * Funcao: adiciona um elemento textual ao painel direito em uma posicao especifica,
      *         encapsulando o texto em um StackPane para controle de layout
      * Parametros: texto - conteudo textual a ser exibido
      *             x - coordenada horizontal de posicionamento
      *             y - coordenada vertical de posicionamento
      * Retorno: vazio
      ********************************************************************************************/
      public void adicionarTexto(String texto, double x, double y) {
         Platform.runLater(() -> {
            double largura = 20; double altura = 20;    
            StackPane stackPane = new StackPane(); 
            stackPane.setPrefSize(largura, altura);
            Label textoLabel = new Label(texto);
            textoLabel.setTextFill(Color.BLACK);
            textoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 8));
            stackPane.getChildren().add(textoLabel); 
            stackPane.setLayoutX(x-largura/2); stackPane.setLayoutY(y-altura/2);
            painel.getChildren().add(stackPane);
        });
      }//fim do metodo adicionarTexto
      
     /********************************************************************************************
      * Metodo: rearranjarPainel
      * Funcao: define propriedades visuais e estruturais do painel direito, como cor de fundo,
      *         posicao e dimensoes
      * Parametros: nenhum
      * Retorno: vazio
      ********************************************************************************************/
     private void rearranjarPainel() {               
 	      painel.setStyle("-fx-background-color: #FFDBBB;");
              painel.setLayoutX(202);
              painel.setPrefSize(larguraPanelDireito, alturaPanelDireito);  
     } //fim do metodo rearranjarPainel

    /********************************************************************************************
    * Metodo: getLargurar
    * Funcao: retorna a largura padrao definida para o painel direito
    * Parametros: nenhum
    * Retorno: inteiro correspondente a largura
    ********************************************************************************************/
    public static int getLargurar() {
           return larguraPanelDireito;
    } //fim do metodo getLargurar
    
    /********************************************************************************************
    * Metodo: getAltura
    * Funcao: retorna a altura padrao definida para o painel direito
    * Parametros: nenhum
    * Retorno: inteiro correspondente a altura
    ********************************************************************************************/
    public static int getAltura() {
           return alturaPanelDireito;
    }//fim do metodo getAltura
}