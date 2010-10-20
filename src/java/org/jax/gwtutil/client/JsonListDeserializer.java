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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;

/**
 * A class that gives you free list deserialization if you know how to do
 * element deserialization
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 * @param <T>
 *          the element type of the lists that we know how to deserialize
 */
public class JsonListDeserializer<T> implements JsonObjectDeserializer<List<T>>
{
    private final JsonObjectDeserializer<T> elementDeserializer;
    
    /**
     * Constructor
     * @param elementDeserializer
     *          the element deserializer to use
     */
    public JsonListDeserializer(JsonObjectDeserializer<T> elementDeserializer)
    {
        this.elementDeserializer = elementDeserializer;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> fromJsonValue(JSONValue jsonValue)
            throws IllegalArgumentException
    {
        JSONArray jsonArray = jsonValue.isArray();
        if(jsonArray == null)
        {
            throw new IllegalArgumentException(
                    "Cannot deserialize the given JSON value because it is " +
                    "not an array");
        }
        else
        {
            int jsonArraySize = jsonArray.size();
            List<T> list = new ArrayList<T>(jsonArraySize);
            for(int i = 0; i < jsonArraySize; i++)
            {
                JSONValue currJsonVal = jsonArray.get(i);
                list.add(this.elementDeserializer.fromJsonValue(currJsonVal));
            }
            
            return list;
        }
    }
}
