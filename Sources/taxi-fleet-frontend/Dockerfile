# Build stage
FROM node:23-slim AS build
WORKDIR /app/Sources
COPY . .
RUN npm install && npm run build

# Serve stage
FROM nginx:alpine
COPY --from=build /app/dist/taxi-fleet-frontend/browser /usr/share/nginx/html
