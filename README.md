# Ultraman FE3 Cooker (starter)

Android landscape launcher prototype for the user's own Ultraman Fighting Evolution 3 Taikenban ISO.

## Included
- Native Android Java project (min SDK 23, target SDK 35)
- Landscape UI with Play Game / Pengaturan / Grafik sidebar
- ISO/BIN/CHD document picker with persisted URI permission
- Kecil / Menengah / Tinggi graphics presets
- Bundled save-data archive extracted from the supplied ZIP
- Touchscreen control-layout preview (direction pad, □ △ × ○, L1/L2/R1/R2)

## Important status
This is a **launcher/UI starter**, not yet a playable PS2 port. The Play button currently reports that the PS2 engine is not integrated; the control screen is a visual/touch-layout preview and does not send input to an emulator. The supplied save folder is packaged as app data but is not yet mounted as an emulator memory card.

A PS2 engine integration, its native build dependencies, memory-card adapter, BIOS selection flow, and real input bridge must be added and tested before gameplay works. Do not treat the APK from this starter as a working game emulator.

## User-provided save data
The supplied ZIP contains:
- `BISLPS-25441icoULTR3/icon.sys`
- `BISLPS-25441icoULTR3/ultra3.ico`
- `BISLPS-25441icoULTR3/BISLPS-25441icoULTR3`
- `README.txt`

The README describes this as a PCSX2 folder-memory-card save. The app preserves it under its private `files/bundled-save-data/` directory.

## Build with GitHub Actions
1. Create a GitHub repository named `UltramanFE3-Cooker`.
2. Upload/push all project files to the `main` branch.
3. Open **Actions** and run **Build Ultraman FE3 Cooker APK** (or push a commit).
4. Download `UltramanFE3-Cooker-debug` from the workflow run's Artifacts.

## Push from Termux (Android)
```bash
pkg update -y
pkg install git gh unzip -y
termux-setup-storage
mkdir -p ~/UltramanFE3-Cooker
cd ~/UltramanFE3-Cooker
unzip ~/storage/downloads/UltramanFE3-Cooker-Termux-GitHub-Actions.zip
bash termux-push.sh
```
If the ZIP is in another folder, adjust the `unzip` path. The script asks you to sign in with `gh auth login`, then creates the GitHub repository and pushes the source. The repository is public by default; change `--public` to `--private` in `termux-push.sh` if you prefer a private repository.

## Build locally
Requires JDK 17, Android SDK platform 35, build-tools 35.0.0, and Gradle 8.9+:
```bash
gradle assembleDebug
```

## Copyright and firmware
The app does not contain a commercial game ISO or PS2 BIOS. Users must provide game data and firmware they are legally entitled to use. The save data included here is the user-supplied archive.
