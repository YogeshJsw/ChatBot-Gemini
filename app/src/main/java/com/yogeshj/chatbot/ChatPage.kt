package com.yogeshj.chatbot

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogeshj.chatbot.ui.theme.Purple40
import com.yogeshj.chatbot.ui.theme.aiMsgColor
import com.yogeshj.chatbot.ui.theme.userMsgColor

@Composable
fun ChatPage(modifier: Modifier=Modifier,chatViewModel: ChatViewModel){
    Column(modifier=Modifier.fillMaxSize()) {

        //Header
        Box(modifier= Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
            Text(text = "Gemini AI",Modifier.padding(16.dp), color = Color.White, fontSize = 24.sp)
        }
        MessageList(modifier = Modifier.weight(1f), msgList = chatViewModel.msgList)

//        Box(modifier=Modifier.fillMaxWidth(), contentAlignment =  Alignment.Bottom) {
            MessageInput(onMsgSend = { chatViewModel.sendMsg(it) })
//        }



    }
}

@Composable
fun MessageList(modifier: Modifier=Modifier,msgList:List<MessageModel>) {
    if(msgList.isEmpty())
    {
        Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = "Icon", tint = Purple40, modifier = Modifier.size(80.dp))
            Text(text = "Start you Search!", fontSize = 32.sp, fontFamily = FontFamily(Typeface.DEFAULT_BOLD))
        }
    }
    else {
        LazyColumn(modifier = modifier, reverseLayout = true) {
            items(msgList.reversed()) {
//            Text(text = it.msg, color = Color.Black)
                if (it.role == "user") {
                    Box(modifier = Modifier
                        .padding(72.dp, 8.dp, 8.dp, 8.dp)
                        .fillMaxWidth(), contentAlignment = Alignment.CenterEnd)
                    {
                        Text(text = it.msg, modifier = Modifier
                            .clip(RoundedCornerShape(42f))
                            .background(userMsgColor)
                            .padding(8.dp),
                            fontWeight = FontWeight.W400,
                            color = Color.White,
                            textAlign = TextAlign.End
                        )
                    }

                } else {
                    Box(
                        modifier = Modifier.padding(8.dp, 8.dp, 72.dp, 8.dp),
                        contentAlignment = Alignment.CenterEnd
                    )
                    {
                        SelectionContainer {
                            Text(text = it.msg, modifier = Modifier.clip(RoundedCornerShape(42f)).background(aiMsgColor).padding(8.dp), color = Color.White, fontWeight = FontWeight.W400, textAlign = TextAlign.Center)
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MessageInput(onMsgSend:(String)->Unit){
    var msg by remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(value = msg, onValueChange = {msg=it}, modifier = Modifier.weight(1f))
        IconButton(onClick = {
            if(msg.trim().isNotEmpty())
            {
                onMsgSend(msg.trim())
                msg=""
            }
            }) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription ="Send" )
        }
    }
}