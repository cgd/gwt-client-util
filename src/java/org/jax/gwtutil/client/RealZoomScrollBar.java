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

/**
 * For zoom/scrolls that use a 0.0-1.0 range
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public class RealZoomScrollBar extends ZoomScrollBar
{
    private double selectedRangeStart = 0.0;
    
    private double selectedRangeExtent = 0.0;
    
    private int selectedRangeExtentInPixels;
    
    private int selectedRangeStartInPixels;
    
    private volatile RealZoomScrollListener[] listeners =
        new RealZoomScrollListener[0];
    
    /**
     * Constructor
     */
    public RealZoomScrollBar()
    {
    }
    
    /**
     * Add the given zoom/scroll listener
     * @param listenerToAdd
     *          the listener to add
     */
    public void addRealZoomScrollListener(RealZoomScrollListener listenerToAdd)
    {
        RealZoomScrollListener[] listeners = this.listeners;
        RealZoomScrollListener[] newListeners =
            new RealZoomScrollListener[listeners.length + 1];
        
        for(int i = 0; i < listeners.length; i++)
        {
            newListeners[i] = listeners[i];
        }
        
        newListeners[newListeners.length - 1] = listenerToAdd;
        this.listeners = newListeners;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected int getSelectedRangeExtentInPixels()
    {
        return this.selectedRangeExtentInPixels;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected int getSelectedRangeStartInPixels()
    {
        return this.selectedRangeStartInPixels;
    }
    
    /**
     * @return the selectedRangeStart
     */
    public double getSelectedRangeStart()
    {
        return this.selectedRangeStart;
    }
    
    /**
     * @return the selectedRangeExtent
     */
    public double getSelectedRangeExtent()
    {
        return this.selectedRangeExtent;
    }
    
    /**
     * Update the selected range
     * @param selectedRangeStart
     *          the range start 0-1
     * @param selectedRangeExtent
     *          the range extent 0-1
     * @param zoomScrollComplete
     *          if zoom scroll was complete
     * @param fireEvent
     *          flag indicating whether an event should be fired
     */
    public void updateSelectedRange(
            double selectedRangeStart,
            double selectedRangeExtent,
            boolean zoomScrollComplete,
            boolean fireEvent)
    {
        this.selectedRangeStart = selectedRangeStart;
        this.selectedRangeExtent = selectedRangeExtent;
        
        int fullRangeStartInPixels = this.getFullRangeStartInPixels();
        int fullRangeExtentInPixels = this.getFullRangeExtentInPixels();
        
        this.selectedRangeStartInPixels =
            fullRangeStartInPixels +
            (int)(selectedRangeStart * fullRangeExtentInPixels);
        this.selectedRangeExtentInPixels =
            (int)(Math.round(selectedRangeExtent * fullRangeExtentInPixels));
        
        super.refreshScrollBar(zoomScrollComplete);
        
        if(fireEvent)
        {
            this.fireZoomScrollRangeUpdated(
                    selectedRangeStart,
                    selectedRangeExtent,
                    zoomScrollComplete);
        }
    }
    
    /**
     * Tell all of our listeners that there has been a change
     * @param selectedRangeStart
     *          the updated start
     * @param selectedRangeExtent
     *          the updated extent
     * @param zoomScrollComplete
     *          if zoom scroll was complete
     */
    private void fireZoomScrollRangeUpdated(
            double selectedRangeStart,
            double selectedRangeExtent,
            boolean zoomScrollComplete)
    {
        RealZoomScrollListener[] listeners = this.listeners;
        
        for(RealZoomScrollListener currListener: listeners)
        {
            currListener.zoomScrollRangeUpdated(
                    selectedRangeStart,
                    selectedRangeExtent,
                    zoomScrollComplete);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateSelectedRangeInPixels(
            int newSelectedRangeStartInPixels,
            int newSelectedRangeExtentInPixels,
            boolean zoomScrollComplete)
    {
        // Set the pixel ranges
        this.selectedRangeStartInPixels = newSelectedRangeStartInPixels;
        this.selectedRangeExtentInPixels = newSelectedRangeExtentInPixels;
        
        int fullRangeStartInPixels = this.getFullRangeStartInPixels();
        int fullRangeExtentInPixels = this.getFullRangeExtentInPixels();
        
        // Set the 0-1 range values
        this.selectedRangeStart =
            (newSelectedRangeStartInPixels - fullRangeStartInPixels) /
            (double)fullRangeExtentInPixels;
        this.selectedRangeExtent =
            newSelectedRangeExtentInPixels /
            (double)fullRangeExtentInPixels;
        
        // repaint the scroll bar to reflect the newly selected range
        this.refreshScrollBar(zoomScrollComplete);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void refreshScrollBar(boolean zoomScrollComplete)
    {
        this.updateSelectedRange(
                this.selectedRangeStart,
                this.selectedRangeExtent,
                zoomScrollComplete,
                true);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void zoomScrollComplete()
    {
        this.updateSelectedRange(
                this.selectedRangeStart,
                this.selectedRangeExtent,
                true,
                true);
    }
}
