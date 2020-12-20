package ru.sfedu.my_pckg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.api.AbstractDataProvider;
import ru.sfedu.my_pckg.api.DataProviderCsv;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProjectAppClient {

    //    logger
    private static Logger log = LogManager.getLogger(ProjectAppClient.class);

    protected static AbstractDataProvider resolveDataProvider(List<String> arguments) {
        if (arguments.size() == 0) return null;
        log.debug(arguments.get(0));
        switch (arguments.get(0)) {
            case Constants.CSV_COMMAND:
                try {
                    return new DataProviderCsv();
                } catch (IOException e) {
                    log.error(e);
                    return null;
                }
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);
        log.debug(arguments.toString());

        AbstractDataProvider dataProvider = resolveDataProvider(arguments);
        if (dataProvider == null) {
            log.error(Constants.BAD_ARGS_FORMAT);
            return;
        }


    }
}
