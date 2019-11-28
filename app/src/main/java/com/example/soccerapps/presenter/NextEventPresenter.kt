package com.example.soccerapps.presenter

import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.api.TheSportDBApi
import com.example.soccerapps.model.EventResponse
import com.example.soccerapps.view.interfaces.NextView
import com.example.soccerapps.view.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextEventPresenter(
    private val lastView: NextView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getEventList(leagueId: String?) {
        lastView.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(
                        TheSportDBApi.getNextEvent(leagueId)
                    ).await(), EventResponse::class.java
                )

            lastView.hideLoading()
            lastView.showEventList(data.events)
        }
    }
}
