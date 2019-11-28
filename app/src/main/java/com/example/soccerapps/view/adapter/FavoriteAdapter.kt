package com.example.soccerapps.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soccerapps.R
import com.example.soccerapps.db.EventDB
import com.example.soccerapps.view.activity.DetailEventActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class FavoriteAdapter(
    private val favoriteEvent: List<EventDB>,
    private val context: Context?
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
    )

    override fun getItemCount(): Int = favoriteEvent.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favoriteEvent[position])
    }

    class FavoriteViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(eventDB: EventDB) {
            val timeEvent = eventDB.eventDate?.let {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
                    it
                )
            }

            val dateEvent = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                .format(timeEvent)
            itemView.date_event.text = dateEvent.toString()
            if (eventDB.homeScore != null) {
                itemView.home_name.text = eventDB.homeTeam
                itemView.away_name.text = eventDB.awayTeam
                itemView.home_score.text = eventDB.homeScore
                itemView.away_score.text = eventDB.awayScore
            } else {
                itemView.home_name.text = eventDB.homeTeam
                itemView.away_name.text = eventDB.awayTeam
                itemView.home_score.text = "-"
                itemView.away_score.text = "-"
            }

            itemView.setOnClickListener {
                itemView.context.startActivity<DetailEventActivity>(
                    "idEvent" to eventDB.eventId,
                    "idHomeTeam" to eventDB.homeTeamId,
                    "idAwayTeam" to eventDB.awayTeamId
                )
            }
        }
    }
}