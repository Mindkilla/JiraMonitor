package servlets;

import net.rcarz.jiraclient.JiraClient;
import utils.DateUtils;
import utils.JiraApiUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrey Smirnov
 */
public class AllServletActions
{
    private static JiraClient jiraClient = JiraApiUtils.jiraConnect();

    private AllServletActions()
    {
    }

    public static void fillPageWithData(String whoCallMe, HttpServletRequest request)
    {
        if ( "main".equals(whoCallMe) )
        {
            //Открытых\Закрытых обращений за сегодня
            request.setAttribute("issueNewToday", JiraApiUtils.issueCount(jiraClient, Consts.NEW, Consts.ISSUE_NEW_TODAY));
            request.setAttribute("issueClosedToday", JiraApiUtils.issueCount(jiraClient, Consts.CLOSED, Consts.ISSUE_CLOSED_TODAY));
            //Кол-во АКТИВНЫХ обращений на данный момент для каждого
            request.setAttribute("Levas", JiraApiUtils.issueCount(jiraClient, Consts.NAME_ALEX, Consts.ISSUE_ACTIVE_NOW + Consts.LEVAS));
            request.setAttribute("Esies", JiraApiUtils.issueCount(jiraClient, Consts.NAME_JOHN, Consts.ISSUE_ACTIVE_NOW + Consts.ESIES));
            request.setAttribute("Kuaae", JiraApiUtils.issueCount(jiraClient, Consts.NAME_AIDAR, Consts.ISSUE_ACTIVE_NOW + Consts.KUAAE));
            request.setAttribute("Zheev", JiraApiUtils.issueCount(jiraClient, Consts.NAME_ZH, Consts.ISSUE_ACTIVE_NOW + Consts.ZHEEV));
            //Кол-во ЗАКРЫТЫХ обращений за текущий месяц для каждого
            request.setAttribute("LevasС", JiraApiUtils.issueCount(jiraClient, Consts.NAME_ALEX, Consts.ISSUE_CLOSED_MONTH + Consts.LEVAS));
            request.setAttribute("EsiesС", JiraApiUtils.issueCount(jiraClient, Consts.NAME_JOHN, Consts.ISSUE_CLOSED_MONTH + Consts.ESIES));
            request.setAttribute("KuaaeС", JiraApiUtils.issueCount(jiraClient, Consts.NAME_AIDAR, Consts.ISSUE_CLOSED_MONTH + Consts.KUAAE));
            request.setAttribute("ZheevС", JiraApiUtils.issueCount(jiraClient, Consts.NAME_ZH, Consts.ISSUE_CLOSED_MONTH + Consts.ZHEEV));
            //Лидер по кол-ву решенных обращений за прошлый месяц
            request.setAttribute("Month", DateUtils.getMonthName());
            request.setAttribute("Max", JiraApiUtils.maxCountMonthRest(jiraClient));
            //Открытые закрытые
            request.setAttribute("issueOpenCurmonth", JiraApiUtils.issueCount(jiraClient, Consts.OPENED, Consts.ISSUE_OPEN_CURMONTH));
            request.setAttribute("issueClosedCurmonth", JiraApiUtils.issueCount(jiraClient, Consts.CLOSED, Consts.ISSUE_CLOSED_CURMONTH));
            //ЗАКРЫТЫЕ Дефекты консультации пожелания
            request.setAttribute("defectClosedMonth", JiraApiUtils.issueCount(jiraClient, Consts.DEFECT_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.DEFECT));
            request.setAttribute("consultClosedMonth", JiraApiUtils.issueCount(jiraClient, Consts.CONSULT_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.CONSULT));
            request.setAttribute("whishClosedMonth", JiraApiUtils.issueCount(jiraClient, Consts.WHISH_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.WHISH));
            //Кол-во ОТКРЫТЫХ дефектов, консультаций
            request.setAttribute("defectOpenNow", JiraApiUtils.issueCount(jiraClient, Consts.DEFECT_NAME, Consts.DEFECT_OPENED_NOW + Consts.DEFECT));
            request.setAttribute("consultOpenNow", JiraApiUtils.issueCount(jiraClient, Consts.CONSULT_NAME, Consts.DEFECT_OPENED_NOW + Consts.CONSULT));
        }
        else
        {
            //Отпуска
            request.setAttribute("countVacationL", JiraApiUtils.projectTimeCount(jiraClient, Consts.VACATION_CURRENT_YEAR + Consts.LEVAS) / 3600 / 8);
            request.setAttribute("countVacationE", JiraApiUtils.projectTimeCount(jiraClient, Consts.VACATION_CURRENT_YEAR + Consts.ESIES) / 3600 / 8);
            request.setAttribute("countVacationK", JiraApiUtils.projectTimeCount(jiraClient, Consts.VACATION_CURRENT_YEAR + Consts.KUAAE) / 3600 / 8);
            request.setAttribute("countVacationZh", JiraApiUtils.projectTimeCount(jiraClient, Consts.VACATION_CURRENT_YEAR + Consts.ZHEEV) / 3600 / 8);
            //Простои
            request.setAttribute("countInactivityL", JiraApiUtils.projectTimeCount(jiraClient, Consts.INACTIVITY_CURRENT_YEAR + Consts.LEVAS) / 3600);
            request.setAttribute("countInactivityE", JiraApiUtils.projectTimeCount(jiraClient, Consts.INACTIVITY_CURRENT_YEAR + Consts.ESIES) / 3600);
            request.setAttribute("countInactivityK", JiraApiUtils.projectTimeCount(jiraClient, Consts.INACTIVITY_CURRENT_YEAR + Consts.KUAAE) / 3600);
            request.setAttribute("countInactivityZh", JiraApiUtils.projectTimeCount(jiraClient, Consts.INACTIVITY_CURRENT_YEAR + Consts.ZHEEV) / 3600);
        }
    }

}
