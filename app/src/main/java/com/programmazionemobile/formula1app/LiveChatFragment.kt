//package com.programmazionemobile.formula1app
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.FrameLayout
//import android.widget.ImageView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.constraintlayout.widget.ConstraintSet
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentContainerView
//import androidx.navigation.findNavController
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.programmazionemobile.formula1app.adapter.MessageAdapter
//import com.programmazionemobile.formula1app.model.Message
//
//class LiveChatFragment: Fragment(), MessageAdapter.MessageClickListener {
//
//    private var isSpecificFragment = false
//
//    private val messages: ArrayList<Message> = ArrayList()
//
//    private lateinit var messageAdapter: MessageAdapter // Dichiarazione della variabile messageAdapter
//    private val messageList = ArrayList<Message>() // Dichiarazione e inizializzazione della variabile messageList
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_livechat, container, false)
//
//        // Inizializza messageAdapter qui
//        messageAdapter = MessageAdapter(requireContext(), messageList)
//
//        // Imposta il listener nel tuo adapter
//        messageAdapter.setMessageClickListener(this)
//
//        // Configura la RecyclerView
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//        val layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = messageAdapter
//
//        // Ottieni il FragmentContainerView
//        val fragmentContainer = activity?.findViewById<FragmentContainerView>(R.id.fragContainer)
//
//        if (requireContext() == fragmentContainer?.context)
//            isSpecificFragment = true
//
//        if (fragmentContainer != null && isSpecificFragment) {
//            val layoutParams = fragmentContainer.layoutParams as ConstraintLayout.LayoutParams
//            layoutParams.bottomMargin = -25 // Cambio il valore del margine inferiore da 4 a -25
//            fragmentContainer.layoutParams = layoutParams
//        }
//
//        return view
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private fun addNewMessageToChat(newMessage: Message) {
//        messageList.add(newMessage)
//        messageAdapter.notifyDataSetChanged()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        messageAdapter.setMessageClickListener(this)
//
//        // Inizializza il tuo RecyclerView
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
//
//        // Inizializza il tuo MessageAdapter
//        val adapter = MessageAdapter(requireContext(), messages)
//
//        // Imposta l'implementazione dell'interfaccia MessageClickListener
//        adapter.setMessageClickListener(this)
//
//        // Collega l'adattatore al RecyclerView
//        recyclerView.adapter = adapter
//
//        // Configura il layout manager, ad esempio LinearLayoutManager
//        val layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.layoutManager = layoutManager
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        // Ripristina il margine inferiore del FragmentContainerView quando esce da questo fragment
//        if (isSpecificFragment) {
//            val fragmentContainer = activity?.findViewById<FragmentContainerView>(R.id.fragContainer)
//            val layoutParams = fragmentContainer?.layoutParams as ConstraintLayout.LayoutParams
//            layoutParams.bottomMargin = 5 // Imposta il margine inferiore predefinito o desiderato quando si esce dal fragment specifico
//            fragmentContainer.layoutParams = layoutParams
//            isSpecificFragment = false // Ripristina la variabile
//        }
//    }
//
//    override fun onSendMessageClick(message: Message) {
//        // Qui gestisci l'invio del messaggio
//        // Ad esempio, puoi inviare il messaggio al server o fare altre operazioni desiderate.
//
//        // Aggiungi il messaggio alla lista
//        messages.add(message)
//
//        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
//
//        // Aggiorna il RecyclerView per riflettere il nuovo messaggio
//        recyclerView.adapter?.notifyItemInserted(messages.size - 1)
//
//        // Scorrere automaticamente verso il nuovo messaggio (opzionale)
//        recyclerView.smoothScrollToPosition(messages.size - 1)
//    }
//}