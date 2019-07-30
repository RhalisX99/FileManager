package /* package */;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

/* 

	Simple solution for creating configuration files by RhalisX99
	Please do not remove this comment.
	
	Usage example:
	FileManager configuration = new FileManager("<name>.yml");

*/

public class FileManager{

    private File dataFolder;
    private Configuration config;

    private String configName;

    public FileManager(File dataFolder, String configName) {
        this.dataFolder = dataFolder;
        this.configName = configName;
        saveDefaultConfig();
    }

    public Configuration getConfig() {
        if (config == null) {
           config = getConfigFromFile();
        }
        return config;
    }

    private Configuration getConfigFromFile(){
        File file = new File(dataFolder, configName);
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ex){
            throw new RuntimeException("Could not find config file " + configName, ex);
        }
    }

    public void saveConfig() {
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(dataFolder, configName));
        } catch(IOException ex){
            throw new RuntimeException("Could not save config file " + configName, ex);
        }
    }

    public void reloadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(dataFolder, configName));
        } catch (IOException ex) {
            throw new RuntimeException("Could not reload config file " + configName, ex);
        }
    }

    public void saveDefaultConfig() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File file = new File(dataFolder, configName);
        if (!file.exists()) {
            try {
                file.createNewFile();

                InputStream is = /*<instance of class which extends net.md_5.bungee.api.plugin.Plugin>*/.getResourceAsStream(configName);
                OutputStream os = new FileOutputStream(file);

                ByteStreams.copy(is, os);

            } catch (IOException ex) {
                throw new RuntimeException("There was an error during the creation of file " + configName, ex);
            }
        }
    }
}
