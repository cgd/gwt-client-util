/*
 * Copyright (c) 2008 The Jackson Laboratory
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jax.gwtutil.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * JSON utilities
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class JsonUtil
{
    /**
     * Private constructor. Use static methods
     */
    private JsonUtil()
    {
        
    }
    
    /**
     * Convert the given json string into a list
     * @param jsonArrayString
     *          the json array string to convert
     * @return
     *          the list
     */
    public static List<String> toStringList(String jsonArrayString)
    {
        return new ArrayList<String>(Arrays.asList(toStringArray(jsonArrayString)));
    }
    
    /**
     * Convert the JSON string array into a Java string array
     * @param jsonArrayString
     *          the JSON string array
     * @return
     *          the Java strings
     */
    public static String[] toStringArray(String jsonArrayString)
    {
        JSONValue jsonStrains = JSONParser.parse(jsonArrayString);
        JSONArray jsonStrainsArray = jsonStrains.isArray();
        
        if(jsonStrainsArray != null)
        {
            final String[] stringArray = new String[jsonStrainsArray.size()];
            for(int i = 0; i < stringArray.length; i++)
            {
                JSONString currString = jsonStrainsArray.get(i).isString();
                if(currString != null)
                {
                    stringArray[i] = currString.stringValue();
                }
                else
                {
                    return null;
                }
            }
            
            return stringArray;
        }
        else
        {
            return null;
        }
    }
    
//    public static Map<String, Object> toDataMap(String jsonDataStructureString)
//    {
//        JSONValue jsonValue = JSONParser.parse(jsonDataStructureString);
//        JSONObject jsonDataStructure = jsonValue.isObject();
//        if(jsonDataStructure != null)
//        {
//            Set<String> keys = jsonDataStructure.keySet();
//        }
//        else
//        {
//            return null;
//        }
//    }
}
