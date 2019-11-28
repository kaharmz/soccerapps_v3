package com.example.soccerapps.presenter

import com.example.soccerapps.TestContextProvider
import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.model.Event
import com.example.soccerapps.model.EventResponse
import com.example.soccerapps.model.Team
import com.example.soccerapps.model.TeamResponse
import com.example.soccerapps.view.interfaces.DetailView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DetailEventPresenterTest {

    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailEventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val home: MutableList<Team> = mutableListOf()
        val away: MutableList<Team> = mutableListOf()
        val response = EventResponse(events, events)
        val homeResponse = TeamResponse(home)
        val awayResponse = TeamResponse(away)
        val idEvent = "526006"
        val idHome = "124708"
        val idAway = "123610"

        runBlocking {
            `when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(
                apiResponse
            )
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", EventResponse::class.java)).thenReturn(response)

            `when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(
                apiResponse
            )
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(homeResponse)

            `when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(
                apiResponse
            )
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(awayResponse)

            presenter.getEventDetail(idEvent, idHome, idAway)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events, home, away)
            Mockito.verify(view).hideLoading()
        }
    }
}