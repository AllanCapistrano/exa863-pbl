/**
 * Componente Curricular: Módulo Integrado de Programação II
 * Autores: <Kevin Cerqueira Gomes e Allan Capistrano de Santana Santos>
 * Data:  <09/08/2019>
 *
 * Declaro que este código foi elaborado por nós de forma individual e não
 * contém nenhum trecho de código de outro colega ou de outro autor, tais como
 * provindos de livros e apostilas, e páginas ou documentos eletrônicos da
 * Internet. Qualquer trecho de código de outra autoria que uma citação para o
 * não a minha está destacado com autor e a fonte do código, e estou ciente que
 * estes trechos não serão considerados para fins de avaliação. Alguns trechos
 * do código podem coincidir com de outros colegas pois estes foram discutidos
 * em sessões tutorias.
 */
package br.uefs.ecomp.forteseguro.controller;

import br.uefs.ecomp.forteseguro.exception.ArestaDuplicadaException;
import br.uefs.ecomp.forteseguro.exception.VerticeDuplicadoException;
import br.uefs.ecomp.forteseguro.model.AlgoritimoDijkstra;
import br.uefs.ecomp.forteseguro.model.Aresta;
import br.uefs.ecomp.forteseguro.model.Grafo;
import br.uefs.ecomp.forteseguro.model.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por gerenciar todo o sistema (classes e objetos) do
 * programa.
 *
 * @author Kevin Cerqueira.
 * @author Allan Capistrano.
 */
public class Controller {

    private Grafo<String> grafo;

    /**
     * Construtor da classe. Inicia a variável.
     */
    public Controller() {
        this.grafo = new Grafo<>();
    }

    /**
     * Retorna o Grafo
     *
     * @return objeto do tipo Grafo;
     */
    public Grafo<String> getGrafo() {
        return this.grafo;
    }

    /**
     * Cria o grafo com base nos vértices e nas arestas que estão contidos
     * dentro do arquivo de texto.
     *
     * @param nomeArq String - Nome do arquivo de texto que estão localizados os
     * dados para a criação do grafo.
     * @return Grafo criado com sucesso - Caso tudo ocorra corretamente |
     * Arquivo de texto não encontrado! - Caso haja algum problema em encontrar
     * o arquivo de texto | Dados inválidos e/ou incompreensíveis no arquivo! -
     * Caso os dados inseridos não estejam corretos.
     */
    public String criarGrafo(String nomeArq) {
        File file = new File(nomeArq);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String leitura = null;
            /*Lê a parte do arquivo que estão os vértices*/
            while (!"[Arestas]".equals(leitura = br.readLine())) {
                try {
                    if (leitura.equals("[Vertices]")) {
                        continue;
                    }
                    int contador = 0;
                    String[] subString = leitura.split(" ");
                    String identificador = subString[0];
                    //Tipo: 0 - Vértice comum | 1 - Banco | 2 - Coleta | 3 - Estacionamento
                    int tipo = Integer.parseInt(subString[1]);
                    int x = Integer.parseInt(subString[2]);
                    int y = Integer.parseInt(subString[3]);
                    try {
                        this.grafo.novoVertice(identificador, tipo, x, y);
                        contador++;
                    } catch (VerticeDuplicadoException v) {
                        v.toString();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "Erro na leitura do arquivo: Dados inválidos.";
                }
            }
            /*Lê a parte do arquivo que estão as arestas*/
            while ((leitura = br.readLine()) != null) {
                String[] subString = leitura.split(" ");
                int distancia = Integer.parseInt(subString[2]);

                try {
                    this.grafo.criarAresta(this.grafo.buscarVertice(subString[0]),
                            this.grafo.buscarVertice(subString[1]), distancia);
                    this.grafo.criarAresta(this.grafo.buscarVertice(subString[1]),
                            this.grafo.buscarVertice(subString[0]), distancia);
                } catch (ArestaDuplicadaException a) {
                    a.toString();
                }
            }
        } catch (FileNotFoundException e) {
            return "Arquivo de texto não encontrado!";
        } catch (IOException e) {
            return "Dados inválidos e/ou incompreensíveis no arquivo!";
        }
        return "Grafo criado com sucesso";
    }

