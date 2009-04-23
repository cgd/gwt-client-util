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
