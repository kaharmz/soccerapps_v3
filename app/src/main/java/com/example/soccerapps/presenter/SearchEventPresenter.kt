package com.example.soccerapps.presenter

import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.api.TheSportDBApi
import com.example.soccerapps.model.EventResponse
import com.example.soccerapps.view.interfaces.SearchViews
import com.example.soccerapps.view.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchEventPresenter(
    private val searchViews: SearchViews,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun searchEventList(query: String?) {
        searchViews.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(
                        TheSportDBApi.searchEvent(query)
                    ).await(), EventResponse::class.java
                )

            searchViews.hideLoading()
            val result = data.search?.filter { it.nameSport == "Soccer" }
            searchViews.showEventList(result)
        }
    }
}