package ru.sfedu.my_pckg.api.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
@Root
public class WrapperXML <T>{
    @ElementList(inline = true, required = false)
    public List<T> list;

    public WrapperXML() { }

    public WrapperXML(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

