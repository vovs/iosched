package org.uamobile.droidconsched.io.model.events.sessions;

import org.uamobile.droidconsched.io.ServerRequest;
import org.uamobile.droidconsched.io.model.FeedbackResponse;
import org.uamobile.droidconsched.io.model.ModifyFeedbackRequest;

/**
 * Events.Sessions.Feedback as modelled in the Google Developers API.
 *
 * This is an implementation which replaces the generated version which shipped with IOsched and
 * makes the code human more human readable and removed the dependence on additional Google libraries.
 */
public class Feedback extends ServerRequest<FeedbackResponse>
{
    private static final String REST_PATH = "events/{eventId}/sessions/{sessionId}/feedback";

    private String eventId;

    private String sessionId;

    public Feedback(String eventId, String sessionId, ModifyFeedbackRequest content)
    {
        super("PUT", "events/{eventId}/sessions/{sessionId}/feedback", content, FeedbackResponse.class);
        assert eventId != null;
        this.eventId = eventId;

        assert sessionId != null;
        this.sessionId = sessionId;
    }

    public String getEventId()
    {
        return this.eventId;
    }

    public Feedback setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getSessionId()
    {
        return this.sessionId;
    }

    public Feedback setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }
}
