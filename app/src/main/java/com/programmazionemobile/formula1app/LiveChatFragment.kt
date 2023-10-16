package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.programmazionemobile.formula1app.adapter.MessageAdapter
import com.programmazionemobile.formula1app.model.Message

class LiveChatFragment: Fragment(){

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    private val args: CircuitInfoFragmentArgs by navArgs()

    private var isSpecificFragment = false

    private var room: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_livechat, container, false)

        val raceInfoBackArrow = view.findViewById<ImageView>(R.id.back)
        raceInfoBackArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        view.findViewById<TextView>(R.id.intestazioneText).text = "Chat di " + args.circuitName

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().reference

        chatRecyclerView = view.findViewById(R.id.recyclerView)
        messageBox = view.findViewById(R.id.editText)
        sendButton = view.findViewById(R.id.send)

        room = args.circuitID

        messageList = ArrayList()
        messageAdapter = MessageAdapter(requireContext(), messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatRecyclerView.adapter = messageAdapter

        //Logica per aggiungere i messsaggi alla recycler
        mDbRef.child("chats").child(room!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshoot in snapshot.children){
                        val message = postSnapshoot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid, FirebaseAuth.getInstance().currentUser?.displayName,
                FirebaseAuth.getInstance().currentUser?.photoUrl)

            mDbRef.child("chats").child(room!!).child("messages").push()
                .setValue(messageObject)

            messageBox.setText("")
        }

        // Ottieni il FragmentContainerView
        val fragmentContainer = activity?.findViewById<FragmentContainerView>(R.id.fragContainer)

        if (requireContext() == fragmentContainer?.context)
            isSpecificFragment = true

        if (fragmentContainer != null && isSpecificFragment) {
            val layoutParams = fragmentContainer.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomMargin = -25 // Cambio il valore del margine inferiore da 4 a -25
            fragmentContainer.layoutParams = layoutParams
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Ripristina il margine inferiore del FragmentContainerView quando esce da questo fragment
        if (isSpecificFragment) {
            val fragmentContainer = activity?.findViewById<FragmentContainerView>(R.id.fragContainer)
            val layoutParams = fragmentContainer?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomMargin = 5 // Imposta il margine inferiore predefinito o desiderato quando si esce dal fragment specifico
            fragmentContainer.layoutParams = layoutParams
            isSpecificFragment = false // Ripristina la variabile
        }
    }
}
