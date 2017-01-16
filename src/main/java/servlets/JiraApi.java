package servlets;

import net.rcarz.jiraclient.*;
import org.apache.log4j.Logger;
import java.util.*;

/**
 *
 * @author Andrey Smirnov
 */
class JiraApi {
    private static final Logger log = Logger.getLogger(JiraApi.class);

    //Connect to jira rest api
    private static JiraClient jiraConnect() {
        BasicCredentials creds = new BasicCredentials(Consts.JIRA_USER, Consts.JIRA_PASSWORD);
        return new JiraClient(Consts.JIRA_URL, creds);
    }

    //Count of issue
    private static String issueCountRest(String jql) {
        int total = 0;
        try {
            Issue.SearchResult result = jiraConnect().searchIssues(jql, "project");
            total = result.total;
        } catch (JiraException ex) {
            if (ex.getCause() != null)
                log.error(ex.getCause().getMessage());
        }
        log.info(jql + " - RESULT: " + total);
        return Integer.toString(total);
    }

    //в секундах , можно форматировать результат при выводе куда-либо
    static Integer projectTimeCount(String jql) {
        int time = 0;
        try {
            Issue.SearchResult result = jiraConnect().searchIssues(jql);
            if (result.issues.isEmpty()) {
                time = 0;
            }
            for (int i = 0; i < result.issues.size(); i++) {
                time = time + result.issues.get(i).getTimeSpent();
            }
        } catch (JiraException ex) {
            if (ex.getCause() != null)
                log.error(ex.getCause().getMessage());
        }
        log.info(jql + " - RESULT: " + time);
        return time;
    }

    static String issueCount(String str, String jql) {
        return str + " : " + issueCountRest(jql);
    }

    //Лидер по кол-ву решенных обращений за прошлый месяц
    static String maxCountMonthRest() {
        HashMap<String, Integer> arrayOfCounts = new HashMap<String, Integer>();
        arrayOfCounts.put(Consts.NAME_ALEX, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.LEVAS)));
        arrayOfCounts.put(Consts.NAME_ANDREY, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.SMIANA)));
        arrayOfCounts.put(Consts.NAME_JOHN, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.ESIES)));
        arrayOfCounts.put(Consts.NAME_AIDAR, Integer.parseInt(issueCountRest(Consts.ISSUE_CLOSED_PRMONTH + Consts.KUAAE)));

        int maxValueInMap = Collections.max(arrayOfCounts.values());
        String maxName = null;

        for (Map.Entry<String, Integer> entry : arrayOfCounts.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                maxName = entry.getKey();
            }
        }

        StringBuilder maxOfCounts = new StringBuilder();
        maxOfCounts.append(maxName).append(", ").append(maxValueInMap);

        log.info("Максимальное кол-во за прошлый месяц - " + maxOfCounts);

        return maxOfCounts.toString();
    }
}
