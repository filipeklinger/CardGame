package br.com.si.ufrrj.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "usuarios"
    //,indices = [Index("id_usuario")]
)
data class User(

    @PrimaryKey(autoGenerate = true)
    val id_usuario: Long,

    val nome: String,
    val idade: Int
)