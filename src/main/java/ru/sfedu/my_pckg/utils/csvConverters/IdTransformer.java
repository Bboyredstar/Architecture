package ru.sfedu.my_pckg.utils.csvConverters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.my_pckg.utils.helpers.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

;

public class IdTransformer extends AbstractBeanField {
    public static Logger log = LogManager.getLogger(IdTransformer.class);
    public String delimiter = " ,";
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return Helper.ListToString((List<Long>)value);
    }

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value.toLowerCase().equals("null")){
            return null;
        }
      return  Arrays.asList(value.split(delimiter)).stream().map(Long::parseLong).collect(Collectors.toList());
    }
}
