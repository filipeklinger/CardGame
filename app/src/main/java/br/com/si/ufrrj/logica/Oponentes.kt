package br.com.si.ufrrj.logica

import android.content.Context
import android.util.Log
import br.com.si.ufrrj.carta.singleCard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*
import java.util.concurrent.Semaphore
import kotlin.collections.ArrayList
import kotlin.random.Random

//gera cards aleatorios para 2 oponentes
class Oponentes(var context: Context) {
    private val game:GameStatus

    private val op1sharedCounterLock = Semaphore(1)
    private val op2sharedCounterLock = Semaphore(1)
    private var cartasBaixadasOp1: Stack<String> = Stack()
    private var cartasBaixadasOp2: Stack<String> = Stack()

    private val tag = "Oponentes.class"
    init {
        GameStatus.terminaPartida() //certificando que nao temos nenhuma partida em aberto
        game = GameStatus.getPartida()
    }

    fun gerar() {
        val apiConect = ApiConect(context)//contactando API

        val op1 = deckAleatorio();/*selecionando cartas do oponente 1*/ Log.d(tag,"Deck Oponente 1: ${op1.joinToString(",")}")//Log do deck
        op1.forEach { id -> apiConect.buscarId(id){//para cada id busca na API
                jsonString -> armazenaJsonCardOp1(jsonString)//para cada resposta um parse
        } }

        val op2 = deckAleatorio();Log.d(tag,"Deck Oponente 2: ${op2.joinToString(",")}")//Log do deck
        op2.forEach { id -> apiConect.buscarId(id){ jsonString -> armazenaJsonCardOp2(jsonString)} }//mesmo do op1 sem comentarios
    }

    /**
     * Funcao que gera um deck para os oponentes
     * @return ArrayList
     */
    private fun deckAleatorio(): ArrayList<Int> {
        //deve ser um random variando de 1 a 731
        val deck:ArrayList<Int> = ArrayList()
        while(deck.size < game.qtdCartasOponente){
            val cartaAtual = Random.nextInt(731)
            if(!deck.contains(cartaAtual) && cartaAtual > 0) deck.add(cartaAtual) //somente adiciona cartas nao repetidas
        }
        return deck
    }

    /**
     * Armazena o json recebido
     * Deve ser sincronizado por usar multiplos threads dinamicamente enquanto baixa os arquivos
     * Usando semaforos para isso
     */
    fun armazenaJsonCardOp1(jsonString: String?){
        try {
            op1sharedCounterLock.acquire()
            jsonString?.let { it -> cartasBaixadasOp1.push(it) }
            if(cartasBaixadasOp1.size == game.qtdCartasOponente){
                Log.d(tag,"Cartas baixadas op 1: ${cartasBaixadasOp1.size}")
                parseBackThread(1)
            }//quando chegamos na ultima carta inicializa parse
        } finally {
            op1sharedCounterLock.release()
        }
    }

    /**
     * Precisei implementar outra funcao igual para nao entrar em conflito com o semaforo do card 1
     */
    fun armazenaJsonCardOp2(jsonString: String?){
        try {
            op2sharedCounterLock.acquire()
            jsonString?.let { it -> cartasBaixadasOp2.push(it) }
            if(cartasBaixadasOp2.size == game.qtdCartasOponente){
                Log.d(tag,"Cartas baixadas op 2: ${cartasBaixadasOp2.size}")
                parseBackThread(2)
            }//quando chegamos na ultima carta inicializa parse
        } finally {
            op2sharedCounterLock.release()
        }
    }


    /**
     * Aqui usamos a mesma funcao para fazer parse dos 2 oponentes simultaneamente
     * como no val de cada um passamos ponteiros diferentes não ha problema
     */
    fun parseBackThread(oponente: Int){
        Log.d(tag,"Parse Oponente ${oponente}")
        // Start a coroutine
        GlobalScope.async {
            val deck:ArrayList<singleCard> = if(oponente == 1){ game.cartasOponente1}else{ game.cartasOponente2 }
            val cartasBaixadas:Stack<String> = if(oponente == 1){ cartasBaixadasOp1 }else{ cartasBaixadasOp2 }

            while (cartasBaixadas.isNotEmpty()){
                val strcard = cartasBaixadas.pop()
                val singleCard = ApiConect.parseResponse(strcard)
                deck.add(singleCard)
            }
            Log.d(tag,"Parse Oponente ${oponente} finalizado")
            Log.d(tag,"Deck do Op ${oponente} com ${deck.size}")
        }
    }
}