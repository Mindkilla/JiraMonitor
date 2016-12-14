package servlets;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Created by Mindkilla on 10.12.2016.
 */
public class Date {

    public static int getPrevMonth(){
        DateTime date = new DateTime();
        return date.getMonthOfYear()-1;
    }

    public static int getYear(){
        DateTime date = new DateTime();
        return date.getYear();
    }

    public static int getLastDayOfMonth() {
        int lastDay = 0;
        if ((getPrevMonth() >= 1) && (getPrevMonth() <= 12)) {
            LocalDate aDate = new LocalDate(getYear(), getPrevMonth(), 1);
            lastDay = aDate.dayOfMonth().getMaximumValue();
        }
        return lastDay;
    }

    public static String dataEnd(){
        return Integer.toString(getYear())+"-"+Integer.toString(getPrevMonth())+"-"+Integer.toString(getLastDayOfMonth());
    }

    public static String dataBegin(){
        return Integer.toString(getYear())+"-"+Integer.toString(getPrevMonth())+"-"+Integer.toString(1);
    }
}
