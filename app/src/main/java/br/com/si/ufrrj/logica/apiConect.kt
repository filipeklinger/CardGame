package br.com.si.ufrrj.logica

import android.content.Context
import android.util.Log
import br.com.si.ufrrj.carta.singleCard
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import kotlinx.coroutines.*

class apiConect(var context: Context){
    //Aqui vamos armazenar as credenciais e criar as coneccoes necessarias para uso da API
    private val token = "1625416087590258"
    private val apiBase = "https://superheroapi.com/api/${token}/"
    private val tag = "API_conect"


    //definindo os metodos staticos da classe
    companion object Factory {
        /**
         * transforma a resposta JSON em um objeto singleCard
         * Deve ser executado em outro tread
         */
        fun parseResponse(json: String): singleCard{
            var rootObj = JSONObject(json)
            return if(rootObj.getString("response") == "success"){
                val nome = rootObj.getString("name")
                val card = singleCard(nome)
                card.inteligencia = rootObj.getInt("intelligence")
                card.forca  = rootObj.getInt("strength")
                card.velocidade = rootObj.getInt("speed")
                card.vigor = rootObj.getInt("durability")
                card.poder = rootObj.getInt("power")
                card.combate = rootObj.getInt("combat")
                card
            }else{
                singleCard("Err")
            }
        }
    }

    fun buscarId(id: Int, myCallback: (result: String?) -> Unit){
        Log.d(tag,"Init busca id ${id}")
        makeRequest(apiBase + "${id}/powerstats",myCallback)
    }



    fun makeRequest(url:String, callback: (result: String?) -> Unit){

        // Volley request
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                try {
                    callback.invoke(response.toString())
                }catch (e:Exception){
                    Log.d(tag,"Erro ${e}!!")
                }
            }, Response.ErrorListener{
                // Error in request
                Log.d(tag,"Resposta ERRO!!")
            })

        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        VolleySingleton.getInstance(context).addToRequestQueue(request)

    }

}