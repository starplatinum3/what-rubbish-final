package com.example.compose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ui.theme.ComposeTheme
import com.example.whatrubbish.Bus
import com.example.whatrubbish.constant.MessageInfoType
import com.example.whatrubbish.entity.ImUser
import com.example.whatrubbish.im.SendInfo
import com.example.whatrubbish.im.SendMsg
import com.example.whatrubbish.service.UserServiceKt
import com.example.whatrubbish.util.HttpUtil
import com.example.whatrubbish.util.JsonUtil
import com.example.whatrubbish.util.ThreadPoolFactory
import com.sdust.im.util.MyWebSocketClient
import okhttp3.*
import org.java_websocket.client.WebSocketClient
//import com.facebook.stetho.json.ObjectMapper
//import com.mashape.unirest.http.ObjectMapper
//import com.example.httputil.HttpUtil
import java.io.IOException
import java.lang.StringBuilder
import java.util.*


//Compose fragment
class SearchFriendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentSearchFriend()
        }
    }
}



@Preview()
@Composable
fun ContentSearchFriend() {
//    compose 居中
//    HttpUtil.post3()
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
//            MainViewSearchFriend()
//            compose 列表
//            List
//            messages.forEach { message ->
//                MessageRow(message)
//            }
        }


    }
}

@Composable
fun MainViewSearchFriend(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = login_screen){
//        composable("first_screen"){
//            FirstScreen(navController = navController)
//        }

        composable(login_screen){
            LoginScreen(navController = navController)
        }
        composable("ChatScreen"){
            ChatScreen(navController = navController)
        }


        composable("second_screen"){
            SecondScreen(navController = navController)
        }
        composable("third_screen"){
            ThirdScreen(navController = navController)
        }
    }
}

