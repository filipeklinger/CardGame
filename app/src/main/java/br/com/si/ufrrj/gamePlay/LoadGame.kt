package br.com.si.ufrrj.gamePlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import br.com.si.ufrrj.R
import br.com.si.ufrrj.logica.GameStatus
import br.com.si.ufrrj.logica.Oponentes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


class LoadGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)
        //1 Gerar Oponentes
        //TODO 2 embaralhar deck do jogador
        //TODO Criar pilha de cartas ganhadoras e cartas perdedoras
        //TODO se pilha de cartas ganhadoras > cartas perdedoras jogador ganha 1 carta das cartas ganhadoras
        //TODO jogo acaba quando cartas do jogador ou oponente terminarem

        val textoLoadgame = findViewById<TextView>(R.id.text_load_game)
        textoLoadgame.text = "Buscando Oponentes"
        //iniciando status do novo jogo
        GameStatus.getPartida()
        //criando oponentes
        val oponentes = Oponentes(this)
        oponentes.gerar()
        progresso(textoLoadgame)
    }

    fun progresso(textView: TextView){
        val game = GameStatus.getPartida()
        val progressBar = findViewById<ProgressBar>(R.id.progress_load_game)
        progressBar.max = game.qtdCartasOponente * 2 //2 oponentes
        progressBar.isIndeterminate = false

        GlobalScope.async {
            while (true){
                delay(200)
                if(game.cartasOponente1.size >= game.qtdCartasOponente){//como é uma pilha assim que estiver vazia é pq o job acabou
                    textView.text = "Finalizado"
                    progressBar.visibility = View.INVISIBLE
                    break
                }else{
                    progressBar.progress = game.cartasOponente1.size + game.cartasOponente2.size
                }
            }
        }
    }
}
