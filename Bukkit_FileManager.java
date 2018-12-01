package /* package */;

import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

/* 

	Simple solution for creating configuration files by RhalisX99
	Please do not remove this comment.
	
	Usage example:
	FileManager configuration = new FileManager("<name>.yml");

*/

public class FileManager {

    private File dataFolder;
    private FileConfiguration config;

    private String configName;

    public FileManager(File dataFolder, String configName) {
        this.dataFolder = dataFolder;
        this.configName = configName;
        saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        if(config == null){
            config = getConfigFromFile();
        }
        return config;
    }

    private FileConfiguration getConfigFromFile(){
        File file = new File(dataFolder, configName);
        return YamlConfiguration.loadConfiguration(file);
    }

    public void saveDefaultConfig(){
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File file = new File(dataFolder, configName);
        if (!file.exists()) {
            try {
                file.createNewFile();

                InputStream is = /*<instance of class which extends org.bukkit.plugin.java.JavaPlugin>*/.getResource(configName);
                OutputStream os = new FileOutputStream(file);

                ByteStreams.copy(is, os);

            } catch (IOException ex) {
                throw new RuntimeException("There was an error during the creation of file " + configName, ex);
            }
        }
    }

    public void reloadConfig(){
        File file = new File(dataFolder, configName);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig(){
        File file = new File(dataFolder, configName);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}