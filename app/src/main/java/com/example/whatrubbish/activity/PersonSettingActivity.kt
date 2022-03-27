package com.example.whatrubbish.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
//import com.example.compose.Content
import com.example.compose.MainView
import com.example.compose.ui.theme.ComposeTheme
import com.example.httputil.HttpUtil
import com.example.whatrubbish.Bus
import com.example.whatrubbish.util.ThreadPoolFactory
import com.example.whatrubbish.util.ThreadPoolManager
import com.google.gson.JsonObject
import java.util.concurrent.ThreadPoolExecutor


//Compose fragment
class PersonSettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@Preview()
@Composable
fun Content() {
    var username = remember {
        mutableStateOf(Bus.curUser.username)
    }
    var nickname = remember {
        mutableStateOf(Bus.curUser.nickname)
    }
    var address = remember {
        mutableStateOf(Bus.curUser.address)
    }


//    compose 居中
    ComposeTheme {
//        https://blog.csdn.net/unreliable_narrator/article/details/122544446
        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            // A surface container using the 'background' color from the theme
//            Surface(color = MaterialTheme.colors.background) {
//                Greeting("Android")
//            }
//            login()
//            HelloCompose()
//            MainView()
            TextField(
                value = username.value,
                onValueChange = { username.value = it }
            )
            TextField(
                value = nickname.value,
                onValueChange = { nickname.value = it }
            )
            TextField(
                value = address.value,
                onValueChange = { address.value = it }
            )
            Button(onClick = { /*TODO*/
//                Bus.curUser.username=username.value
                Bus.curUser.nickname=nickname.value
                Bus.curUser.address=address.value
//                ThreadPoolManager.getInstance().execute(){
//                    com.example.whatrubbish.util.HttpUtil.post(Bus.baseDbUrl+"/user/update", Bus.curUser)
//                }
                ThreadPoolFactory.getExecutorService().execute{
                   val jsonObject:JsonObject=  com.example.whatrubbish.util.HttpUtil.post(Bus.baseDbUrl+"/user/update", Bus.curUser)
                    if (jsonObject["code"].asInt==Bus.codeSuccess) {
                       val data= jsonObject["data"].asJsonObject
                        Log.i("data", "Content: $data")
                    }
                }

            }) {
                Text(text = "确认修改")
            }
        }


    }
}
