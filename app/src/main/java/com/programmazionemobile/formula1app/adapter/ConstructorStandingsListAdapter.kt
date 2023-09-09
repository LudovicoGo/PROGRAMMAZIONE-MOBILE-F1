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
//    private val constructorStandings: List<ConstructorStanding>,
    private val selectedSpinnerYear: String

) :
    ListAdapter<ConstructorStanding, ConstructorStandingsListAdapter.ConstructorStandingsViewHolder>(
        ConstructorStandingsRowItemDiffCallback()
    ) {


    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1


   /* override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {


        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.header_driver_standings, parent, false)
            )
            TYPE_ITEM -> ConstructorStandingsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.constructor_championship_recycler_view, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
        *//*
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.constructor_championship_recycler_view, parent, false)



        return ConstructorStandingsViewHolder(itemView)*//*
    }*/


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConstructorStandingsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.constructor_championship_recycler_view, parent, false)



        return ConstructorStandingsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConstructorStandingsViewHolder, position: Int) {
        val currentConstructorStanding = getItem(position)


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
            holder.backgroundTeam.setImageResource(R.drawable.nodriverpic)
        }


        if (teamColorResId != 0) {
            val teamColor = ContextCompat.getColor(context, teamColorResId)

            // Cambiare il colore del Drawable di sfondo
            val backgroundDrawable =
                ContextCompat.getDrawable(context, R.drawable.standings_bottom_border)
            backgroundDrawable?.setTint(teamColor)
            /*
                        holder..setTextColor(
                            ColorUtils.setAlphaComponent(
                                teamColor,
                                32
                            ))*/


            holder.bottomBorderImage.background = backgroundDrawable
//            holder.backgroundTeam.background = backgroundDrawable
        }



        holder.itemView.setOnClickListener { view ->
             val bundle = Bundle()
//             val selectedYear = view.findViewById<Spinner>(R.id.DriverStandingsYearSpinner)
             bundle.putString("constructorID", currentConstructorStanding.constructor.constructorId)
             bundle.putString("constructorName", currentConstructorStanding.constructor.name.toString())
             bundle.putString("constructorNationality", currentConstructorStanding.constructor.nationality)
             bundle.putString("constructorSeasonPosition", currentConstructorStanding.position)
             bundle.putString("constructorSeasonPoints", currentConstructorStanding.points)
             bundle.putString("constructorSeasonWins", currentConstructorStanding.wins)
             bundle.putString("selectedSpinnerYear", selectedSpinnerYear)

            view.findNavController().navigate(R.id.constructorProfileFragment, bundle)
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
    // Inizializza le viste all'interno dell'elemento della riga qui
    // Esempio:
    // val teamNameTextView = itemView.findViewById<TextView>(R.id.teamNameTextView)
    // val pointsTextView = itemView.findViewById<TextView>(R.id.pointsTextView)

    val constructorName = itemView.findViewById<TextView>(R.id.ConstructorName)
    val constructorWins = itemView.findViewById<TextView>(R.id.ConstructorWins)
    val constructorID = itemView.findViewById<TextView>(R.id.ConstructorID)
    val constructorPosition = itemView.findViewById<TextView>(R.id.ConstructorPosition)
    val constructorPoints = itemView.findViewById<TextView>(R.id.ConstructorPoints)

    val bottomBorderImage = itemView.findViewById<ImageView>(R.id.BottomBorderStandings)
    val backgroundTeam = itemView.findViewById<ImageView>(R.id.backgroundTeam)


}
}
