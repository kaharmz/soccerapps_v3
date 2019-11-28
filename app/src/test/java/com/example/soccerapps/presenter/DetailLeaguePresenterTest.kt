package com.example.soccerapps.presenter

import com.example.soccerapps.TestContextProvider
import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.model.Leagues
import com.example.soccerapps.model.LeaguesResponse
import com.example.soccerapps.view.interfaces.DetailLeagueView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLeaguePresenterTest {

    @Mock
    private lateinit var view: DetailLeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: DetailLeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(
            view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun getDetailLeague() {
        val league: MutableList<Leagues> = mutableListOf()
        val idLeague = "4328"
        val response = LeaguesResponse(league)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", LeaguesResponse::class.java)).thenReturn(response)

            presenter.getDetailLeague(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueDetail(league)
            Mockito.verify(view).hideLoading()
        }
    }
}