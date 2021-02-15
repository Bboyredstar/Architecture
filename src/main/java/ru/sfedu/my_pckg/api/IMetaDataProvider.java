package ru.sfedu.my_pckg.api;

import java.io.IOException;
import java.util.List;

public interface IMetaDataProvider {
    List getAllSchemas();
    String getCatalogName();
    String getCurrentUser();
    List getTablesSize();
    List getTablesName();
}

