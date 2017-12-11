package servlets;

import utils.DateUtils;
import utils.JiraApiUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrey Smirnov
 */
public class AllServletActions
{
    private AllServletActions()
    {
    }

    public static void fillPageWithData(String whoCallMe, HttpServletRequest request)
    {
        if ( "main".equals(whoCallMe) )
        {
            //Открытых\Закрытых обращений за сегодня
            request.setAttribute("todayIssues", JiraApiUtils.todayIssues());
            //Кол-во АКТИВНЫХ обращений на данный момент для каждого
            request.setAttribute("currentActive", JiraApiUtils.getCurrentActive());
            //Кол-во ЗАКРЫТЫХ обращений за текущий месяц для каждого
            request.setAttribute("currentClosed", JiraApiUtils.getCurrentClosed());
            //Лидер по кол-ву решенных обращений за прошлый месяц
            request.setAttribute("month", DateUtils.getMonthName());
            request.setAttribute("max", JiraApiUtils.getLider());
            //Открытые закрытые
            request.setAttribute("issueCurmonth", JiraApiUtils.getIssueCurrMonth());
            //ЗАКРЫТЫЕ Дефекты консультации пожелания
            request.setAttribute("issueClosedMonth", JiraApiUtils.getIssueClosedCurrMonth());
            //Кол-во ОТКРЫТЫХ дефектов, консультаций
            request.setAttribute("issueOpenedNow", JiraApiUtils.getIssueOpenedCurrMonth());
        }
        else
        {
            //Отпуска
            request.setAttribute("vacation", JiraApiUtils.getVacation());
            //Простои
            request.setAttribute("inactivity", JiraApiUtils.getInactivity());
        }
    }

}
