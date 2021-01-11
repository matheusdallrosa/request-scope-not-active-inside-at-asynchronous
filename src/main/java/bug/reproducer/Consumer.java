package bug.reproducer;

import com.rabbitmq.client.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class Consumer {

    private Connection connection;
    private Channel channel;

    private String rabbitmqUri;
    private String exchangeName;
    private String queueName;

    @Inject
    AsynchronousSevice asynchronousSevice;

    public void start(String rabbitmqUri, String exchangeName, String queueName) {
        this.rabbitmqUri = rabbitmqUri;
        this.exchangeName = exchangeName;
        this.queueName = queueName;

        try {
            startConsumer();
        } catch (Exception e) {
        }
    }

    public void stop() {
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {
        }
    }

    public void onMessage(final String payload){
        try {
            asynchronousSevice.asynchronousMethod().toCompletableFuture().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Connection connect(String rabbitmqUri) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(rabbitmqUri);
        return factory.newConnection();
    }


    private void startConsumer() throws IOException, URISyntaxException,
            TimeoutException, KeyManagementException, NoSuchAlgorithmException  {
        connection = connect(rabbitmqUri);
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.exchangeDeclare(exchangeName, "direct", true);
        channel.queueBind(queueName, exchangeName, "");
        channel.basicQos(100);
        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body);
                onMessage(message);

                if (channel.isOpen()) {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });
        channel.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException exception) {
            }
        });
    }
}
