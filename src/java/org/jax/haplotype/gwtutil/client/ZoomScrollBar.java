/*
 * Copyright (c) 2008 The Jackson Laboratory
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jax.haplotype.gwtutil.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for zoom-scroll bars
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public abstract class ZoomScrollBar extends Composite
{
    private static final String BASE_STYLE_NAME = "jax-ZoomScrollBar";
    private static final String SLIDER_RIGHT_STYLE_NAME =
        BASE_STYLE_NAME + "-SliderRight";
    private static final String SLIDER_LEFT_STYLE_NAME =
        BASE_STYLE_NAME + "-SliderLeft";
    private static final String SLIDER_MIDDLE_STYLE_NAME =
        BASE_STYLE_NAME + "-SliderCenter";
    
    private int widthPixles = 0;

    private final AbsolutePanel absolutePositionsPanel;
    private final FocusPanel mainFocusPanel;
    
    private final Image leftSliderImage;
    private final Image rightSliderImage;
    private final Widget centerSliderWidget;
    
    private int leftSliderWidth = 0;
    private int rightSliderWidth = 0;
    private int maximumImageHeight = 0;
    private int backgroundImageWidth = 0;
    
    private Image backgroundImage;
    
    private final LoadListener resizeOnLoadListener = new LoadListener()
    {
        /**
         * {@inheritDoc}
         */
        public void onError(Widget sender)
        {
        }
        
        /**
         * {@inheritDoc}
         */
        public void onLoad(Widget sender)
        {
            ZoomScrollBar.this.resizeScrollBar();
        }
    };

    private static EventPreview preventDefaultMouseEvents = new EventPreview()
    {
        public boolean onEventPreview(Event event)
        {
            switch(DOM.eventGetType(event))
            {
                case Event.ONMOUSEDOWN:
                case Event.ONMOUSEMOVE:
                    DOM.eventPreventDefault(event);
            }
            return true;
        }
    };

    /**
     * Construct a new zoom/scroll bar.
     */
    public ZoomScrollBar()
    {
        this.mainFocusPanel = new FocusPanel();
        this.initWidget(this.mainFocusPanel);
        
        this.absolutePositionsPanel = new AbsolutePanel();
        this.mainFocusPanel.setWidget(this.absolutePositionsPanel);
        
        this.leftSliderImage = new Image("left-zoom-handle.png");
        this.leftSliderImage.addLoadListener(this.resizeOnLoadListener);
        this.leftSliderImage.setStyleName(SLIDER_LEFT_STYLE_NAME);
        this.absolutePositionsPanel.add(this.leftSliderImage);
        
        this.rightSliderImage = new Image("right-zoom-handle.png");
        this.rightSliderImage.addLoadListener(this.resizeOnLoadListener);
        this.rightSliderImage.setStyleName(SLIDER_RIGHT_STYLE_NAME);
        this.absolutePositionsPanel.add(this.rightSliderImage);
        
        this.centerSliderWidget = new AbsolutePanel();
        this.centerSliderWidget.setStyleName(SLIDER_MIDDLE_STYLE_NAME);
        this.absolutePositionsPanel.add(this.centerSliderWidget);
        
        this.mainFocusPanel.addMouseListener(new ScrollZoomMouseListener());
    }
    
    /**
     * 
     */
    private void resizeScrollBar()
    {
        this.leftSliderWidth = this.leftSliderImage.getOffsetWidth();
        this.rightSliderWidth = this.rightSliderImage.getOffsetWidth();
        this.backgroundImageWidth = this.backgroundImage.getOffsetWidth();
        
        this.maximumImageHeight = Math.max(
                this.leftSliderImage.getOffsetHeight(),
                this.rightSliderImage.getOffsetHeight());
        this.maximumImageHeight = Math.max(
                this.maximumImageHeight,
                this.backgroundImage.getOffsetHeight());
        
        this.setPixelSize(
                this.leftSliderWidth + this.rightSliderWidth + this.backgroundImageWidth,
                this.maximumImageHeight);
    }

    /**
     * Getter for the background image
     * @return the background image
     */
    public Image getBackgroundImage()
    {
        return this.backgroundImage;
    }
    
    /**
     * Setter for the background image
     * @param backgroundImage
     *          the background image to set
     */
    public synchronized void setBackgroundImage(Image backgroundImage)
    {
        if(this.backgroundImage != null)
        {
            this.backgroundImage.removeLoadListener(
                    this.resizeOnLoadListener);
            this.absolutePositionsPanel.remove(this.backgroundImage);
        }
        
        this.backgroundImage = backgroundImage;
        
        if(this.backgroundImage != null)
        {
            this.backgroundImage.addLoadListener(
                    this.resizeOnLoadListener);
            this.absolutePositionsPanel.add(this.backgroundImage);
        }
        
        this.resizeScrollBar();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPixelSize(int width, int height)
    {
        super.setPixelSize(width, height);
        this.absolutePositionsPanel.setPixelSize(width, height);
        this.widthPixles = width;
        
        if(this.backgroundImage != null)
        {
            this.absolutePositionsPanel.setWidgetPosition(
                    this.backgroundImage,
                    this.leftSliderWidth,
                    0);
//                    this.absolutePositionsPanel.getWidgetTop(this.backgroundImage));
        }
        
        this.refreshScrollBar(true);
    }
    
    /**
     * Get the full range start in pixel units
     * @return
     *          the full range start in pixels
     */
    protected int getFullRangeStartInPixels()
    {
        return this.leftSliderImage.getOffsetWidth();
    }
    
    /**
     * Get the full range extent in pixels
     * @return
     *          the full range extent in pixels
     */
    protected int getFullRangeExtentInPixels()
    {
        return (this.widthPixles - this.leftSliderImage.getOffsetWidth()) -
               this.rightSliderImage.getOffsetWidth();
    }
    
    /**
     * Get's the left boundary pixel position of the scroll bar
     * @return
     *          the left boundary
     */
    protected abstract int getSelectedRangeStartInPixels();
    
    /**
     * Gets the width of the scroll bar in pixels.
     * @return
     *          the width
     */
    protected abstract int getSelectedRangeExtentInPixels();
    
    /**
     * Get the end in pixels.
     * overlap
     * @return
     *          the selected range end in pixels
     */
    private int getSelectedRangeEndInPixels()
    {
        return this.getSelectedRangeStartInPixels() +
               this.getSelectedRangeExtentInPixels();
    }
    
    /**
     * Update internal information based on a scroll bar drag
     * @param newSelectedRangeStartInPixels
     *          the new left boundary in pixels
     * @param newSelectedRangeExtentInPixels
     *          the new width in pixels
     * @param zoomScrollComplete
     *          if zoom scroll was complete
     */
    protected abstract void updateSelectedRangeInPixels(
            int newSelectedRangeStartInPixels,
            int newSelectedRangeExtentInPixels,
            boolean zoomScrollComplete);
    
    /**
     * Refresh the scroll bar position
     * @param zoomScrollComplete
     *          if zoom scroll was complete
     */
    protected void refreshScrollBar(boolean zoomScrollComplete)
    {
        this.absolutePositionsPanel.setWidgetPosition(
                this.leftSliderImage,
                this.getSelectedRangeStartInPixels() - this.leftSliderWidth,
                (int)(this.maximumImageHeight / 2.0 - this.leftSliderImage.getOffsetHeight() / 2.0));
        this.absolutePositionsPanel.setWidgetPosition(
                this.rightSliderImage,
                this.getSelectedRangeEndInPixels(),
                (int)(this.maximumImageHeight / 2.0 - this.rightSliderImage.getOffsetHeight() / 2.0));
        
        this.centerSliderWidget.setPixelSize(
                this.getSelectedRangeExtentInPixels(),
                this.maximumImageHeight);
        this.absolutePositionsPanel.setWidgetPosition(
                this.centerSliderWidget,
                this.getSelectedRangeStartInPixels(),
                0);
    }
    
    private enum DragType
    {
        /**
         * enum for left zoom handle
         */
        LEFT_ZOOM_HANDLE,
        
        /**
         * enum for right zoom handle
         */
        RIGHT_ZOOM_HANDLE,
        
        /**
         * enum for slider handle
         */
        SLIDER_HANDLE,
        
        /**
         * enum for no dragging
         */
        NO_DRAG
    }
    
    private class ScrollZoomMouseListener implements MouseListener
    {
        /**
         * The starting drag position
         */
        private int dragStartX;
        
        private DragType dragType = DragType.NO_DRAG;

        private int dragStartSelectedRangeExtent;

        private int dragStartSelectedRangeStart;
        
        private int dragStartSelectedRangeEnd;
        
        private int dragStartFullRangeExtent;
        
        private int dragStartFullRangeStart;
        
        private int dragStartFullRangeEnd;
        
        /**
         * {@inheritDoc}
         */
        public void onMouseDown(Widget widget, int x, int y)
        {
            this.dragStartX = x;
            this.dragStartSelectedRangeExtent =
                ZoomScrollBar.this.getSelectedRangeExtentInPixels();
            this.dragStartSelectedRangeStart =
                ZoomScrollBar.this.getSelectedRangeStartInPixels();
            this.dragStartSelectedRangeEnd =
                this.dragStartSelectedRangeStart + this.dragStartSelectedRangeExtent;
            this.dragStartFullRangeExtent =
                ZoomScrollBar.this.getFullRangeExtentInPixels();
            this.dragStartFullRangeStart =
                ZoomScrollBar.this.getFullRangeStartInPixels();
            this.dragStartFullRangeEnd =
                this.dragStartFullRangeStart + this.dragStartFullRangeExtent;
            
            int dragStartingLeftZoomX = ZoomScrollBar.this.getSelectedRangeStartInPixels();
            int dragStartingRightZoomX =
                dragStartingLeftZoomX +
                ZoomScrollBar.this.getSelectedRangeExtentInPixels();
            
            if(dragStartingLeftZoomX - this.dragStartX <= ZoomScrollBar.this.leftSliderWidth &&
               dragStartingLeftZoomX - this.dragStartX >= 0)
            {
                this.dragType = DragType.LEFT_ZOOM_HANDLE;
                DOM.setCapture(widget.getElement());
            }
            else if(this.dragStartX - dragStartingRightZoomX <= ZoomScrollBar.this.rightSliderWidth &&
                    this.dragStartX - dragStartingRightZoomX >= 0)
            {
                this.dragType = DragType.RIGHT_ZOOM_HANDLE;
                DOM.setCapture(widget.getElement());
            }
            else if(this.dragStartX >= dragStartingLeftZoomX &&
                    this.dragStartX <= dragStartingRightZoomX)
            {
                this.dragType = DragType.SLIDER_HANDLE;
                DOM.setCapture(widget.getElement());
            }
            else
            {
                this.dragType = DragType.NO_DRAG;
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onMouseMove(Widget widget, int x, int y)
        {
            int deltaX = x - this.dragStartX;
            switch(this.dragType)
            {
                case LEFT_ZOOM_HANDLE:
                {
                    this.dragLeftZoomHandle(deltaX);
                }
                break;
                
                case RIGHT_ZOOM_HANDLE:
                {
                    this.dragRightZoomHandle(deltaX);
                }
                break;
                
                case SLIDER_HANDLE:
                {
                    this.dragSliderHandle(deltaX);
                }
                break;
                
                case NO_DRAG:
                {
                    // no-op
                }
                break;
                
                default:
                {
                    System.err.println("unknown drag type: " + this.dragType);
                }
                break;
            }
        }

        /**
         * @param deltaX
         */
        private void dragSliderHandle(int deltaX)
        {
            int newSelectedRangeStart =
                this.dragStartSelectedRangeStart + deltaX;
            int newSelectedRangeEnd =
                this.dragStartSelectedRangeEnd + deltaX;
            
            // bound the drag
            if(newSelectedRangeStart < this.dragStartFullRangeStart)
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartFullRangeStart,
                        this.dragStartSelectedRangeExtent,
                        false);
            }
            else if(newSelectedRangeEnd > this.dragStartFullRangeEnd)
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartFullRangeEnd - this.dragStartSelectedRangeExtent,
                        this.dragStartSelectedRangeExtent,
                        false);
            }
            else
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        newSelectedRangeStart,
                        this.dragStartSelectedRangeExtent,
                        false);
            }
        }

        private void dragRightZoomHandle(int deltaX)
        {
            int newSelectedRangeEnd =
                this.dragStartSelectedRangeEnd + deltaX;
            
            // bound the drag
            if(newSelectedRangeEnd < this.dragStartSelectedRangeStart)
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartSelectedRangeStart,
                        0,
                        false);
            }
            else if(newSelectedRangeEnd > this.dragStartFullRangeEnd)
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartSelectedRangeStart,
                        this.dragStartFullRangeEnd - this.dragStartSelectedRangeStart,
                        false);
            }
            else
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartSelectedRangeStart,
                        this.dragStartSelectedRangeExtent + deltaX,
                        false);
            }
        }

        private void dragLeftZoomHandle(int deltaX)
        {
            int newSelectedRangeStart =
                this.dragStartSelectedRangeStart + deltaX;
            
            // bound the drag
            if(newSelectedRangeStart < this.dragStartFullRangeStart)
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartFullRangeStart,
                        this.dragStartSelectedRangeEnd - this.dragStartFullRangeStart,
                        false);
            }
            else if(newSelectedRangeStart > this.dragStartSelectedRangeEnd)
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        this.dragStartSelectedRangeEnd,
                        0,
                        false);
            }
            else
            {
                ZoomScrollBar.this.updateSelectedRangeInPixels(
                        newSelectedRangeStart,
                        this.dragStartSelectedRangeExtent - deltaX,
                        false);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onMouseUp(Widget widget, int x, int y)
        {
            if(this.dragType != DragType.NO_DRAG)
            {
                this.dragType = DragType.NO_DRAG;
                DOM.releaseCapture(widget.getElement());
                ZoomScrollBar.this.zoomScrollComplete();
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onMouseEnter(Widget sender)
        {
            DOM.addEventPreview(preventDefaultMouseEvents);
        }

        /**
         * {@inheritDoc}
         */
        public void onMouseLeave(Widget sender)
        {
            DOM.removeEventPreview(preventDefaultMouseEvents);
        }
    }

    /**
     * The zoom & scroll are complete
     */
    protected abstract void zoomScrollComplete();
}
