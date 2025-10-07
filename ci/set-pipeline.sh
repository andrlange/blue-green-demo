#!/bin/bash

# Script to set the Concourse pipeline
# Usage: ./set-pipeline.sh

CONCOURSE_URL="http://localhost:8081"
PIPELINE_NAME="user-api-deploy"
PIPELINE_FILE="pipeline.yml"
CREDENTIALS_FILE="credentials.yml"

echo "Setting up Concourse pipeline..."

# Check if fly CLI is installed
if ! command -v fly &> /dev/null; then
    echo "❌ fly CLI is not installed"
    echo "Please install it from: https://concourse-ci.org/download.html"
    echo ""
    echo "For macOS: brew install --cask fly"
    echo "For Linux: Download from Concourse web UI at $CONCOURSE_URL"
    exit 1
fi

# Login to Concourse
echo "Logging in to Concourse..."
fly -t local login -c $CONCOURSE_URL -u concourse -p secret

# Set the pipeline
echo "Setting pipeline '$PIPELINE_NAME'..."
if [ -f "$CREDENTIALS_FILE" ]; then
    fly -t local set-pipeline -p $PIPELINE_NAME -c $PIPELINE_FILE -l $CREDENTIALS_FILE
else
    echo "⚠️  Warning: $CREDENTIALS_FILE not found"
    echo "Creating from template..."
    cp credentials.yml.template credentials.yml
    echo "Please edit ci/credentials.yml with your Cloud Foundry credentials"
    echo "Then run this script again"
    exit 1
fi

# Unpause the pipeline
echo "Unpausing pipeline..."
fly -t local unpause-pipeline -p $PIPELINE_NAME

echo ""
echo "✅ Pipeline set successfully!"
echo "Visit: $CONCOURSE_URL/teams/main/pipelines/$PIPELINE_NAME"
