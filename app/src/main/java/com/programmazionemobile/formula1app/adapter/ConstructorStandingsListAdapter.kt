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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstructorStandingsViewHolder {
        val itemView = when (viewType) {
            TYPE_HEADER -> {
                // Infla il layout per l'elemento di intestazione
                LayoutInflater.from(parent.context).inflate(R.layout.header_constructors_standings, parent, false)
            }
            TYPE_ITEM -> {
                // Infla il layout per gli elementi della lista
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

    override fun onBindViewHolder(holder: ConstructorStandingsViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            // Gestisci l'elemento di intestazione
//            holder.headerTextView.text = "Intestazione" // Sostituisci con il testo desiderato
        } else {
            val currentConstructorStanding = getItem(position-1)
            holder.constructorName.text = currentConstructorStanding.constructor.name
            holder.constructorWins.text = "Wins: " + currentConstructorStanding.wins
            holder.constructorPosition.text = currentConstructorStanding.position
            holder.constructorPoints.text = currentConstructorStanding.points

            val teamColorResId = context.resources.getIdentifier(
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
                // Se l'ID è diverso da 0, il drawable è stato trovato
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



            holder.itemView.setOnClickListener { view ->
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
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }


    class ConstructorStandingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val constructorName = itemView.findViewById<TextView>(R.id.ConstructorName)
    val constructorWins = itemView.findViewById<TextView>(R.id.ConstructorWins)
    val constructorID = itemView.findViewById<TextView>(R.id.ConstructorID)
    val constructorPosition = itemView.findViewById<TextView>(R.id.ConstructorPosition)
    val constructorPoints = itemView.findViewById<TextView>(R.id.ConstructorPoints)
    val bottomBorderImage = itemView.findViewById<ImageView>(R.id.BottomBorderStandings)
    val backgroundTeam = itemView.findViewById<ImageView>(R.id.backgroundTeam)
}

    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }
}
