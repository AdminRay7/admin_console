# Admin Console — Native Android App

A native Android application with admin/self-monitoring features:

- 📷 Camera capture (front + rear)
- 🎤 Mic recording
- 🎥 Video preview (CameraX)
- 🖥 Screen capture consent (MediaProjection)
- 📍 GPS one-shot
- 📩 SMS inbox + sent reader
- 📱 Device info
- 🌐 Network info
- 🛠 Tools: battery, vibrate, tone, notifications, clipboard, in-app keylog

## Build

The APK is built automatically by GitHub Actions on every push to `main`.

Download the latest APK:
1. Open the **Actions** tab
2. Click the latest successful **Build APK** run
3. Download the **admin-console-debug** artifact
4. Unzip → install `app-debug.apk`

## Install on phone

1. Enable **Install unknown apps** for your file manager / browser
2. Tap the APK → Install
3. Grant permissions when the app asks

## Permissions used

- CAMERA, RECORD_AUDIO — camera + mic
- ACCESS_FINE_LOCATION — GPS
- READ_SMS, RECEIVE_SMS — SMS reader
- POST_NOTIFICATIONS — notifications
- VIBRATE, INTERNET, FOREGROUND_SERVICE

## License

Personal use.
