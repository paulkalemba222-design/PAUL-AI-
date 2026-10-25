# PAUL AI Android — pack source v0.2

Ce dépôt contient le projet source Android de PAUL AI et le workflow GitHub Actions pour générer un APK debug.

## Fonctionnalités du prototype
- Interface de discussion
- Saisie vocale via le service de reconnaissance vocale Android
- Sélection de fichiers
- Connexion préparée au backend Supabase Edge Function `paul-ai`
- Compilation APK automatique via GitHub Actions

## Limites à connaître
- Ceci est le code source, pas un APK déjà compilé ni testé sur appareil.
- Le backend `paul-ai` configuré actuellement peut exiger un jeton Supabase (verify_jwt=true). Cette version n'inclut pas encore l'écran de connexion Supabase ; une erreur 401 signifie que l'authentification doit être ajoutée.
- Les fichiers texte TXT/MD/CSV/JSON peuvent être ajoutés au prompt. Les PDF et images ne sont pas encore analysés.
- La clé publique Supabase est une clé client, pas un secret. Ne jamais placer une clé OpenRouter, `service_role` ou autre secret dans l'application Android.
- La reconnaissance vocale dépend des services installés sur l'appareil et peut utiliser une connexion Internet.

## Compilation gratuite
1. Crée un dépôt GitHub nommé `PAUL-AI`.
2. Envoie le contenu du dossier du projet à la racine du dépôt (ne dépose pas uniquement le ZIP imbriqué).
3. Ouvre l'onglet **Actions**.
4. Choisis **Build PAUL AI APK**, puis **Run workflow**. Le workflow peut aussi démarrer après un push sur `main`.
5. À la fin, ouvre l'exécution réussie et télécharge l'artefact `PAUL-AI-debug-APK`.

L'APK produit est une version debug destinée aux tests, pas une version signée pour publication. Le projet doit encore être compilé et testé avant de confirmer qu'il fonctionne correctement.
