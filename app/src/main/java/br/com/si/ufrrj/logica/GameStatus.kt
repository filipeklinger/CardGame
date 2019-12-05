package br.com.si.ufrrj.logica

import android.content.Context
import br.com.si.ufrrj.carta.singleCard
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Aqui devemos salvar e recuperar o status de uma dada PARTIDA
 */
class GameStatus {
    var cartasGanharam: ArrayList<singleCard> = ArrayList() /* cartas do opopnente que ganharam */
    var cartasPerderam: ArrayList<singleCard> = ArrayList() /* cartas do oponente que perderam */
    var cartasEmpataram: ArrayList<singleCard> = ArrayList() /* cartas do oponente que perderam */

    var cardsJogador:Stack<singleCard> = Stack()

    init {
        //ao iniciar obtemos o deck atual e colocamos na pilha do jogador (stack)
        cardsJogador.addAll(UserStatus.deckAtual)
    }

    var qtdCartasOponente:Int = 5

    var pontuacaoJogador:Int = 0 //cada carta ganha vale X pontos a serem definidos
    var pontuacaoOp1:Int = 0
    var pontuacaoOp2:Int = 0

    //cartas
    var cartasOponente1: ArrayList<singleCard> = ArrayList()
    var cartasOponente2: ArrayList<singleCard> = ArrayList()

    companion object {
        @Volatile
        private var INSTANCE: GameStatus? = null
        fun getPartida() =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GameStatus().also {
                    INSTANCE = it
                }
            }
        fun terminaPartida() {
            INSTANCE = null
        }
    }
}