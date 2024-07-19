package com.yogeshj.chatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel:ViewModel() {
    val generativeModel:GenerativeModel=GenerativeModel(modelName = "gemini-pro",Constants.apiKey)

    val msgList by lazy {
        mutableStateListOf<MessageModel>()
    }

    fun sendMsg(question:String){
        viewModelScope.launch {
            try {

                val chat = generativeModel.startChat(
                    history = msgList.map {
                        content(it.role) { text(it.msg) }
                    }.toList()
                )

                msgList.add(MessageModel(question, "user"))
                msgList.add(MessageModel("Typing...", "model"))
                val response = chat.sendMessage(question)
                msgList.removeLast()
                msgList.add(MessageModel(response.text.toString(), "model"))

                Log.i("Response by AI", response.text.toString())

            } catch (e: Exception) {
                msgList.removeLast()
                msgList.add(MessageModel("Error: " + e.message.toString(), "model"))
            }
        }
    }
}