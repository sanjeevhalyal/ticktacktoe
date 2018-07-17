package TickTackToe;

import InstanceObjects.GameInstance;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;


public class recv {


    public recv(String host, String queuename, GameInstance GI) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queuename, true, false, false, null);
        System.out.println(" [*] Waiting for data from client. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");

                JSONObject jsonObj = new JSONObject(message);
                String operation=jsonObj.getString("operation");
                String data=jsonObj.getString("data");

                try {
                    Class clazz=Class.forName("TickTackToe."+operation);
                    processinput pi=(processinput)clazz.newInstance();
                    pi.process(data,host,GI);

                } catch (ClassNotFoundException e) {
                    System.out.println("Data not processed no implementation created");
                }
                catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        };
        String tag1=channel.basicConsume(queuename, true, consumer);
    }
}
