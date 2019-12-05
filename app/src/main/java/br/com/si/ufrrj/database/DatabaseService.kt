package br.com.si.ufrrj.database

import android.content.Context
import br.com.si.ufrrj.database.AppDatabase
import br.com.si.ufrrj.database.DAOs.HeroDAO
import br.com.si.ufrrj.database.QueriesInterface
import br.com.si.ufrrj.database.models.Card
import br.com.si.ufrrj.database.models.User

class DatabaseService(context: Context) : QueriesInterface {
    private val dao: HeroDAO = AppDatabase.getInstance(context).heroDAO()
    override suspend fun getAllCartas(): List<Card>? {
        return dao.getCartas();
    }

    override suspend fun getCartasById(id_carta: Int): Card {
        return dao.getCartasById(id_carta);
    }

    override suspend fun getUserDeck(pessoa: Long): List<Card> {
        return dao.getUserDeck(pessoa)
    }

    override suspend fun getCartasByName(titulo: String): Card {
        return dao.getCartasByName(titulo)
    }

    override suspend fun adicionaCarta(carta: Card, pessoa: User): Boolean {
        return try {
            dao.adicionaCarta(carta, pessoa)
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }

    }

}