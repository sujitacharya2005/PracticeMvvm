package com.example.practiceingeneral.car

import android.util.Log
import com.example.practiceingeneral.car.Tires.Companion.TAG
import javax.inject.Inject
import javax.inject.Named

class PetrolEngine @Inject constructor(
    @Named("horse power") private val horsePower: Int,
    @Named("engine capacity") private val engineCapacity: Int): Engine {
    companion object {
        const val TAG = "Car"
    }
    override fun start() {
        Log.d(TAG, "Petrol engine started. " +
                "\nHorsepower: " + horsePower +
                "\nEngine capacity: " + engineCapacity);
    }
}