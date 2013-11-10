package org.uamobile.droidconsched.io;

import org.uamobile.droidconsched.io.model.CheckIns;
import org.uamobile.droidconsched.io.model.Events;
import org.uamobile.droidconsched.io.model.Users;

/**
 * API for interacting with the server stored data.
 *
 * @author Al Sutton, Funky Android Ltd. (http://funkyandroid.com/)
 */
public class ConferenceAPI {

    public ConferenceAPI()
    {
    }

    public CheckIns checkIns()
    {
        return new CheckIns();
    }

    public Events events()
    {
        return new Events();
    }

    public Users users()
    {
        return new Users();
    }
}