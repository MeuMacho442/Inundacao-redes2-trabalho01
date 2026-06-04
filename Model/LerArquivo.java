package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LerArquivo {
      private String caminho;
      private File arquivo;
      public LerArquivo(String caminho) {
          this.caminho = caminho;
          this.arquivo = new File(caminho);
      }
      
      public String lerArquivo(int linha) {
           String res = "";
           try {
               int acc = 0;
               Scanner sc = new Scanner(arquivo);
               while(sc.hasNextLine()) {
                   if(acc == linha) {
                       res = sc.nextLine();
                       break;
                   }
                   sc.nextLine();
                   acc++;
               }
               sc.close();
           } catch(FileNotFoundException ex) {
                System.out.println("arquivo nao encontrado");
           }
           return res; 
      }
      
      public Integer extrairNumeroDocumentoSubrede(int linha, int coluna) {//funciona somente para o documento backbone.txt
           String res = lerArquivo(linha);         
           return Integer.parseInt(res.split(";")[coluna]);
      }
      
      public Integer tamanhoArquivo() {
         int tamanho = 0;
         try {
            Scanner sc = new Scanner(arquivo);
            while(sc.hasNextLine()) {
                if(sc.nextLine() == "") {
                   break;
                }
                tamanho++;
            }
            sc.close();
         } catch(FileNotFoundException ex) {
            System.out.println("arquivo nao encontrado");
         }
         System.out.println(tamanho);
         return tamanho;
      }
}
