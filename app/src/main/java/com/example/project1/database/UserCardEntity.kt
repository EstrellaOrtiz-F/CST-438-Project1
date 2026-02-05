package com.example.project1.database
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "user_cards")
data class UserCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0, // row id
    val username: String,// Links card to the user
    val cardId:Long,//Card id from api
    val cardName: String,//displays card name
    val imageUrl:String?//the image url for the UI display
)