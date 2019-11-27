package br.com.si.ufrrj.carta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.si.ufrrj.R

class CartaAdapter(context: Context, var lista: ArrayList<singleCard>) : ArrayAdapter<singleCard>(context, R.layout.carta_single, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rootView: View?

        rootView = if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.carta_single, null)
        } else {
            convertView
        }

        //buscando a carta atual da lista (retorna um objeto do tipo singleCard)
        var carta:singleCard = lista[position]

        var titulo:TextView? = rootView?.findViewById(R.id.titulo_card)
        titulo?.text = carta.nome

        return super.getView(position, rootView, parent)
    }

}