package br.com.si.ufrrj.logica

class apiConect {
    //Aqui vamos armazenar as credenciais e criar as coneccoes necessarias para uso da API
    private val token = "1625416087590258"
    private val apiBase = "https://superheroapi.com/api/${token}/"


    fun buscar(id: Int): String {
        return apiBase + "$id"
    }

}