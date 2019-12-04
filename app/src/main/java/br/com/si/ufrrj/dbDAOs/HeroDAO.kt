package br.com.si.ufrrj.dbDAOs

import androidx.room.*
import br.com.si.ufrrj.dbModels.Card
import br.com.si.ufrrj.dbModels.User

@Dao
interface HeroDAO{

    //retorna todas as cartas do sistema
    @Query("SELECT * FROM cartas")
    suspend fun getCartas(): List<Card>

    //retorna cartas por id
    @Query("SELECT * FROM cartas WHERE id_carta = :id_carta")
    suspend fun getCartasById(id_carta: Int): Card

    //retorna cartas de um usuário
    @Query("SELECT * FROM cartas WHERE pessoa = :pessoa")
    suspend fun getUserDeck(pessoa: Long): ArrayList<Card>

    //retorna carta pelo título
    @Query("SELECT * FROM cartas WHERE titulo = :titulo")
    suspend fun getCartasByName(titulo: String): Card

    //suspend fun deleteCarta(carta: Card)

    //adiciona carta ao sistema
    @Insert(onConflict = OnConflictStrategy.ABORT) // usuário já possui carta
    suspend fun adicionaCarta(carta: Card, pessoa: User): Boolean
}