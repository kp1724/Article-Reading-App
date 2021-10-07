package com.example.miniproject

import android.app.Application
import com.example.miniproject.di.component.AppComponent
import com.example.miniproject.di.component.DaggerAppComponent

open class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}