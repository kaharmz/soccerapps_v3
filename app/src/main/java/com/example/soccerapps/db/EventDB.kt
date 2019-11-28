package com.example.soccerapps.db

class EventDB(
    val id: Long?,
    val eventId: String?,
    val eventDate: String?,
    val homeTeamId: String?,
    val homeTeam: String?,
    val homeScore: String?,
    val awayTeamId: String?,
    val awayTeam: String?,
    val awayScore: String?
) {

    companion object {
        const val TABLE_EVENT: String = "TABLE_EVENT"
        const val ID: String = "ID"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM_ID = "AWAY_TEAM_ID"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}