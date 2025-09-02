package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//TODO я закоммитила properties, надо их не запушить
public class ConfigLoader {
    public static ConfigLoader instance;
    private Properties properties;
    private ConfigLoader(){
       properties=new Properties();
       loadProperties();
    }
    public static synchronized ConfigLoader getInstance(){
        if (instance==null){
            instance=new ConfigLoader();
        }
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }
    public void loadProperties() {
        try(InputStream input=getClass().getClassLoader().getResourceAsStream("application.properties")){
            if(input==null){
                throw new RuntimeException("Не удалось найти файл application.properties в папке resources");

            }
            properties.load(input);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
