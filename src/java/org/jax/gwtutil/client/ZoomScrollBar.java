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

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
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
    private static final String BACK_PANEL_STYLE_NAME =
        BASE_STYLE_NAME + "-BackPanel";
    
    private static final String LEFT_SLIDER_IMAGE_URL = "images/left-zoom-handle.png";
    private static final String RIGHT_SLIDER_IMAGE_URL = "images/right-zoom-handle.png";
    
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
    private HandlerRegistration backgroundLoadReg;
    private HandlerRegistration backgroundErrorReg;
    
    private final LoadHandler resizeOnLoadListener = new LoadHandler()
    {
        /**
         * {@inheritDoc}
         */
        public void onLoad(LoadEvent event)
        {
            ZoomScrollBar.this.resizeScrollBar();
        }
    };
    
    private final ErrorHandler errorHandler = new ErrorHandler()
    {
        public void onError(ErrorEvent event)
        {
            System.err.println("Failed to load: " + event.getSource());
        }
    };
    
    private HandlerRegistration previewRegistration;

    private static NativePreviewHandler preventDefaultMouseEvents = new NativePreviewHandler()
    {
        /**
         * {@inheritDoc}
         */
        public void onPreviewNativeEvent(NativePreviewEvent event)
        {
            
            switch(event.getTypeInt())
            {
                case Event.ONMOUSEDOWN:
                case Event.ONMOUSEMOVE:
                    event.getNativeEvent().preventDefault();
            }
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
        this.absolutePositionsPanel.setStyleName(BACK_PANEL_STYLE_NAME);
        
        this.leftSliderImage = new Image(LEFT_SLIDER_IMAGE_URL);
        this.leftSliderImage.addLoadHandler(this.resizeOnLoadListener);
        this.leftSliderImage.addErrorHandler(this.errorHandler);
        this.leftSliderImage.setStyleName(SLIDER_LEFT_STYLE_NAME);
        this.absolutePositionsPanel.add(this.leftSliderImage);
        
        this.rightSliderImage = new Image(RIGHT_SLIDER_IMAGE_URL);
        this.rightSliderImage.addLoadHandler(this.resizeOnLoadListener);
        this.rightSliderImage.addErrorHandler(this.errorHandler);
        this.rightSliderImage.setStyleName(SLIDER_RIGHT_STYLE_NAME);
        this.absolutePositionsPanel.add(this.rightSliderImage);
        
        this.centerSliderWidget = new AbsolutePanel();
        this.centerSliderWidget.setStyleName(SLIDER_MIDDLE_STYLE_NAME);
        this.absolutePositionsPanel.add(this.centerSliderWidget);
        
        ScrollZoomMouseListener scrollZoomListener =
            new ScrollZoomMouseListener();
        this.mainFocusPanel.addMouseUpHandler(scrollZoomListener);
        this.mainFocusPanel.addMouseDownHandler(scrollZoomListener);
        this.mainFocusPanel.addMouseOverHandler(scrollZoomListener);
        this.mainFocusPanel.addMouseOutHandler(scrollZoomListener);
        this.mainFocusPanel.addMouseMoveHandler(scrollZoomListener);
    }
    
    /**
     * Resize the scroll bar to fit its contents
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
     * Returns the image width that shoudl be used for
     * {@link #setBackgroundImage(Image)} to achieve the desired
     * total zoom/scroll bar with. This accounts for the handle widgets that
     * are at either side of this widget
     * @param desiredScrollBarWidth
     *          the scroll bar width that we want to achieve
     * @return
     *          the image width that should be used
     */
    public int getImageWidthForScrollBarWidth(int desiredScrollBarWidth)
    {
        return (desiredScrollBarWidth - this.leftSliderWidth) - this.rightSliderWidth;
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
            this.backgroundLoadReg.removeHandler();
            this.backgroundErrorReg.removeHandler();
            this.absolutePositionsPanel.remove(this.backgroundImage);
        }
        
        this.backgroundImage = backgroundImage;
        
        if(this.backgroundImage != null)
        {
            this.backgroundLoadReg = this.backgroundImage.addLoadHandler(
                    this.resizeOnLoadListener);
            this.backgroundErrorReg = this.backgroundImage.addErrorHandler(
                    this.errorHandler);
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
    
    private class ScrollZoomMouseListener
    implements MouseUpHandler, MouseDownHandler, MouseOverHandler, MouseOutHandler, MouseMoveHandler
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
        public void onMouseUp(MouseUpEvent event)
        {
            if(this.dragType != DragType.NO_DRAG)
            {
                this.dragType = DragType.NO_DRAG;
                
                Widget widget = (Widget)event.getSource();
                DOM.releaseCapture(widget.getElement());
                ZoomScrollBar.this.zoomScrollComplete();
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onMouseDown(MouseDownEvent event)
        {
            Widget widget = (Widget)event.getSource();
            
            this.dragStartX = event.getX();
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
        public void onMouseOver(MouseOverEvent event)
        {
            ZoomScrollBar.this.previewRegistration =
                Event.addNativePreviewHandler(preventDefaultMouseEvents);
        }

        /**
         * {@inheritDoc}
         */
        public void onMouseOut(MouseOutEvent event)
        {
            if(ZoomScrollBar.this.previewRegistration != null)
            {
                ZoomScrollBar.this.previewRegistration.removeHandler();
            }
        }
        
        /**
         * {@inheritDoc}
         */
        public void onMouseMove(MouseMoveEvent event)
        {
            int deltaX = event.getX() - this.dragStartX;
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
    }

    /**
     * The zoom & scroll are complete
     */
    protected abstract void zoomScrollComplete();
}
