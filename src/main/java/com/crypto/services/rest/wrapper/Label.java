package com.crypto.services.rest.wrapper;

import java.awt.*;

/**
 * A series label and legend on the chart.
 * Contains series defaults.
 *
 * Created by nl04990 on 7-9-2015.
 */
// Label class
public class Label {

    private String label;

    private Boolean showMarker = false;

    // Point label property of jqplot defaults
    private class PointLabel {
        private Boolean show = false;
        private String location = "n";  // North
        private Integer yPadding = 3;

       public PointLabel (final Boolean show) {
           this.show = show;
       }

        public Boolean getShow() {
            return show;
        }

        public String getLocation() {
            return location;
        }

        public Integer getyPadding() {
            return yPadding;
        }
    }

    private PointLabel pointLabels;

    /**
     * Series values
     * @param label the label string (used in the legend)
     * @param showMarker show marker on data point
     */
    public Label(final String label, final Boolean showMarker) {

        this.label = label;
        this.showMarker = showMarker;
        this.pointLabels = new PointLabel(showMarker);
    }

    public String getLabel() {
        return this.label;
    }

    public Boolean getShowMarker() {
        return showMarker;
    }

    public PointLabel getPointLabels() {
        return pointLabels;
    }
}

