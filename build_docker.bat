@echo off
setlocal enabledelayedexpansion

:: Define project root and docker directory
set PROJECT_ROOT=%cd%
set DOCKER_DIR=%PROJECT_ROOT%\src\main\resources\docker

:: Check if required commands exist
for %%C in (mvn docker docker-compose) do (
    where %%C >nul 2>nul
    if errorlevel 1 (
        echo Error: %%C is not installed or not in PATH. Please install it first.
        exit /b 1
    )
)

:: Step 0: Clean up existing Docker containers, images, and volumes
echo Cleaning up existing Docker containers, images, and volumes...

:: Remove all containers
for /f "delims=" %%i in ('docker ps -aq') do (
    docker rm -f %%i
)

:: Remove all images
for /f "delims=" %%i in ('docker images -q') do (
    docker rmi -f %%i
)

:: Remove all unused volumes
for /f "delims=" %%i in ('docker volume ls -q') do (
    docker volume rm %%i
)

echo Docker cleanup complete.

:: Step 1: Run Maven clean and verify
echo Running Maven clean and verify...
mvn clean verify
if errorlevel 1 (
    echo Maven command failed.
    exit /b 1
)

:: Step 2: Build Docker image
echo Building Docker image...
docker build -t medimate-backend-app -f "%DOCKER_DIR%\Dockerfile" "%PROJECT_ROOT%"
if errorlevel 1 (
    echo Docker build failed.
    exit /b 1
)

:: Step 3: Start Docker Compose
echo Starting Docker Compose services...
docker-compose -f "%DOCKER_DIR%\mysql.yaml" up -d
if errorlevel 1 (
    echo Docker Compose failed.
    exit /b 1
)

echo All steps completed successfully!