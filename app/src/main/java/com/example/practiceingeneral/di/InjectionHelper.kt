package com.example.practiceingeneral.di

import android.app.Application
import com.example.practiceingeneral.MainActivity
import org.w3c.dom.DocumentFragment

object InjectionHelper {
    private var appComponent:AppComponent ?= null

    fun getAppComponent(application:Application) : AppComponent {
        return appComponent ?: run {
            appComponent = DaggerAppComponent.factory()
                .create(application, 10, 10, 20)
            appComponent!!
        }
    }

}