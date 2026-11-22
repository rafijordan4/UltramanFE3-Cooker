# Ultraman FE3 Cooker — PS2 Engine build

This branch uses Ametrine as the Android PS2 emulator application and ARMSX2 as its ARM64 emulation core. The APK is a single application (`com.rafi.ultramanfe3`), not a separate launcher plus emulator.

## Build requirements

- Android 10+ (API 29+), `arm64-v8a`
- Device with 4 KB memory pages; 16 KB page devices are not supported by this Ametrine build yet
- JDK 17
- Gradle wrapper 9.6.0
- Android SDK platform 37
- NDK 29.0.14206865
- CMake 3.31.6
- Python 3 and Git

## Build

```bash
git clone --recurse-submodules https://github.com/rafijordan4/UltramanFE3-Cooker.git
cd UltramanFE3-Cooker
git checkout engine-integration
./scripts/setup-armsx2.sh
./gradlew :app:assembleDebug
```

GitHub Actions installs the pinned SDK/NDK/CMake versions, prepares the ARMSX2 submodule and uploads the debug APK as an artifact.

## BIOS, games and saves

No PS2 BIOS or commercial game image is included. Import a BIOS dumped from a PS2 you own and game images you are entitled to use through the app. Use the app's memory-card manager to import/manage save data.

## Upstream and licenses

This branch is based on Ametrine and its ARMSX2/PCSX2-derived emulation core. Ametrine and ARMSX2 are GPL-3.0-or-later. Keep the upstream license notices and corresponding source available when distributing modified builds. Third-party notices are available in the upstream source and in the app's Open Source Licenses screen.

- Ametrine: https://github.com/kiraponz/Ametrine
- ARMSX2: https://github.com/kitakatashima/ARMSX2
