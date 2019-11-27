package br.com.si.ufrrj

import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import br.com.si.ufrrj.logica.userStatus

import kotlinx.android.synthetic.main.activity_deck_atual.*

class DeckAtual : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_atual)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //buscando o ListView em content_deck_atual.xml
        var deckAtualList = findViewById<ListView>(R.id.deck_atual_list)

        val user:userStatus = userStatus()

        //criando um adapter temporario
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, user.deckAtual.toArray())

        deckAtualList.adapter = adapter


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Ainda não é possivel adicionar cartas", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
