package ru.sfedu.my_pckg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Source;

import java.io.IOException;

import static ru.sfedu.my_pckg.Constants.SOURCE;
import static ru.sfedu.my_pckg.utils.ConfigurationUtil.getConfigurationEntry;

public class ProjectAppClient {
//    logger
    private static Logger log = LogManager.getLogger(ProjectAppClient.class);
//    initialization logger
    public static void main (String []args) throws IOException {
        ProjectAppClient project = new ProjectAppClient();
        project.logBasicInfo();

    }
    public ProjectAppClient(){
        log.debug("ProjectApp: Starting application");
    }
    public void logBasicInfo() throws IOException {
        log.info("Launching application:");
        log.info("Operating System " + System.getProperty("os.name") + " " +
                System.getProperty("os.version"));
        log.info("JRE version " + System.getProperty("java.version"));
        log.info("Java launched from " + System.getProperty("java.home") );
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("Java Class :" + System.getProperty("java.class.path"));
        log.info("Java Library: " + System.getProperty("java.library.path"));
        log.info("Test INFO logging.");
        System.out.println(getConfigurationEntry(SOURCE));
    }


}
