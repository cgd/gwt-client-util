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

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utilities to manipulate GWT widgets.
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class WidgetUtilities
{
    /**
     * A convenience function for getting the selected item text
     * @param listBox
     *          the list box to get the text from
     * @return
     *          the text or null if nothing is selected
     */
    public static String getSelectedItem(ListBox listBox)
    {
        int selectedIndex = listBox.getSelectedIndex();
        if(selectedIndex >= 0)
        {
            return listBox.getItemText(selectedIndex);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Update the list box using the given entries
     * @param newEntries
     *          the entries
     * @param listBox
     *          the listbox to update
     * @return
     *          true on success
     */
    public static boolean updateListBoxEntries(
            String[] newEntries,
            ListBox listBox)
    {
        return updateListBoxEntries(Arrays.asList(newEntries), listBox);
    }
    
    /**
     * Update the list box using the given entries
     * @param newEntries
     *          the entries
     * @param listBox
     *          the listbox to update
     * @return
     *          true on success
     */
    public static boolean updateListBoxEntries(
            List<String> newEntries,
            ListBox listBox)
    {
        // check if the lists already match
        int newEntryCount = newEntries.size();
        int currEntryCount = listBox.getItemCount();
        boolean anyMissmatch = newEntryCount != currEntryCount;
        if(!anyMissmatch)
        {
            for(int i = 0; i < newEntryCount && !anyMissmatch; i++)
            {
                if(newEntries.get(i).equals(listBox.getItemText(i)))
                {
                    anyMissmatch = true;
                }
            }
        }
        
        // don't do anything unless we have to
        if(anyMissmatch)
        {
            listBox.clear();
            for(int i = 0; i < newEntryCount; i++)
            {
                listBox.addItem(newEntries.get(i));
            }
        }
        
        return anyMissmatch;
    }

    /**
     * Get the row index of the widget
     * @param widget
     *      the widget
     * @param table
     *      the table
     * @return
     *      the row index or -1 if the widget isn't in the table
     */
    public static int getRowIndexOf(Widget widget, HTMLTable table)
    {
        int rowCount = table.getRowCount();
        for(int row = 0; row < rowCount; row++)
        {
            int cellCount = table.getCellCount(row);
            for(int column = 0; column < cellCount; column++)
            {
                if(table.getWidget(row, column) == widget)
                {
                    return row;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Create a message
     * @param messageType
     *          the type of message
     * @param message
     *          the message text
     * @return
     *          the message
     */
    public static Widget createMessage(MessageType messageType, String message)
    {
        return createMessage(messageType, new InlineLabel(message));
    }
    
    /**
     * Create a message
     * @param messageType
     *          the type of message
     * @param messageWidget
     *          the message widget (usually inline label or inline HTML)
     * @return
     *          the message
     */
    public static Widget createMessage(MessageType messageType, Widget messageWidget)
    {
        FlowPanel panel = new FlowPanel();
        panel.setStyleName("jax-MessagePanel");
        
        if(messageType.getIconUrl() != null)
        {
            Image icon = new Image(messageType.getIconUrl());
            icon.addStyleName(messageType.getStyle());
            panel.add(icon);
        }
        
        messageWidget.addStyleName(messageType.getStyle());
        panel.add(messageWidget);
        
        return panel;
    }
}
