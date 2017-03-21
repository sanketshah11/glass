/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.xy.XYSeries;


public class TextFileInterface {

    public double xValues[];
    public double yValues[];
    public String path = "";
    public int numberOfLines = 0;
    //config
    public String title = "";
    public String xLabel = "";
    public String yLabel = "";
    public String color = "auto";
    public String graphType = "line";
    public String tooltip = "";
    public Boolean plotLines = true;
    public Boolean setRange = false;
    public double yMin = 0;
    public double ymax = 0;
    //jfreechart
    XYSeries series = new XYSeries("tempName");

    public TextFileInterface(String path) {
        this.path = path;
        numberOfLines = getNumberOfLinesInFile(path);
        xValues = new double[numberOfLines];
        yValues = new double[numberOfLines];
    }

    boolean readFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
            for (int i = 0; i < numberOfLines; i++) {
                String[] line_cloumn = lines.get(i).split(",");
                xValues[i] = Double.parseDouble(line_cloumn[0]);
                yValues[i] = Double.parseDouble(line_cloumn[1]);
                series.add(xValues[i], yValues[i]);
            }
        } catch (IOException ex) {
            Logger.getLogger(TextFileInterface.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean readConfig() {
        String configPath = path.substring(0, path.length() - 4) + "_config.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(configPath), Charset.defaultCharset());
            
            for (int i = 0; i < lines.size(); i++) {
                
                String[] line_cloumn = lines.get(i).split("=");
                
                String subject = line_cloumn[0].trim();
                String value = line_cloumn[1].trim();
                
                if (subject.equalsIgnoreCase("title")) {
                    title = value;
                } else if (subject.equalsIgnoreCase("xLabel")) {
                    xLabel = value;
                } else if (subject.equalsIgnoreCase("yLabel")) {
                    yLabel = value;
                } else if (subject.equalsIgnoreCase("color")) {
                    color = value;
                } else if (subject.equalsIgnoreCase("graphType")) {
                    graphType = value;
                } else if (subject.equalsIgnoreCase("tooltip")) {
                    tooltip = value;
                } else if (subject.equalsIgnoreCase("plotLines")) {
                    plotLines = Boolean.parseBoolean(value);
                } else if (subject.equalsIgnoreCase("setRange")) {
                    setRange = Boolean.parseBoolean(value);
                } else if (subject.equalsIgnoreCase("yMin")) {
                    yMin = Double.parseDouble(value);
                } else if (subject.equalsIgnoreCase("ymax")) {
                    ymax = Double.parseDouble(value);
                } else {
                }//ignore, add to logging error}
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(TextFileInterface.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private int getNumberOfLinesInFile(String filename) {
        int lineCount = 0;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(filename));
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            is.close();
            lineCount = (count == 0 && !empty) ? 1 : count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineCount;
    }
}