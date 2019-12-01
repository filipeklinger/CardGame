package br.com.si.ufrrj.logica

import br.com.si.ufrrj.carta.singleCard
import kotlin.random.Random

/**
 * Usa a palavra reservada Object pois essa classe é do Tipo singleTon
 * Ou seja somente 1 instancia para toda a aplicacao
 */
object UserStatus {
    //Todo Aqui devemos salvar e recuperar o status do usuario no Game
    //Todo Acesso a Banco de dados

    //aqui armazenamos os cards completos exceto pela foto que deve ser um link a ser carregado
    var cartasDisponiveis:ArrayList<singleCard> = ArrayList()
    var deckAtual:ArrayList<singleCard> = ArrayList()

    //aqui temos somente as referencias das cartas na API
    var deckAtualId:ArrayList<Int>

    init {
        //inicializando user status
        deckAtualId = ArrayList()
        //TODO Essa verificacao vai passar bela busca em banco de dados
        //verificando se o jogador iniciou agora e atribuindo as referencias
        if(deckAtual.size == 0 && cartasDisponiveis.size == 0){
            deckAtualId = this.deckInicial()
        }
    }



    /**
     * Funcao que gera um deck inicial para o jogador
     * @return ArrayList
     */
    private fun deckInicial(): ArrayList<Int> {
        //deve ser um random variando de 1 a 731
        var deck:ArrayList<Int> = ArrayList()
        while(deck.size < 10){
            var cartaAtual = Random.nextInt(731)
            if(!deck.contains(cartaAtual)) deck.add(cartaAtual) //somente adiciona cartas nao repetidas
        }
        return deck
    }
}