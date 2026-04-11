@echo off

set "PATH_TO_FX=C:\Program Files\Java\javafx-sdk-26\lib"
set OUT_DIR=out

echo Cleaning...
if exist %OUT_DIR% rmdir /s /q %OUT_DIR%
mkdir %OUT_DIR%

echo Compiling Java files...
dir /s /b src\*.java > sources.txt

javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d %OUT_DIR% @sources.txt

echo Copying resources (FXML, CSS)...

xcopy /E /I /Y src\ui %OUT_DIR%\ui

echo Running application...

java --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -cp %OUT_DIR% MainApp

pause