@echo off
setlocal enabledelayedexpansion

:: Define Docker directory
set DOCKER_DIR=src\main\resources\docker

:: Step 1: Check Docker and Docker Compose
for %%C in (docker docker-compose) do (
    where %%C >nul 2>&1
    if errorlevel 1 (
        echo Error: %%C is not installed or not in PATH. Please install it first.
        exit /b 1
    )
)

:: Step 2: Clean up Docker resources
echo Cleaning up Docker containers, images, and volumes...

for /f "delims=" %%i in ('docker ps -aq') do (
    docker rm -f %%i
)

for /f "delims=" %%i in ('docker images -q') do (
    docker rmi -f %%i
)

for /f "delims=" %%i in ('docker volume ls -q') do (
    docker volume rm %%i
)

echo Docker cleanup completed.

:: Step 3: Build Docker image
echo Building Docker image...
docker build -t medimate-backend-app -f "%DOCKER_DIR%\Dockerfile" .
if errorlevel 1 (
    echo Docker build failed. Check logs for details.
    exit /b 1
)

echo Docker build completed successfully.

:: Step 4: Start Docker Compose
echo Starting Docker Compose services...
docker-compose -f "%DOCKER_DIR%\mysql.yaml" up -d
if errorlevel 1 (
    echo Docker Compose failed. Check logs for details.
    exit /b 1
)

echo Docker Compose services started successfully.
pause
