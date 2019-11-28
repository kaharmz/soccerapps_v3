package com.example.soccerapps.presenter

import com.example.soccerapps.TestContextProvider
import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.model.Event
import com.example.soccerapps.model.EventResponse
import com.example.soccerapps.view.interfaces.LastView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LastEventPresenterTest {

    @Mock
    private lateinit var view: LastView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>


    private lateinit var presenter: LastEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastEventPresenter(
            view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun getEventList() {
        val event: MutableList<Event> = mutableListOf()
        val idLeague = "4328"
        val response = EventResponse(event, event)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", EventResponse::class.java)).thenReturn(response)

            presenter.getEventList(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(event)
            Mockito.verify(view).hideLoading()
        }
    }
}