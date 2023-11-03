package com.example.practiceingeneral.di

import android.app.Application
import com.example.practiceingeneral.MainActivity
import com.example.practiceingeneral.car.CarComponent
import com.example.practiceingeneral.car.PetrolEngineModule
import com.example.practiceingeneral.car.WheelsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@Component(modules = [ViewModelModule::class, WheelsModule::class, PetrolEngineModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance counter: Int,



            @BindsInstance @Named("horse power") horsePower: Int,

        @BindsInstance @Named("engine capacity") engineCapacity: Int
        ) : AppComponent

    }

    fun inject(mainActivity: MainActivity)

}