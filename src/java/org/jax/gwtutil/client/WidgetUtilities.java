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

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTMLTable;
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
}
