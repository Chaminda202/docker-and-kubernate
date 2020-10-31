FROM node:12.9.0-alpine
WORKDIR /app
ADD package*.js ./
RUN npm install
ADD . .
CMD node index.js