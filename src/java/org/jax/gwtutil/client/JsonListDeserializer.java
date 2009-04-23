/*
 * Copyright (c) 2008 The Jackson Laboratory
 *
 * Permission is hereby granted, free of charge, to any person obtaining  a copy
 * of this software and associated documentation files (the  "Software"), to
 * deal in the Software without restriction, including  without limitation the
 * rights to use, copy, modify, merge, publish,  distribute, sublicense, and/or
 * sell copies of the Software, and to  permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be  included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,  EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF  MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY  CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,  TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE  SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
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
