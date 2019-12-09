package br.com.si.ufrrj.gamePlay

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.si.ufrrj.R
import br.com.si.ufrrj.logica.GameStatus
import br.com.si.ufrrj.logica.Oponentes
import br.com.si.ufrrj.logica.UserStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


class LoadGame : AppCompatActivity() {
    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)
        //fazendo um bind do contexto atual
        mContext = this
        //---Fluxo Principal
        //Gerar Oponentes
        //embaralhar deck do jogador
        //Criar pilha de cartas ganhadoras e cartas perdedoras
        //se pilha de cartas ganhadoras > cartas perdedoras jogador ganha 1 carta das cartas ganhadoras
        //jogo acaba quando cartas do jogador terminarem

        val textoLoadgame = findViewById<TextView>(R.id.text_load_game)
        textoLoadgame.text = "Buscando Oponentes"
        //iniciando status do novo jogo
        GameStatus.getPartida()
        //criando oponentes
        val oponentes = Oponentes(this)
        oponentes.gerar()
        UserStatus.deckAtual.shuffle()//Embaralhando cartas do Jogador
        progresso()
    }

    fun progresso(){
        val game = GameStatus.getPartida()

        GlobalScope.async {
            while (true){
                delay(200)
                if(game.cartasOponente1.size >= game.qtdCartasOponente && game.cartasOponente2.size >= game.qtdCartasOponente){//como é uma pilha assim que estiver vazia é pq o job acabou
                    startGamePlay()
                    break
                }
            }
        }
    }

    fun startGamePlay(){
        val intent = Intent(mContext, Jogando::class.java)
        startActivity(intent)
        finish()
    }
}
