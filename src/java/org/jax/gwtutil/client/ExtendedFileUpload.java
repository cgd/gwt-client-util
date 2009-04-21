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

import java.util.ArrayList;
import java.util.List;

import org.jax.gwtutil.client.event.ChangeBroadcaster;
import org.jax.gwtutil.client.event.ChangeListener;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A file upload extended to publish change events
 * TODO is there a smart way to have this use the ChangeHandler types instead?
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class ExtendedFileUpload
extends FileUpload
implements ChangeBroadcaster<ExtendedFileUpload>
{
    private List<ChangeListener<ExtendedFileUpload>> changeListeners = null;
    
    /**
     * Constructor
     */
    public ExtendedFileUpload()
    {
        super();
        this.sinkEvents(Event.ONCHANGE);
    }

    /**
     * Constructor for wrapping an existing element
     * @param element
     *          the element to wrap
     */
    protected ExtendedFileUpload(Element element)
    {
        super(element);
        this.sinkEvents(Event.ONCHANGE);
    }

    /**
     * Creates a {@link ExtendedFileUpload} widget that wraps an existing &lt;input
     * type='file'&gt; element.
     * 
     * This element must already be attached to the document. If the element is
     * removed from the document, you must call
     * {@link RootPanel#detachNow(Widget)}.
     * 
     * @param element the element to be wrapped
     * @return
     *          the wrapped {@link ExtendedFileUpload}
     */
    public static ExtendedFileUpload wrap(Element element)
    {
      // Assert that the element is attached.
      assert Document.get().getBody().isOrHasChild(element);

      ExtendedFileUpload fileUpload = new ExtendedFileUpload(element);

      // Mark it attached and remember it for cleanup.
      fileUpload.onAttach();
      RootPanel.detachOnWindowClose(fileUpload);

      return fileUpload;
    }
    
    /**
     * Add a change listener to this widget
     * @param changeListener
     *          the change listener
     */
    public void addChangeListener(
            ChangeListener<ExtendedFileUpload> changeListener)
    {
        if(this.changeListeners == null)
        {
            this.changeListeners =
                new ArrayList<ChangeListener<ExtendedFileUpload>>();
        }
        this.changeListeners.add(changeListener);
    }
    
    /**
     * Remove a change listener from this widget
     * @param changeListener
     *          the change listener
     */
    public void removeChangeListener(
            ChangeListener<ExtendedFileUpload> changeListener)
    {
        if(this.changeListeners != null)
        {
            this.changeListeners.remove(changeListener);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onBrowserEvent(Event event)
    {
        super.onBrowserEvent(event);
        if(event.getTypeInt() == Event.ONCHANGE && this.changeListeners != null)
        {
            for(ChangeListener<ExtendedFileUpload> listener: this.changeListeners)
            {
                listener.changeOccured(this);
            }
        }
    }
}
