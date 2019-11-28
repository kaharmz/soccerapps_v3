package com.example.soccerapps.api

import com.example.soccerapps.BuildConfig

object TheSportDBApi {

    //Last Event
    fun getLastEvent(leagueId: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.LAST_EVENT + leagueId
    }

    //Next Event
    fun getNextEvent(leagueId: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.NEXT_EVENT + leagueId
    }

    //Detail Event
    fun getDetailEvent(eventId: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.DETAIL_EVENT + eventId
    }

    //Home Badge
    fun getHomeBadge(leagueId: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.HOME_BADGE + leagueId
    }

    //AwayBadge
    fun getAwayBadge(leagueId: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.AWAY_BADGE + leagueId
    }

    //Search Event
    fun searchEvent(query: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.SEARCH_EVENT + query
    }

    //Get Detail League
    fun getDetailLeagueById(leagueId: String?): String {
        return BuildConfig.BASE_URL + BuildConfig.PATH + BuildConfig.TSDB_API_KEY + BuildConfig.DETAIL_LEAGUE + leagueId
    }
}