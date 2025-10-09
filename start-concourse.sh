#!/bin/bash

echo "🚀 Starting Concourse CI..."
echo ""

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker Desktop first."
    exit 1
fi

echo "✅ Docker is running"
echo ""

# Check if images need to be pulled
if ! docker images | grep -q "concourse/concourse.*7.14"; then
    echo "📥 Pulling Concourse images (this may take 5-10 minutes on first run)..."
    echo "   concourse/concourse:7.14 (~1.4GB)"
    docker-compose pull
    echo ""
fi

# Start services
echo "🔄 Starting Concourse services..."
docker-compose up -d

# Wait for services to be healthy
echo ""
echo "⏳ Waiting for services to be ready..."
sleep 10

# Check status
echo ""
docker-compose ps

echo ""
echo "✅ Concourse should be starting up!"
echo ""
echo "📍 Concourse Web UI: http://localhost:8081"
echo "👤 Username: concourse"
echo "🔑 Password: secret"
echo ""
echo "To view logs: docker-compose logs -f"
echo "To stop: docker-compose down"
