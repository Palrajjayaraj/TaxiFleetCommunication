# Build stage
FROM node:18 AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

# Serve stage
FROM nginx:alpine
COPY --from=build /app/dist/taxi-fleet-frontend/browser /usr/share/nginx/html
