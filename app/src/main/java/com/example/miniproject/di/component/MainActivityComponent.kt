package com.example.miniproject.di.component

import com.example.miniproject.activities.MainActivity
import com.example.miniproject.di.module.ViewModelModule
import com.example.miniproject.di.scope.ActivityScope
import com.example.miniproject.fragments.ArticleFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : MainActivityComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ArticleFragment)
}