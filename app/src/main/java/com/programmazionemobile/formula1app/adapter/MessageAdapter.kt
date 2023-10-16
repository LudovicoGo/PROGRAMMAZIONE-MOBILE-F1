package com.programmazionemobile.formula1app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.programmazionemobile.formula1app.DateConverter
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.databinding.RecivedMsgBinding
import com.programmazionemobile.formula1app.databinding.SendMsgBinding
import com.programmazionemobile.formula1app.model.Message
import coil.load
import coil.transform.RoundedCornersTransformation

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recived_msg, parent, false)
            return ReceiveViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send_msg, parent, false)
            return SentViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message
            viewHolder.time.text = DateConverter.convertDateMessages(currentMessage.timeStamp!!)

        } else {
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message
            viewHolder.time.text = DateConverter.convertDateMessages(currentMessage.timeStamp!!)
            viewHolder.photoSender.load(currentMessage.photo){
                transformations(RoundedCornersTransformation(100f))
            }

            val partiNomeCognome = currentMessage.nome!!.split(" ")?.toMutableList()

            viewHolder.messageSender.text = (partiNomeCognome?.get(0) ?: "noName") + " " + (partiNomeCognome?.get(1) ?: "noSurname")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }
    }

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val sentMessage = itemView.findViewById<TextView>(R.id.sentTextMessage)
        val time = itemView.findViewById<TextView>(R.id.messageData)

    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val receiveMessage = itemView.findViewById<TextView>(R.id.recivedTextMessage)
        val time = itemView.findViewById<TextView>(R.id.messageData)
        val messageSender = itemView.findViewById<TextView>(R.id.messageSender)
        val photoSender = itemView.findViewById<ImageView>(R.id.photoSender)

    }
}
