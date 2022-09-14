# Refiner Mobile SDK integration

## Android

Refiner Android SDK supports Android version 5.0+ (API level 21+)

### 1) Installation

Refiner Android SDK is published to Maven. Add dependency below in your `app/build.gradle` file.

[![Maven Central](https://img.shields.io/maven-central/v/io.refiner/refiner.svg?label=maven%20central)](#)

```gradle
dependencies {
    implementation 'io.refiner:refiner:latest.release'
}
```

You may also need to add the following to your `project/build.gradle` file.

```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

### 2) Usage

Visit our [documentation](https://refiner.io/docs/kb/mobile-sdk/mobile-sdk-reference/) for more information about how to use the SDK methods.

#### Initialization & Configuration

Initialize Refiner Android SDK in your application class with the needed configuration parameters.

```kotlin
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            Refiner.initialize(
                context = this,
                refinerConfigs = RefinerConfigs(
                    projectId = "PROJECT_ID",
                    enableDebugMode = false
                )
            )
        } catch (e: Exception) {
            Log.e("Refiner", e.printStackTrace().toString())
        }
    }
}
```

#### Identify User

Call `Identify User` to create or update user traits in Refiner.

The first parameter is the userId of your logged-in user and is the only mandatory parameter.

The second parameter is an object of user traits. You can omit or set this value to `null` if you don't want to send any user traits to your Refiner account.

User traits map accepts values with the following types: String, Int or Boolean.

```kotlin
try {
    Refiner.identifyUser(
        userId = "USER_ID",
        userTraits = linkedMapOf(
            Pair("email", "hello@hello.com"),
            Pair("a_number", 123),
            Pair("a_date", "2022-16-04 12:00:00")
        )
    )
} catch (e: Exception) {
    Log.e("Refiner", e.printStackTrace().toString())
}
```

The third parameter is for setting the `locale` of a user and is optional. The expected format is a two letter [ISO 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) language code. When provided, the locale code is used for launching surveys for specific languages, as well as launching translated surveys. You can omit or set the value to `null` if you are not using any language specific features.

The fourth parameter is an optional [Identity Verification](https://refiner.io/docs/kb/mobile-sdk/identify-verification-for-mobile-sdks/) signature. We recommend to use a Identify Verification signature for increased security in a production environment. For development purposes, you can omit or set this value to `null`.

```kotlin
try {
    Refiner.identifyUser(
        userId = "USER_ID",
        userTraits = linkedMapOf(
            Pair("email", "hello@hello.com"),
            Pair("a_number", 123),
            Pair("a_date", "2022-16-04 12:00:00")
        ),
        locale = "en",
        signature = "SIGNATURE"
    )
} catch (e: Exception) {
    Log.e("Refiner", e.printStackTrace().toString())
}
```

#### Track Event

`Track Event` lets you track user events. Tracked events can be used to create user segments and target audiences in Refiner.

```kotlin
Refiner.trackEvent(eventName = "EVENT_NAME")
```

#### Track Screen

`Track Screen` provides to track screen that user is currently on. Screen information can be used to launch surveys in specific areas of your app.

```kotlin
Refiner.trackScreen(screenName = "SCREEN_NAME")
```

#### Show Form

If you use the Manual Trigger Event for your survey, you need to call `Show Form` whenever you want to launch the survey.

```kotlin
Refiner.showForm(formUuid = "FORM_UUID")
```

For testing purposes, you can also provide an additional `force` parameter which will bypass all targeting rules and always display the survey.

```kotlin
Refiner.showForm(formUuid = "FORM_UUID", force = true)
```

#### Attach Contextual Data

Attach contextual data to the survey submissions with `attachToResponse`. Set `null` to remove the contextual data. 

```kotlin
Refiner.attachToResponse(
    contextualData = hashMapOf(
        Pair("some_data", "hello"),
        Pair("some_more_data", "hello again"),
    )
)
```

#### Reset User

Call `Reset User` to reset the user identifier previously set through `Identify User`. We recommend calling this method when the user logs out from your app.

```kotlin
Refiner.resetUser()
```

#### Register callback functions

Registering callback functions allows you to execute any code at specific moments in the lifecycle of a survey.
A popular use-case for callback functions is to redirect a user to a new screen once they completed a survey.

`onBeforeShow` gets called right before a survey is supposed to be shown.

```kotlin
Refiner.onBeforeShow { formId, formConfig ->
    Log.i(TAG, "onBeforeShow \nformId: $formId \nformConfig: $formConfig")
    if (formId == "ABC") {
        Log.i(TAG, "Abort mission")
    } else {
        Log.i(TAG, "Continue and show survey")
    }
}
```

`onNavigation` gets called when the user moves through the survey

```kotlin
Refiner.onNavigation { formId, formElement, progress ->
    Log.i(
        TAG,
        "onNavigation \nformId: $formId \nformElement: $formElement \nprogress: $progress"
    )
}
```

`onShow` gets called when a survey widget becomes visible to your user.

```kotlin
Refiner.onShow { formId ->
    Log.i(TAG, "onShow \nformId: $formId")
}
```

`onClose` gets called when the survey widgets disappears from the screen.

```kotlin
Refiner.onClose { formId ->
    Log.i(TAG, "onClose \nformId: $formId")
}
```

`onDismiss` gets called when the user dismissed a survey by clicking on the “x” in the top right corner.

```kotlin
Refiner.onDismiss { formId ->
    Log.i(TAG, "onDismiss \nformId: $formId")
}
```

`onComplete` gets called when the user completed (submitted) a survey.

```kotlin
Refiner.onComplete { formId, formData ->
   Log.i(TAG, "onComplete \nformId: $formId \nformData: $formData")
}
```
