/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ConfigurarPainel
*Funcao...........: gerenciar a construcao e interacao dos componentes do painel lateral,
*                   incluindo entradas de dados, selecao de versoes, exibicao de informacoes
*                   e controle de eventos associados a interface grafica
**************************************************************************************************/


package View;

import javafx.scene.layout.Pane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import Controller.funcaoAltaOrdem; 
import javafx.scene.control.TextFormatter;


public class ConfigurarPainel {
     private Pane painel;
     private ComboBox<String> selecionar;
     private Pane paneTextoReceptor;
     private VBox sequenciaArranjo;
     private Button comecar;
     private TextField textoInput1;
     private TextField textoInput2;
     private TextField textoTTL;
     private Spinner<Integer> spinner;
     private Button BUTTONINFO;
     private Aviso aviso;
     private int estado = 0;
     private Label TextoContadorPacotes;
     
     /********************************************************************************************
     * Metodo: ConfigurarPainel (construtor)
     * Funcao: inicializa os componentes principais do painel e aciona o gerenciamento central
     * Parametros: painel - referencia ao painel base da interface
     * Retorno: nenhum
     ********************************************************************************************/
     public ConfigurarPainel(Pane painel) {
            this.painel = painel;
            this.aviso = new Aviso();
            this.sequenciaArranjo = new VBox(50);
            this.TextoContadorPacotes = new Label();
            centralGerenciamento();
            
     } 
     
     /********************************************************************************************
     * Metodo: centralGerenciamento
     * Funcao: coordena a inicializacao estrutural do painel, incluindo layout, botoes,
     *         campos de entrada e elementos informativos
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     public void centralGerenciamento() {
               rearranjarPainel();
               configurarHBox();
               estruturarButao();  
               estruturarButaoINFO(); 
               mostrarContador();     
               painel.getChildren().add(sequenciaArranjo);
               painel.getChildren().add(selecionarTexo());
     }//fim do metodo centralGerenciamento
      
      
     /********************************************************************************************
     * Metodo: estruturarButao
     * Funcao: cria e posiciona o botao principal de execucao ("comecar") no painel
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void estruturarButao() {
               String cor = "#C0C0C0";
               String cor_fundo = "black";
               String tamanho_fonte = "12";
               String fonte = "ARIAL";
               String radius = "6";
               double largura = 120;
               double altura = 20;
               double larguraPainel = 200; 
               double posicaoX = (larguraPainel-largura)/2;
               double posicaoY =  480;
               String title = "comecar";
               this.comecar = configurarButaoEnvio(cor, cor_fundo, tamanho_fonte, fonte, radius, posicaoX, posicaoY, largura, altura, title);
               painel.getChildren().add(comecar);
     } //fim do metodo estruturarButao
  
     /********************************************************************************************
     * Metodo: mostrarContador
     * Funcao: exibe um contador de pacotes gerados no topo do painel
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void mostrarContador() {
            Pane paneTexto = new Pane();
            paneTexto.setPrefSize(200, 12); 
            paneTexto.setLayoutY(10);
            TextoContadorPacotes.setText("Pacotes gerados: ");
            TextoContadorPacotes.setTextFill(Color.BLACK);
            TextoContadorPacotes.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            TextoContadorPacotes.setLayoutX(20);
            TextoContadorPacotes.setLayoutY(6);
            paneTexto.getChildren().add(TextoContadorPacotes);
            painel.getChildren().add(paneTexto);
     } //fim do metodo mostrarContador
     
     /********************************************************************************************
     * Metodo: editarTexto
     * Funcao: atualiza dinamicamente o valor do contador de pacotes exibido
     * Parametros: contador - quantidade atual de pacotes
     * Retorno: vazio
     ********************************************************************************************/
     public void editarTexto(int contador) {
        Platform.runLater(() -> {
            TextoContadorPacotes.setText("Pacotes gerados: " + contador);
        });
          
     }//fim do metodo editarTexto
   
