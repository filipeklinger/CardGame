package br.com.si.ufrrj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.si.ufrrj.logica.apiConect

class CartasDisponiveis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartas_disponiveis)

        //TODO
        var texto:TextView = findViewById(R.id.response)


    }
}
