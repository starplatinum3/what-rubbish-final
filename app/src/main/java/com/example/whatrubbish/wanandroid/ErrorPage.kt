package com.example.whatrubbish.wanandroid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.example.whatrubbish.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.whatrubbish.viewModel.MainViewModel
@Composable
fun ErrorPage(onclick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(300.dp, 180.dp),
//            painter = painterResource(id = R.drawable.no_signal),
            painter = painterResource(id = R.drawable.no_chat),
            contentDescription = "网络问题",
            contentScale = ContentScale.Crop
        )
        Button(modifier = Modifier.padding(8.dp), onClick = onclick) {
            Text(text = "网络不佳，请点击重试")
        }
    }
}
