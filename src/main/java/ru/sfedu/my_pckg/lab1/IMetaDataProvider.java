package ru.sfedu.my_pckg.lab1;

import java.util.List;

public interface IMetaDataProvider {
    List getAllSchemas();
    String getCatalogName();
    String getCurrentUser();
    List getTablesSize();
    List getTablesName();
}

