package br.com.si.ufrrj.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.si.ufrrj.database.DAOs.HeroDAO
import br.com.si.ufrrj.database.models.*

@Database(
    entities = [Card::class],
    version = 1 ,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun heroDAO(): HeroDAO

    companion object {

        private var appDatabase: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase =
                    Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, "hero.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return appDatabase!!
        }
    }

}