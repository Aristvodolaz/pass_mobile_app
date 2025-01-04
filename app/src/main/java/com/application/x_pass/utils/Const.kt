package com.application.x_pass.utils

object Const {

    // Для теста
    const val TEST = true
    const val URL_PROD = "https://vacwmlvn.com/acs/"
    const val URL_TEST = "https://vacwmlvn.com/xacs/"

    val URL: String = if (TEST) URL_TEST else URL_PROD

    // Обновления
    const val UPDATE_URL = "/x_pass/new_info.json"
    const val UPDATE_INFO_AMAZON = "https://qwe/android_update/info.json"

    const val API_ENDPOINT = "https://mob-experience.space"
    const val URL_STANDARD = "https://mob-experience.space"

    // TODO: аутентификация
    const val AUTH = "api/Token/Auth"
    const val REFRESH_TOKEN = "api/Token/Refresh"

    // TODO: события
    const val GET_EVENTS = "api/Event/GetEvents"
    const val GET_EVENT_INSPECTOR = "api/Event/GetEventInspectors"

    // TODO: доступ к событиям
    const val SCAN_QR = "api/EventAccess/CheckQR"
    const val SCAN_HISTORY = "api/EventAccess/GetCheckHistory"
    const val SCAN_HISTORY_WITH_ID = "api/EventAccess/GetCheckHistory"

    const val TIKETS = "api/EventAccess/CheckTickets"
    const val TIKETS_VIP = "api/EventAccess/ActivateVipTickets"

    const val STATISTICA = "api/Report/VisitStatistic"

    const val ARCHIVE_EVENTS = "api/Event/GetEventsHistory"

    const val GET_EVENT = "api/Event/GetEvent"
    const val ACTIVE_LIST_OF_TICKETS = "api/EventAccess/ActivateListOfTickets"
}
