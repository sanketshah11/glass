/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class ChartManager
{

public static Color colorArray[] = new Color[30];
public static Color backgroundColor = new Color(152, 175, 199);
static Color appDefaultBackgroung = new Color(152, 175, 199);

public static void initializeColorArray()
{
    colorArray[0] = Color.BLUE;
    colorArray[1] = Color.BLACK;
    colorArray[2] = Color.MAGENTA;
    colorArray[3] = Color.CYAN;
    colorArray[4] = Color.GREEN;
    colorArray[5] = Color.GRAY;
    colorArray[6] = Color.ORANGE;
    colorArray[7] = new Color(128, 0, 0);//brown
    colorArray[8] = new Color(0xFFFFB300); //Vivid Yellow
    colorArray[9] = new Color(0xFF803E75); //Strong Purple

    colorArray[10] = new Color(0xFFFF6800); //Vivid Orange
    colorArray[11] = new Color(0xFFA6BDD7); //Very Light Blue
    colorArray[12] = new Color(0xFFC10020); //Vivid Red
    colorArray[13] = new Color(0xFFCEA262); //Grayish Yellow
    colorArray[14] = new Color(0xFF007D34); //Vivid Green
    colorArray[15] = new Color(0xFFF6768E); //Strong Purplish Pink
    colorArray[16] = new Color(0xFF00538A); //Strong Blue
    colorArray[17] = new Color(0xFF53377A); //Strong Violet
    colorArray[18] = new Color(0xFFFF8E00); //Vivid Orange Yellow
    colorArray[19] = new Color(0xFFB32851); //Strong Purplish Red

    colorArray[20] = new Color(0xFFF4C800); //Vivid Greenish Yellow
    colorArray[21] = new Color(0xFF7F180D); //Strong Reddish Brown
    colorArray[22] = new Color(0xFF7F180D); //Strong Reddish Brown
    colorArray[23] = new Color(0xFF593315); //Deep Yellowish Brown
    colorArray[24] = new Color(0xFFF13A13); //Vivid Reddish Orange
    colorArray[25] = new Color(0xFF232C16); //Dark Olive Green
    colorArray[26] = new Color(0xFFB32851);
    colorArray[27] = Color.DARK_GRAY;
    colorArray[28] = Color.PINK;
    colorArray[29] = Color.BLACK;

}

public static void BuildChartPanel(XYSeriesCollection seriesCollection[],
                                   JFreeChart jFreeChart[], ChartPanel chartPanel[], int chartPanelCount)
{

    for (int i = 0; i < chartPanelCount; i++)
    {

        seriesCollection[i] = new XYSeriesCollection();

        jFreeChart[i] = ChartFactory.createXYLineChart(
                "", // chart title
                "", // x axis label
                "", // y axis label
                seriesCollection[i], // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );

        jFreeChart[i].setBackgroundPaint(backgroundColor);
        setCommonGraphDesign(jFreeChart[i]);
        chartPanel[i] = new ChartPanel(jFreeChart[i]);

    }
}

public static void setCommonGraphDesign(JFreeChart chart)
{

    //graph design
    XYPlot plot = chart.getXYPlot();

    plot.setBackgroundPaint(Color.WHITE);
    plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));

    plot.setDomainGridlinePaint(Color.GRAY);
    plot.setRangeGridlinePaint(Color.GRAY);
    plot.setDomainGridlinesVisible(false);
    plot.setRangeGridlinesVisible(false);

    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(true);

    plot.setDomainPannable(true);
    plot.setRangePannable(true);

    //y axis design
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    rangeAxis.setTickMarkPaint(Color.black);
    rangeAxis.setTickLabelPaint(Color.black);
    rangeAxis.setTickLabelFont(new java.awt.Font("Garamond", java.awt.Font.PLAIN, 10));
    rangeAxis.setAxisLinePaint(Color.black);

    rangeAxis.setLabelPaint(Color.black);
    rangeAxis.setLabelFont(new java.awt.Font("Garamond", java.awt.Font.PLAIN, 14));

    rangeAxis.setAutoRange(true);
    rangeAxis.setAutoRangeIncludesZero(false);
    rangeAxis.setAutoRangeStickyZero(false);
    rangeAxis.setAxisLineVisible(false);

    //x axis design
    NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();

    domainAxis.setTickMarkPaint(Color.black);
    domainAxis.setTickLabelPaint(Color.black);
    domainAxis.setTickLabelFont(new java.awt.Font("Garamond", java.awt.Font.BOLD, 10));
    domainAxis.setAxisLinePaint(Color.black);

    domainAxis.setLabelPaint(Color.black);
    domainAxis.setLabelFont(new java.awt.Font("Garamond", java.awt.Font.BOLD, 14));

    domainAxis.setAutoRange(true);
    domainAxis.setAutoRangeIncludesZero(false);
    domainAxis.setAutoRangeStickyZero(false);
    domainAxis.setAxisLineVisible(false);

}

