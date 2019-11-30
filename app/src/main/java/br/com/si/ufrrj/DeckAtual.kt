package br.com.si.ufrrj

import android.os.AsyncTask
import android.os.Bundle
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
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //buscando o ListView em content_deck_atual.xml

        var deckAtualList = findViewById<RecyclerView>(R.id.deck_atual_list)

        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        deckAtualList.layoutManager = layoutManager

        //otimizando list to scrool smootly
        deckAtualList.setHasFixedSize(true)
        deckAtualList.setItemViewCacheSize(10)


//        var statusUser = UserStatus
//        var deckNumber:ArrayList<Int> = statusUser.deckAtual
//
//        var cardsList:ArrayList<singleCard> = ArrayList()
//        //buscando cards com API
//        var api =  apiConect(this)
//
//        //obtendo instancia do parser em background
//        var parseApi = ParseApiString(cardsList,deckAtualList)
//
//        //percorrendo os ids armazenados
//        deckNumber.iterator().forEach { idBusca ->
//            //para cada Id uma basca na API
//            println("Id recebido: $idBusca")
//        }
//        api.buscarId(70){
//                parseApi.execute(it)
//        }






//        cardsList.add(singleCard("Super X"))
//        cardsList.add(singleCard("Mega Y"))

        //criando um adapter
        //setando na view

        deckAtualList?.adapter = CartaAdapter(this,UserStatus.deckAtual)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Ainda não é possivel adicionar cartas", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}
