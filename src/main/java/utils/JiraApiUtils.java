package utils;

import net.rcarz.jiraclient.*;
import org.apache.log4j.Logger;
import servlets.Consts;

import java.util.*;

/**
 * @author Andrey Smirnov
 */
public class JiraApiUtils
{
    private static final Logger log = Logger.getLogger(JiraApiUtils.class);

    private JiraApiUtils()
    {
    }

    private static JiraClient connect;

    //Connect to jira rest api
    public static void jiraConnect()
    {
        BasicCredentials creds = new BasicCredentials(Consts.JIRA_USER, Consts.JIRA_PASS);
        connect =  new JiraClient(Consts.JIRA_URL, creds);
    }

    //Count of issue
    private static String issueCountRest(String jql)
    {
        int total = 0;
        try
        {
            Issue.SearchResult result = connect.searchIssues(jql, "project");
            total = result.total;
        }
        catch ( JiraException ex )
        {
            if ( ex.getCause() != null )
            {
                log.error(ex + ex.getCause().getMessage());
            }
        }
        log.info(jql + " - RESULT: " + total);
        return Integer.toString(total);
    }

    //в секундах , можно форматировать результат при выводе куда-либо
    public static Integer projectTimeCount(String jql)
    {
        int time = 0;
        try
        {
            Issue.SearchResult result = connect.searchIssues(jql);
            if ( result.issues.isEmpty() )
            {
                time = 0;
            }
            for ( int i = 0; i < result.issues.size(); i++ )
            {
                time = time + result.issues.get(i).getTimeSpent();
            }
        }
        catch ( JiraException ex )
        {
            if ( ex.getCause() != null )
            {
                log.error(ex + ex.getCause().getMessage());
            }
        }
        log.info(jql + " - RESULT: " + time);
        return time;
    }

    public static String issueCount(String str, String jql)
    {
        return str + " : " + issueCountRest(jql);
    }

    //Лидер по кол-ву решенных обращений за прошлый месяц
    public static String maxCountMonthRest()
    {
        HashMap<String, Integer> arrayOfCounts = new HashMap<>();
        arrayOfCounts.put(Consts.NAME_ALEX, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.LEVAS)));
        arrayOfCounts.put(Consts.NAME_ZH, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.ZHEEV)));
        arrayOfCounts.put(Consts.NAME_JOHN, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.ESIES)));
        arrayOfCounts.put(Consts.NAME_AIDAR, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.KUAAE)));

        int maxValueInMap = Collections.max(arrayOfCounts.values());
        String maxName = null;

        for ( Map.Entry<String, Integer> entry : arrayOfCounts.entrySet() )
        {
            if ( entry.getValue() == maxValueInMap )
            {
                maxName = entry.getKey();
            }
        }

        StringBuilder maxOfCounts = new StringBuilder();
        maxOfCounts.append(maxName).append(", ").append(maxValueInMap);

        log.info("Максимальное кол-во за прошлый месяц - " + maxOfCounts);

        return maxOfCounts.toString();
    }
}
