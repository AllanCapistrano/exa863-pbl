/**
 * Componente Curricular: Módulo Integrado de Programação II
 * Autores: <Kevin Cerqueira Gomes e Allan Capistrano>
 * Data:  <11/08/2019>
 *
 * Declaro que este código foi elaborado por nós de forma individual e
 * não contém nenhum trecho de código de outro colega ou de outro autor, 
 * tais como provindos de livros e apostilas, e páginas ou documentos 
 * eletrônicos da Internet. Qualquer trecho de código de outra autoria que
 * uma citação para o  não a minha está destacado com  autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins
 * de avaliação. Alguns trechos do código podem coincidir com de outros
 * colegas pois estes foram discutidos em sessões tutorias.
 */
package br.uefs.ecomp.ccma.util;

/**
 * Classe geradora de nó, que gerencia os nós.
 *
 * @author Kevin e Allan
 */
public class Link {
    private Object obj;
    private Link nextLink;
    
    public Link(Object obj){
        this.obj = obj;
    }
    
    /**
     * Retorna o objeto referenciado pelo nó.
     * @return O objeto contido no nó.
     */
    public Object getObj() {
        return obj;
    }
    
    /**
     * Inicializa o objeto que será referenciado pelo nó.
     * @param obj novo objeto que ficará dentro do nó.
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }
    
    /**
     * Retorna o próximo nó.
     * @return O proximo nó da lista.
     */
    public Link getNext() {
        return nextLink;
    }
    
    /**
     * Atribui o próximo nó da lista.
     * @param nextLink próximo nó que será ligado a este.
     */
    public void setNext(Link nextLink) {
        this.nextLink = nextLink;
    }
}
