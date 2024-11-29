@echo off
setlocal enabledelayedexpansion

:: Step 1: Check Maven
where mvn >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH. Please install it first.
    exit /b 1
)

:: Step 2: Run Maven clean and verify
echo Running Maven clean and verify...
mvn clean verify
if errorlevel 1 (
    echo Maven clean and verify failed.
    exit /b 1
)

echo Maven clean and verify completed successfully.
pause
