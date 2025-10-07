# Concourse CI Local Setup

This directory contains the configuration for running Concourse CI locally to deploy the Spring Boot User API to Cloud Foundry.

## Prerequisites

- Docker Desktop installed and running
- `fly` CLI installed (Concourse CLI tool)

## Quick Start

### 1. Start Docker Desktop

Make sure Docker Desktop is running on your machine.

### 2. Start Concourse CI

From the project root directory:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database (for Concourse metadata)
- Concourse web server on http://localhost:8081
- Concourse worker

**Login credentials:**
- Username: `concourse`
- Password: `secret`

### 3. Install fly CLI

If you don't have the `fly` CLI installed:

**macOS:**
```bash
brew install --cask fly
```

**Linux/Windows:**
Download from the Concourse web UI at http://localhost:8081

### 4. Configure Cloud Foundry Credentials

1. Copy the credentials template:
```bash
cd ci
cp credentials.yml.template credentials.yml
```

2. Edit `ci/credentials.yml` with your Cloud Foundry details:
```yaml
cf-api-endpoint: https://api.cf.your-domain.com
cf-username: your-username
cf-password: your-password
cf-org: your-org
cf-space: development
cf-domain: cfapps.io
cf-app-suffix: dev
```

### 5. Set the Pipeline

Run the setup script:
```bash
cd ci
./set-pipeline.sh
```

Or manually:
```bash
# Login to Concourse
fly -t local login -c http://localhost:8081 -u concourse -p secret

# Set the pipeline
fly -t local set-pipeline -p user-api-deploy -c pipeline.yml -l credentials.yml

# Unpause the pipeline
fly -t local unpause-pipeline -p user-api-deploy
```

### 6. View the Pipeline

Open http://localhost:8081 in your browser and login with:
- Username: `concourse`
- Password: `secret`

## Pipeline Overview

The pipeline consists of two jobs:

### 1. build-and-deploy
- Checks out the code from Git
- Builds the Spring Boot application with Maven
- Packages the JAR file
- Deploys to Cloud Foundry

### 2. test-deployment
- Runs after successful deployment
- Performs smoke tests against the deployed application

## Useful Commands

```bash
# View pipeline status
fly -t local pipelines

# Trigger a build manually
fly -t local trigger-job -j user-api-deploy/build-and-deploy

# Watch build logs
fly -t local watch -j user-api-deploy/build-and-deploy

# Pause pipeline
fly -t local pause-pipeline -p user-api-deploy

# Destroy pipeline
fly -t local destroy-pipeline -p user-api-deploy

# Stop Concourse
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Troubleshooting

### Docker not running
```bash
# Check Docker status
docker ps

# If not running, start Docker Desktop
```

### Cannot login to Concourse
```bash
# Check if Concourse web is running
docker-compose ps

# View logs
docker-compose logs concourse-web
```

### Pipeline fails to build
```bash
# Check worker status
fly -t local workers

# View detailed logs
fly -t local watch -j user-api-deploy/build-and-deploy
```

## File Structure

```
ci/
├── README.md                      # This file
├── pipeline.yml                   # Concourse pipeline definition
├── credentials.yml.template       # Template for CF credentials
├── credentials.yml                # Your actual credentials (gitignored)
└── set-pipeline.sh               # Script to set up the pipeline

docker-compose.yml                 # Concourse services configuration
keys/                             # SSH keys for Concourse (gitignored)
├── web/
│   ├── session_signing_key
│   ├── tsa_host_key
│   └── authorized_worker_keys
└── worker/
    ├── worker_key
    └── tsa_host_key.pub
```

## Next Steps

1. Start Docker Desktop
2. Run `docker-compose up -d`
3. Configure your Cloud Foundry credentials in `ci/credentials.yml`
4. Run `ci/set-pipeline.sh`
5. Visit http://localhost:8081 to see your pipeline!
