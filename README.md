# Instafeed

Android app for reviewing food and finding nearby restaurants.

## Build and debug

1. Open this folder in Android Studio and let Gradle sync.
2. Install Android SDK Platform 37 (37.0) and use JDK 25 for Gradle (the project includes daemon JVM criteria).
3. Select the `app` module and the `debug` build variant.
4. Start an API 24+ emulator or connect a phone with USB debugging enabled. Use a Google Play emulator image for Maps.
5. Set breakpoints and click **Debug app**. The launcher opens `SplashScreen`, then `MainActivity`.

From PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
.\gradlew.bat :app:installDebug
```

The debug APK is `app/build/outputs/apk/debug/app-debug.apk`. The application ID is `com.example.instafeed`; Java classes and generated bindings use `hyvo.prog1415.instafeed`.

## Usage

https://youtu.be/lglFtV_ROx8

## Optional Maps setup

Add your own key to the ignored `local.properties` file, alongside `sdk.dir`:

```properties
MAPS_API_KEY=your_key_here
```

You can also supply `MAPS_API_KEY` as an environment variable or Gradle project property. Rebuild after changing it. Without a key, search, reviews, and history can still be debugged; the map screen shows a setup message and closes.

Enable Maps SDK for Android and the Places API used by the nearby-search endpoint in your Google Cloud project. Configure credentials and restrictions appropriate to each API. See [Google Maps setup](https://developers.google.com/maps/documentation/android-sdk/config).

Grant foreground location permission and enable device location for nearby search. Background location is not required.
