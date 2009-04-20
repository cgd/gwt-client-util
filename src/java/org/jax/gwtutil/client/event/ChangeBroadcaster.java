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
