/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass.Plugins;

import glass.GUI;
import glass.Plugins.PluginInterface;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class LinuxOSPlugin extends PluginInterface {

    public LinuxOSPlugin() {
    }

    @Override
    public String processFile(String path) {
        //this program opens a file , processes each line seperately,
        //each input line is of format : 20140727,10:12,0.18,0.19,0.18,0.07,0.11  
        //sample output line : 7.0,0.18    
        //output line processed and printed to a seperate file  
        String output_folder = "C:\\Temp\\WebServer\\";
        new File(output_folder).mkdirs();//create if does not exist
        String timeStamp = new SimpleDateFormat("MMddYY_HHmmss").format(Calendar.getInstance().getTime());
        try {
            FileUtils.copyFile(new File(GUI.toolPath + "WebserverConfigFiles\\Webserver_config.txt"), new File(output_folder + timeStamp + "_config.txt"));//copy config files to dest
            //FileUtils.
            List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF8"));
            double xValues[] = new double[lines.size()];
            double yValues[] = new double[lines.size()];
            ArrayList<String> lines_out = new ArrayList<>();
            ArrayList<String> lines_out2 = new ArrayList<>();
            ArrayList<String> lines_out3 = new ArrayList<>();
            int previousMinute = 0;
            int total = 0;
            int count = 0;
            int errorCount = 0;
            int dataCount = 0;
            for (int i = 1; i < lines.size(); i++) {
                System.out.println(i);
                String[] line_cloumn = lines.get(i).split(" ");
                String[] time = line_cloumn[3].split(":");
                int minutes = Integer.parseInt(time[2]);
                if (minutes == previousMinute) {
                    total++;
                    if (line_cloumn.length == 10) {
                        dataCount = dataCount + Integer.parseInt(line_cloumn[9]);
                        if (!line_cloumn[8].equals("200")) {
                            errorCount++;
                        }
                    } else if (line_cloumn.length == 9) {
                        dataCount = dataCount + Integer.parseInt(line_cloumn[8]);
                        if (!line_cloumn[7].equals("200")) {
                            errorCount++;
                        }
                    } else {
                        errorCount++;
                    }
                } else {
                    lines_out.add("" + count + "," + total);
                    lines_out2.add("" + count + "," + errorCount);
                    lines_out3.add("" + count + "," + dataCount);
                    count++;
                    total = 0;
                    previousMinute = minutes;
                    errorCount = 0;
                    dataCount = 0;
                }
            }
            Files.write(Paths.get(output_folder + timeStamp + "_hits.txt"), lines_out, Charset.defaultCharset());
            Files.write(Paths.get(output_folder + timeStamp + "_error.txt"), lines_out2, Charset.defaultCharset());
            Files.write(Paths.get(output_folder + timeStamp + "_data.txt"), lines_out3, Charset.defaultCharset());
            return output_folder;
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(LinuxOSPlugin.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
