package com.example.practiceingeneral.car

import android.util.Log
import javax.inject.Inject

class Car @Inject constructor(private val engine: Engine,
                              private val wheels : Wheels) {
    companion object {
        const val TAG = "Car"
    }

    fun drive() {
        engine.start();
        Log.d(TAG, "driving ...")
    }

}