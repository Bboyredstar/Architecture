package ru.sfedu.my_pckg.utils.csvConverters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.util.Arrays;
import java.util.List;

public class UrlTransformer extends AbstractBeanField {
    private  String elementDelimiter = "::";

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<String> urls = (List<String>)value;
        return String.join(elementDelimiter,urls);
    }

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        List<String> urls =  Arrays.asList(value.split(elementDelimiter));
        return urls;

    }
}
