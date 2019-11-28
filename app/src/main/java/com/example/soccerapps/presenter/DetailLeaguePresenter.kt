package com.example.soccerapps.presenter

import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.api.TheSportDBApi
import com.example.soccerapps.model.LeaguesResponse
import com.example.soccerapps.view.interfaces.DetailLeagueView
import com.example.soccerapps.view.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter(
    private val detailView: DetailLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailLeague(leagueId: String?) {
        detailView.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(
                        TheSportDBApi.getDetailLeagueById(leagueId)
                    ).await(), LeaguesResponse::class.java
                )
            detailView.hideLoading()
            detailView.showLeagueDetail(data.leagues)
        }
    }
}