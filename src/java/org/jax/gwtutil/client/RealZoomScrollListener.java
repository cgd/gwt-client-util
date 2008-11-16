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

package org.jax.gwtutil.client;

/**
 * Interface that should be implemented to listen to
 * {@link RealZoomScrollBar}'s
 * @author <A HREF="mailto:keith.sheppard@jax.org">Keith Sheppard</A>
 */
public interface RealZoomScrollListener
{
    /**
     * Respond to an update in the scroll
     * @param selectedRangeStart
     *          the new selected range starting position. this should range
     *          from 0.0->1.0
     * @param selectedRangeExtent
     *          the new selected range extent. this should range from 0.0->1.0
     * @param zoomScrollComplete
     *          if zoom scroll was complete
     */
    public void zoomScrollRangeUpdated(
            double selectedRangeStart,
            double selectedRangeExtent,
            boolean zoomScrollComplete);
}
