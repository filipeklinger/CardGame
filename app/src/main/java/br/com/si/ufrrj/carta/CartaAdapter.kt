package br.com.si.ufrrj.carta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.si.ufrrj.R

//construtor primario recebe uma lista de singleCard e o contexto em que foi chamado
//implementa a classe abstrata Adapter do tipo ViewHolder
class CartaAdapter(private val context:Context,private val cartaList: ArrayList<singleCard>) : Adapter<CartaAdapter.ViewHolder>(){

    //essa inner class implementa o tipo abstrato ViewHolder para ser utilizado no Adapter

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //aqui vamos associar os ids em carta_single com variaveis da nossa classe
        val titulo = itemView.findViewById<TextView>(R.id.titulo_card)
        //TODO pegar os outros atributos
        //val figure = itemView.findViewById<ImageView>(R.id.card_figure)

        fun bindView(card: singleCard){
            //obtendo a carta da visualizacao atual
            titulo.text = card.nome
        }

    }

    /**
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return cartaList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflando o layout criado em carta single dentro do ReciclerView
        val view = LayoutInflater.from(context).inflate(R.layout.carta_single, parent, false)
        return ViewHolder(view)
    }

    /**
     * Aqui inserimos as informacoes de singleCard nas variaveis setadas em ViewHolder
     * para cada item do ReciclerView
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cartaList[position])
    }

}