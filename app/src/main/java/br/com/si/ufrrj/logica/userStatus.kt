package br.com.si.ufrrj.logica

import kotlin.random.Random

class userStatus() {
    //Todo Aqui devemos salvar e recuperar o status do usuario no Game
    //Todo Acesso a Banco de dados
    //Status que devemos ter
    //Esses Vetores vao guardar o id das cartas armazenadas na API!!!
    var cartasDisponiveis:ArrayList<Int>

    var deckAtual:ArrayList<Int>

    init {
        cartasDisponiveis = ArrayList()
        deckAtual = ArrayList()

        //verificando se o jogador iniciou agora
        if(deckAtual.size == 0 && cartasDisponiveis.size == 0){
            cartasDisponiveis = this.deckInicial()
            deckAtual = cartasDisponiveis
        }
    }



    /**
     * Funcao que gera um deck inicial para o jogador
     * @return ArrayList
     */
    private fun deckInicial(): ArrayList<Int> {
        //deve ser um random variando de 1 a 731
        var deck:ArrayList<Int> = ArrayList()
        for(i in 0..9){
            var cartaAtual = Random.nextInt(731)
            deck.add(cartaAtual)
        }

        return deck
    }
}