/*
 * Copyright (c) 2008 The Jackson Laboratory
 * 
 * This software was developed by Gary Churchill's Lab at The Jackson
 * Laboratory (see http://research.jax.org/faculty/churchill).
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

/**
 * A class for encoding strings
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class Encoder
{
    /**
     * Convert the list into a string that can be used as a part
     * of a URL (an encoded comma-separated string)
     * @param list
     *          this list
     * @return
     *          the partial URL
     */
    public static String listToURIComponent(List<?> list)
    {
        StringBuffer sb = new StringBuffer();
        int listSize = list.size();
        for(int i = 0; i < listSize; i++)
        {
            if(i >= 1)
            {
                sb.append(',');
            }
            sb.append(list.get(i).toString());
        }
        return Encoder.encodeURIComponent(sb.toString());
    }
    
    /**
     * Like {@link com.google.gwt.http.client.URL#encodeComponent(String)}
     * except we use "%20" for spaces
     * @param uriString
     *          the string to encode
     * @return
     *          the encoded component
     */
    public static String encodeURIComponent(String uriString)
    {
        return encodeURIComponentImpl(uriString);
    }
    
    /**
     * Native JavaScript function to uncode the given URL component
     * @param decodedURLComponent
     *          the URL component to encode
     * @return
     *          the URL component
     */
    private static native String encodeURIComponentImpl(String decodedURLComponent) /*-{
        return encodeURIComponent(decodedURLComponent);
    }-*/;
}
