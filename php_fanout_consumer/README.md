## PHP AMQP starter

1. Build the image
```bash
docker build -t php-amqp-starter .
```
2. Run the container
```bash
docker run -it --rm \
    -v $(pwd):/app \
    -w /app \
    php-amqp-starter
```