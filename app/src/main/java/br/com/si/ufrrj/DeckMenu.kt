package br.com.si.ufrrj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DeckMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_menu)

        var btnCartas = findViewById<Button>(R.id.deck_cartas_btn)
        var btnDeckAtual = findViewById<Button>(R.id.deck_atual_btn)

        btnDeckAtual.setOnClickListener{
            val intent = Intent(this,DeckAtual::class.java)
            startActivity(intent)
        }

    }
}
