package br.com.si.ufrrj.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cartas",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id_deque"),
            childColumns = arrayOf("pessoa"),
            onDelete = ForeignKey.NO_ACTION //não faça nada ao remover carta do Usuário
        )
    )

)
data class Card(

    @PrimaryKey(autoGenerate = true)
    val id_carta: Long,

    val titulo: String,
    val imagem: String,
    val forca: Int,
    val velocidade: Int,
    val conhecimento: Int,
    val poder: Int,
    val vigor: Int,
    val combate: Int,

    val pessoa: Long //foreign Key
)
