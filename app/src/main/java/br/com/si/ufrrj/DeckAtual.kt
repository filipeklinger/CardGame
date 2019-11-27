package br.com.si.ufrrj

import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.si.ufrrj.carta.CartaAdapter
import br.com.si.ufrrj.carta.singleCard
import br.com.si.ufrrj.logica.userStatus

import kotlinx.android.synthetic.main.activity_deck_atual.*

class DeckAtual : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_atual)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //buscando o ListView em content_deck_atual.xml
        var deckAtualList = findViewById<RecyclerView>(R.id.deck_atual_list)

        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        deckAtualList.layoutManager = layoutManager

        //otimizando list to scrool smootly
        deckAtualList.setHasFixedSize(true)
        deckAtualList.setItemViewCacheSize(10)


        var user:userStatus = userStatus()
        var cardsList:ArrayList<singleCard> = ArrayList()
        cardsList.add(singleCard("Super X"))
        cardsList.add(singleCard("Mega Y"))

        //criando um adapter
        //setando na view
        deckAtualList.adapter = CartaAdapter(this,cardsList)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Ainda não é possivel adicionar cartas", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
