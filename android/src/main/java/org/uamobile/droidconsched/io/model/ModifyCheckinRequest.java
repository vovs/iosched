package org.uamobile.droidconsched.io.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.uamobile.droidconsched.io.ServerRequestData;

/**
 * Created with IntelliJ IDEA.
 * User: alsutton
 * Date: 10/09/2013
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class ModifyCheckinRequest extends ServerRequestData {
    private String confirmationCode;

    public ModifyCheckinRequest()
    {
    }

    public String getConfirmationCode()
    {
        return this.confirmationCode;
    }

    public ModifyCheckinRequest setConfirmationCode(String confirmationCode)
    {
        this.confirmationCode = confirmationCode;
        return this;
    }

    @Override
    public String toJSON()
        throws JSONException {
        return new JSONObject()
                .put("confirmationCode", confirmationCode)
                .toString();
    }
}
