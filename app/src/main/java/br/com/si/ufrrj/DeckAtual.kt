package br.com.si.ufrrj

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.si.ufrrj.carta.CartaAdapter
import br.com.si.ufrrj.carta.CartaAdapter.OnChildClickListener
import br.com.si.ufrrj.carta.singleCard
import br.com.si.ufrrj.logica.UserStatus

import kotlinx.android.synthetic.main.activity_deck_atual.*

class DeckAtual : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_atual)

        botaoFlutuante()
    }

    fun criaVisualizacao(deckAtualList: RecyclerView){
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        deckAtualList.layoutManager = layoutManager
        //otimizando list to scrool smootly
        deckAtualList.setItemViewCacheSize(3)
        //inserindo dados na lista
        var adapter = CartaAdapter(this,UserStatus.deckAtual)

        //implementacao anonima do click listener para remover carta
        adapter.childClickListener = (object : OnChildClickListener {
            override fun onChildClick(v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                val singleCard:singleCard = UserStatus.deckAtual.removeAt(childPosition)
                Toast.makeText(baseContext,"Removido ${singleCard.nome}",Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                mostraQtd()
                return true
            }
        })

        deckAtualList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        //buscando o ReciclerView em content_deck_atual.xml
        val deckAtualList = findViewById<RecyclerView>(R.id.deck_atual_list)
        criaVisualizacao(deckAtualList)
        mostraQtd()

    }

    fun mostraQtd(){
        var qtdTv = findViewById<TextView>(R.id.deck_atual_qtd_cartas)
        qtdTv.text = "${UserStatus.deckAtual.size} / 5"

        var emptyView = findViewById<TextView>(R.id.empty_view)
        var recyclerView = findViewById<RecyclerView>(R.id.deck_atual_list)

        if(UserStatus.deckAtual.size == 0){
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }else{
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    fun botaoFlutuante(){
        fab.setOnClickListener { view ->
            if(UserStatus.deckAtual.size >= 5){
                val snack = Snackbar.make(view, "Não é possivel adicionar mais cartas", Snackbar.LENGTH_LONG)
                snack.setAction("Action", null)
                // snackbar background color
                snack.view.setBackgroundColor(Color.parseColor("#FFFFFF"))
                snack.show()
            }else{
                val intent = Intent(this,CartasDisponiveis::class.java)
                startActivity(intent)
            }
        }
    }
}
