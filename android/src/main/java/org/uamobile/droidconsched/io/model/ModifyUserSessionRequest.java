package org.uamobile.droidconsched.io.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.uamobile.droidconsched.io.ServerRequestData;

/**
 * Created with IntelliJ IDEA.
 * User: alsutton
 * Date: 10/09/2013
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class ModifyUserSessionRequest extends ServerRequestData {

    private String eventId;

    private String sessionId;

    public ModifyUserSessionRequest()
    {
    }

    public String getEventId()
    {
        return this.eventId;
    }

    public ModifyUserSessionRequest setEventId(String eventId)
    {
        this.eventId = eventId;
        return this;
    }

    public String getSessionId()
    {
        return this.sessionId;
    }

    public ModifyUserSessionRequest setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public String toJSON()
        throws JSONException {
        return new JSONObject()
                .put("eventId", eventId)
                .put("sessionId", sessionId)
                .toString();
    }
}