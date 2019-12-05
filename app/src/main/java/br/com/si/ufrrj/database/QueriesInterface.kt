package br.com.si.ufrrj.database

import br.com.si.ufrrj.database.models.Card
import br.com.si.ufrrj.database.models.User

interface QueriesInterface {
    suspend fun getAllCartas(): List<Card>?

    //retorna cartas por id
    suspend fun getCartasById(id_carta: Int): Card

    //retorna cartas de um usuário
    suspend fun getUserDeck(pessoa: Long): List<Card>

    //retorna carta pelo título
    suspend fun getCartasByName(titulo: String): Card

    //adiciona carta ao sistema
    suspend fun adicionaCarta(carta: Card, pessoa: User): Boolean
}