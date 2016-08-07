package com.accessone.Messages.Composer;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

/**
 * Current Project cardholders_tester
 * Created by duncan.browne on 07/08/2016.
 */
public class GetCardholderMessageComposer
{
    private static GetCardholderMessageComposer ourInstance = new GetCardholderMessageComposer();

    public static GetCardholderMessageComposer getInstance()
    {
        return ourInstance;
    }

    private static int REQUESTER_ID = 111;
    private static String REQUESTER_TYPE = "automatedtester";

    private GetCardholderMessageComposer()
    {
    }

    public String createGetCardholderByCardholderIDMessage(int cardholderID)
    {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("requester", factory.createObjectBuilder()
                        .add("id", REQUESTER_ID)
                        .add("type", REQUESTER_TYPE))
                .add("data", factory.createObjectBuilder()
                        .add("get", factory.createObjectBuilder()
                            .add("type", "cardholder")
                            .add("value", Integer.toString(cardholderID))))
                .build();

        String strJSONMsg = value.toString();
        return strJSONMsg;
    }
}
