package com.example.miniproject.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniproject.di.qualifier.ViewModelKey
import com.example.miniproject.utils.ViewModelFactory
import com.example.miniproject.viewModel.ArticleViewModel
import com.example.miniproject.viewModel.SourceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SourceViewModel::class)
    abstract fun bindsSourceViewModel(viewModel: SourceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    abstract fun bindsArticleViewModel(viewModel: ArticleViewModel): ViewModel

}