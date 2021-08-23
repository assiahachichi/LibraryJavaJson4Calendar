package java.json4calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Assia HACHICHI
 */
public class MappingDateString {
    //*********************************************//
    //    Static Methods                           //
    //*********************************************//

    /**
     * This method converts a date variable into JSON format.
     * @param date
     * @return date into JSON format.
     */
    public static String convertDateToString4Json(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

    }

    /**
     * This method converts a date variable into String format, in order to be used in Google Calendar.
     * @param date
     * @return date into String format for Google Calendar
     */
    public static String convertDateToString4Calendar(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    /**
     * This method converts a String variable to a date, by applying a pattern.
     * This pattern allows the user to easily read resulting date.
     * @param sDate a String which contains a date information
     * @return resulting date
     */
    public static Date convertStringToDate(String sDate) {
        Date date = null;
        try {
            if(sDate.equals("")||(sDate==null)){
                return date;
            }else{
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
