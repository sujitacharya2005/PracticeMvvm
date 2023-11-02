package com.example.practiceingeneral.car

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PetrolEngineModule {
    @Binds
    abstract fun bindEngine(engine: PetrolEngine) : Engine
}