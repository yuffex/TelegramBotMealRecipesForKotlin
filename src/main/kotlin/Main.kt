package org.example

import kotlinx.coroutines.runBlocking
import org.example.presentation.RecipeBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val bot = RecipeBot("6757771032:AAHBckMJETGA8vraM4VTNxU2jkDTyocTvQM")
    runBlocking {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
    }
}