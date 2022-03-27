package com.example.whatrubbish.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.whatrubbish.repository.Repository
import com.example.whatrubbish.service.ProjectPagingSource

class MainViewModel: ViewModel() {
    val projects = Pager(PagingConfig(pageSize = 20)){
        ProjectPagingSource(Repository)
    }.flow.cachedIn(viewModelScope)
}
