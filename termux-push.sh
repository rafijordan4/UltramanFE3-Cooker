#!/data/data/com.termux/files/usr/bin/bash
set -euo pipefail

REPO_NAME="UltramanFE3-Cooker"

echo "[1/5] Memeriksa GitHub CLI..."
command -v git >/dev/null || { echo "Jalankan: pkg install git"; exit 1; }
command -v gh >/dev/null || { echo "Jalankan: pkg install gh"; exit 1; }

if ! gh auth status >/dev/null 2>&1; then
  echo "Login GitHub terlebih dahulu:"
  gh auth login
fi

if [ ! -d .git ]; then
  git init -b main
fi

git add .
git commit -m "Initial Ultraman FE3 Cooker launcher starter" || true

if ! git remote get-url origin >/dev/null 2>&1; then
  gh repo create "$REPO_NAME" --public --source=. --remote=origin --push
else
  git push -u origin main
fi

echo
echo "Selesai. Buka GitHub > Actions > Build Ultraman FE3 Cooker APK."
