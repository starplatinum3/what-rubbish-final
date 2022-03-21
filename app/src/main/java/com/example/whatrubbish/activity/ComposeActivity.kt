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
import com.example.whatrubbish.entity.ImUser
import com.example.whatrubbish.service.UserServiceKt
import com.example.whatrubbish.util.HttpUtil
import com.example.whatrubbish.util.JsonUtil
import com.example.whatrubbish.util.ThreadPoolFactory
import okhttp3.*
//import com.facebook.stetho.json.ObjectMapper
//import com.mashape.unirest.http.ObjectMapper
//import com.example.httputil.HttpUtil
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
            MainView()
        }


    }
}

val first_screen="first_screen"
val login_screen="login_screen"
@Composable
fun MainView(){
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
//https://juejin.cn/post/6983968223209193480

@Composable
fun FirstScreen(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("second_screen")
        }) {
            Text(text = "I am First 点击我去Second")
        }
    }
}

//https://stackoverflow.com/questions/64675386/how-to-get-activity-in-compose
//这个不行
fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

@Composable
fun LoginScreen(navController: NavController){

    var username = remember {
        mutableStateOf("18358502222")
    }
    var password = remember {
        mutableStateOf("2222")
    }

    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize(),
//        .background(Color.Blue),
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

            val okHttpClient = OkHttpClient()
            //RequestBody:FormBody，表单键值对
            //okhttp3.RequestBody formBody = new FormBody.Builder()
            //        .add("username", "hfy")
            //        .add("password", "qaz")
            //        .build();

            //RequestBody:FormBody，表单键值对
            //okhttp3.RequestBody formBody = new FormBody.Builder()
            //        .add("username", "hfy")
            //        .add("password", "qaz")
            //        .build();

//            kotlin compose 获取 activity
//            getAc



           val usernameStr=username.value
           val passwordStr=password.value
            val formBody = FormBody.Builder()
                .add("client_id","v-client")
                .add("client_secret","v-client-ppp")
                .add("grant_type","password")
                .add("scope","select")
                .add("username",usernameStr)
                .add("password",passwordStr).build()
            val getRequest: Request =
                Request.Builder() //.url("https://api.github.com/markdown/raw")
                    .url(Bus.baseDbUrl+"/oauth/token") //.addHeader()
                    .post(formBody)
                    .build()


            val newCall = okHttpClient.newCall(getRequest)
//            Callback.
//            kotlin okhttp3 callback
//            newCall.enqueue(object :Callback{
//                override fun onFailure(call: Call, e: IOException) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    TODO("Not yet implemented")
//                }
//
//            })

//            https://www.jianshu.com/p/9fe3ba7cdf88
            newCall.enqueue( object : Callback{

                override fun onResponse(call: Call?, response: Response?) {
                    var responseData = response?.body()?.let {
                        it.string()
                    }

                    if (responseData != null) {
                        val token = JsonUtil.strToJsonObject(responseData)
                        Bus.token = token
//                        navigation.
//                        navController.navigate("second_screen")
                        Log.i(" Bus.token", "onResponse: ${ Bus.token}")
//                        val activity = context.getActivity()
                        if(activity==null){
                            Log.i("activity", "onResponse: null")
                            return
                        }

                        userInit(activity ,navController)

//                        context.getActivity()?.runOnUiThread {
//                            activity.runOnUiThread {
////                            没走
//                            Log.i("navigate", "onResponse: ChatScreen ")
//                            navController.navigate("ChatScreen")
//                        }

                    }


//                    showResponse(responseData)
//                    parseJsonWithGson(responseData!!)
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    e?.printStackTrace()
                }
            } )

        }) {
//            Text(text = "I am First login 点击我去 聊天")
            Text(text = "登录获取 ws token")
        }
    }
}

fun  userInit(activity: Activity,navController: NavController){
    UserServiceKt.userInit().enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call, response: Response) {
//            TODO("Not yet implemented")
            val toString = response.body()?.string()
//            val toString = response.body().toString()
            Log.i("user init data", "onResponse: $toString")
            val  json= toString?.let { JsonUtil.strToJsonObject(it) }
//            json.
//            Bus.curUser

           val me= json?.get("me")
            if (me != null) {
              val meObj=  me.asJsonObject
//                Bus. curImUserObj=me.asJsonObject
//                Bus. curImUserObj[]

//                val fromJson = JsonUtil.fromJson<ImUser>(meObj)
                val fromJson = JsonUtil.fromJson<ImUser>( meObj.toString())
                Bus. curImUser=fromJson
                Log.i(" Bus. curImUser", "onResponse: "+ Bus. curImUser)
            }
//            https://www.jianshu.com/p/3c85ad975a82
//            JsonObjec

//            val  gson= Gson()
//            gson.to(me.asJsonObject)
//            Gson kotlin 转化为 实体
           val friends= json?.get("friends")
            if (friends != null) {
//                Bus.friends=friends.asJsonObject
                Bus.friends=friends.asJsonArray
                Log.i(" Bus. friends", "onResponse: "+ Bus. friends)
            }

           val groups= json?.get("groups")
            if (groups != null) {
                Bus.groups=groups.asJsonArray
                Log.i(" Bus. groups", "onResponse: "+ Bus. groups)
            }
            Log.i("me", "onResponse: $me")
            Log.i("friends", "onResponse: $friends")
            Log.i("groups", "onResponse: $groups")


            activity.runOnUiThread {
//                            没走
                Log.i("navigate", "onResponse: ChatScreen ")
                navController.navigate("ChatScreen")
            }
        }

    })
}
//val login_screen="login_screen"

@Composable
fun ChatScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = "1", onValueChange ={} )
        Button(onClick = {
            navController.navigate("third_screen")
        }) {
            Text(text = "I am Second 点击我去Third")
        }
        Button(onClick = {
//            "/api/user/init",
//            Request.Builder().url(Bus.baseDbUrl+"/api/user/chatUserList")
//                .
//            navController.navigate("third_screen")
        }) {
            Text(text = "发送")
        }
        Button(onClick = {
            navController.navigate(login_screen)
        }) {
            Text(text = "返回login")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            navController.navigate("third_screen")
        }) {
            Text(text = "I am Second 点击我去Third")
        }
    }
}
@Composable
fun ThirdScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            navController.navigate("first_screen")
        }) {
            Text(text = "I am Third 点击我去first")
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
//       val access_token= strToJsonObject?.get("access_token")?.asString
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
//        HttpUtil.post3("",access_token)
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

@Composable
fun HelloCompose() {
    // 创建NavController
    val navController = rememberNavController()
    // 用NavHost将NavController和导航图相关联，startDestination指定起始的可组合项
    NavHost(navController = navController, startDestination = "first_page") {
        // 给FirstPage可组合项指定路径
        composable("first_page") { FirstPage(navController) }
        // 给SecondPage可组合项指定路径
        composable("second_page") { SecondPage(navController) }
        // 给ThirdPage可组合项指定路径
        //composable("third_page") { ThirdPage(navController) }
    }
}

/**
 *FirstPage可组合项
 */
@Composable
fun FirstPage(navController: NavController) {
    Column {
        Text(text = "FirstPage页面")
        Button(onClick = {
            // 导航到SecondPage可组合项
            navController.navigate("second_page")
        }) {
            Text(text = "去SecondPage")
        }
    }
}

/**
 * SecondPage可组合项
 */
@Composable
fun SecondPage(navController: NavController) {
    Text(text = "SecondPage页面")
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
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),
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