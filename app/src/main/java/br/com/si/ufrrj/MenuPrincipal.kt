package br.com.si.ufrrj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MenuPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnJogar = this.findViewById<Button>(R.id.jogar_btn)
        var btnDeck = this.findViewById<Button>(R.id.deck_btn)
        var btnInstruc = this.findViewById<Button>(R.id.instrucoes_btn)
        //setando os clicaveis do botao

        btnJogar.setOnClickListener {
            Toast.makeText(this,"Iniciando...",Toast.LENGTH_LONG).show();
        }
        btnDeck.setOnClickListener {
            val intent = Intent(this,DeckMenu::class.java)
            startActivity(intent)
        }
        btnInstruc.setOnClickListener {
            val intent = Intent(this,Instrucoes::class.java)
            startActivity(intent)
        }
    }
    //Design - Immersive mode
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) escondeUI() // removendo o system UI
    }


    private fun escondeUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
