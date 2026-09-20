# Top Topy Board

A custom Android IME based on the included keyboard source, branded as **Top Topy Board**.

## Faceemoji-style feature set (original implementation)
- Top Topy Studio toolbar button
- Unicode font transformations: Bold, Italic, Script, Gothic, Double, Mono, Fullwidth, Circled, Squared, Small Caps and Upside Down
- Kaomoji collection
- Symbols collection
- Text Art collection
- One-tap insertion into the current text field
- Transform selected text or the current word before the cursor
- Built-in Top Topy background image plus custom gallery background
- Existing Light/Dark/System appearance controls
- Existing emoji and clipboard features from the keyboard base
- Space horizontal swipe language switching remains enabled by default

## GitHub APK build
Push this repository to GitHub and open **Actions → Build Top Topy Board APK → Run workflow**.
The workflow builds `assembleDebug` and uploads the APK as a workflow artifact.

Android IMEs are implemented as `InputMethodService` components; this project keeps that architecture intact.
