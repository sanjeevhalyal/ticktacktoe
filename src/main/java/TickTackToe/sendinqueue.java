package TickTackToe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class sendinqueue {
    public void sendinqueue(String host,String queuename, String returnstring) {


        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection = factory.newConnection();
            Channel send= connection.createChannel();


            send.queueDeclare(queuename, true, false, false, null);
            send.basicPublish("", queuename, null, returnstring.getBytes("UTF-8"));



            System.out.println(" [x] Sent '" + returnstring + "'");

            send.close();

            connection.close();
            return;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void sendinexchange(String host,String queuename, String returnstring,String EXCHANGE_NAME) {


        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            channel.basicPublish(EXCHANGE_NAME, queuename, null, returnstring.getBytes());
            System.out.println(" [x] Sent '" + queuename + "':'" + returnstring + "'");

            channel.close();
            connection.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}

