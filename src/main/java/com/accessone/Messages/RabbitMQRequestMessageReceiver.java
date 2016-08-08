package com.accessone.Messages;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * Created by duncan.browne on 06/08/2016.
 */

public class RabbitMQRequestMessageReceiver extends RabbitMQMessageQueue implements Runnable, Consumer
{
    public RabbitMQRequestMessageReceiver(String queueName) throws IOException, TimeoutException
    {
        super(queueName);
    }

    public void run()
    {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(queueName, true,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag)
    {
        System.out.println("Consumer "+consumerTag +" registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException
    {
        Map map = (HashMap) SerializationUtils.deserialize(body);
        String strMessage = map.get("body").toString();
        System.out.println("Message " + map.get("body") + " received.");

        assert strMessage.equals(
                "{\"requester\":{\"id\":111,\"type\":\"automatedtester\"},\"status\":\"success\",\"data\":{\"post\":{\"cardholderid\":\"2\",\"title\":\"\",\"firstname\":\"Ewan\",\"surname\":\"Fleet\",\"employeenumber\":\"EF001\",\"departmentid\":\"3\",\"emailaddress\":\"\"}}}");

        if (strMessage.equals(
                "{\"requester\":{\"id\":111,\"type\":\"automatedtester\"},\"status\":\"success\",\"data\":{\"post\":{\"cardholderid\":\"2\",\"title\":\"\",\"firstname\":\"Ewan\",\"surname\":\"Fleet\",\"employeenumber\":\"EF001\",\"departmentid\":\"3\",\"emailaddress\":\"\"}}}"))
        {
            System.out.println("Success... Returned message is as expected");
        } else
        {
            System.err.println("Failure... Returned message not as expected");
        }
    }

    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}