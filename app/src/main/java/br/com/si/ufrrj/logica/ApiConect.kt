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

class ApiConect(var context: Context){
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
                val nome = rootObj.getString("name")?: "Err:p"
                val card = singleCard(nome)
                try{
                    card.id = rootObj.getInt("id")
                    card.inteligencia = rootObj.getString("intelligence")?: "0"
                    card.forca  = rootObj.getString("strength")?: "0"
                    card.velocidade = rootObj.getString("speed")?: "0"
                    card.vigor = rootObj.getString("durability")?: "0"
                    card.poder = rootObj.getString("power")?: "0"
                    card.combate = rootObj.getString("combat")?: "0"
                }catch (e:Exception){

                }

                card
            }else{
                singleCard("Err")
            }
        }
    }


    fun buscarId(id: Int, callback: (result: String?) -> Unit){
        val url = apiBase + "${id}/powerstats"

        // Volley request
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                try {
                    callback.invoke(response.toString())
                }catch (e:Exception){
                    Log.d(tag,"Erro ${e}!!")
                    callback.invoke("-1")
                }
            }, Response.ErrorListener{
                // Error in request
                Log.d(tag,"Resposta ERRO!!")
                callback.invoke("-1")
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

    /**
     * retorna a Url do card Dado um Id
     * @callback String
     */
    fun buscarImage(id: Int, callback: (result: String?) -> Unit){
        val url = apiBase + "${id}/image"

        // Volley request
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                try {
                    callback.invoke(response.getString("url"))
                }catch (e:Exception){
                    Log.d(tag,"Erro ${e}!!")
                    callback.invoke("-1")
                }
            }, Response.ErrorListener{
                // Error in request
                Log.d(tag,"Resposta ERRO!!")
                callback.invoke("-1")
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