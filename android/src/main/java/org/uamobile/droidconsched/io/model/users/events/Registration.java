package org.uamobile.droidconsched.io.model.users.events;

import org.uamobile.droidconsched.io.ServerRequest;
import org.uamobile.droidconsched.io.model.RegistrationResponse;

import java.io.IOException;

/**
 * Users.Events.Registration as modelled in the Google Developers API.
 *
 * This is an implementation which replaces the generated version which shipped with IOsched and
 * makes the code human more human readable and removed the dependence on additional Google libraries.
 */
public class Registration
{
    public Registration()
    {
    }

    public Get get(String eventId)
            throws IOException
    {
        return new Get(eventId);
    }

    public class Get extends ServerRequest<RegistrationResponse>
    {
        private static final String REST_PATH = "events/{eventId}/registration";

        private String eventId;

        protected Get(String eventId)
        {
            super("GET", "events/{eventId}/registration", null, RegistrationResponse.class);
            assert eventId != null;
            this.eventId = eventId;
        }

        public String getEventId()
        {
            return this.eventId;
        }

        public Get setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
    }
}
