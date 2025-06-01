const amqp = require('amqplib');

const REQ_QUEUE = process.env.REQ_QUEUE || 'requests';
const RESP_QUEUE = process.env.RESP_QUEUE || 'responses';

async function start() {
    const conn = await amqp.connect('amqp://135.125.89.160');
    const ch = await conn.createChannel();

    await ch.assertExchange('amq.fanout', 'fanout');

    const q = await ch.assertQueue('', {exclusive: true});
    await ch.bindQueue(q.queue, 'amq.fanout', '');
    await ch.assertQueue(RESP_QUEUE, {durable: true});

    console.log('En attente des messages dans %s...', REQ_QUEUE);

    ch.consume(q.queue, msg => {
        const input = msg.content.toString();
        console.log("Message reçu :", input);

        const response = `Réponse à : ${input}`;
        ch.sendToQueue(RESP_QUEUE, Buffer.from(response));
        ch.ack(msg);
    });
}

start().catch(console.error);
