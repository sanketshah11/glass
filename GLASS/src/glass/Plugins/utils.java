/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glass.Plugins;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class utils
{

public static void cleanWorkDir(String output_folder)
{
    new File(output_folder).mkdirs();//create if does not exist
    try
    {
        FileUtils.deleteDirectory(new File(output_folder));
    } catch (IOException ex)
    {
        Logger.getLogger(ServerLogPlugin.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
    }
    new File(output_folder).mkdirs();//create if does not exist
}

}
