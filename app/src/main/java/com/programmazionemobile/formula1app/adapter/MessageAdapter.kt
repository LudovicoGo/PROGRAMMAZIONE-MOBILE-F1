package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.databinding.SendMsgBinding
import com.programmazionemobile.formula1app.model.Message

class MessageAdapter(
    var context: Context,
    messages: ArrayList<Message>?,
    room: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    lateinit var messages: ArrayList<Message>
    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2
    val room :String

    inner class SentMsgHolder(itemView: View): ViewHolder(itemView){
        var binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }

    inner class ReceiveMsgHolder(itemView: View): ViewHolder(itemView){
        var binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == ITEM_SENT){
            val view = LayoutInflater.from(context).inflate(R.layout.send_msg, parent,false)
            SentMsgHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.recived_msg, parent,false)
            ReceiveMsgHolder(view)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    init {
        if (messages != null){
            this.messages = messages
        }
        this.room = room
    }

}
