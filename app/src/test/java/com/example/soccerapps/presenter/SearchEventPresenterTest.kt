package com.example.soccerapps.presenter

import com.example.soccerapps.TestContextProvider
import com.example.soccerapps.api.ApiRepository
import com.example.soccerapps.model.Event
import com.example.soccerapps.model.EventResponse
import com.example.soccerapps.view.interfaces.SearchViews
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class SearchEventPresenterTest {

    @Mock
    private lateinit var view: SearchViews

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>


    private lateinit var presenter: SearchEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchEventPresenter(
            view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun searchEventList() {
        val event: MutableList<Event> = mutableListOf()
        val search: MutableList<Event> = mutableListOf()
        val query = "Everton"
        val response = EventResponse(event, search)

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", EventResponse::class.java)).thenReturn(response)

            presenter.searchEventList(query)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(search)
            Mockito.verify(view).hideLoading()
        }
    }
}