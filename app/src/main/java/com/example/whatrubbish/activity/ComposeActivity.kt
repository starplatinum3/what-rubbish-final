package com.example.compose

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejlchina.okhttps.OkHttps.newBuilder
import com.ejlchina.okhttps.internal.HttpClient
import com.example.compose.ui.theme.ComposeTheme
import com.example.whatrubbish.Bus
import com.example.whatrubbish.dto.Problem
import com.example.whatrubbish.util.HttpUtil
import com.example.whatrubbish.util.JsonUtil
import com.example.whatrubbish.util.ThreadPoolFactory
import com.example.whatrubbish.util.ThreadPoolManager
//import com.facebook.stetho.json.ObjectMapper
import com.google.gson.JsonObject
import java.nio.file.attribute.AclEntry.newBuilder
import java.util.concurrent.FutureTask
import kotlin.math.log
//import com.mashape.unirest.http.ObjectMapper
//import com.example.httputil.HttpUtil
import kotlin.reflect.KProperty
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import okhttp3.FormBody
import okhttp3.Request
import java.io.IOException


//Compose fragment
class ComposeActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContent {

            Content()
//            ComposeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//                login()
////                compose
////                EditText()
//
//
//
//            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview()
//@Composable
//fun Login(name: String) {
//
//    var username by remember { mutableStateOf("username") }
//    var password by remember { mutableStateOf("Text") }
//
//
//    TextField(
//        value = username.value,
//        onValueChange = {
//            text = it
//        },
//        label = { Text("Label") }
//    )
//}


//@Preview()
@Composable
fun textFeildTest(){
    var phone = remember {
        mutableStateOf("")
    }
    Column() {
        TextField(
            value = phone.value,
            onValueChange = { phone.value = it }
        )
    }
}

@Preview()
@Composable
fun Content() {
//    compose 居中
    ComposeTheme {
        Column(  modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                Greeting("Android")
            }
            login()
        }


    }
}


//@Throws(IOException::class)
//fun accessToken(): Oauth? {
//    val client: OkHttpClient = Builder().authenticator(
//        object : Authenticator() {
//            fun authenticate(route: Route?, response: Response): Request? {
//                val credential: String = Credentials.basic("api", "secret")
//                return response.request().newBuilder().header("Authorization", credential).build()
//            }
//        }
//    ).build()
//    val url = "https://api.netkiller.cn/oauth/token"
//    val formBody: RequestBody = Builder()
//        .add("grant_type", "password")
//        .add("username", "blockchain")
//        .add("password", "123456")
//        .build()
//    val request: Request = Builder()
//        .url(url)
//        .post(formBody)
//        .build()
//    val response: Response = client.newCall(request).execute()
//    if (!response.isSuccessful()) {
//        throw IOException("服务器端错误: $response")
//    }
//    val gson = Gson()
//    val oauth: Oauth = gson.fromJson(response.body().string(), Oauth::class.java)
//    Log.i("oauth", oauth.toString())
//    return oauth
//}

fun  doLogin(username:String,password:String){

//            HttpUtil.
//            HttpUtil.post()
//            Problem();
//            kotlin 直接 大括号作为 json
//            kotlin 字符串 模板
//            FormData

//            mapOf("client_id" to "v-client", "client_secret" to "v-client-ppp",
//            "grant_type":)

//    var form= mapOf(
//        "client_id" to "v-client",
//        "client_secret" to "v-client-ppp",
//        "grant_type" to "password",
//        "scope" to "select",
//        "username" to username.value,
//        "password" to password.value,
//
//        )



//            kotlin lambda
//            ThreadPoolManager.getInstance().execute()
//            ThreadPoolFactory.getExecutorService().execute { Runnable {
//                var post:JsonObject = HttpUtil.post(Bus.baseDbUrl + "/oauth/token",  form);
//                Log.i("post", "login: post  "+post)
//            }
//
//            }

    ThreadPoolFactory.getExecutorService().execute {
//                var post:JsonObject = HttpUtil.post(Bus.baseDbUrl + "/oauth/token",  form);
//                FormBody.create()
//                new FormBody.Builder()
//                        .add("username", "hfy")
//                        .add("password", "qaz")
//                        .build();

//                可以拿到

        val formBody = FormBody.Builder()
            .add("client_id","v-client")
            .add("client_secret","v-client-ppp")
            .add("grant_type","password")
            .add("scope","select")
            .add("username",username)
            .add("password",password)
//            .add("username",username.value)
//            .add("password",password.value)
            .build()
        val post3 = HttpUtil.post3(Bus.baseDbUrl + "/oauth/token", formBody)
        Log.i("post", "login: post  "+post3)
//        post3.
//        JsonUtil
//        Jso
//        I/post: login: post  {"access_token":"93b4d6f2-0216-4b44-9377-a449ccf8081b","token_type":"bearer","refresh_token":"d58b02ad-2da8-45d9-81ac-744a8e408c8b","expires_in":2000,"scope":"select"}

        val strToJsonObject = JsonUtil.strToJsonObject(post3)
       val access_token= strToJsonObject?.get("access_token")?.asString
        Bus.token=strToJsonObject
//        Request.Builder().url()
//        okhttp3.Request req =
//                okhttp3.Request.Builder()
//                    //.url("https://api.github.com/markdown/raw")
//                    .url(url)
//                    //.addHeader()
//                    .post(formBody)
//                    .build();
//        Authorization: "bearer " + access_token
        HttpUtil.post3("",access_token)
    }
//            ThreadPoolManager.getInstance().execute(   FutureTask(){
//                var post:JsonObject = HttpUtil.post(Bus.baseDbUrl + "/oauth/token",  form);
//                Log.i("post", "login: post  "+post)
//            })

//            var code:Int =   post.get("code").asInt
//            int code = post.get("code").getAsInt();
//            HttpUtil.po

//            val values = mapOf("name" to "John Doe", "occupation" to "gardener")

//            val objectMapper = ObjectMapper()
//            val requestBody: String = objectMapper
//                .writeValueAsString(values)
//
//            val client = HttpClient.newBuilder().build();
//            val request = HttpRequest.newBuilder()
//                .uri(URI.create("https://httpbin.org/post"))
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                .build()
//            val response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            println(response.body())
//            username.value
}
//@Preview()
@Composable
fun login(){
    var username = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username.value,
            onValueChange = { username.value = it }
        )
//        compose preview 更新很慢
//        https://www.coder.work/article/7327856
//        Spacer(modifier = Modifier.width(4.dp))
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = password.value,
            onValueChange = { password.value = it }
        )
//        Spacer(modifier = Modifier.width(4.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            doLogin(username.value,password.value)
        }) {

            Column() {
                Text(text = "登录获取 ws token")
            }

        }
    }
}


//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Greeting("Android")
    }
}