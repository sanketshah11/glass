/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass.Plugins;

import glass.GUI;
import glass.Plugins.PluginInterface;
import static glass.Plugins.utils.cleanWorkDir;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class WaterLevelPlugin extends PluginInterface
{

public WaterLevelPlugin()
{
}

/**
 * @param args the command line arguments
 */
@Override
public String processFile(String path)
{

    //code to create/cleanup work directory 
    String output_folder = "work/WaterLevelPlugin/";
    cleanWorkDir(output_folder);
    try
    {
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\waterlevelConfigFiles\\waterlevel_high_config.txt"), new File(output_folder + "waterlevel_high_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\waterlevelConfigFiles\\waterlevel_low_config.txt"), new File(output_folder + "waterlevel_low_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\waterlevelConfigFiles\\waterlevel_trend_config.txt"), new File(output_folder + "waterlevel_trend_config.txt"));//copy config files to dest
        //FileUtils.
        List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
        
        double xValues[] = new double[lines.size()];
        double yValues1[] = new double[lines.size()];
        double yValues2[] = new double[lines.size()];
        double yValues3[] = new double[lines.size()];
        
        ArrayList<String> lines_out1 = new ArrayList<>();
        ArrayList<String> lines_out2 = new ArrayList<>();
        ArrayList<String> lines_out3 = new ArrayList<>();
        
        for (int i = 1; i < lines.size(); i++)
        {
            String[] line_cloumn = lines.get(i).split(",");
            xValues[i] = i;

            yValues1[i] = Double.parseDouble(line_cloumn[5]);
            yValues2[i] = Double.parseDouble(line_cloumn[6]);
            yValues3[i] = Double.parseDouble(line_cloumn[4]);

            lines_out1.add("" + xValues[i] + "," + yValues1[i]);
            lines_out2.add("" + xValues[i] + "," + yValues2[i]);
            lines_out3.add("" + xValues[i] + "," + yValues3[i]);
        }
        
        Files.write(Paths.get(output_folder + "waterlevel_high" + ".txt"), lines_out1, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "waterlevel_low" + ".txt"), lines_out2, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "waterlevel_trend" + ".txt"), lines_out3, Charset.defaultCharset());
        return output_folder;
    } catch (Exception ex)
    {
        Logger.getLogger(WaterLevelPlugin.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
        return "";
    }
}

}
