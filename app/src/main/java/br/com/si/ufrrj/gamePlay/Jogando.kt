package br.com.si.ufrrj.gamePlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import br.com.si.ufrrj.R
import br.com.si.ufrrj.carta.singleCard
import br.com.si.ufrrj.logica.GameStatus
import br.com.si.ufrrj.logica.UserStatus
import kotlinx.android.synthetic.main.activity_jogando.*
import kotlinx.android.synthetic.main.carta_single.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

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
        val gameStatus = GameStatus.getPartida()
        //primeiro removendo esse botao q nao tem nada a ver aparecer aqui
        add_or_remove_card.visibility = View.GONE

        card_principal.animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright)
        //obtendo a primeira carta da pilha
        if(!gameStatus.cardsJogador.empty()){
            if(gameStatus.cardsJogador.size == 1) Toast.makeText(baseContext,"Ultima carta",Toast.LENGTH_LONG).show()
            val card = gameStatus.cardsJogador.pop()

            //setando os atributos do card atual para visao do usuario
            titulo_card.text = card.nome

            inteligencia_card.text = "Inteligencia: ${card.inteligencia}"

            forca_card.text = "Força: ${card.forca}"
            forca_card.setOnClickListener{
                comparar(card,"forca")
            }
            velocidade_card.text = "Velocidade: ${card.velocidade}"
            vigor_card.text = "Vigor: ${card.vigor}"
            poder_card.text = "Poder: ${card.poder}"
            combate_card.text = "Combate: ${card.combate}"
        }else{
            card_principal.visibility = View.GONE
            game_over_text.visibility = View.VISIBLE
        }

    }

    fun comparar(card:singleCard,atributo:String){
        //compara com todos os jogadores
        //coloca na pilha de ganohu ou perdeu
        //atualiza pontuacao de todos os jogadores
        //mostra quem ganhou
        //mostra proxima carta

        //comparando com jogador 1



//         Handler().postDelayed({
//                    mostraCarta()
//                }, 1000)
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
