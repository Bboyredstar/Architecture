package ru.sfedu.my_pckg.helpers;

public class  IdCreator {
  public static long createId() {
     return  Math.round(System.currentTimeMillis()/(Math.random()*1000000000));
    }
}
