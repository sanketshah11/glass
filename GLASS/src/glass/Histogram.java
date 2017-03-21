/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeriesCollection;

public class Histogram
{

boolean[] isHistogramWindowOpen;
JFreeChart[] histogramChart;
JFrame[] histogramFrame;
//static boolean[][] availableInZoomArea;
int[] histogramFrameState, histogramFrameXPos, histogramFrameYPos;

public Histogram(final int plotno,
                 ArrayList<ArrayList<String>> legendList,
                 int chartPanelCount,
                 HistogramDataset[] histDataset,
                 XYSeriesCollection seriesCollection[],
                 double[][] min,
                 double[][] max,
                 double[][] sum,
                 double[][] mean,
                 double[][] std_dev)
{

    isHistogramWindowOpen = new boolean[chartPanelCount];
    histogramChart = new JFreeChart[chartPanelCount];
    histogramFrame = new JFrame[chartPanelCount];
    histogramFrameState = new int[chartPanelCount];
    histogramFrameXPos = new int[chartPanelCount];
    histogramFrameYPos = new int[chartPanelCount];
        //availableInZoomArea = new boolean[n][];

    for (int i = 0; i < chartPanelCount; i++)
    {

        histogramFrameState[i] = -1;
        histogramFrameXPos[i] = -1;
        histogramFrameYPos[i] = -1;
    }

    histogramFrame[plotno] = new JFrame();
    if (isHistogramWindowOpen[plotno] == true)
    {
        histogramFrame[plotno].setState(histogramFrameState[plotno]);
        histogramFrame[plotno].setLocation(histogramFrameXPos[plotno], histogramFrameYPos[plotno]);
    }

    histogramFrame[plotno].addWindowListener(new WindowListener()
    {
    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        isHistogramWindowOpen[plotno] = false;
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    });
    int histSeriesCount = histDataset[plotno].getSeriesCount();
    int numberOfPlots = seriesCollection[plotno].getSeriesCount();
    String[] legendNames = new String[histSeriesCount];

    for (int i = 0; i < numberOfPlots; i++)
    {
        legendNames[i] = legendList.get(plotno).get(i);
    }

    JPanel hist_panel = new JPanel();
    JPanel statisticsDataPanel = new JPanel();
    JPanel statisticsSelectionPanel = new JPanel();

    JScrollPane statisticsScrollPane = new JScrollPane(statisticsSelectionPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    GridLayout experimentLayout = new GridLayout(0, 1);
    statisticsDataPanel.setLayout(experimentLayout);

    if (histSeriesCount > 0)
    {
        GridLayout dataLayout = new GridLayout(0, histSeriesCount);
        statisticsSelectionPanel.setLayout(dataLayout);
    }
    JLabel titleLabel = new JLabel();
    statisticsDataPanel.add(titleLabel);

    JLabel meanLabel = new JLabel("Mean");
    statisticsDataPanel.add(meanLabel);

    JLabel std_devLabel = new JLabel("Std_Deviation");
    statisticsDataPanel.add(std_devLabel);

    JLabel minLabel = new JLabel("Min");
    statisticsDataPanel.add(minLabel);

    JLabel maxLabel = new JLabel("Max");
    statisticsDataPanel.add(maxLabel);
    int widthOfEachRow = 75;

    for (int i = 0; i < histSeriesCount; i++)
    {
        JTextField titleText = new JTextField();
        titleText.setEditable(false);
        String titleString = legendNames[i];
        titleText.setText(titleString);
        titleText.setFont(new Font("Thoma", Font.PLAIN, 10));
        statisticsSelectionPanel.add(titleText);
    }

    for (int i = 0; i < histSeriesCount; i++)
    {
        JTextField meanText = new JTextField();
        meanText.setEditable(false);
        String meanString = String.format("%.3f", mean[plotno][i]);
        meanText.setText(meanString);
        meanText.setFont(new Font("Thoma", Font.PLAIN, 10));
        statisticsSelectionPanel.add(meanText);
    }
    for (int i = 0; i < histSeriesCount; i++)
    {
        JTextField std_devText = new JTextField();
        std_devText.setEditable(false);
        String std_devString = String.format("%.3f", std_dev[plotno][i]);
        std_devText.setText(std_devString);
        std_devText.setFont(new Font("Thoma", Font.PLAIN, 10));
        statisticsSelectionPanel.add(std_devText);
    }
    for (int i = 0; i < histSeriesCount; i++)
    {
        JTextField minText = new JTextField();
        minText.setEditable(false);
        String minString = Double.toString(min[plotno][i]);
        minText.setText(minString);
        minText.setFont(new Font("Thoma", Font.PLAIN, 10));
        statisticsSelectionPanel.add(minText);
    }
    for (int i = 0; i < histSeriesCount; i++)
    {
        JTextField maxText = new JTextField();
        maxText.setEditable(false);
        String maxString = Double.toString(max[plotno][i]);
        maxText.setText(maxString);
        maxText.setFont(new Font("Thoma", Font.PLAIN, 10));
        statisticsSelectionPanel.add(maxText);
    }

    histogramFrame[plotno].add(statisticsDataPanel);
    histogramFrame[plotno].add(statisticsScrollPane);

    HistogramDataset histogramDataset = new HistogramDataset();
    histogramDataset.setType(HistogramType.FREQUENCY);

    histogramChart[plotno] = ChartFactory.createHistogram(" Histogram", null, null, histDataset[plotno], PlotOrientation.VERTICAL, true, true, false);
    ChartPanel chartPanel = new ChartPanel(histogramChart[plotno]);
    chartPanel.setBorder(new LineBorder(Color.gray));
    Color backgroundColor = new Color(0x98AFC7);
    histogramChart[plotno].setBackgroundPaint(backgroundColor);

    XYPlot histPlot = (XYPlot) histogramChart[plotno].getPlot();
    histPlot.setBackgroundPaint(Color.WHITE);
    histPlot.setDomainGridlinePaint(Color.BLACK);
    histPlot.setRangeGridlinePaint(Color.BLACK);

    XYBarRenderer barRenderer = (XYBarRenderer) histPlot.getRenderer();
    barRenderer.setDrawBarOutline(false);

    barRenderer.setBarPainter(new StandardXYBarPainter());
    //barRenderer.setShadowVisible(false);
    barRenderer.setMargin(0.2);

    int seriesCount = seriesCollection[plotno].getSeriesCount();

    int histSeries = 0;
    for (int i = 0; i < seriesCount; i++)
    {

             //histPlot.getRenderer().setSeriesPaint(histSeries, seriesCollection[i].getRenderer().getSeriesPaint(i));
        //histSeries++;
    }
    histPlot.mapDatasetToRangeAxis(1, 0);
    hist_panel.add(chartPanel);
    hist_panel.setBounds(100, 100, 400, 200);
    hist_panel.setBackground(Color.white);
    histogramFrame[plotno].add(hist_panel);
    statisticsDataPanel.setBounds(5, 430, 100, 120);
    statisticsDataPanel.setBackground(backgroundColor);
    statisticsSelectionPanel.setBounds(105, 430, (widthOfEachRow * histSeriesCount), 120);
    statisticsScrollPane.setBounds(105, 430, 580, 140);

    histogramFrame[plotno].setSize(700, 620);
    histogramFrame[plotno].setVisible(true);
    histogramFrame[plotno].setTitle("Histogram");
    histogramFrame[plotno].setBackground(Color.white);
    isHistogramWindowOpen[plotno] = true;
    JFrame.setDefaultLookAndFeelDecorated(true);
    histogramFrame[plotno].setIconImage(Toolkit.getDefaultToolkit().getImage("statistics.png"));
}

}
