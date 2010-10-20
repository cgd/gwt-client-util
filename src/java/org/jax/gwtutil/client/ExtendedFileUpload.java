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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A file upload extended to publish change events
 * TODO is there a smart way to have this use the ChangeHandler types instead?
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class ExtendedFileUpload extends FileUpload
{
    /**
     * Constructor
     */
    public ExtendedFileUpload()
    {
        super();
    }

    /**
     * protected constructor for wrapping an existing element
     * @param element
     *          the element to wrap
     */
    protected ExtendedFileUpload(Element element)
    {
        super(element);
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
     * Add a change handler to this file upload so that we are notified when
     * the user selects a file.
     * @param changeHandler
     *          the change handler to register
     * @return
     *          the registration
     */
    public HandlerRegistration addChangeHandler(ChangeHandler changeHandler)
    {
        return this.addDomHandler(changeHandler, ChangeEvent.getType());
    }
}
