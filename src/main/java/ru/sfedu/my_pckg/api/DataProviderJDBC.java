package ru.sfedu.my_pckg.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.utils.ConfigurationUtil;

import java.io.IOException;

public class DataProviderJDBC {
    private final String CONNECTION = ConfigurationUtil.getConfigurationEntry("CONNECTION");
    private final String DEFAULT_PATH = "jdbc:./DB/testdb.db";
    private final String USER = ConfigurationUtil.getConfigurationEntry("USER");
    private final String DEFAULT_USER = "user";
    private final String PASSWORD = ConfigurationUtil.getConfigurationEntry("PASSWORD");
    private final String DEFAULT_PASSWORD = "12345";
    private final String DRIVER = ConfigurationUtil.getConfigurationEntry("DRIVER");
    private final String DEFAULT_DRIVER = "12345";
    public static Logger log = LogManager.getLogger(DataProviderJDBC.class);


    public DataProviderJDBC() throws IOException {

    }
}
