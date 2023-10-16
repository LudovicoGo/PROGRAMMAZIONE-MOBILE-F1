package com.programmazionemobile.formula1app.model

import java.util.Date

class Message {

    var message: String? = null
    var senderId: String? = null
    var timeStamp: Date? = null
    var nome: String? = null


    constructor(){}

    constructor(message: String?, senderId: String?, nome: String?) {
        this.message = message
        this.senderId = senderId
        this.timeStamp = Date()
        this.nome = nome
    }
}
