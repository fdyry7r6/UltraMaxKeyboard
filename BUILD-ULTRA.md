# Ultra Max Keyboard — Build Ready

This project is an Ultra Max themed Android IME based on the full HeliBoard keyboard engine.

## Included
- Persian and English keyboard support
- Suggestions / autocorrection / gesture typing from the keyboard engine
- Emoji and clipboard tooling
- Rounded key style
- Ultra Max dark theme (default)
- Glass Ultra theme
- Neon Cyber theme
- Haptic/sound controls through the existing keyboard settings
- Ultra Max launcher branding
- Application ID: `com.ultramax.keyboard`

## Privacy
The keylogger projects supplied alongside the keyboard sources were **not merged**. This build does not add a background keylogger or a feature that exports typed text.

## Windows build
Use JDK 17.

```bat
gradlew.bat :app:assembleDebug
```

APK output:
`app\build\outputs\apk\debug\UltraMaxKeyboard_1.0-ultra-debug.apk`

If Gradle reports an incompatible Java version, point `JAVA_HOME` to a 64-bit JDK 17 installation.

The source retains the upstream HeliBoard license/notices where required.
