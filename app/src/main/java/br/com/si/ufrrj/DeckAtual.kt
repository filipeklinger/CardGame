package br.com.si.ufrrj

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_deck_atual.*

class DeckAtual : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_atual)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Ainda não é possivel adicionar cartas", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
