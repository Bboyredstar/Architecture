package ru.sfedu.my_pckg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ProjectAppClientTest {
    @Test
    void logBasicInfo() throws IOException {
        ProjectAppClient Project = new ProjectAppClient();
        Project.logBasicInfo();
    }
}