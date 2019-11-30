package br.com.si.ufrrj

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.si.ufrrj.carta.CartaAdapter
import br.com.si.ufrrj.carta.singleCard
import br.com.si.ufrrj.logica.apiConect
import br.com.si.ufrrj.logica.UserStatus

import kotlinx.android.synthetic.main.activity_deck_atual.*

class DeckAtual : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_atual)

        //buscando o ListView em content_deck_atual.xml

        var deckAtualList = findViewById<RecyclerView>(R.id.deck_atual_list)

        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        deckAtualList.layoutManager = layoutManager

        //otimizando list to scrool smootly
        deckAtualList.setHasFixedSize(true)
        deckAtualList.setItemViewCacheSize(10)

        deckAtualList?.adapter = CartaAdapter(this,UserStatus.deckAtual)

        mostraQtd()

        fab.setOnClickListener { view ->
            if(UserStatus.deckAtual.size == 10){
                val snack = Snackbar.make(view, "Não é possivel adicionar mais cartas", Snackbar.LENGTH_LONG)
                snack.setAction("Action", null)
                // snackbar background color
                snack.view.setBackgroundColor(Color.parseColor("#FFFFFF"))
                snack.show()
            }
        }
    }

    fun mostraQtd(){
        var qtdTv = findViewById<TextView>(R.id.deck_atual_qtd_cartas)
        qtdTv.text = "${UserStatus.deckAtual.size} / 10"
    }
}
