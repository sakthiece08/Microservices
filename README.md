#### Note: This is in continuation to the 'Master' branch, please refer to the Master branch readme file before coming here for better understanding

# Docker

## Buildpack
paketobuildpacks/builder-jammy-base:latest

## Images
- https://hub.docker.com/
- sakthiece08/mortgage-rate-service:0.0.1-SNAPSHOT
- sakthiece08/mortgage-calculation-service:0.0.1-SNAPSHOT

### Maven build image
```
mvn spring-boot:build-image -DskipTest
```
### Docker compose with profile
https://windpoly.run/posts/docker-compose-profile/
