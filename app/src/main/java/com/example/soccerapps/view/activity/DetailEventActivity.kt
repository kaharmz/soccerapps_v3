package com.example.soccerapps.view.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.soccerapps.R
import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.db.EventDB
import com.example.soccerapps.db.database
import com.example.soccerapps.model.Event
import com.example.soccerapps.model.Team
import com.example.soccerapps.presenter.DetailEventPresenter
import com.example.soccerapps.view.interfaces.DetailView
import com.example.soccerapps.view.util.EspressoIdlingResource
import com.example.soccerapps.view.util.gone
import com.example.soccerapps.view.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*
import kotlinx.android.synthetic.main.match_detail.*
import kotlinx.android.synthetic.main.match_lineup.*
import kotlinx.android.synthetic.main.match_score.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class DetailEventActivity : AppCompatActivity(), DetailView {

    private lateinit var eventDetail: Event
    private lateinit var badgeHome: Team
    private lateinit var badgeAway: Team
    private lateinit var presenter: DetailEventPresenter

    private var idEventDetail: String? = null
    private var homeId: String? = null
    private var awayId: String? = null

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        idEventDetail = intent.getStringExtra("idEvent")
        homeId = intent.getStringExtra("idHomeTeam")
        awayId = intent.getStringExtra("idAwayTeam")
        getEventDetail()
    }

    private fun getEventDetail() {
        favoriteState()
        presenter = DetailEventPresenter(this, ApiRepository(), Gson())
        EspressoIdlingResource.increment()
        presenter.getEventDetail(idEventDetail, homeId, awayId)
    }

    private fun setPlayer(playerName: String?): String? {
        return playerName?.split(";".toRegex())?.dropLastWhile {
            it.isEmpty()
        }?.map { it.trim() }?.toTypedArray()?.joinToString("\n")
    }

    override fun hideLoading() {
        detail_event_progressbar.gone()
        layout_detail.visible()
    }

    override fun showLoading() {
        detail_event_progressbar.visible()
        layout_detail.gone()
    }

    override fun showEventList(data: List<Event>, home: List<Team>, away: List<Team>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        eventDetail = data[0]
        badgeAway = away[0]
        badgeHome = home[0]
        bindToView()
    }

    private fun bindToView() {
        val timeEvent = eventDetail.dateEvent?.let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)
        }
        val dateEvents = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            .format(timeEvent)

        if (eventDetail.homeScore != null) {
            date_event_detail.text = dateEvents.toString()
            Picasso.get().load(badgeHome.teamBadge).into(home_image_detail)
            Picasso.get().load(badgeAway.teamBadge).into(away_image_detail)
            home_score_detail.text = eventDetail.homeScore.toString()
            away_score_detail.text = eventDetail.awayScore.toString()
            home_name_detail.text = eventDetail.nameHomeTeam
            away_name_detail.text = eventDetail.nameAwayTeam
            home_goals_detail.text = setPlayer(eventDetail.homeGoalDetails)
            away_goals_detail.text = setPlayer(eventDetail.awayGoalDetails)
            home_formation_detail.text = eventDetail.homeFormation
            away_formation_detail.text = eventDetail.awayFormation
            home_red_card_detail.text = setPlayer(eventDetail.homeRedCards)
            away_red_card_detail.text = setPlayer(eventDetail.awayRedCards)
            home_yellow_card_detail.text = setPlayer(eventDetail.homeYellowCards)
            away_yellow_card_detail.text = setPlayer(eventDetail.awayYellowCards)
            home_sub_detail.text = setPlayer(eventDetail.homeLineupSubstitutes)
            away_sub_detail.text = setPlayer(eventDetail.awayLineupSubstitutes)
            home_keeper_detail.text = setPlayer(eventDetail.homeLineupGoalKeeper)
            away_keeper_detail.text = setPlayer(eventDetail.awayLineupGoalkeeper)
            home_defense_detail.text = setPlayer(eventDetail.homeLineupDefense)
            away_defends_detail.text = setPlayer(eventDetail.awayLineupDefense)
            home_mid_detail.text = setPlayer(eventDetail.homeLineupMidfield)
            away_mid_detail.text = setPlayer(eventDetail.awayLineupMidfield)
            home_forward_detail.text = setPlayer(eventDetail.homeLineupForward)
            away_forward_detail.text = setPlayer(eventDetail.awayLineupForward)
        } else {
            home_score_detail.text = "-"
            away_score_detail.text = "-"
            Picasso.get().load(badgeHome.teamBadge).into(home_image_detail)
            Picasso.get().load(badgeAway.teamBadge).into(away_image_detail)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.homes -> {
                finish()
                true
            }

            R.id.add_favorite -> {
                if (isFavorite) removeFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(EventDB.TABLE_EVENT)
                .whereArgs("(EVENT_ID = {id})", "id" to idEventDetail.toString())
            val favorite = result.parseList(classParser<EventDB>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        menuItem?.getItem(0)?.icon =
            if (isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_added_favorite_white)
            else ContextCompat.getDrawable(this, R.drawable.ic_add_favorite_white)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    EventDB.TABLE_EVENT,
                    EventDB.EVENT_ID to eventDetail.idEvent,
                    EventDB.EVENT_DATE to eventDetail.dateEvent,
                    EventDB.HOME_TEAM_ID to homeId,
                    EventDB.HOME_TEAM to eventDetail.nameHomeTeam,
                    EventDB.HOME_SCORE to eventDetail.homeScore,
                    EventDB.AWAY_TEAM_ID to awayId,
                    EventDB.AWAY_TEAM to eventDetail.nameAwayTeam,
                    EventDB.AWAY_SCORE to eventDetail.awayScore
                )
            }
            toast(resources.getString(R.string.toast_add))
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage
        }
    }

    private fun removeFavorite() {
        try {
            database.use {
                delete(
                    EventDB.TABLE_EVENT, "(EVENT_ID = {id})",
                    "id" to idEventDetail.toString()
                )
            }
            toast(resources.getString(R.string.toast_remove))
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage
        }
    }
}
