@echo off
setlocal

set JAVAFX_PATH=%~dp0openjfx\javafx-sdk-26\lib
set SRC_DIR=%~dp0src
set OUT_DIR=%~dp0out

echo ============================================
echo   CipherForge — 9to5 Edition  Build Script
echo ============================================
echo.

:: Clean old output
if exist "%OUT_DIR%" rmdir /s /q "%OUT_DIR%"
mkdir "%OUT_DIR%"

:: Collect all .java files
echo [1/3] Compiling Java sources...
dir /s /b "%SRC_DIR%\*.java" > "%~dp0sources.txt"

javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -d "%OUT_DIR%" -sourcepath "%SRC_DIR%" @"%~dp0sources.txt"

if errorlevel 1 (
    echo.
    echo [ERROR] Compilation failed. See errors above.
    pause
    exit /b 1
)
echo        Compilation successful.

:: Copy resources (FXML + CSS)
echo [2/3] Copying resources...
xcopy /s /y /q "%SRC_DIR%\ui\fxml\*.fxml" "%OUT_DIR%\ui\fxml\" >nul 2>&1
xcopy /s /y /q "%SRC_DIR%\ui\css\*.css" "%OUT_DIR%\ui\css\" >nul 2>&1
echo        Resources copied.

:: Launch
echo [3/3] Launching CipherForge...
echo.

java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "%OUT_DIR%" Launcher

endlocal
