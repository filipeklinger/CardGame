package br.com.si.ufrrj

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.si.ufrrj.carta.CartaAdapter
import br.com.si.ufrrj.logica.UserStatus


class CartasDisponiveis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartas_disponiveis)

        var cartasDisponiveisList = findViewById<RecyclerView>(R.id.cartas_disponiveis_list)

        //customized GridLayoutManager
        var gridLayoutManager = object : GridLayoutManager(this, 2) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean { // force size of viewHolder here, this will override layout_height and layout_width from xml
                lp.height = height / spanCount
                lp.width = width / spanCount
                lp.bottomMargin = 10
                lp.marginEnd = 1
                lp.marginStart = 1
                lp.layoutDirection = HORIZONTAL
                return true
            }
        }

        cartasDisponiveisList.layoutManager = gridLayoutManager//layoutManager

        //otimizando list to scrool smootly
        cartasDisponiveisList.setHasFixedSize(true)
        cartasDisponiveisList.setItemViewCacheSize(10)

        val adapter = CartaAdapter(this, UserStatus.cartasDisponiveis)
        adapter.childClickListener = (object : CartaAdapter.OnChildClickListener{
            override fun onChildClick(v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                Toast.makeText(baseContext,"Clicado",Toast.LENGTH_SHORT).show()
                return true
            }
        })

        cartasDisponiveisList?.adapter = adapter


    }
}
