package com.example.whatrubbish.wanandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.whatrubbish.viewModel.MainViewModel

@Composable
fun ProjectList(vm: MainViewModel) {
    val projects = vm.projects.collectAsLazyPagingItems()
    when (projects.loadState.refresh) {
        is LoadState.NotLoading -> LazyColumn {
            itemsIndexed(projects) { _, project ->
                ProjectItem(project = project!!)
            }
            when (projects.loadState.append) {
                is LoadState.NotLoading -> itemsIndexed(projects) { _, project ->
                    ProjectItem(project = project!!)
                }
                is LoadState.Error -> item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ){
                        Button(onClick = {
                            projects.retry()
                        }) {
                            Text(text = "重试")
                        }
                    }

                }
                LoadState.Loading -> item {
                    LoadingPage()
                }

            }
        }
        is LoadState.Error -> ErrorPage { projects.refresh() }
        LoadState.Loading -> LoadingPage()
    }
}
