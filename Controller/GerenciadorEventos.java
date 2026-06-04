/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: GerenciadorEventos
*Funcao...........: gerenciar os eventos da interface grafica, controlando o fluxo de execucao
*                   da simulacao, validando entradas e coordenando a inicializacao da sub-rede
**************************************************************************************************/


package Controller;

import View.Janela;
import Model.ListaDinamica;
import Model.Roteador;
import Model.Grafo;
import Model.InicializarSubRede;
import Controller.Excessao.ExcessaoIncosistenciaEntrada;
import Model.ProcurarElementoGrafo;
import Model.Posicao;
import Model.Node;


//trabalho ainda nao pronto, somente um prototipo
public class GerenciadorEventos {
    private Integer idEmissor;
    private Integer idReceptor;
    private funcaoAltaOrdem funcao;
    private Janela janela;
    private InicializarSubRede subRede;
    private enum EstadoPrograma {INICIADO, NAOINICIADO}
    private EstadoPrograma estadoPrograma;
     
    /********************************************************************************************
    * Metodo: GerenciadorEventos (construtor)
    * Funcao: inicializa o gerenciador de eventos com estado inicial nao iniciado
    * Parametros: janela - referencia da interface principal
    * Retorno: nenhum
    ********************************************************************************************/
    public GerenciadorEventos(Janela janela) {
           this.estadoPrograma = EstadoPrograma.NAOINICIADO;
           this.janela = janela;
    }
    
    /********************************************************************************************
    * Metodo: start
    * Funcao: associa uma funcao de alta ordem ao evento principal da interface
    * Parametros: alocarRede - responsavel pela configuracao inicial da rede
    * Retorno: vazio
    ********************************************************************************************/
    public void start(AlocarNode alocarRede) {
         funcao = () -> {iniciarSimulacao(alocarRede);};
         janela.definirEvento(funcao);
    }//fim do metodo start
    
    /********************************************************************************************
    * Metodo: iniciarSimulacao
    * Funcao: controla o fluxo de inicializacao e execucao da simulacao, incluindo validacao
    *         de entradas e invocacao da logica da sub-rede
    * Parametros: alocarRede - responsavel pela estrutura da rede
    * Retorno: vazio
    ********************************************************************************************/
    private void iniciarSimulacao(AlocarNode alocarRede)  {
       if(estadoPrograma == EstadoPrograma.INICIADO) { 
          alocarRede.setNode(); alocarRede.setAresta();
       } else { this.estadoPrograma = EstadoPrograma.INICIADO; }
        subRede = new InicializarSubRede(alocarRede.getGrafo());
        try {  
          idEmissor =  Integer.parseInt(janela.getInputs(0));
          idReceptor =  Integer.parseInt(janela.getInputs(1)); 
          verificarIntegridade(idEmissor, idReceptor, alocarRede.getGrafo().getConjuntoVertice());
          subRede.iniciarSimulacao(idEmissor, idReceptor, janela.versao(), janela.getTTL());
       } catch(NumberFormatException kx) { 
          janela.emitirAlerta("formato deve ser numerico");
           janela.rabilitarButao(); 
          System.out.println("fluxo de execuacao interrompido");
       } catch(ExcessaoIncosistenciaEntrada ex) {
            janela.rabilitarButao(); 
          System.out.println("fluxo de execuacao interrompido");
       }
    } //fim do metodo 

     /********************************************************************************************
    * Metodo: verificarIntegridade
    * Funcao: valida os dados de entrada garantindo consistencia logica dos identificadores
    * Parametros: idEmissor, idReceptor - identificadores dos nos
    *             listNode - conjunto de nos do grafo
    * Retorno: vazio (lanca excecao em caso de erro)
    ********************************************************************************************/
    private void verificarIntegridade(Integer idEmissor, Integer idReceptor, Node<Roteador, Posicao>[] listNode) throws ExcessaoIncosistenciaEntrada {
      
         if(idEmissor == idReceptor) {
              janela.emitirAlerta("dois host's iguais"); 
              throw new ExcessaoIncosistenciaEntrada("entrada inconsistente");  //lancar um popup de aviso na versao final
         }
          
         ProcurarElementoGrafo verificarValidadeDeExistencia = new ProcurarElementoGrafo(listNode);
         if(!verificarValidadeDeExistencia.procurarNode(idEmissor-1) ||  !verificarValidadeDeExistencia.procurarNode(idReceptor-1)) {
              janela.emitirAlerta("Emissor ou recptor nao existente na rede");
            
              throw new ExcessaoIncosistenciaEntrada("entrada inconsistente"); 
         }
    }//fim do metodo verificarIntegridade

}
