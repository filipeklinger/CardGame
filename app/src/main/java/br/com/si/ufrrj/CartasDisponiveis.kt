package br.com.si.ufrrj

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.si.ufrrj.carta.CardItemDecoration
import br.com.si.ufrrj.carta.CartaAdapter
import br.com.si.ufrrj.logica.UserStatus


class CartasDisponiveis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartas_disponiveis)

        var cartasDisponiveisList = findViewById<RecyclerView>(R.id.cartas_disponiveis_list)
        criaVisualizacao(cartasDisponiveisList)
        val adapter = CartaAdapter(this, UserStatus.cartasDisponiveis)

        addOrRemoveListenner(adapter)
        cartasDisponiveisList?.adapter = adapter
    }

    fun criaVisualizacao(cardList: RecyclerView){
        //customized GridLayoutManager
        var gridLayoutManager = object : GridLayoutManager(this, 2) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean { // force size of viewHolder here, this will override layout_height and layout_width from xml
                lp.height = (height / spanCount)
                lp.width =  (width / spanCount) - 60
                return true
            }
        }

        cardList.layoutManager = gridLayoutManager //layoutManager

        //otimizando list to scrool smootly
        cardList.setItemViewCacheSize(5)

        //criando padding para cada child
        cardList.addItemDecoration(
            CardItemDecoration(
            resources.getDimension(R.dimen.default_padding).toInt())
        )
    }

    /**
     * Logica de adicao de cartas ao Deck jogavel
     */
    fun addOrRemoveListenner(adapter: CartaAdapter){
        adapter.childClickListener = (object : CartaAdapter.OnChildClickListener{
            override fun onChildClick(v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                val singleCard = UserStatus.cartasDisponiveis[childPosition]
                val deckAtual = UserStatus.deckAtual
                var msg = ""

                if(deckAtual.contains(singleCard)){
                    var indice  = deckAtual.indexOf(singleCard)//as vezes a remocao por comparacao de objetos falha por isso pegamos o indice
                    deckAtual.removeAt(indice)
                    adapter.notifyDataSetChanged()
                    msg = "${singleCard.nome} Removido do deck"
                }else{
                    if(deckAtual.size < 10){
                        deckAtual.add(singleCard)
                        adapter.notifyDataSetChanged()
                        msg = "${singleCard.nome} Adicionado ao deck"
                    }else{
                        msg = "Deck cheio"
                    }
                }
                Toast.makeText(baseContext,msg,Toast.LENGTH_SHORT).show()
                return true
            }
        })
    }
}
