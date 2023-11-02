package com.example.practiceingeneral.car

import android.util.Log
import com.example.practiceingeneral.car.Tires.Companion.TAG
import javax.inject.Inject

class DieselEngine @Inject constructor(var horsePower:Int): Engine {
    companion object {
        const val TAG = "Car"
    }


    override fun start() {
        Log.d(TAG, "Diesel engine started. Horsepower:  $horsePower ");
    }
}