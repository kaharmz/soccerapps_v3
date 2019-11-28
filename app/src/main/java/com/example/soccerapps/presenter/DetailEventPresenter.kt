package com.example.soccerapps.presenter

import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.api.TheSportDBApi
import com.example.soccerapps.model.EventResponse
import com.example.soccerapps.model.TeamResponse
import com.example.soccerapps.view.interfaces.DetailView
import com.example.soccerapps.view.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailEventPresenter(
    private val view: DetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getEventDetail(idEvent: String?, idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val eventDetail =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDBApi.getDetailEvent(idEvent)).await(),
                    EventResponse::class.java
                )

            val badgeHome =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDBApi.getHomeBadge(idHomeTeam)).await(),
                    TeamResponse::class.java
                )

            val badgeAway =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDBApi.getAwayBadge(idAwayTeam)).await(),
                    TeamResponse::class.java
                )

            view.hideLoading()
            view.showEventList(eventDetail.events, badgeHome.team, badgeAway.team)
        }
    }
}