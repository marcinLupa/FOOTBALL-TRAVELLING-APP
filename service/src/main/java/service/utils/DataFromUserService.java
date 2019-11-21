package service.utils;

import exceptions.MyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DataFromUserService {

    private static Scanner sc=new Scanner(System.in);

    public static String getStringSpaceAndUpperCase(){
        String text = sc.nextLine();
        if (!text.matches("[A-Za-z ]+")) {
            throw new MyException("VALUE IS NOT LETTER AND SPACE STRING: " + text);
        }
        return text;

    }
    public static String getStringLettersAndDigits(){
        String text = sc.nextLine();
        if (!text.matches("[A-Za-z0-9 ]+")) {
            throw new MyException("VALUE IS NOT LETTER AND SPACE STRING: " + text);
        }
        return text;

    }
    public static String getCityNameToSearch(){
        String text = sc.nextLine();
        if (!text.matches("[A-Za-z ]+")) {
            throw new MyException("VALUE IS NOT LETTER OR SPACE STRING OR YOU USE POLISH MARKS: " + text);
        }
        return text.replaceAll(" ","_");

    }
    public static int getInt() {

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException( "VALUE IS NOT DIGIT: " + text);
        }
        return Integer.parseInt(text);
    }
    public static Long getLong() {

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException( "VALUE IS NOT DIGIT: " + text);
        }
        return Long.parseLong(text);
    }

    public static int getInt(int scale) {

        String text = sc.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException("VALUE IS NOT DIGIT: " + text);
        }
        if (Integer.parseInt(text) < 0 || Integer.parseInt(text) > scale) {
            throw new MyException( "RANGE OUT OF BOUND " + text);
        }

        return Integer.parseInt(text);
    }


    public static boolean getYesOrNo() {

        String text = sc.nextLine();
        if (!text.matches("TAK|NIE|tak|nie+")) {
            throw new MyException( "NOT CORRECT AGREEMENT FORMAT " + text);
        }
        if (text.equalsIgnoreCase("TAK")) {
            return true;
        }
        if (text.equalsIgnoreCase("NIE")) {
            return false;
        } else {
            throw new MyException("INCORRECT VALUE " + text);
        }
    }

    public static BigDecimal getPrice() {

        String text = sc.nextLine();
        if (!text.matches("\\d.+|\\d|-\\d.+|-\\d")) {

            throw new MyException( "VALUE IS NOT BIG DECIMAL: " + text);
        }
        if (new BigDecimal(text).compareTo(BigDecimal.ZERO) < 0) {
            throw new MyException( "VALUE UNDER ZERO " + text);
        }

        return new BigDecimal(text);
    }
    public static String getEmail() {

        String text = sc.nextLine();
        if (!text.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
            throw new MyException( "WRONG FORMAT OF E-MAIL " + text);
        }
        return text;
    }
    public static LocalDateTime getLocalDateTime() {

        String text = sc.nextLine();
        if (!text.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]")) {
            throw new MyException( "WRONG FORMAT OF LOCAL DATE TIME " + text);
        }
        return LocalDateTime.parse(
                text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDate getLocalDate() {
        String text = sc.nextLine();
        if (!text.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])")) {
            throw new MyException("WRONG FORMAT OF LOCAL DATE FROM USER " + text);
        }
        return LocalDate.parse(
                text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


}