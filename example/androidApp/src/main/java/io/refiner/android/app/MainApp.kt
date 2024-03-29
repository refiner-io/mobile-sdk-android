package io.refiner.android.app

import android.app.Application
import android.util.Log
import io.refiner.Refiner

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            Refiner.initialize(
                context = this,
                projectId = PROJECT_ID,
                debugMode = true
            )
        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
        }
    }

    companion object {
        const val TAG = "Refiner"
        private const val PROJECT_ID = "56421950-5d32-11ea-9bb4-9f1f1a987a49"
    }
}
