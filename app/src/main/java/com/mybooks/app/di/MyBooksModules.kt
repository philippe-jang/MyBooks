package com.mybooks.app.di

import com.mybooks.app.api.BookService
import com.mybooks.app.data.BookRepository
import com.mybooks.app.ui.detail.DetailViewModel
import com.mybooks.app.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        DetailViewModel()
    }
}

val repositoryModule = module {
    single { BookRepository(get()) }
}

val apiModule = module {
    single { BookService.create() }
}
