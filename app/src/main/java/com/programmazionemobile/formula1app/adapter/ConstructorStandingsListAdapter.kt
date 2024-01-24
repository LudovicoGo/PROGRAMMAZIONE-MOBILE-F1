import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.adapter.ConstructorStandingsRowItemDiffCallback
import com.programmazionemobile.formula1app.data.constructorStandingsData.ConstructorStanding

class ConstructorStandingsListAdapter(
    private val context: Context,
    private val selectedSpinnerYear: String) : ListAdapter<ConstructorStanding, ConstructorStandingsListAdapter.ConstructorStandingsViewHolder>(ConstructorStandingsRowItemDiffCallback()) {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    //Questo metodo viene chiamato quando la RecyclerView ha bisogno di creare un nuovo ViewHolder,
    // cioè di creare un nuovo elemento per inserirlo nella lista, in base al tipo di vista (header o elemento normale della lista), viene fatto l'inflating del layout corrispondente.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstructorStandingsViewHolder {
        val itemView = when (viewType) {
            TYPE_HEADER -> {
                // Inflate layout per l'elemento di intestazione
                LayoutInflater.from(parent.context).inflate(R.layout.header_constructors_standings, parent, false)
            }
            TYPE_ITEM -> {
                // Inflate layout per gli elementi della lista
                LayoutInflater.from(parent.context).inflate(R.layout.constructor_championship_recycler_view, parent, false)
            }
            else -> throw IllegalArgumentException("Tipo di vista sconosciuto")
        }
        return ConstructorStandingsViewHolder(itemView)
    }

   /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstructorStandingsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.constructor_championship_recycler_view, parent, false)
        return ConstructorStandingsViewHolder(itemView)
    }*/


    // metodo serrve per popolare i dati di un elemento della lista. a seconda del tipo di vista (header o elemento normale della lista)
    // vengono impostati i dati relativi all'header o all'elemento normale della lista.
    override fun onBindViewHolder(holder: ConstructorStandingsViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
        } else {
            val currentConstructorStanding = getItem(position-1) //uso position-1 perché nella posizione 0 ho messo l'header e ho allungato itemCount di
            holder.constructorName.text = currentConstructorStanding.constructor.name
            holder.constructorWins.text = "Wins: " + currentConstructorStanding.wins
            holder.constructorPosition.text = currentConstructorStanding.position
            holder.constructorPoints.text = currentConstructorStanding.points

            val teamColorResId = context.resources.getIdentifier(       //cerco il colore col nome del team tra le risors
                "${
                    currentConstructorStanding.constructor.constructorId.replace(
                        "-",
                        "_"
                    )
                }", "color", context.packageName
            )


            val drawableResId = context.resources.getIdentifier(
                currentConstructorStanding.constructor.constructorId + "_logo",
                "drawable",
                context.packageName
            )
            if (drawableResId != 0) {
                // Se drawableResId è diverso da 0, il drawable è stato trovato
                holder.backgroundTeam.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
            } else {
                holder.backgroundTeam.visibility = View.GONE
            }


            if (teamColorResId != 0) {
                val teamColor = ContextCompat.getColor(context, teamColorResId)
                //cambio il colore del drawable di sfondo
                val backgroundDrawable =
                    ContextCompat.getDrawable(context, R.drawable.standings_bottom_border)
                backgroundDrawable?.setTint(teamColor)
                holder.bottomBorderImage.background = backgroundDrawable
            }



            holder.itemView.setOnClickListener { view ->        //su ogni elemento della lista mettto un clickListener che mi porta al fragment del profilo del team a cui passo anche alcuni dati
                val bundle = Bundle()
                bundle.putString(
                    "constructorID",
                    currentConstructorStanding.constructor.constructorId
                )
                bundle.putString(
                    "constructorName",
                    currentConstructorStanding.constructor.name.toString()
                )
                bundle.putString(
                    "constructorNationality",
                    currentConstructorStanding.constructor.nationality
                )
                bundle.putString("constructorSeasonPosition", currentConstructorStanding.position)
                bundle.putString("constructorSeasonPoints", currentConstructorStanding.points)
                bundle.putString("constructorSeasonWins", currentConstructorStanding.wins)
                bundle.putString("selectedSpinnerYear", selectedSpinnerYear)
                view.findNavController().navigate(R.id.constructorProfileFragment, bundle)
            }
        }
    }


    //restituisce il tipo di vista in base alla posizione. l'header va inserito in posizione 0
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }


    class ConstructorStandingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {  //classe con i riferimenti degli elementi presenti in un item della lista
    val constructorName = itemView.findViewById<TextView>(R.id.ConstructorName)
    val constructorWins = itemView.findViewById<TextView>(R.id.ConstructorWins)
    val constructorID = itemView.findViewById<TextView>(R.id.ConstructorID)
    val constructorPosition = itemView.findViewById<TextView>(R.id.ConstructorPosition)
    val constructorPoints = itemView.findViewById<TextView>(R.id.ConstructorPoints)
    val bottomBorderImage = itemView.findViewById<ImageView>(R.id.BottomBorderStandings)
    val backgroundTeam = itemView.findViewById<ImageView>(R.id.backgroundTeam)
}

    override fun getItemCount(): Int { //ritorno il getItemCount di default +1 perché alla posizione 0 della lista va mostrato l'header e quindi tutti gli elementi in posizione i vanno mostrati in posizione i+1
        return super.getItemCount()+1
    }
}
