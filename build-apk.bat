@echo off
REM Windows batch script to build Cockfight Sabah APK
echo ==========================================================
echo   Building Cockfight Sabah Android APK
echo ==========================================================

call gradle :app:buildApk

echo.
echo Done! APK Path: app\build\outputs\apk\debug\app-debug.apk
echo ==========================================================
pause
