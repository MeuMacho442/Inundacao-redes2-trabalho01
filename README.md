Nesse trabalho busquei simular o algoritmo de inundação na rede, usando diferentes tecnicas para controlar o fluxo de produção de pacotes na rede.
A representação da rede é flexivel, e pode ser editada pelo arquivo backbone.txt com o seguinte formato:
(NumeroVertices; "/n"
vertice1;vertice2;custo
)O formato deve ser extamente como ilustrado acima, uma excessão será gerada caso houver algum espaço a mais entre as linhas.
Essas tecnincas são apresentadas de form cumulativa e com crescente de eficiencia segundo as diferentes versões que podem ser escolhidas pelo usuario.
Versão1: floding puro, sua CPU vai explodir, não recomendo.
Versão2: controle de pacotes pela interface de chegada, não enviando pacotes pelas interfaces em que chegou. Se o grafo não for uma arvore, infinitos pacotes serão gerados, sua CPU taambém explodirá, não recomendo.
versão3: usa TTL para controlar os pacotes gerados na rede conjuntamente com controle das interface de chegada.
versão 4: usa o controle de estado, cada roteador é marcado como visitado, se um pacote chega e foi validado de que aquele roteador já foi visitado, esse pacote é considerado uma copia e é descartado. Esse é basicamente um algoritmo de busca em largura distribuida.
