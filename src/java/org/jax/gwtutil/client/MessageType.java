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
