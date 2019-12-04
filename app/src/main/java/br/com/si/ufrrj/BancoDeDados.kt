package br.com.si.ufrrj

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.si.ufrrj.dbDAOs.HeroDAO
import br.com.si.ufrrj.dbModels.Card
import br.com.si.ufrrj.dbModels.User

private const val DATABASE = "heros_war"

@Database(
    entities = [Card::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class BancoDeDados : RoomDatabase() {
    abstract fun bancoDeDados(): HeroDAO


}