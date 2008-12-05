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

import com.google.gwt.user.client.ui.Image;

/**
 * An enum which allows for different types of messages to the user
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public enum MessageType
{
    /**
     * To give the user important information that we want to emphasize
     */
    ALERT
    {
        @Override
        public String getIconUrl()
        {
            return "images/alert-16x16.png";
        }

        @Override
        public String getStyle()
        {
            return "jax-PlainMessage";
        }
    },
    
    /**
     * To give the user normal info... no emphasis needed
     */
    PLAIN
    {
        @Override
        public String getIconUrl()
        {
            return null;
        }

        @Override
        public String getStyle()
        {
            return "jax-PlainMessage";
        }
    },
    
    /**
     * tells the user an error ocurred
     */
    ERROR
    {
        @Override
        public String getIconUrl()
        {
            return "images/error-16x16.png";
        }

        @Override
        public String getStyle()
        {
            return "jax-ErrorMessage";
        }
    },
    
    /**
     * tells the user processing is OK
     */
    OK
    {
        @Override
        public String getIconUrl()
        {
            return "images/ok-16x16.png";
        }

        @Override
        public String getStyle()
        {
            return "jax-PlainMessage";
        }
    },
    
    /**
     * to tell the user that we're working on something
     */
    WORKING
    {
        @Override
        public String getIconUrl()
        {
            return "images/spinner-16x16.gif";
        }

        @Override
        public String getStyle()
        {
            return "jax-PlainMessage";
        }
    };
    
    /**
     * Get the icon for this message
     * @return
     *          the icon or null
     */
    public abstract String getIconUrl();
    
    /**
     * Get the style for this message
     * @return
     *          the style (can't be null)
     */
    public abstract String getStyle();
    
    /**
     * Prefetch the icon for this message type (if there's no icon
     * then do nothing)
     */
    public void prefetchIcon()
    {
        if(this.getIconUrl() != null)
        {
            Image.prefetch(this.getIconUrl());
        }
    }
    
    /**
     * A convenience method for prefetching all icons
     */
    public static void prefetchIcons()
    {
        for(MessageType messageType: MessageType.values())
        {
            messageType.prefetchIcon();
        }
    }
}