     /********************************************************************************************
     * Metodo: estruturarButaoINFO
     * Funcao: cria o botao informativo que apresenta descricao das versoes do algoritmo
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void estruturarButaoINFO() {
               String cor = "#C0C0C0";
               String cor_fundo = "black";
               String tamanho_fonte = "12";
               String fonte = "ARIAL";
               String radius = "6";
               double largura = 120;
               double altura = 20;
               double larguraPainel = 200; 
               double posicaoX = (larguraPainel-largura)/2;
               double posicaoY =  440;
               String title = "sobre";
               this.BUTTONINFO = configurarButaoEnvio(cor, cor_fundo, tamanho_fonte, fonte, radius, posicaoX, posicaoY, largura, altura, title);
               BUTTONINFO.setOnAction(e -> {
                     aviso.mostrarAviso("Versao 1: Cria um pacote e o envia para todas as interfaces de saidas." + "\n" + "\nVersao 2: Cria um pacote e o envia para todas as interfaces de saidas, exceto para aquela em que chegou" + "\n" +"\nVersao 3: cria um pacote e o envia para todas as interfaces de saidas exceto por aquela em que chegou. Levando em consideracao a informacao do TTL: time to live, que devera ser maior que zero para a propagacao do pacote" + "\n" + "\nVersao 4: Algoritimo especial em que marca cada roteador como visitado para o primeiro pacote que chega, enviando-o para todas ass interfaces de saidas exeto pela aquela em que chegou, somente se o roteador atual nao estiver marcado como visitado.");
               });
                
            BUTTONINFO.setOnMouseEntered(e -> {
                   costumizar(
                      "lightblue", "black", "12", "Arial", "6", BUTTONINFO  
                   );
            }); 
            BUTTONINFO.setOnMouseExited(e -> {
                    costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6", BUTTONINFO
                    );
            });
            BUTTONINFO.setOnMousePressed(e -> {
                   costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6", BUTTONINFO
                   );
            });
            
            BUTTONINFO.setOnMouseReleased(e -> {
                   costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6", BUTTONINFO
                   );
            });

               painel.getChildren().add(BUTTONINFO);
     }//fim do metodo estruturarButaoINFO

     /********************************************************************************************
     * Metodo: configurarHBox
     * Funcao: organiza os campos de entrada de emissor e receptor no layout vertical
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void configurarHBox() {
              sequenciaArranjo.setLayoutY(250);	
              sequenciaArranjo.getChildren().addAll(configurarEnvio("Emissor", 0));
              sequenciaArranjo.getChildren().addAll(configurarEnvio("Receptor", 1));
     } //fim do metodo configurarHBox

     /********************************************************************************************
     * Metodo: rearranjarPainel
     * Funcao: define estilo, dimensoes e posicionamento base do painel
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void rearranjarPainel() {
 	      painel.setStyle("-fx-background-color: #f3f3f3;" + "-fx-background-radius: 10;");
              painel.setLayoutY(0);
              painel.setPrefSize(200, 650);  
     } //fim do metodo rearranjarPainel


     /********************************************************************************************
     * Metodo: configurarEnvio
     * Funcao: cria um campo de entrada associado a um rotulo (emissor/receptor/TTL)
     * Parametros: nome - identificador textual do campo
     *             idInput - indice que define qual campo sera atribuido
     * Retorno: Pane contendo os elementos configurados
     ********************************************************************************************/
     private Pane configurarEnvio(String nome, int idInput) {
             Pane paneTexto = new Pane();
             paneTexto.setPrefSize(200, 12);  
             paneTexto.setLayoutY(100);
             TextField textField = setText(); 
             
             configurarTexto(paneTexto, nome);
             paneTexto.getChildren().add(textField); 
             if(idInput == 0) {
                textoInput1 = textField;
             } else if(idInput == 1) {
                textoInput2 = textField;
             } else {
                textoTTL = textField;
             }
             return paneTexto;  	  
     }

    /********************************************************************************************
     * Metodo: setText
     * Funcao: instancia e configura um campo de texto padrao
     * Parametros: nenhum
     * Retorno: objeto TextField configurado
     ********************************************************************************************/
    private TextField setText() { 
           TextField texto = new TextField();
           texto.setPrefSize(50, 12);
           texto.setLayoutY(5);
           texto.setLayoutX(100+20);
           return texto;
    } //fim do metodo setText

    /********************************************************************************************
     * Metodo: configurarTexto
     * Funcao: adiciona um rotulo estilizado a um painel
     * Parametros: pane - container do texto
     *             nome - conteudo textual
     * Retorno: vazio
     ********************************************************************************************/
    private void configurarTexto(Pane pane, String nome) {
           Platform.runLater(() -> {
             
             Label TextoEmissor = new Label(nome);
             TextoEmissor.setTextFill(Color.GREEN);
             TextoEmissor.setFont(Font.font("Arial", FontWeight.BOLD, 16));
             TextoEmissor.setLayoutX(20);
             TextoEmissor.setLayoutY(6);
             pane.getChildren().add(TextoEmissor);  
           });
           
     } //fim do metodo configurarTexto

     
    /********************************************************************************************
     * Metodo: configurarButaoEnvio
     * Funcao: cria um botao com propriedades visuais customizadas
     * Parametros: propriedades de estilo e dimensoes
     * Retorno: objeto Button configurado
     ********************************************************************************************/
    private Button configurarButaoEnvio(String cor, String cor_fundo, String tamanho_fonte, String fonte, String radius, double x, double y, double largura, double altura, String title) {
          Button butao = new Button(title);
          butao.setLayoutX(x); butao.setLayoutY(y);
          butao.setPrefSize(largura, altura);
          butao.setStyle(
               "-fx-background-color: " + cor + ";" +
               "-fx-text-fill: " +  cor_fundo +  ";" +
               "-fx-font-size: " + tamanho_fonte + ";" +
               "-fx-font-family: " + fonte + ";" +
               "-fx-background-radius: " + radius + ";"  
          );
          return butao;
    }//fim do metodo configurarButaoEnvio


