/*
 * Copyright (c) 2010 The Jackson Laboratory
 * 
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jax.gwtutil.client;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;

/**
 * A class that basically gives you free list serialization if you know how
 * to do element serialization
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 * @param <T>
 *          the element type of the lists we can serialize
 */
public class JsonListSerializer<T> implements JsonObjectSerializer<List<T>>
{
    private final JsonObjectSerializer<T> elementSerializer;
    
    /**
     * Constructor
     * @param elementSerializer
     *          the element serializer to use
     */
    public JsonListSerializer(JsonObjectSerializer<T> elementSerializer)
    {
        this.elementSerializer = elementSerializer;
    }

    /**
     * {@inheritDoc}
     */
    public JSONValue toJsonValue(List<T> list)
    {
        int listSize = list.size();
        
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < listSize; i++)
        {
            jsonArray.set(i, this.elementSerializer.toJsonValue(list.get(i)));
        }
        
        return jsonArray;
    }
}
