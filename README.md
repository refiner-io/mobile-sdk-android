# Refiner Mobile SDK for Android

[![Maven Central](https://img.shields.io/maven-central/v/io.refiner/refiner.svg?label=maven%20central)](#)

This repository hosts the official Refiner Android SDK. Installing the SDK allows you to track user data in Refiner and launch in-app surveys within your Android app.

The Refiner Android SDK supports Android version 5.0+ (API level 21+)

Refiner is a customer feedback survey platform designed specifically for web and mobile applications. Get spot-on insights from your users with perfectly timed [mobile app microsurveys](https://refiner.io/features/mobile-app-surveys/).

With Refiner you can ask your users any question while they are using your Android mobile app. Measure [customer satisfaction (CSAT)](https://refiner.io/solutions/csat/), [Net Promoter Score (NPS)](https://refiner.io/solutions/nps/), or [customer effort score (CES)](https://refiner.io/solutions/ces/), research what to built next or profile your users. Refiner supports all product feedback survey use cases and comes packed with expert-built templates that will get you started quickly.

Refiner integrates into your marketing & sales tech stack seamlessly. Our integrations allow you to sync survey response data in real time to third party tools, such as your CRM, email marketing automation platform, your backend API or data warehouse.  

Please find more information about how Refiner mobile app survey work in our [documentation](https://refiner.io/docs/kb/mobile-sdk/getting-started/).

## 1) Installation

Refiner Android SDK is published to Maven. Add dependency below in your `app/build.gradle` file.



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

## 2) Usage

Visit our [documentation](https://refiner.io/docs/kb/mobile-sdk/mobile-sdk-reference/) for more information about how to use the SDK methods.

### Initialization & Configuration

Initialize Refiner Android SDK in your application class with the needed configuration parameters.

```kotlin
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            Refiner.initialize(
                context = this,
                projectId = "PROJECT_ID",
                debugMode = false
            )
        } catch (e: Exception) {
            Log.e("Refiner", e.printStackTrace().toString())
        }
    }
}
```

### Identify User

Call `Identify User` to create or update a user record in Refiner.

The first parameter is the userId of your logged-in user and is the only mandatory parameter.

The second parameter is an object of user traits. You can omit or set this value to `null` if you don't want to attach any traits to the user object in Refiner. User traits map accepts values with the following types: String, Int or Boolean.

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

The fourth parameter is an optional [Identity Verification](https://refiner.io/docs/kb/settings/identity-verification/) signature. We recommend to use a Identify Verification signature for increased security in a production environment. For development purposes, you can omit or set this value to `null`.

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

### Set User

The `Set User` method acts as an alternative to the `Identify User` method described above. 

In contrast to the `Identify User` method, the `Set User` method does not immediately create a user object in your Refiner account. The provided user Id and traits are kept locally in your app and no data is communicated to our servers at first. Only when the user performs a meaningful action in your app (e.g. `Track Event` or `Track Screen` is executed) will a user object be created in Refiner. Provided user traits will be attached to the user object when a survey is shown.

The purpose of this alternative method is provide a way to identify users locally when the SDK is initialised but keep the number of tracked users in your Refiner account to a minimum.

```kotlin
try {
    Refiner.setUser(
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

### Track Event

`Track Event` lets you track user events. Tracked events can be used to create user segments and target audiences in Refiner.

```kotlin
Refiner.trackEvent(eventName = "EVENT_NAME")
```

### Track Screen

`Track Screen` provides to track screen that user is currently on. Screen information can be used to launch surveys in specific areas of your app.

We recommend to track screens on which you might want to show a survey one day. There is no need to systematically track all screens of your app.

```kotlin
Refiner.trackScreen(screenName = "SCREEN_NAME")
```

### Ping

Depending on your setup, you might want to initiate regular checks for surveys that are scheduled for the current user. For example when you are using time based trigger events, or when a target audience is based on user data received by our backend API. 

The `Ping` method provides an easy way to perform such checks. You can call the `Ping` method at key moments in a user's journey, such as when the app is re-opened, or when the user performs a specific action.

```kotlin
Refiner.ping()
```

### Show Form

If you use the Manual Trigger Event for your survey, you need to call `Show Form` whenever you want to launch the survey.

```kotlin
Refiner.showForm(formUuid = "FORM_UUID")
```

For testing purposes, you can also provide an additional `force` parameter which will bypass all targeting rules and always display the survey.

```kotlin
Refiner.showForm(formUuid = "FORM_UUID", force = true)
```

### Attach Contextual Data

Attach contextual data to the survey submissions with `addToResponse`. Set `null` to remove the contextual data. 

```kotlin
Refiner.addToResponse(
    contextualData = hashMapOf(
        Pair("some_data", "hello"),
        Pair("some_more_data", "hello again"),
    )
)
```

### Start user session

A new user session is automatically detected when a user returns to your application after at least one hour of inactivity. You can choose to manually start a new user session with the method shown below. You can call this method for example right after a user opens your app.

```kotlin
Refiner.startSession()
```

### Reset User

Call `Reset User` to reset the user identifier previously set through `Identify User`. We recommend calling this method when the user logs out from your app.

```kotlin
Refiner.resetUser()
```

### Set Project

Change the environment UUID during runtime, after the SDK has been initialised.

```kotlin
Refiner.setProject(projectId ="PROJECT_ID")
```

### Close Surveys

Close a survey programmatically without sending any information to the backend API with the `closeForm` method.

```kotlin
Refiner.closeForm(formUuid = "FORM_UUID")
```

Close a survey programmatically and send a "dismissed at" timestamp to the backend server with the `dismissForm` method.

```kotlin
Refiner.dismissForm(formUuid = "FORM_UUID")
```

### Register callback functions

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

`onError` gets called when an error occured.

```kotlin
Refiner.onError { message ->
   Log.i(TAG, "onError \nmessage: $message")
}
```
