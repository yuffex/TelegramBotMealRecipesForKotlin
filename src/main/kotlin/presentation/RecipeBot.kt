package org.example.presentation

import kotlinx.coroutines.runBlocking
import org.example.data.api.MealApi
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result.*
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class RecipeBot(private val token: String) : TelegramLongPollingBot() {

    override fun getBotToken(): String = token

    override fun getBotUsername(): String = "RecipeBot"
    private var lastResponse: String = "" // Переменная для хранения последнего ответа бота

    override fun onUpdateReceived(update: Update?) {
        if (update != null && update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId
            val response = processMessage(messageText)
            lastResponse = response // Сохраняем текущий ответ в lastResponse
            sendTextMessage(chatId.toString(), response)
        }
    }


    private fun sendTextMessage(chatId: String, message: String) {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId
        sendMessage.text = message
        try {
            execute(sendMessage)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }


    private fun processMessage(message: String): String {
        return when {
            message.startsWith("/random") -> getRandomMeal()
            message.startsWith("/translate") -> translateLastResponse() // Вызываем функцию перевода последнего ответа
            else -> searchMeal(message)
        }
    }
    private fun translateLastResponse(): String {
        return if (lastResponse.isNotEmpty()) {
            val translatedText = translateText(lastResponse, "en") // Переводим последний ответ на английский
            "Переведенный текст:\n$translatedText"
        } else {
            "Предыдущий ответ для перевода отсутствует."
        }
    }


    fun translateText(text: String, targetLanguage: String): String {
        val translate = TranslateOptions.getDefaultInstance().service
        val translation = translate.translate(
            text,
            Translate.TranslateOption.targetLanguage(targetLanguage)
        )
        return translation.translatedText
    }

    private fun searchMeal(name: String): String {
        return try {
            val meal = runBlocking { MealApi.service.getMealByName(name) }
            if (meal.meals.isNotEmpty()) {
                val firstMeal = meal.meals[0]
                "Название: ${firstMeal.strMeal}\nКатегория: ${firstMeal.strCategory}\nИнструкции: ${firstMeal.strInstructions}"
            } else {
                "Извините, не удалось найти информацию о блюде '$name'."
            }
        } catch (e: Exception) {
            "Извините, не удалось получить информацию о блюде '$name'."
        }
    }
    private fun getRandomMeal(): String {
        return try {
            val meal = runBlocking { MealApi.service.getRandomMeal() }
            if (meal.meals.isNotEmpty()) {
                val randomMeal = meal.meals[0]
                "Случайное блюдо:\nНазвание: ${randomMeal.strMeal}\nКатегория: ${randomMeal.strCategory}\nИнструкции: ${randomMeal.strInstructions}"
            } else {
                "Извините, не удалось получить информацию о случайном блюде."
            }
        } catch (e: Exception) {
            "Извините, не удалось получить информацию о случайном блюде."
        }
    }
}
