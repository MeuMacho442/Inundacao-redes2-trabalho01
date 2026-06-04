/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: EstruturarSubRede
*Funcao...........: construir a representacao visual da rede mediante a composicao atomica de tecnicas para representar a rede visualmente, que eh um grafo, arestas, vertices
**************************************************************************************************/


package View;

import Model.Operadores;

public class EstruturarSubRede {
    private Janela janela;
    private static final int dimensaoX = 40;
    private static final int dimensaoY = 40;
    private double largura;
    private double altura;
    
    /********************************************************************************************
    * Metodo: EstruturarSubRede
    * Funcao: cria um objeto de referencia para o local de armazenamento dos componetes visuais, como paineis, textfields, etc.
    * Parametros: janela  
    * Retorno: vaio  
    ********************************************************************************************/
    public EstruturarSubRede(Janela janela) {
           this.janela = janela;
           this.largura = janela.getPainelDireito().getLargurar();
           this.altura = janela.getPainelDireito().getAltura(); 
    } //fim do metodo EstruturarSubRede
    
    /********************************************************************************************
    * Metodo: alocarNodeView
    * Funcao: criar uma representação visual do vertice de composicao do grafo
    * Parametros: Integer Valor, double posicaoX, double posicaoY   
    * Retorno: vaio  
    ********************************************************************************************/
    public void alocarNodeView(Integer Valor, double posicaoX, double posicaoY) {
            NodeView<Integer> nodeView = new NodeView<>(Valor);
            nodeView.definirDimensao(dimensaoX, dimensaoY);
            posicaoX = posicaoX+largura/2;//normalizando para o centro
            posicaoY = altura/2-posicaoY; //normalizando para o centro
            nodeView.setPosicaoVertice(posicaoX-(dimensaoX/2), posicaoY-(dimensaoY/2));
            janela.adicionarNoPainelDireito(nodeView.getVertice());
    }// fim do metodo alocarNodeView
    
    /********************************************************************************************
    * Metodo: alocarArestaView
    * Funcao: criar uma representação visual da aresta da composicao do grafo, usando tecnicas de geometria analitica para alocar valores na logo acima da posicao media de separacao dos dois nodes. Exemplo: (x,y), entao o peso sera: (-x,y)*(distancia)/raiz((proecaoX(x)-projecaoX(y))*(proecaoX(x)-projecaoX(y)) + ((proecaoY(x)-projecaoY(y))*(proecaoY(x)-projecaoY(y)) sera o ponto de alocacao do peso)
    * Parametros: Integer Valor, double posicaoX, double posicaoY   
    * Retorno: vaio  
    ********************************************************************************************/
    public void alocarArestaView(int peso, double posicaoX0, double posicaoY0, double posicaoX, double posicaoY) {
       
           ArestaView arestaView = new ArestaView();
           double NewPosicaoX0 = posicaoX0+largura/2;
           double NewPosicaoY0 = altura/2-posicaoY0;
           double NewPosicaoX = posicaoX+largura/2;
           double NewPosicaoY = altura/2-posicaoY;
           janela.adicionarNoPainelDireito(arestaView.criarAresta(NewPosicaoX0, NewPosicaoY0, NewPosicaoX, NewPosicaoY));
           double distancia = 8; 
           double t = distancia/(new Operadores()).calcularRaiz((posicaoY-posicaoY0)*(posicaoY-posicaoY0) + (posicaoX-posicaoX0)*(posicaoX-posicaoX0));
           if((posicaoY-posicaoY0) >= 0 && (posicaoX-posicaoX0) >= 0) { 
              janela.adicionarTextoPainelDireito(peso, ((-t)*(posicaoY-posicaoY0)+(posicaoX0+posicaoX)/2)+largura/2, altura/2+(-1)*((posicaoY+posicaoY0)/2 + (t)*(posicaoX-posicaoX0)));
           } else {
              janela.adicionarTextoPainelDireito(peso, ((t)*(posicaoY-posicaoY0)+(posicaoX0+posicaoX)/2)+largura/2, altura/2+(-1)*((posicaoY+posicaoY0)/2 + (-t)*(posicaoX-posicaoX0)));
           } 
    } //fim do metodo alocarArestaView
}
