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

package org.jax.gwtutil.client.event;

/**
 * Interface for class that broadcast action events
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 * @param <S> the source type
 */
public interface ActionBroadcaster<S>
{
    /**
     * Add the given listener to this action broadcaster
     * @param listener
     *          the listener to add
     */
    public void addActionListener(ActionListener<S> listener);
    
    /**
     * Removes the given listener from this action broadcaster
     * @param listener
     *          the listener to remove
     */
    public void removeActionListener(ActionListener<S> listener);
}
