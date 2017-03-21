/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass.Plugins;

import glass.GUI;
import static glass.Plugins.utils.cleanWorkDir;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class AirPollutionLogsPlugin extends PluginInterface
{

public AirPollutionLogsPlugin()
{
}

/**
 * @param args the command line arguments
 */
@Override
public String processFile(String path)
{

    //code to create/cleanup work directory 
    String output_folder = "work/AirPollutionLogsPlugin/";
    cleanWorkDir(output_folder);
    try
    {
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\AirPollutionConfigFiles\\AirPollution_o3_config.txt"), new File(output_folder + "AirPollution_o3_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\AirPollutionConfigFiles\\AirPollution_no2_config.txt"), new File(output_folder + "AirPollution_no2_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\AirPollutionConfigFiles\\AirPollution_no_config.txt"), new File(output_folder + "AirPollution_no_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\AirPollutionConfigFiles\\AirPollution_so2_config.txt"), new File(output_folder + "AirPollution_so2_config.txt"));//copy config files to dest
        FileUtils.copyFile(new File(GUI.toolPath + "configFiles\\AirPollutionConfigFiles\\AirPollution_particles_config.txt"), new File(output_folder + "AirPollution_particles_config.txt"));//copy config files to dest        
        //FileUtils.
        List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());

        double xValues[] = new double[lines.size()];
        double yValues1[] = new double[lines.size()];
        double yValues2[] = new double[lines.size()];
        double yValues3[] = new double[lines.size()];
        double yValues4[] = new double[lines.size()];
        double yValues5[] = new double[lines.size()];

        ArrayList<String> lines_out1 = new ArrayList<>();
        ArrayList<String> lines_out2 = new ArrayList<>();
        ArrayList<String> lines_out3 = new ArrayList<>();
        ArrayList<String> lines_out4 = new ArrayList<>();
        ArrayList<String> lines_out5 = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++)
        {
            String[] line_cloumn = lines.get(i).split(",");
            xValues[i] = i;

            yValues1[i] = Double.parseDouble(line_cloumn[1]);
            yValues2[i] = Double.parseDouble(line_cloumn[2]);
            yValues3[i] = Double.parseDouble(line_cloumn[3]);
            yValues4[i] = Double.parseDouble(line_cloumn[4]);
            yValues5[i] = Double.parseDouble(line_cloumn[5]);

            lines_out1.add("" + xValues[i] + "," + yValues1[i]);
            lines_out2.add("" + xValues[i] + "," + yValues2[i]);
            lines_out3.add("" + xValues[i] + "," + yValues3[i]);
            lines_out4.add("" + xValues[i] + "," + yValues4[i]);
            lines_out5.add("" + xValues[i] + "," + yValues5[i]);
        }

        Files.write(Paths.get(output_folder + "AirPollution_o3" + ".txt"), lines_out1, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "AirPollution_no2" + ".txt"), lines_out2, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "AirPollution_no" + ".txt"), lines_out3, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "AirPollution_so2" + ".txt"), lines_out4, Charset.defaultCharset());
        Files.write(Paths.get(output_folder + "AirPollution_particles" + ".txt"), lines_out5, Charset.defaultCharset());

        return output_folder;
    } catch (Exception ex)
    {
        Logger.getLogger(AirPollutionLogsPlugin.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
        return "";
    }
}

}
