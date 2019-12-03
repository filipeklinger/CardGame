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
    private val game = GameStatus.getPartida()

    private val op1sharedCounterLock = Semaphore(1)
    private val op2sharedCounterLock = Semaphore(1)
    private var cartasBaixadasOp1: Stack<String> = Stack()
    private var cartasBaixadasOp2: Stack<String> = Stack()

    private val tag = "Oponentes.class"

    fun gerar() {
        //contactando API
        Log.d(tag,"Contactando API")
        val apiConect = ApiConect(context)
        //selecionando cartas do oponente 1
        //inicializando Oponente 1
        Log.d(tag,"Iniciando Oponente 1")
        val op1 = deckAleatorio()
        Log.d(tag,"Deck Oponente 1: ${op1.joinToString(",")}")
        op1.forEach { id -> apiConect.buscarId(id){//para cada id busca na API
                jsonString -> armazenaJsonCardOp1(jsonString)//para cada resposta um parse
        } }

        Log.d(tag,"Iniciando Oponente 2")
        val op2 = deckAleatorio()
        Log.d(tag,"Deck Oponente 2: ${op2.joinToString(",")}")
        op2.forEach { id -> apiConect.buscarId(id){//para cada id busca na API
                jsonString -> armazenaJsonCardOp2(jsonString)//para cada resposta um parse
        } }

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
            if(!deck.contains(cartaAtual)) deck.add(cartaAtual) //somente adiciona cartas nao repetidas
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
            if(cartasBaixadasOp1.size == game.qtdCartasOponente){ parseBackThread(1) }//quando chegamos na decima carta inicializa parse
        } finally {
            op1sharedCounterLock.release()
        }
    }

    fun armazenaJsonCardOp2(jsonString: String?){
        try {
            op2sharedCounterLock.acquire()
            jsonString?.let { it -> cartasBaixadasOp2.push(it) }
            if(cartasBaixadasOp2.size == game.qtdCartasOponente){ parseBackThread(2) }//quando chegamos na decima carta inicializa parse
        } finally {
            op2sharedCounterLock.release()
        }
    }

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
                deck.add(singleCard)
            }
            Log.d(tag,"Parse Oponente ${oponente} finalizado")
        }
    }
}