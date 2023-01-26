package testMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.ThreadLocalRandom;

public class Produce {
    private final static String QUEUE_NAME = "order_queue";
    private final static String HOST = "localhost";
    private final static int PORT = 5672;
    public static void produce() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        try {
            Channel channel = factory.newConnection().createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // produce 10 messages
            for (int i = 0; i < 10; i++) {
                String message = "{\"oderId\":" + ThreadLocalRandom.current().nextInt(
                        1000, 2000) + ", \"items\":[{\"id\":" + ThreadLocalRandom.current().nextInt(
                        1000, 2000) + "},{\"id\":" + ThreadLocalRandom.current().nextInt(
                        1, 10) + "}]}";

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("Produced: " + message);
            }
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        produce();
    }
}
