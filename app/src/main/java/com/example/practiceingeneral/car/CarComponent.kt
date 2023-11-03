package com.example.practiceingeneral.car

import com.example.practiceingeneral.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named


@Component(modules = [WheelsModule::class, PetrolEngineModule::class])
interface CarComponent {
//    fun getCar() : Car

   // fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun horsePower(@Named("horse power") horsePower: Int): Builder


        @BindsInstance
        fun engineCapacity(@Named("engine capacity") engineCapacity: Int): Builder
        fun build() : CarComponent
    }
    fun inject(mainActivity: MainActivity)

}