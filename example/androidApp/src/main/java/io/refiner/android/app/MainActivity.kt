package io.refiner.android.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.refiner.Refiner
import io.refiner.android.app.MainApp.Companion.TAG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            Refiner.identifyUser(
                userId = USER_ID,
                userTraits = linkedMapOf(
                    Pair("something", 123),
                    Pair("else", "123"),
                ),
                locale = LOCALE,
                signature = null
            )
        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
        }

        Refiner.attachToResponse(
            contextualData = hashMapOf(
                Pair("contextual", 456),
                Pair("data", "456"),
            )
        )

        Refiner.showForm(formUuid = FORM_ID, force = true)

//        Refiner.resetUser()
//        Refiner.trackEvent(eventName = "event")
//        Refiner.trackScreen(screenName = "main_activity")
    }

    companion object {
        private const val LOCALE = "en"
        private const val USER_ID = "my-user-id"
        private const val FORM_ID = "616fc500-5d32-11ea-8fd5-f140dbcb9780"
    }
}