    /**
     * Cria um novo grafo, e exclui o antigo com base nos vértices e nas arestas
     * que estão contidos dentro do arquivo de texto.
     *
     * @param nomeArq String - Nome do arquivo de texto que estão localizados os
     * dados para a criação do grafo.
     * @return Grafo criado com sucesso - Caso tudo ocorra corretamente |
     * Arquivo de texto não encontrado! - Caso haja algum problema em encontrar
     * o arquivo de texto | Dados inválidos e/ou incompreensíveis no arquivo! -
     * Caso os dados inseridos não estejam corretos.
     */
    public String criarNovoGrafo(String nomeArq) {
        Grafo<String> newGrafo = new Grafo<>();
        File file = new File(nomeArq);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String leitura;
            /*Lê a parte do arquivo que estão os vértices*/
            while (!"[Arestas]".equals(leitura = br.readLine())) {
                try {
                    if (leitura.equals("[Vertices]")) {
                        continue;
                    }
                    int contador = 0;
                    String[] subString = leitura.split(" ");
                    String identificador = subString[0];
                    //Tipo: 0 - Vértice comum | 1 - Banco | 2 - Coleta | 3 - Estacionamento
                    int tipo = Integer.parseInt(subString[1]);
                    int x = Integer.parseInt(subString[2]);
                    int y = Integer.parseInt(subString[3]);
                    try {
                        newGrafo.novoVertice(identificador, tipo, x, y);
                        contador++;
                    } catch (VerticeDuplicadoException v) {
                        v.toString();
                    }
                } catch (NumberFormatException e) {
                    return "Arquivo com erro: Dados inválidos!";
                }
            }
            /*Lê a parte do arquivo que estão as arestas*/
            while ((leitura = br.readLine()) != null) {
                String[] subString = leitura.split(" ");
                int distancia = Integer.parseInt(subString[2]);

                try {
                    newGrafo.criarAresta(newGrafo.buscarVertice(subString[0]),
                            newGrafo.buscarVertice(subString[1]), distancia);
                    newGrafo.criarAresta(newGrafo.buscarVertice(subString[1]),
                            newGrafo.buscarVertice(subString[0]), distancia);
                } catch (ArestaDuplicadaException a) {
                    a.toString();
                }
            }
        } catch (FileNotFoundException e) {
            return "Arquivo de texto não encontrado!";
        } catch (IOException e) {
            return "Dados inválidos e/ou incompreensíveis no arquivo!";
        }
        this.grafo = newGrafo;
        return "Grafo criado com sucesso";
    }

    /**
     * Adiciona um novo vértice ao grafo.
     *
     * @param cruzamento String - Identificador do vértice que se deseja
     * adicionar.
     * @param tipo int - 0 - Vértice comum | 1 - Banco | 2 - Coleta | 3 -
     * Estacionamento
     * @return Cruzamento adicionado com sucesso! - Caso o vértice seja
     * adicionado com sucesso | Vértice já está adicionado no grafo! - caso
     * ocorra algum problema ao tentar novoVertice determinado vértice.
     */
    public String adicionarCruzamento(String cruzamento, int tipo) {
        try {
            this.grafo.novoVertice(cruzamento, tipo);
            return "Cruzamento adicionado com sucesso!";
        } catch (VerticeDuplicadoException v) {
            return "Cruzamento já está adicionado no grafo!";
        }
    }

    /**
     * Adiciona um novo vértice ao grafo.
     *
     * @param cruzamento String - Identificador do vértice que se deseja
     * adicionar.
     * @param tipo int - 0 - Vértice comum | 1 - Banco | 2 - Coleta | 3 -
     * Estacionamento
     * @param x Posição X do vértice.
     * @param y Posição Y do vértice.
     * @return Cruzamento adicionado com sucesso! - Caso o vértice seja
     * adicionado com sucesso | Vértice já está adicionado no grafo! - caso
     * ocorra algum problema ao tentar novoVertice determinado vértice.
     */
    public String adicionarVertice(String cruzamento, int tipo, int x, int y) {
        try {
            this.grafo.novoVertice(cruzamento, tipo, x, y);
            return "Cruzamento adicionado com sucesso!";
        } catch (VerticeDuplicadoException v) {
            return "Cruzamento já está adicionado no grafo!";
        }
    }

    /**
     * Adiciona uma nova ligação ao grafo.
     *
     * @param v1 String - Um dos vértices que faz parte da ligação.
     * @param v2 String - O outro vértice que faz parte da ligação.
     * @param distancia int - Distância entre os dois vértices formadoes.
     * @return Ligação adicionada com sucesso! - Caso a aresta seja adicionada
     * com sucesso | Aresta já está adicionada no sistema! - Caso ocoorra algum
     * problema ao tentar novoVertice determinada aresta.
     */
    /*Por enquanto está com a distância definida pelo usuário*/
    public String adicionarLigacao(String v1, String v2, int distancia) {
        Vertice<String> vertice1 = this.grafo.buscarVertice(v1);
        Vertice<String> vertice2 = this.grafo.buscarVertice(v2);

        try {
            this.grafo.criarAresta(vertice1, vertice2, distancia);
            return "Ligação adicionada com sucesso!";
        } catch (ArestaDuplicadaException a) {
            return "Ligação já está adicionada no sistema!";
        }

    }

    /**
     * Remove uma determinada aresta do grafo.
     *
     * @param aresta Aresta - Aresta que se deseja remover.
     * @return Ligação não existe! - Caso não seja possível remover a ligação
     * Ligação removida com sucesso! - Caso a remoção ocorra com sucesso.
     */
    public String removerLigacao(Aresta<String> aresta) {
        if (this.grafo.buscarAresta(aresta.getVertice1(), aresta.getVertice2()) == null) {
            return "Ligação não existe!";
        }
        this.getGrafo().removerAresta(aresta);
        return "Ligação removida com sucesso!";
    }

    /**
     * Remove um vértice e suas ligações do grafo.
     *
     * @param cruzamento String - Identificador do vértice que se deseja
     * remover.
     * @return Cruzamento não existe! - Caso não seja possível remover o
     * cruzamento Cruzamento removido com sucesso! - Caso a remoção ocorra com
     * sucesso.
     */
    public String removerCruzamento(String cruzamento) {
        if (this.grafo.buscarVertice(cruzamento) == null) {
            return "Cruzamento não existe!";
        }
        this.getGrafo().removerVertice(cruzamento);
        return "Cruzamento removido com sucesso!";
    }

    /**
     * Altera o tipo do cruzamento.
     *
     * @param cruzamento String - Identificador do vértice que se deseja alterar
     * o tipo.
     * @param tipo int - 0 - Vértice comum | 1 - Banco | 2 - Coleta | 3 -
     * Estacionamento.
     */
    public void alterarTipoCruzamento(String cruzamento, int tipo) {
        Vertice<String> vertice = this.grafo.buscarVertice(cruzamento);

        if (vertice != null) {
            vertice.setId(tipo);
        }
    }

    /**
     * Verifica se existe algum vértice do tipo estacionamento.
     *
     * @return true - Caso exista | false - Caso contrário.
     */
    public boolean existeEstacionamento() {
        for (Vertice<String> vertice : this.grafo.getVertices()) {
            if (vertice.getId() == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se existe algum vértice do tipo Banco.
     *
     * @return true - Caso exista | false - Caso contrário.
     */
    public boolean existeBanco() {
        for (Vertice<String> bancos : this.getGrafo().getVertices()) {
            if (bancos.getId() == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se existe algum vértice do tipo Coleta.
     *
     * @return true - Caso exista | false - Caso contrário.
     */
    public boolean existeColeta() {
        for (Vertice<String> coleta : this.getGrafo().getVertices()) {
            if (coleta.getId() == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se existe algum vértice do tipo Cruzamento.
     *
     * @return true - Caso exista | false - Caso contrário.
     */
    public boolean existeCruzamento() {
        for (Vertice<String> cruza : this.getGrafo().getVertices()) {
            if (cruza.getId() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se existe vértices no grafo.
     *
     * @return true - Caso exista | false - Caso contrário.
     */
    public boolean existePontos() {
        return !this.grafo.getVertices().isEmpty();
    }

    /**
     * Retorna o vértice que é o estacionamento.
     *
     * @return objeto vértice do estacionamento, null caso não exista o
     * estacionamento.
     */
    public Vertice<String> getEstacionamento() {
        for (Vertice<String> vertice : this.grafo.getVertices()) {
            if (vertice.getId() == 3) {
                return vertice;
            }
        }
        return null;
    }

    /**
     * Busca e retorna um vértice.
     *
     * @param nomePonto Nome do vértice a ser buscado e retornado.
     * @return objeto Vertice se caso exista um vértice com o nome procurado,
     * null caso contrário.
     */
    public Vertice<String> getPonto(String nomePonto) {
        return this.grafo.buscarVertice(nomePonto);
    }

    /**
     * Verifica se o vértice passado como parâmetro existe no grafo.
     *
     * @param cruzamento String - Identificador do vértice que se deseja
     * procurar no grafo.
     * @return true - Caso exista | false - Caso contrário.
     */
    public boolean existeVertice(String cruzamento) {
        Vertice<String> vertice = this.getGrafo().buscarVertice(cruzamento);

        return vertice != null;
    }

    /**
     * Retorna todos os nomes dos vértices que estão no grafo.
     *
     * @return iterator apontando para o primeiro nome dos vértices.
     */
    public Iterator getNomesVertices() {
        return this.grafo.getVertices().iterator();
    }

    /**
     * Retorna todos os nomes dos vértices de um determinado tipo (Banco, Coleta
     * , Estacionamento ou Cruzamento).
     *
     * @param tipo Tipo do vértice a ser buscado.
     * @return iterator apontando para o primeiro nome do vértice da lista.
     */
    public List<String> getNomesVertices(int tipo) {
        List<String> lista = new ArrayList<>();

        if (tipo == -1) {
            for (Vertice<String> vertice : this.grafo.getVertices()) {
                lista.add(vertice.getObj());
            }
            return lista;
        }
        for (Vertice<String> vertice : this.grafo.getVertices()) {
            if (vertice.getId() == tipo) {
                lista.add(vertice.getObj());
            }
        }
        return lista;
    }

    /**
     * Retorna todas as ligações contidas no grafo.
     *
     * @return iterator apontando para a primeira aresta da lista.
     */
    public Iterator getLigacoesArestas() {
        return this.grafo.getArestas().iterator();
    }

    /**
     * Calcula o menor caminho entre 3 pontos (Estacionamento, Coleta e Banco).
     *
     * @param coleta Vértice do lugar que será feita a coleta.
     * @param banco Vértice do Banco que será feita a entrega.
     * @return string com o menor caminho possível entre os 3 pontos.
     */
    public String calculaCaminho(String coleta, String banco) {
        Vertice<String> pontoColeta = this.getGrafo().buscarVertice(coleta);
        Vertice<String> pontoBanco = this.getGrafo().buscarVertice(banco);

        AlgoritimoDijkstra<String> dijkstra1 = this.getGrafo().executarDijkstra();
        dijkstra1.calcularMenoresCaminhos(this.getEstacionamento());
        List<List<Vertice<String>>> menorCaminhoColeta = dijkstra1.getDijkstra(null, pontoColeta);
        if (menorCaminhoColeta == null) {
            return "O PONTO DE COLETA É INACESSÍVEL PELO ESTACIONAMENTO";
        }
        String caminhoFinal = "• Estacion. para a Coleta:\n - ";

        int i = 0;
        for (List<Vertice<String>> caminhos : menorCaminhoColeta) {
            for (Vertice<String> vertice : caminhos) {
                caminhoFinal += vertice.getObj();
                if (menorCaminhoColeta.get(0).size() - 1 > i) {
                    caminhoFinal += " -> ";
                }
                i++;
            }
        }

        AlgoritimoDijkstra<String> dijkstra2 = this.getGrafo().executarDijkstra();
        dijkstra2.calcularMenoresCaminhos(pontoColeta);
        List<List<Vertice<String>>> menorCaminhoBanco = dijkstra2.getDijkstra(null, pontoBanco);
        if (menorCaminhoBanco == null) {
            return "O BANCO É INACESSÍVEL PELO PONTO DE COLETA";
        }

        caminhoFinal += "\n• Coleta para Banco:\n - ";
        i = 0;
        for (List<Vertice<String>> caminhos : menorCaminhoBanco) {
            for (Vertice<String> vertices : caminhos) {
                caminhoFinal += vertices.getObj();
                if (menorCaminhoBanco.get(0).size() - 1 > i) {
                    caminhoFinal += " -> ";
                }
                i++;
            }
        }

        return caminhoFinal;
    }

    /**
     * Remove uma aresta do Grafo.
     *
     * @param vertice1 Vértice 1 da ligação da aresta.
     * @param vertice2 Vértice 2 da ligação da aresta.
     */
    public void removerAresta(String vertice1, String vertice2) {
        this.getGrafo().removerAresta(this.getGrafo().buscarAresta(vertice1, vertice2));
    }

    /**
     * Verifica se o vértice passado por parametro tem arestas incidentes.
     *
     * @param pontoLigacao Nome do vértice a ser vertificado.
     * @return true: caso exista ligações, false: caso contrário.
     */
    public boolean existeLigacoes(String pontoLigacao) {
        return this.grafo.arestasIncidentes(pontoLigacao) != null;
    }

    /**
     * Retorna a lista dos nomes dos vértice ligados ao vértice passado por
     * parametro
     *
     * @param pontoLigacao Nome do vértice a ser buscado as suas ligações.
     * @return lista com todas as ligações do vértice.
     */
    public List<String> listaArestasIncidentes(String pontoLigacao) {

        Vertice<String> vertice = this.grafo.buscarVertice(pontoLigacao);
        List<String> pontos = new ArrayList<>();

        for (Aresta<String> arestas : this.getGrafo().arestasIncidentes(vertice)) {
            if (arestas.getVertice1().equals(vertice)) {
                pontos.add(arestas.getVertice2().getObj());
            }
        }
        if (pontos.isEmpty()) {
            return null;
        }

        return pontos;
    }
}
