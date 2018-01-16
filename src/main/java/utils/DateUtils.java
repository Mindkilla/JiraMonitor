package utils;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * @author Andrey Smirnov
 */
public class DateUtils
{
    private static final List<String> MONTH_RU = ImmutableList.of("январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь");
    private DateUtils()
    {
    }

    private static int getPrevMonth()
    {
        DateTime date = new DateTime();
        if ( date.getMonthOfYear() - 1 == 0 )
        {
            return 12;
        }
        return date.getMonthOfYear() - 1;
    }

    public static String getMonthName()
    {
        LocalDate aDate = new LocalDate(getYear(), getPrevMonth(), 1);
        return MONTH_RU.get(Integer.parseInt(aDate.monthOfYear().getAsString()) - 1);
    }

    private static int getYear()
    {
        DateTime date = new DateTime();
        if ( getPrevMonth() == 12 )
        {
            return date.getYear() - 1;
        }
        return date.getYear();
    }

    private static int getLastDayOfMonth()
    {
        int lastDay = 0;
        if ( (getPrevMonth() >= 1) && (getPrevMonth() <= 12) )
        {
            LocalDate aDate = new LocalDate(getYear(), getPrevMonth(), 1);
            lastDay = aDate.dayOfMonth().getMaximumValue();
        }
        return lastDay;
    }

    public static String dataEnd()
    {
        return Integer.toString(getYear()) + "-" + Integer.toString(getPrevMonth()) + "-" + Integer.toString(getLastDayOfMonth());
    }

    public static String dataBegin()
    {
        return Integer.toString(getYear()) + "-" + Integer.toString(getPrevMonth()) + "-" + Integer.toString(1);
    }
}
