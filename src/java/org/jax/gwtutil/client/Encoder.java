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
    
    private static native String encodeURIComponentImpl(String decodedURLComponent) /*-{
        return encodeURIComponent(decodedURLComponent);
    }-*/;
}
