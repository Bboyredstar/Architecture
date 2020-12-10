package ru.sfedu.my_pckg;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.api.DataProviderCsv;

import java.io.IOException;

public class ProjectAppClient {

    //    logger
    private static Logger log = LogManager.getLogger(ProjectAppClient.class);

    //    initialization logger
    public ProjectAppClient() {
        log.debug("ProjectApp: Starting application");
    }

    public static void logBasicInfo() throws IOException {
        log.info("Launching application:");
        log.warn("Operating System " + System.getProperty("os.name") + " " +
                System.getProperty("os.version"));
        log.debug("JRE version " + System.getProperty("java.version"));
        log.fatal("Java launched from " + System.getProperty("java.home"));
        log.error("User Home Directory: " + System.getProperty("user.home"));
        log.info("Java Class :" + System.getProperty("java.class.path"));
        log.info("Java Library: " + System.getProperty("java.library.path"));
        log.info("Test INFO logging.");
    }

    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        DataProviderCsv provider = new DataProviderCsv();


    }
};
