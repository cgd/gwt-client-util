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
 * Interface for classes that broadcast change events to
 * {@link ChangeListener}s
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 * @param <S>
 *          the event source type
 */
public interface ChangeBroadcaster<S>
{
    /**
     * Add the given listener to the notification list
     * @param changeListener
     *          the change listener to add
     */
    public void addChangeListener(ChangeListener<S> changeListener);
    
    /**
     * Remove the given change listener from the notification list
     * @param changeListener
     *          the change listener to remove
     */
    public void removeChangeListener(ChangeListener<S> changeListener);
}
