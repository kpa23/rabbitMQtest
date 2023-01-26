package testMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private final static String QUEUE_NAME = "order_queue";
    private final static String HOST = "localhost";
    private final static int PORT = 5672;



    public static void consume() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(
                        String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body) throws IOException {

                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("Consumed: " + message);
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Produce.produce();
            consume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
