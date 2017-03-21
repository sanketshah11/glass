/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glass.Plugins;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import glass.GUI;
import static glass.Plugins.utils.cleanWorkDir;
import java.io.File;
import java.io.IOException;
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

public class ServerLogPlugin extends PluginInterface
{

public ServerLogPlugin()
{

}

/**
 * @param args the command line arguments
 */
@Override
public String processFile(String path)
{

    //code to create/cleanup work directory 
    String output_folder = "work/ServerLogPlugin/";
    cleanWorkDir(output_folder);

    try
    {
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\ServerLogConfigFiles\\temperature_config.txt"), new File(output_folder + "temperature_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\ServerLogConfigFiles\\cpu_config.txt"), new File(output_folder + "cpu_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\ServerLogConfigFiles\\power_config.txt"), new File(output_folder + "power_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\ServerLogConfigFiles\\dma_config.txt"), new File(output_folder + "dma_config.txt"));//copy config files to dest

        //FileUtils.
        List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
        double xValues[] = new double[lines.size()];

        double yValues1[] = new double[lines.size()];
        double yValues2[] = new double[lines.size()];
        double yValues3[] = new double[lines.size()];
        double yValues4[] = new double[lines.size()];

        ArrayList<String> lines_out1 = new ArrayList<>();
        ArrayList<String> lines_out2 = new ArrayList<>();
        ArrayList<String> lines_out3 = new ArrayList<>();
        ArrayList<String> lines_out4 = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++)
        {
            String[] line_cloumn = lines.get(i).split(",");
            xValues[i] = i;

            yValues1[i] = Double.parseDouble(line_cloumn[49]);
            yValues2[i] = Double.parseDouble(line_cloumn[1]);
            yValues3[i] = Double.parseDouble(line_cloumn[48]);
            yValues4[i] = Double.parseDouble(line_cloumn[11]);

            lines_out1.add("" + xValues[i] + "," + yValues1[i]);
            lines_out2.add("" + xValues[i] + "," + yValues2[i]);
            lines_out3.add("" + xValues[i] + "," + yValues3[i]);
            lines_out4.add("" + xValues[i] + "," + yValues4[i]);
        }

        Files.write(Paths.get(output_folder + "temperature" + ".txt"), lines_out1, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "cpu" + ".txt"), lines_out2, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "power" + ".txt"), lines_out3, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "dma" + ".txt"), lines_out4, Charset.defaultCharset());

        return output_folder;
    } catch (Exception ex)
    {
        Logger.getLogger(ServerLogPlugin.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
        return "";
    }
}

}
