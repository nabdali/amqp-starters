<?php
require_once __DIR__ . '/vendor/autoload.php';

use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

$exchange = 'amq.fanout'; // Nom de l'exchange fanout

// Connexion à RabbitMQ
$connection = new AMQPStreamConnection('135.125.89.160', 5672, 'guest', 'guest');
$channel = $connection->channel();

// Déclaration d'un exchange de type fanout
$channel->exchange_declare($exchange, 'fanout', false, true, false);

// Création d'une queue temporaire unique
list($queue_name,,) = $channel->queue_declare("", false, false, true, false);

// Liaison de la queue à l'exchange
$channel->queue_bind($queue_name, $exchange);

echo " [*] En attente de missions dans '{$queue_name}'.\n";

// Traitement des messages reçus
$callback = function ($msg) {
    echo " [x] Mission reçue : ", $msg->body, "\n";
};

$channel->basic_consume($queue_name, '', false, true, false, false, $callback);

// Boucle d'attente
while ($channel->is_consuming()) {
    $channel->wait();
}
