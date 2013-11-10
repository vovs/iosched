package org.uamobile.droidconsched.io.model.events;

import org.uamobile.droidconsched.io.ServerRequest;
import org.uamobile.droidconsched.io.model.TrackResponse;
import org.uamobile.droidconsched.io.model.TracksResponse;

import java.io.IOException;

/**
 * Events.Tracks as modelled in the Google Developers API.
 *
 * This is an implementation which replaces the generated version which shipped with IOsched and
 * makes the code human more human readable and removed the dependence on additional Google libraries.
 */
public class Tracks
{
    public Tracks()
    {
    }

    public Get get(String eventId, String trackId)
            throws IOException
    {
        Get result = new Get(eventId, trackId);
        return result;
    }

    public List list(String eventId)
            throws IOException
    {
        List result = new List(eventId);
        return result;
    }

    public class List extends ServerRequest<TracksResponse>
    {
        private static final String REST_PATH = "events/{eventId}/tracks";

        private String eventId;

        protected List(String eventId)
        {
            super("GET", "events/{eventId}/tracks", null, TracksResponse.class);
            assert eventId != null;
            this.eventId = eventId;
        }

        public String getEventId()
        {
            return this.eventId;
        }

        public List setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
    }

    public class Get extends ServerRequest<TrackResponse>
    {
        private static final String REST_PATH = "events/{eventId}/tracks/{trackId}";

        private String eventId;

        private String trackId;

        protected Get(String eventId, String trackId)
        {
            super("GET", "events/{eventId}/tracks/{trackId}", null, TrackResponse.class);
            assert eventId != null;
            this.eventId = eventId;

            assert trackId != null;
            this.trackId = trackId;
        }

        public String getEventId()
        {
            return this.eventId;
        }

        public Get setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public String getTrackId()
        {
            return this.trackId;
        }

        public Get setTrackId(String trackId) {
            this.trackId = trackId;
            return this;
        }
    }
}
