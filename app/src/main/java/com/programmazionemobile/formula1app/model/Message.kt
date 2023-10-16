package com.programmazionemobile.formula1app.model

import android.net.Uri
import java.util.Date

class Message {

    var message: String? = null
    var senderId: String? = null
    var timeStamp: Date? = null
    var nome: String? = null
    var photo: String? = null


    constructor(){}

    constructor(message: String?, senderId: String?, nome: String?, photo: Uri?) {
        this.message = message
        this.senderId = senderId
        this.timeStamp = Date()
        this.nome = nome
        this.photo = photo.toString()
    }
}
