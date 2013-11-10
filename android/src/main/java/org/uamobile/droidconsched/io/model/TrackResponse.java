package org.uamobile.droidconsched.io.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uamobile.droidconsched.io.ServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * TrackResponse as modelled in the Google Developers API.
 *
 * This is an implementation which replaces the generated version which shipped with IOsched and
 * makes the code human more human readable and removed the dependence on additional Google libraries.
 */
public class TrackResponse extends ServerResponse {

    private String description;

    private String iconUrl;

    private String id;

    private List<String> sessions;

    private String title;

    private int meta;

    private String colour;

    public TrackResponse()
    {
    }

    public String getDescription()
    {
        return this.description;
    }

    public TrackResponse setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public String getIconUrl()
    {
        return this.iconUrl;
    }

    public TrackResponse setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
        return this;
    }

    public String getId()
    {
        return this.id;
    }

    public TrackResponse setId(String id)
    {
        this.id = id;
        return this;
    }

    public List<String> getSessions()
    {
        return this.sessions;
    }

    public TrackResponse setSessions(List<String> sessions)
    {
        this.sessions = sessions;
        return this;
    }

    public String getTitle()
    {
        return this.title;
    }

    public TrackResponse setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    protected boolean useClassSpecificSetter(JSONObject jsonObject, final String key)
            throws JSONException {
        if(!"sessions".equals(key)) {
            return false;
        }

        JSONArray array = jsonObject.getJSONArray("sessions");
        List<String> sessionsList = new ArrayList<String>(array.length());
        for(int i = 0 ; i < array.length() ; i++) {
            sessionsList.add(array.getString(i));
        }
        setSessions(sessionsList);
        return true;
    }
}