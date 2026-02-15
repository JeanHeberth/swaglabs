#!/usr/bin/env bash
# Script simples para instalar Chromium/Chrome em agentes Linux Debian/Ubuntu-like
# Uso: install_chrome.sh [version]
# Observação: execução deste script requer privilégios (sudo) e pode não funcionar em todos os agentes.

set -euo pipefail

VERSION="$1"

echo "Iniciando tentativa de instalação do Chrome/Chromium (versão: ${VERSION:-latest})"

if command -v google-chrome-stable >/dev/null 2>&1; then
  echo "google-chrome já instalado"
  exit 0
fi

if command -v chromium-browser >/dev/null 2>&1; then
  echo "chromium-browser já instalado"
  exit 0
fi

# Detecta apt-get
if command -v apt-get >/dev/null 2>&1; then
  echo "Sistema com apt-get detectado: tentando instalar chromium-browser"
  sudo apt-get update -y || true
  sudo apt-get install -y chromium-browser || true
  if command -v chromium-browser >/dev/null 2>&1; then
    echo "Chromium instalado com sucesso"
    exit 0
  fi
fi

# Fallback: tentar instalar google-chrome via deb (x86_64)
ARCH=$(uname -m)
if [ "$ARCH" = "x86_64" ]; then
  echo "Tentando baixar e instalar google-chrome-stable"
  TMP_DEB="/tmp/google-chrome-stable_current_amd64.deb"
  wget -q -O "$TMP_DEB" "https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb" || true
  if [ -f "$TMP_DEB" ]; then
    sudo dpkg -i "$TMP_DEB" || sudo apt-get -f install -y || true
    if command -v google-chrome-stable >/dev/null 2>&1; then
      echo "google-chrome-stable instalado com sucesso"
      exit 0
    fi
  fi
fi

echo "Instalação do Chrome/Chromium não concluída. O agente pode não suportar instalação automática. Saindo com código 0 (não-fatal)"
exit 0

