FROM php:8.1-cli

# Installer sockets + unzip pour composer
RUN apt-get update && apt-get install -y zip unzip \
    && docker-php-ext-install sockets

# Installer Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Créer dossier de travail
WORKDIR /app

# Copier les fichiers et installer les dépendances
COPY . .
RUN composer install

CMD ["php", "consumer_fanout.php"]
