/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class PieChart extends JFrame
{

/**
 * Default constructor.
 *
 * @param title the frame title.
 */
public PieChart(String title, DefaultPieDataset dataset)
{
    super("");
    add(createPanel(dataset));

}

/**
 * Creates a chart.
 *
 * @param dataset the dataset.
 *
 * @return A chart.
 */
private static JFreeChart createChart(PieDataset dataset)
{

    JFreeChart chart = ChartFactory.createPieChart(
            "Pie Chart", // chart title
            dataset, // data
            true, // include legend
            true,
            false);

    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
    plot.setNoDataMessage("No data available");
    plot.setCircular(false);
    plot.setLabelGap(0.02);
    return chart;
}

/**
 * Creates a pane
 *
 * @return A panel.
 */
public static JPanel createPanel(DefaultPieDataset dataset)
{
    JFreeChart chart = createChart(dataset);
    return new ChartPanel(chart);
}

}
