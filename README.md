# PAUL AI — Android

Application Android native (Kotlin + WebView) pour discuter avec PAUL AI via Supabase Edge Function `paul-ai`.

## Configuration
Dans `app/src/main/assets/config.js`, remplace `SUPABASE_ANON_KEY` par la clé publique (anon/publishable) de ton projet Supabase. Ne mets jamais `OPENROUTER_API_KEY` dans l'application.

Le backend exige actuellement un JWT. Cette première version inclut connexion e-mail/mot de passe et chat texte. Téléphone/SIM, voix et fichiers restent à développer.

## Générer l'APK gratuitement
1. Crée un dépôt GitHub et téléverse le contenu du ZIP.
2. Ouvre Actions et lance `Build PAUL AI APK`.
3. Télécharge `PAUL-AI-debug.apk` depuis les artefacts du workflow terminé.

Le ZIP contient le projet source, pas un APK déjà compilé ou testé sur téléphone.
