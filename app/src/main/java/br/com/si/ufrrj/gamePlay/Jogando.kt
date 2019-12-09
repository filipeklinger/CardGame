package br.com.si.ufrrj.gamePlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import br.com.si.ufrrj.R
import br.com.si.ufrrj.carta.singleCard
import br.com.si.ufrrj.logica.GameStatus
import kotlinx.android.synthetic.main.activity_jogando.*
import kotlinx.android.synthetic.main.carta_single.*
import kotlin.reflect.KProperty1

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class Jogando : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_jogando)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        // Set up the user interaction to manually show or hide the system UI.
        fullscreen_content.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        game_over_btn.setOnTouchListener(mDelayHideTouchListener)
        game_over_btn.setOnClickListener {
            encerrarPartida()
        }

        mostraCarta()
    }

    fun mostraCarta(){
        val gs = GameStatus.getPartida()
        //primeiro removendo esse botao q nao tem nada a ver aparecer aqui
        add_or_remove_card.visibility = View.GONE

        card_principal.animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright)
        //obtendo a primeira carta da pilha
        if(!gs.cardsJogador.empty()){
            //if(gameStatus.cardsJogador.size == 1) Toast.makeText(baseContext,"Ultima carta",Toast.LENGTH_LONG).show()
            val card = gs.cardsJogador.pop()

            //setando os atributos do card atual para visao do usuario
            titulo_card.text = card.nome

            inteligencia_card.text = "Inteligencia: ${card.inteligencia}"
            inteligencia_card.background = getDrawable(R.color.colorAccent)
            inteligencia_card.setOnClickListener{
                Log.d("JOGANDO.class","inteligencia")
                comparar(card,singleCard::inteligencia)
            }

            forca_card.text = "Força: ${card.forca}"
            forca_card.background = getDrawable(R.color.colorAccent)
            forca_card.setOnClickListener{
                Log.d("JOGANDO.class","Forca")
                comparar(card,singleCard::forca)
            }

            velocidade_card.text = "Velocidade: ${card.velocidade}"
            velocidade_card.background = getDrawable(R.color.colorAccent)
            velocidade_card.setOnClickListener{
                Log.d("JOGANDO.class","Velocidade")
                comparar(card,singleCard::velocidade)
            }

            vigor_card.text = "Vigor: ${card.vigor}"
            vigor_card.background = getDrawable(R.color.colorAccent)
            vigor_card.setOnClickListener{
                Log.d("JOGANDO.class","vigor")
                comparar(card,singleCard::vigor)
            }

            poder_card.text = "Poder: ${card.poder}"
            poder_card.background = getDrawable(R.color.colorAccent)
            poder_card.setOnClickListener{
                Log.d("JOGANDO.class","poder")
                comparar(card,singleCard::poder)
            }

            combate_card.text = "Combate: ${card.combate}"
            combate_card.background = getDrawable(R.color.colorAccent)
            combate_card.setOnClickListener{
                Log.d("JOGANDO.class","Combate")
                comparar(card,singleCard::combate)
            }

        }else{
            gameOver(gs)
        }

    }

    fun gameOver(gs: GameStatus){
        card_principal.visibility = View.GONE
        game_over_text.visibility = View.VISIBLE

        ganhador_text.visibility = View.VISIBLE
        if(gs.pontuacaoJogador > gs.pontuacaoOp1 && gs.pontuacaoJogador > gs.pontuacaoOp2){
            ganhador_text.text = "Jogador Ganhou"
        }else if(gs.pontuacaoOp1 > gs.pontuacaoJogador && gs.pontuacaoOp1 > gs.pontuacaoOp2){
            ganhador_text.text = "Oponente 1 Ganhou"
        }else if(gs.pontuacaoOp2 > gs.pontuacaoJogador && gs.pontuacaoOp2 > gs.pontuacaoOp1){
            ganhador_text.text = "Oponente 2 Ganhou"
        }else{
            ganhador_text.text = "Hum, parece que temos um Empate"
        }
    }

    fun comparar(jogadorCard:singleCard, atributo: KProperty1<singleCard, *>){
        //--------Fluxo principal------------
        //compara com todos os jogadores
        //mostra quem ganhou
        //coloca a carta do jogador na pilha de ganhou ou perdeu
        //atualiza pontuacao de todos os jogadores
        //mostra proxima carta

        //comparando com jogador 1
        val gs = GameStatus.getPartida()
        val oponente1Card = gs.cartasOponente1.pop()
        val oponente2Card = gs.cartasOponente2.pop()

        //Obtendo o atributo a ser comparado por referencia de propriedade Platonica
        //mais info em https://kotlinlang.org/docs/tutorials/kotlin-for-py/member-references-and-reflection.html
        var atribOp1 = atributo.get(oponente1Card) // o problema é que aqui retorna Any (qualquer coisa)
        var atribOp2 =  atributo.get(oponente2Card)
        var atribJogador =  atributo.get(jogadorCard)

        if(atribOp1 !is String || atribOp2 !is String || atribJogador !is String){ //Por compatibilidade com API os atributos sao String
            Toast.makeText(baseContext,"Atributo ERR =((",Toast.LENGTH_LONG).show()
            return
        }

        //comparando atributos
        if( atribJogador.toInt() > atribOp1.toInt()){
            //ganhou do oponente 1
            if(atribJogador.toInt() > atribOp2.toInt()){
                //ganhou do oponente 2
                Toast.makeText(baseContext,"Sua carta Ganhou de TODOS!!",Toast.LENGTH_SHORT).show()
                gs.pontuacaoJogador += gs.pontosVitoria
            }
        }else if( atribJogador.toInt() == atribOp1.toInt() && atribJogador.toInt() != 100){
            //empatou com jogador 1 vamos ver qual oponente ganha

            if( atribOp1.toInt() < atribOp2.toInt()){ //como OP1 == Jogador entao OP2 ganha
                Toast.makeText(baseContext,"Oponente 2 Ganhou de TODOS!!",Toast.LENGTH_SHORT).show()
                gs.pontuacaoOp2 += gs.pontosVitoria
            }else{
                Toast.makeText(baseContext,"Sua carta Empatou com Oponente 1!!",Toast.LENGTH_SHORT).show()
                gs.pontuacaoOp1 += gs.pontosEmpate
                gs.pontuacaoJogador += gs.pontosEmpate
            }
        }else{
            if(atribOp1.toInt() > atribOp2.toInt()){
                Toast.makeText(baseContext,"Oponente 1 Ganhou de TODOS,o card dele tem: ${atribOp1}!!",Toast.LENGTH_SHORT).show()
                gs.pontuacaoOp1 += gs.pontosVitoria
            }else{
                Toast.makeText(baseContext,"Opoente 2 Ganhou de todos, o card dele tem: ${atribOp2}!!",Toast.LENGTH_SHORT).show()
                gs.pontuacaoOp2 += gs.pontosVitoria
            }
        }

        //atualizando na view
        atualizaPontuacao()
        //mostrando proximo card
        mostraCarta()

    }

    fun atualizaPontuacao(){
        val gs = GameStatus.getPartida()
        pontos_oponente_1.text = gs.pontuacaoOp1.toString()
        pontos_oponente_2.text = gs.pontuacaoOp2.toString()
        pontos_jogador.text = gs.pontuacaoJogador.toString()
    }

    fun encerrarPartida(){
        Toast.makeText(baseContext,"Encerrando Partida",Toast.LENGTH_SHORT).show()
        GameStatus.terminaPartida()
        finish()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 2000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
