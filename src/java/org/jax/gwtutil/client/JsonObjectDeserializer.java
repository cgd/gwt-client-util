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

import com.google.gwt.json.client.JSONValue;

/**
 * For deserializing JSON values
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 * @param <T>
 *          the type of object this derserializes to
 */
public interface JsonObjectDeserializer<T>
{
    /**
     * Convert the given JSON string into an object
     * @param jsonValue
     *          the JSON value
     * @return
     *          the java object
     * @throws IllegalArgumentException
     *          if the JSON value is a bad match for the object structure that
     *          we want to see
     */
    public T fromJsonValue(JSONValue jsonValue) throws IllegalArgumentException;
}
