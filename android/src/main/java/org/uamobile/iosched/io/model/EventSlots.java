/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uamobile.iosched.io.model;

import org.uamobile.droidconsched.io.ServerArrayResponse;

import java.util.List;

public class EventSlots extends ServerArrayResponse<Day> {

    public List<Day> getDays()
    {
        return getArrayContents();
    }

    @Override
    protected String getArrayAttributeName() {
        return "day";
    }

    @Override
    protected Day getNewObject() {
        return new Day();
    }
}


