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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A file upload extended to publish change events
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class ExtendedFileUpload extends FileUpload
{
    private List<ChangeListener> changeListeners = null;
    
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
    public void addChangeListener(ChangeListener changeListener)
    {
        if(this.changeListeners == null)
        {
            this.changeListeners = new ArrayList<ChangeListener>();
        }
        this.changeListeners.add(changeListener);
    }
    
    /**
     * Remove a change listener from this widget
     * @param changeListener
     *          the change listener
     */
    public void removeChangeListener(ChangeListener changeListener)
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
            for(ChangeListener listener: this.changeListeners)
            {
                listener.onChange(this);
            }
        }
    }
}
