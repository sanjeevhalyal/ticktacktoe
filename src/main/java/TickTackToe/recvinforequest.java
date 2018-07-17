package TickTackToe;

import InstanceObjects.GameInstance;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;

import static TickTackToe.ticktacktoe.ulo;

public class recvinforequest {

    static final String EXCHANGE_NAME = "requestinfo";

    public  recvinforequest(String host, GameInstance GI) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();



        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "ticktacktoe");

        System.out.println(" [*] Waiting for info request. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                int slots=ulo.getemptyslots();

                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");

                String returnstring= new Gson().toJson(GI);



                JSONObject jsonObj = new JSONObject(message);
                String uuid=jsonObj.getString("uuid");

                if(slots==0) {
                    new sendinqueue().sendinqueue(host,uuid,"{\"error\":\"slots full\"}");
                    return;
                }

                new sendinqueue().sendinqueue(host,uuid,returnstring);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
