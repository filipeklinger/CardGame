package br.com.si.ufrrj.carta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.si.ufrrj.R
import br.com.si.ufrrj.logica.UserStatus
import br.com.si.ufrrj.logica.VolleySingleton
import com.android.volley.toolbox.NetworkImageView
import kotlinx.android.synthetic.main.carta_single.*


//construtor primario recebe uma lista de singleCard e o contexto em que foi chamado
//implementa a classe abstrata Adapter do tipo ViewHolder
class CartaAdapter(private val context:Context,private val cartaList: ArrayList<singleCard>) : Adapter<CartaAdapter.ViewHolder>() {

    var childClickListener: OnChildClickListener? = null

    //essa inner class implementa o tipo abstrato ViewHolder para ser utilizado no Adapter
    class ViewHolder(itemView: View,childClickListener: OnChildClickListener?) : RecyclerView.ViewHolder(itemView) {
        //aqui vamos associar os ids em carta_single com as variaveis da nossa classe
        private val titulo: TextView = itemView.findViewById(R.id.titulo_card)
        private val inteligencia: TextView = itemView.findViewById(R.id.inteligencia_card)
        private val forca: TextView = itemView.findViewById(R.id.forca_card)
        private val velocidade: TextView = itemView.findViewById(R.id.velocidade_card)
        private val vigor: TextView = itemView.findViewById(R.id.vigor_card)
        private val poder: TextView = itemView.findViewById(R.id.poder_card)
        private val combate: TextView = itemView.findViewById(R.id.combate_card)
        private val card_figure: NetworkImageView = itemView.findViewById(R.id.card_figure)

        private val addOrRemoveButton: ImageButton = itemView.findViewById(R.id.add_or_remove_card)

        init {
            addOrRemoveButton.setOnClickListener {
                childClickListener?.onChildClick(it,layoutPosition,adapterPosition,0)

            }
        }

        fun bindView(card: singleCard,context: Context) {
            //obtendo a carta da visualizacao atual
            titulo.text = card.nome
            inteligencia.text = "Inte: ${card.inteligencia}"
            forca.text = "Forç: ${card.forca}"
            velocidade.text = "Velo: ${card.velocidade}"
            vigor.text = "Vigo: ${card.vigor}"
            poder.text = "Pode: ${card.poder}"
            combate.text = "Comb: ${card.combate}"

            //Mostrando a imagem da Carta
            card_figure.setDefaultImageResId(R.drawable.image_placeholder)
            val imageLoader = VolleySingleton.getInstance(context).getImageLoader()
            card_figure.setImageUrl(card.figura,imageLoader)

            if(UserStatus.deckAtual.contains(card)){//verificando se o card atual esta contido em deck
                addOrRemoveButton.setImageResource(R.drawable.ic_remove_card)
            }else{
                addOrRemoveButton.setImageResource(R.drawable.ic_add_card)
            }
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
        //passando a view e o listenner criado
        return ViewHolder(view,childClickListener)
    }

    /**
     * Aqui inserimos as informacoes de singleCard nas variaveis setadas em ViewHolder
     * para cada item do ReciclerView
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cartaList[position],context)
    }

    fun setChildClickListenner(clickListener: OnChildClickListener) {
        this.childClickListener = clickListener
    }


    interface OnChildClickListener {
        /**
         * Callback method to be invoked when a child in this expandable list has
         * been clicked.
         *
         * @param v The view within the expandable list/ListView that was clicked
         * @param groupPosition The group position that contains the child that
         * was clicked
         * @param childPosition The child position within the group
         * @param id The row id of the child that was clicked
         * @return True if the click was handled
         */
        fun onChildClick(
            v: View?,
            groupPosition: Int,
            childPosition: Int,
            id: Long
        ): Boolean
    }
}