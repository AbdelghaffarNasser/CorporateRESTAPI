package net.cybermak.integration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ReadProperties {
    final Logger logger = LoggerFactory.getLogger(ReadProperties.class);
    //private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("Corporate");
    private static ReadProperties instance = null;
    private Properties properties = null;
    public String serverName = null;
    public String userName = null;
    public String password = null;
    public String huaweiUserName = null;
    public String huaweiPassword = null;
    public String huaweiURL = null;
    public String soapAction = null;
    public String huaweiCertificate = null;

    protected ReadProperties() {

        logger.info("*********************Start Getting Properties****************************");
        properties = new Properties();
        try {
            String filePath = "D:\\Program Files\\BMC Software\\Integrations\\NetBeansProjects\\INTG_Config\\Integ_common_config.properties";
            InputStream file = Files.newInputStream(Paths.get(filePath));
            properties.load(file);
            serverName = properties.getProperty("Servername");
            userName = properties.getProperty("userName");
            password = properties.getProperty("userPassword");
            huaweiUserName = properties.getProperty("Corporate_userName");
            huaweiPassword = properties.getProperty("Corporate_password");
            huaweiURL = properties.getProperty("Corporate_API_URL");
            huaweiCertificate = properties.getProperty("Huawei_Certificate");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    static public synchronized ReadProperties getInstance() {
        if (instance == null) {
            instance = new ReadProperties();
        }
        return instance;
    }

    public String getValue(String key) {
        return this.properties.getProperty(key, String.format("The key %s does not exists!", key));
    }
}
