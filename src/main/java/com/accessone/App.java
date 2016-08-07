package com.accessone;

/**
 * Hello world!
 *
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.accessone.Messages.RabbitMQEventMessageSender;
import com.accessone.Messages.RabbitMQRequestMessageReceiver;
import com.accessone.Messages.Composer.GetCardholderMessageComposer;

public class App 
{

    public static void main( String[] args ) throws IOException, TimeoutException, SQLException
    {
        /*MariaDBFacade dbFacade = new MariaDBFacade();
        dbFacade.GetCardholderRecord(1, null);
        dbFacade.GetCardholderRecord(4, null);*/

        GetCardholderMessageComposer composer = GetCardholderMessageComposer.getInstance();
        String strMessage = composer.createGetCardholderByCardholderIDMessage(2);
        assert strMessage == "{\"requester\":{\"id\":111,\"type\":\"automatedtester\"},\"data\":{\"get\":{\"type\":\"cardholder\",\"cardholderid\":\"2\"}}}";
        System.out.println(strMessage);

         // Set up the RabbitMQ Request message receiver
        RabbitMQRequestMessageReceiver consumer = new RabbitMQRequestMessageReceiver("response");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // Setup the RabbitMQ Event message sender
        RabbitMQEventMessageSender producer = new RabbitMQEventMessageSender("request");

        // Send the message to cardholder service
        HashMap message = new HashMap();
        message.put("body", strMessage);
        producer.sendMessage(message);
        System.out.println("Message sent : " + strMessage);

        /*for (int i = 0; i < 10; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number "+ i +" sent.");
        }*/

    }
}
