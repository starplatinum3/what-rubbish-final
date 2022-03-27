package com.example.whatrubbish.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.compose.ui.theme.ComposeTheme
import com.example.whatrubbish.viewModel.MainViewModel

class WanandroidActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: MainViewModel = viewModel()
//            MyApplicationTheme {
                ComposeTheme {
                ProjectList(vm)
            }
        }
    }
}

//@Composable
//fun ProjectList(vm: MainViewModel) {
//    val projects = vm.projects.collectAsLazyPagingItems()
//    LazyColumn {
//        itemsIndexed(projects) { _, project ->
//        	ProjectItem(project = project!!)
//        }
//    }
//}
