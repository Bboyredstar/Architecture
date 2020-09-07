package ru.sfedu.my_pckg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProjectAppClientTest {

    @org.junit.jupiter.api.Test
    void logBasicInfo() {
        ProjectAppClient client = new ProjectAppClient();
        client.logBasicInfo();
    }
}