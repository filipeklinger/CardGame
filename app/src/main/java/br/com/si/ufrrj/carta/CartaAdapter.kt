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
        //aqui vamos associar os ids em carta_single com as variaveis da nossa classe
        private val titulo: TextView = itemView.findViewById(R.id.titulo_card)
        private val inteligencia: TextView = itemView.findViewById(R.id.inteligencia_card)
        private val forca: TextView = itemView.findViewById(R.id.forca_card)
        private val velocidade: TextView = itemView.findViewById(R.id.velocidade_card)
        private val vigor: TextView = itemView.findViewById(R.id.vigor_card)
        private val poder: TextView = itemView.findViewById(R.id.poder_card)
        private val combate: TextView = itemView.findViewById(R.id.combate_card)


        fun bindView(card: singleCard){
            //obtendo a carta da visualizacao atual
            titulo.text = card.nome
            inteligencia.text = "Inte: ${card.inteligencia}"
            forca.text = "Forç: ${card.forca}"
            velocidade.text = "Velo: ${card.velocidade}"
            vigor.text = "Vigo: ${card.vigor}"
            poder.text = "Pode: ${card.poder}"
            combate.text = "Comb: ${card.combate}"
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