public static void defaultAxisSettings(JFreeChart jFreeChart, XYSeriesCollection seriesCollection, TextFileInterface tfi)
{

    jFreeChart.setTitle(new org.jfree.chart.title.TextTitle(tfi.title, new java.awt.Font("Garamond", java.awt.Font.BOLD, 16)));
    jFreeChart.getTitle().setPaint(Color.WHITE);

    XYPlot plot = jFreeChart.getXYPlot();
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setLabel(tfi.yLabel);

    NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
    domainAxis.setLabel(tfi.xLabel);

    setSeriesMarkerShape(plot, seriesCollection, tfi.plotLines);
    setDefaultSeriesPaints(seriesCollection, plot, tfi);

}

public static void setSeriesMarkerShape(XYPlot plot, XYSeriesCollection seriesCollection, boolean lineVisible)
{

    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
    int count = seriesCollection.getSeriesCount();
    count = count - 1;
    renderer.setSeriesShapesVisible(count, true);
    renderer.setSeriesShape(count, new Ellipse2D.Double(-1.5, -1.5, 3, 3));
    renderer.setSeriesLinesVisible(count, lineVisible);
    if (!lineVisible)
    {
        renderer.setSeriesShape(count, new Ellipse2D.Double(-5, -5, 5, 5));
    }
}

public static void setDefaultSeriesPaints(XYSeriesCollection seriesCollection, XYPlot plot, TextFileInterface tfi)
{
    int count = seriesCollection.getSeriesCount() - 1;
    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();

    if (tfi.color.equalsIgnoreCase("auto"))
    {
        renderer.setSeriesPaint(count, colorArray[count]);
    }
    else if (tfi.color.equalsIgnoreCase("blue"))
    {
        renderer.setSeriesPaint(count, Color.BLUE);
    }
    else if (tfi.color.equalsIgnoreCase("red"))
    {
        renderer.setSeriesPaint(count, Color.red);
    }
    else if (tfi.color.equalsIgnoreCase("green"))
    {
        renderer.setSeriesPaint(count, Color.green);
    }
    else if (tfi.color.equalsIgnoreCase("black"))
    {
        renderer.setSeriesPaint(count, Color.black);
    }
    else if (tfi.color.equalsIgnoreCase("WHITE"))
    {
        renderer.setSeriesPaint(count, Color.WHITE);
    }
    else if (tfi.color.equalsIgnoreCase("YELLOW"))
    {
        renderer.setSeriesPaint(count, Color.YELLOW);
    }
    else if (tfi.color.equalsIgnoreCase("pink"))
    {
        renderer.setSeriesPaint(count, Color.pink);
    }
    else if (tfi.color.equalsIgnoreCase("gray"))
    {
        renderer.setSeriesPaint(count, Color.gray);
    }
    else if (tfi.color.equalsIgnoreCase("orange"))
    {
        renderer.setSeriesPaint(count, Color.orange);
    }
    else
    {
        renderer.setSeriesPaint(count, colorArray[count]);
    }

    if (tfi.setRange)
    {
        ValueMarker markerMin = new ValueMarker(tfi.yMin);  // position is the value on the axis
        markerMin.setPaint(Color.red);
        plot.addRangeMarker(markerMin);

        ValueMarker markerMax = new ValueMarker(tfi.ymax);  // position is the value on the axis
        markerMax.setPaint(Color.red);
        plot.addRangeMarker(markerMax);
    }
}

}
