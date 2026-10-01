#!/usr/bin/env bash
# Script to build Cockfight Sabah APK
set -e

echo "=========================================================="
echo "  Building Cockfight Sabah Android APK"
echo "=========================================================="

gradle :app:buildApk

echo ""
echo "Done! You can install the APK directly via ADB or download it:"
echo "APK Path: app/build/outputs/apk/debug/app-debug.apk"
echo "=========================================================="
