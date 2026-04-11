# 9to5 Studio-Inspired JavaFX Application — Complete Redesign

## Goal

Transform the existing **CipherForge** JavaFX encryption app into a stunning, bold, minimalist portfolio-style application inspired by [9to5studio.it](https://www.9to5studio.it/). The app retains its **encryption/decryption functionality** (Strategy pattern backend) but its entire UI/UX is reimagined with 9to5's high-contrast, architectural aesthetic. The build system is also fixed so the app runs without Maven.

## Design Language (from 9to5studio.it)

| Element | Value |
|---|---|
| **Primary Color** | `#000000` (pure black) |
| **Accent Color** | `#E4FF00` (neon yellow-green) |
| **Background** | `#FFFFFF` (white) / `#F5F5F0` (warm off-white) |
| **Typography** | Bold sans-serif, large scale, uppercase headings |
| **Layout** | Horizontal scroll homepage, vertical sidebars, split-screen project views |
| **Animations** | Fade transitions, scale-on-hover, smooth scene switches |

![9to5 Homepage](file:///C:/Users/ironm/.gemini/antigravity/brain/cb8e95cf-f42e-48b9-92f1-4635f3a6d133/homepage_full_1775739703549.png)

![9to5 Project Page](file:///C:/Users/ironm/.gemini/antigravity/brain/cb8e95cf-f42e-48b9-92f1-4635f3a6d133/project_page_1775739739946.png)

---

## User Review Required

> [!IMPORTANT]
> **Complete UI overhaul**: The existing fintech-inspired dashboard design (dark sidebar, KPI cards, etc.) will be **entirely replaced** with the 9to5 bold-minimalist aesthetic. The backend encryption logic (Strategy pattern, AES/Caesar) remains untouched.

> [!IMPORTANT]
> **Navigation paradigm shift**: Instead of a traditional sidebar, navigation uses **vertical edge labels** ("Menu", "Contacts") with full-screen overlay menus, inspired by the 9to5 website.

> [!IMPORTANT]
> **JavaFX runtime fix**: A `Launcher.java` wrapper class and a `run.bat` script will be created so the app works from the terminal without Maven. IntelliJ instructions are also provided.

---

## Proposed Changes

### Component 1: Build & Launch System (JavaFX Runtime Fix)

The "JavaFX runtime components are missing" error happens because Java 11+ removed bundled JavaFX. We fix this with:

1. A **`Launcher.java`** wrapper that calls `MainApp.main()` — this bypasses the JavaFX module check
2. A **`run.bat`** script that compiles and runs with the correct `--module-path` and `--add-modules` flags

#### [NEW] [Launcher.java](file:///d:/E/encryption-decryption-oopd-java/src/Launcher.java)
- Simple wrapper class that calls `MainApp.main(args)`
- This is the standard workaround for "JavaFX runtime components missing" when not using a module-info.java

#### [NEW] [run.bat](file:///d:/E/encryption-decryption-oopd-java/run.bat)
- Batch script: compiles all `.java` files with `--module-path` pointing to `openjfx/javafx-sdk-26/lib`
- Copies FXML/CSS resources to `out/` preserving directory structure
- Runs `Launcher` with correct module flags
- Single command: `run.bat`

---

### Component 2: Core Application — MainApp Redesign

#### [MODIFY] [MainApp.java](file:///d:/E/encryption-decryption-oopd-java/src/MainApp.java)
- Change window title to **"CipherForge — 9to5 Edition"**
- Load new CSS files: `studio-base.css` + `studio-theme.css`
- Load new root FXML: `StudioLayout.fxml`
- Set stage to undecorated or borderless for immersive feel (optional, user can decide)
- Enable stage resizing and responsive bindings

---

### Component 3: New FXML Layouts

#### [NEW] [StudioLayout.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/StudioLayout.fxml)
- Root `StackPane` → `BorderPane` structure
- **Left edge**: Vertical "MENU" label (rotated -90°), clickable to toggle full-screen overlay
- **Right edge**: Vertical "CONTACTS" label (rotated 90°), clickable for contact overlay
- **Center**: `StackPane` for view switching with fade transitions
- **Default view**: Home/landing with large project carousel

#### [NEW] [HomeView.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/HomeView.fxml)
- Full-width hero section with studio name "CIPHERFORGE" in massive bold text
- Subtitle: "Encryption • Precision • Security"
- Animated "Scroll & Discover" prompt
- Horizontal scrolling project cards row (showing encryption tools as "projects")

#### [NEW] [ProjectCard.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/ProjectCard.fxml)
- Reusable card component: large numbered index (01, 02, etc.)
- Project title, gradient overlay on hover
- Scale-up animation on hover
- Click to navigate to the corresponding tool view

#### [MODIFY] [EncryptionView.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/EncryptionView.fxml)
- Restyled with 9to5 aesthetic: split-screen layout
- Left side: large decorative number/title and description
- Right side: algorithm selector, key input, text areas, action buttons
- Bold black/white contrast with neon accent on buttons

#### [MODIFY] [FileView.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/FileView.fxml)
- Same split-screen treatment as EncryptionView
- Large "FILE OPS" header on the left
- File selection, processing on the right

#### [NEW] [MenuOverlay.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/MenuOverlay.fxml)
- Full-screen black overlay with large, bold navigation links
- Items: HOME, ENCRYPT, FILES, SETTINGS
- Neon accent hover effect on each item
- Fade-in/fade-out animation

#### [NEW] [ContactOverlay.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/ContactOverlay.fxml)
- Full-screen overlay with contact info, version, and team credits
- Minimal design with large typography

#### [MODIFY] [SettingsView.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/SettingsView.fxml)
- Restyled to match new aesthetic
- Bold section headers, clean settings cards

#### [DELETE] [MainLayout.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/MainLayout.fxml)
- Replaced by `StudioLayout.fxml`

#### [DELETE] [DashboardView.fxml](file:///d:/E/encryption-decryption-oopd-java/src/ui/fxml/DashboardView.fxml)
- Dashboard is replaced by the Home/landing page

---

### Component 4: New Controllers

#### [NEW] [StudioController.java](file:///d:/E/encryption-decryption-oopd-java/src/ui/controllers/StudioController.java)
- Root controller managing view switching, overlay visibility, and animations
- Fade/translate transitions for view changes
- Menu overlay toggle logic
- Contact overlay toggle logic

#### [NEW] [HomeController.java](file:///d:/E/encryption-decryption-oopd-java/src/ui/controllers/HomeController.java)
- Controls the horizontal scrolling project card carousel
- Manages card click navigation events
- Entrance animations (fade-in hero text)

#### [MODIFY] [EncryptionController.java](file:///d:/E/encryption-decryption-oopd-java/src/ui/controllers/EncryptionController.java)
- No logic changes, only FXML ID adjustments if needed for the new layout

#### [MODIFY] [FileController.java](file:///d:/E/encryption-decryption-oopd-java/src/ui/controllers/FileController.java)
- No logic changes, only FXML ID adjustments if needed

#### [DELETE] [MainController.java](file:///d:/E/encryption-decryption-oopd-java/src/ui/controllers/MainController.java)
- Replaced by `StudioController.java`

#### [DELETE] [DashboardController.java](file:///d:/E/encryption-decryption-oopd-java/src/ui/controllers/DashboardController.java)
- Dashboard is removed

---

### Component 5: New CSS Design System

#### [NEW] [studio-base.css](file:///d:/E/encryption-decryption-oopd-java/src/ui/css/studio-base.css)
- Complete CSS reset and component styles
- Bold typography system (large headings, uppercase transforms)
- Card, button, input, overlay styles
- Hover animations (scale, shadow, color shift)
- Vertical text rotation classes
- Scrollbar customization

#### [NEW] [studio-theme.css](file:///d:/E/encryption-decryption-oopd-java/src/ui/css/studio-theme.css)
- Color definitions: `#000000`, `#E4FF00`, `#FFFFFF`, `#F5F5F0`
- All color assignments for components
- Light mode (primary) and optional dark mode toggle

#### [KEEP] [components.css](file:///d:/E/encryption-decryption-oopd-java/src/ui/css/components.css)
- Kept for backwards compatibility but no longer loaded by default

#### [KEEP] [dark-theme.css](file:///d:/E/encryption-decryption-oopd-java/src/ui/css/dark-theme.css) / [light-theme.css](file:///d:/E/encryption-decryption-oopd-java/src/ui/css/light-theme.css)
- Kept but not loaded by default (old themes)

---

### Component 6: Backend (No Changes)

The following files remain **completely untouched**:
- `crypto/EncryptionStrategy.java`
- `crypto/AESStrategy.java`
- `crypto/CaesarStrategy.java`
- `context/EncryptionContext.java`
- `util/FileHandler.java`
- `exceptions/EncryptionException.java`
- `exceptions/InvalidKeyException.java`

---

## How to Run the App

### Option 1: Terminal (run.bat) — Recommended

```batch
cd d:\E\encryption-decryption-oopd-java
run.bat
```

This single script:
1. Compiles all `.java` files with JavaFX module path
2. Copies FXML + CSS resources to `out/`
3. Launches the app via the `Launcher` wrapper

### Option 2: IntelliJ IDEA

1. **File → Project Structure → Libraries** → Add `openjfx/javafx-sdk-26/lib` as a library
2. **Run → Edit Configurations** → Add VM options:
   ```
   --module-path "d:\E\encryption-decryption-oopd-java\openjfx\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml
   ```
3. Set **Main class** to `Launcher` (NOT `MainApp`)
4. Click **Run**

### Option 3: Terminal (manual commands)

```powershell
# Step 1: Compile
javac --module-path "openjfx\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml -d out -sourcepath src src\Launcher.java src\MainApp.java src\context\EncryptionContext.java src\crypto\AESStrategy.java src\crypto\CaesarStrategy.java src\crypto\EncryptionStrategy.java src\exceptions\EncryptionException.java src\exceptions\InvalidKeyException.java src\ui\controllers\StudioController.java src\ui\controllers\HomeController.java src\ui\controllers\EncryptionController.java src\ui\controllers\FileController.java src\ui\controllers\SettingsController.java src\util\FileHandler.java

# Step 2: Copy resources
xcopy /s /y src\ui\fxml\*.fxml out\ui\fxml\
xcopy /s /y src\ui\css\*.css out\ui\css\

# Step 3: Run
java --module-path "openjfx\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml -cp out Launcher
```

---

## Open Questions

> [!IMPORTANT]
> 1. **Undecorated window**: Should the app use an undecorated/borderless stage for a more immersive look (like the website), or keep the standard Windows title bar? An undecorated stage requires custom close/minimize/maximize buttons.

> [!IMPORTANT]
> 2. **Generated images**: Should I use the image generation tool to create architectural-style placeholder images for the project cards, or use colored rectangles/gradients?

> [!IMPORTANT]
> 3. **Horizontal scroll on homepage**: The 9to5 site uses horizontal scrolling for projects. JavaFX can do this with a `ScrollPane` set to horizontal. Should we implement this faithfully, or use a standard vertical scrollable grid instead?

---

## Verification Plan

### Automated Tests
- Compile the project using `run.bat` and verify zero compilation errors
- Launch the app and visually verify each section via browser screenshot tool

### Manual Verification
- Navigate through all sections (Home → Encrypt → Files → Settings)
- Test menu and contact overlay open/close animations
- Test encrypt/decrypt functionality with AES and Caesar
- Test file operations (browse, encrypt, save)
- Verify responsive behavior when resizing window
- Test theme switching in Settings