    /********************************************************************************************
    * Metodo: getInput
    * Funcao: retorna o valor digitado nos campos de entrada
    * Parametros: opcao - define qual campo sera retornado
    * Retorno: String contendo o valor inserido
    ********************************************************************************************/
    public String getInput(int opcao) {
         if(opcao == 0) {
             return textoInput1.getText();
         }
         return textoInput2.getText();
    }//fim do metodo getInput
    
    
    public String getInputComboBox() {
         return selecionar.getValue();
    }
    
    public Integer getInputSpinner() {
          if(spinner == null) {
             return 0;
          }
          return spinner.getValue();
    }
    /********************************************************************************************
     * Metodo: selecionarTexo
     * Funcao: cria e gerencia o ComboBox de selecao de versoes, incluindo logica dinamica
     *         para exibicao do campo TTL
     * Parametros: nenhum
     * Retorno: ComboBox configurado
     ********************************************************************************************/
    public ComboBox<String> selecionarTexo() {
           selecionar = new ComboBox<>();
           selecionar.getItems().addAll(
                "Versao 1",
                "Versao 2",
                "Versao 3",
                "Versao 4"
           );
          String cor = "#C0C0C0";
          String radius = "6"; 
          double altura = 20;
          double largura = 120;
          double larguraPainel = 200; 
          double posicaoX = (larguraPainel-largura)/2;
          double posicaoY =  400;
          
          selecionar.setStyle(
               "-fx-background-color: " + cor + ";" +
               "-fx-background-radius: " + radius + ";"  
          );
          selecionar.setPrefSize(largura, 8);
          selecionar.setLayoutX(posicaoX); selecionar.setLayoutY(posicaoY);
          selecionar.setValue("Versao 1");
          selecionar.setOnAction(event -> {
               int deslocamento = 40;
               switch(estado) {
                  case 0:
                    spinner = new Spinner<>(1, 15, 5);
                    spinner.setPrefSize(100, 12);
                    Pane paneTexto = new Pane();
                    spinner.setLayoutX(80);
                    paneTexto.getChildren().add(spinner); 
                    configurarTexto(paneTexto, "TTL: ");
                    
                    if(selecionar.getValue().equals("Versao 3") || selecionar.getValue().equals("Versao 4")) {
                       sequenciaArranjo.getChildren().addAll(paneTexto);
                       comecar.setLayoutY(comecar.getLayoutY()+ deslocamento);
                       selecionar.setLayoutY(selecionar.getLayoutY()+deslocamento);
                       BUTTONINFO.setLayoutY(BUTTONINFO.getLayoutY()+deslocamento);
                       estado = 1;
                    }
                  break;
                  case 1:
                    
                    if(!selecionar.getValue().equals("Versao 3") && !selecionar.getValue().equals("Versao 4")) {
                       sequenciaArranjo.getChildren().remove(2);
                       comecar.setLayoutY(comecar.getLayoutY()-deslocamento);
                       selecionar.setLayoutY(selecionar.getLayoutY()-deslocamento);
                       BUTTONINFO.setLayoutY(BUTTONINFO.getLayoutY()-deslocamento);
                       estado = 0;
                    }
                  break;
               }
          });
           return selecionar;
    }//fim do metodo selecionarTexo
    
    /********************************************************************************************
     * Metodo: setAcaoButao
     * Funcao: define comportamento do botao principal com base em uma funcao de alta ordem
     * Parametros: funcao - acao a ser executada
     * Retorno: vazio
     ********************************************************************************************/
    public void setAcaoButao(funcaoAltaOrdem funcao) {
         
            comecar.setOnMouseEntered(e -> {
                   costumizar(
                      "lightblue", "black", "12", "Arial", "6", comecar  
                   );
            }); 
            comecar.setOnMouseExited(e -> {
                    costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6", comecar
                    );

            });
            comecar.setOnMousePressed(e -> {
                   costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6", comecar
                   );
                   
            });
            comecar.setOnMouseReleased(e -> {
                   costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6", comecar
                   );
                   
            });
             comecar.setOnAction(e -> {
                     comecar.setDisable(true);
                     funcao.implementarAcao();     
             });
             
    } // fim do metodo setAcaoButao


    /********************************************************************************************
     * Metodo: reablitarButao
     * Funcao: reativa o botao principal apos execucao
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
    public void reablitarButao() {
             comecar.setDisable(false);
    } //fim do metodo reablitarButao

    /********************************************************************************************
     * Metodo: costumizar
     * Funcao: aplica estilo customizado a um botao
     * Parametros: propriedades visuais e botao alvo
     * Retorno: vazio
     ********************************************************************************************/     
    private void costumizar(String x, String y, String z, String w, String r, Button k) {
             k.setStyle(
                      "-fx-background-color: " + x + ";" +
                      "-fx-text-fill: " +  y +  ";" +
                      "-fx-font-size: " + z + ";" +
                      "-fx-font-family: " + w + ";" +
                      "-fx-background-radius: " + r + ";"  
            );
    }//fim do metodo costumizar
} 
