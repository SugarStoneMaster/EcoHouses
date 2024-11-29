#!/bin/bash

# Stop on the first error
set -e

# Define project root and docker directory
PROJECT_ROOT="$(pwd)"
DOCKER_DIR="$PROJECT_ROOT/src/main/resources/docker"

# Step 1: Run Maven clean and verify
echo "Running Maven clean and verify..."
mvn clean verify

# Step 2: Build Docker image
echo "Building Docker image..."
docker build -t medimate-backend-app -f "$DOCKER_DIR/Dockerfile" .

# Step 3: Start Docker Compose
echo "Starting Docker Compose services..."
docker-compose -f "$DOCKER_DIR/mysql.yaml" up -d

echo "All steps completed successfully!"