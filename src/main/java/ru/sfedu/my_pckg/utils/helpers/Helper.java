package ru.sfedu.my_pckg.utils.helpers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Helper.
 */
public class Helper {
    /**
     * Create id long.
     *
     * @return the long
     */
    public static long createId() {
        return Math.round(System.currentTimeMillis() / (Math.random() * 100000));
    }

    /**
     * List to string string.
     *
     * @param list the list
     * @return the string
     */
    public static String ListToString(List<Long> list) {
        if (list == null) {
            return "null";
        }
        return list.stream().map(Objects::toString).collect(Collectors.joining(" ,"));
    }

    public static String ListStringToString(List<String> list) {
        return list.stream().map(Objects::toString).collect(Collectors.joining(" ,"));
    }


    /**
     * Generate first name string.
     *
     * @return the string
     */
    public static String generateUserFName() {
        List<String> fnames = Arrays.asList("John", "Peter", "Jacob", "Stan", "Andrew");
        int fnamePos = (int) (Math.random() * fnames.size());
        return fnames.get(fnamePos);
    }

    /**
     * Generate user last name string.
     *
     * @return the string
     */
    public static String generateUserLName() {
        List<String> lnames = Arrays.asList("Beach", "Morgan", "Tump", "Trant", "Doom");
        int lnamePos = (int) (Math.random() * lnames.size());
        return lnames.get(lnamePos);
    }

    /**
     * Random number int.
     *
     * @param rightInterval the right interval
     * @return the int
     */
    public static int randomNumber(int rightInterval) {
        return (int) (Math.random() * rightInterval);
    }

    /**
     * Generate random string.
     *
     * @param length the length of string
     * @return the string
     */
    public static String generateRandomString(int length) {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();


        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    /**
     * Random ur ls array list.
     *
     * @param count the count
     * @return the array list
     */
    public static ArrayList<String> randomURLs(int count) {
        List<String> protocol = Arrays.asList("http://", "https://");
        List<String> body = Arrays.asList("www.udemy", "www.youtube", "www.edyoda", "www.youtube");
        List<String> domain = Arrays.asList(".ru", ".com", ".io", ".su");
        ArrayList<String> urls = new ArrayList<String>();
        randomNumber(protocol.size());
        for (int i = 0; i < count; i++) {
            urls.add(protocol.get(randomNumber(protocol.size())) +
                    body.get(randomNumber(body.size())) +
                    domain.get(randomNumber(domain.size())) +
                    "?video=" + generateRandomString(10));
        }
        return urls;
    }

    public static String randomCountry() {
        List<String> country = Arrays.asList("Russia", "USA", "Poland", "Germany");
        return country.get(randomNumber(country.size()));
    }

    public static String randomPreferences() {
        List<String> pref = Arrays.asList("Machine Learning", "Open CV", "Frontend", "Backend", "Fullstack");
        return pref.get(randomNumber(pref.size()));
    }



}
