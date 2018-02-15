package utils;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrey Smirnov
 */
public class JiraApiUtils {
    private static final Logger LOGGER = Logger.getLogger(JiraApiUtils.class);

    //ОТКРЫТЫХ\ЗАКРЫТЫХ ОБРАЩЕНИЙ ЗА СЕГОДНЯ
    public static String todayIssues() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NEW, Consts.ISSUE_NEW_TODAY);
        userJql.put(Consts.CLOSED, Consts.ISSUE_CLOSED_TODAY);
        return issueCount(userJql);
    }

    //КОЛ-ВО АКТИВНЫХ ОБРАЩЕНИЙ НА ДАННЫЙ МОМЕНТ
    public static String getCurrentActive() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_AIDAR, Consts.ISSUE_ACTIVE_NOW + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.ISSUE_ACTIVE_NOW + Consts.ZHEEV);
        userJql.put(Consts.NAME_JOHN, Consts.ISSUE_ACTIVE_NOW + Consts.ESIES);
        userJql.put(Consts.NAME_ALEX, Consts.ISSUE_ACTIVE_NOW + Consts.LEVAS);
        return issueCount(userJql);
    }

    //КОЛ-ВО ЗАКРЫТЫХ ОБРАЩЕНИЙ ЗА ТЕКУЩИЙ МЕСЯЦ
    public static String getCurrentClosed() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_AIDAR, Consts.ISSUE_CLOSED_MONTH + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.ISSUE_CLOSED_MONTH + Consts.ZHEEV);
        userJql.put(Consts.NAME_JOHN, Consts.ISSUE_CLOSED_MONTH + Consts.ESIES);
        userJql.put(Consts.NAME_ALEX, Consts.ISSUE_CLOSED_MONTH + Consts.LEVAS);
        return issueCount(userJql);
    }

    //КОЛ-ВО ОБРАЩЕНИЙ ЗА ТЕК.МЕСЯЦ
    public static String getIssueCurrMonth() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.OPENED, Consts.ISSUE_OPEN_CURMONTH);
        userJql.put(Consts.CLOSED, Consts.ISSUE_CLOSED_CURMONTH);
        return issueCount(userJql);
    }

    //ЗАКРЫТЫЕ Дефекты консультации пожелания
    public static String getIssueClosedCurrMonth() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.DEFECT_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.DEFECT);
        userJql.put(Consts.CONSULT_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.CONSULT);
        userJql.put(Consts.WHISH_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.WHISH);
        return issueCount(userJql);
    }

    //КОЛ-ВО ОТКРЫТЫХ ОБРАЩЕНИЙ ПО КАТЕГОРИЯМ
    public static String getIssueOpenedCurrMonth() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.DEFECT_NAME, Consts.DEFECT_OPENED_NOW + Consts.DEFECT);
        userJql.put(Consts.CONSULT_NAME, Consts.DEFECT_OPENED_NOW + Consts.CONSULT);
        return issueCount(userJql);
    }

    //Отпуска
    public static String getVacation() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_AIDAR, Consts.VACATION_CURRENT_YEAR + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.VACATION_CURRENT_YEAR + Consts.ZHEEV);
        userJql.put(Consts.NAME_JOHN, Consts.VACATION_CURRENT_YEAR + Consts.ESIES);
        userJql.put(Consts.NAME_ALEX, Consts.VACATION_CURRENT_YEAR + Consts.LEVAS);
        return projectTimeCount(userJql, "day");
    }

    //Простои
    public static String getInactivity() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_AIDAR, Consts.INACTIVITY_CURRENT_YEAR + Consts.KUAAE);
        userJql.put(Consts.NAME_ZH, Consts.INACTIVITY_CURRENT_YEAR + Consts.ZHEEV);
        userJql.put(Consts.NAME_JOHN, Consts.INACTIVITY_CURRENT_YEAR + Consts.ESIES);
        userJql.put(Consts.NAME_ALEX, Consts.INACTIVITY_CURRENT_YEAR + Consts.LEVAS);
        return projectTimeCount(userJql, "hour");
    }

    //Лидер за прошлый месяц
    public static String getLider() {
        HashMap<String, String> userJql = new HashMap<>();
        userJql.put(Consts.NAME_ALEX, Consts.ISSUE_CLOSED_PRMONTH + Consts.LEVAS);
        userJql.put(Consts.NAME_ZH, Consts.ISSUE_CLOSED_PRMONTH + Consts.ZHEEV);
        userJql.put(Consts.NAME_JOHN, Consts.ISSUE_CLOSED_PRMONTH + Consts.ESIES);
        userJql.put(Consts.NAME_AIDAR, Consts.ISSUE_CLOSED_PRMONTH + Consts.KUAAE);
        return maxCountMonthRest(userJql);
    }

    //Connect to jira rest api
    private static JiraClient jiraConnect() {
        BasicCredentials creds = new BasicCredentials(Consts.JIRA_USER, Consts.JIRA_PASS);
        return new JiraClient(Consts.JIRA_URL, creds);
    }

    private static String issueCount(Map<String, String> userJql) {
        return issueCountStr(userJql);
    }

    //Count of issue
    private static String issueCountStr(Map<String, String> userJql) {
        StringBuilder total = new StringBuilder();
        for (Map.Entry<String, String> entry : userJql.entrySet()) {
            try {
                Issue.SearchResult result = jiraConnect().searchIssues(entry.getValue(), "project");
                total.append(entry.getKey());
                total.append(" : ");
                total.append(result.total);
                total.append(Consts.BR);
            } catch (JiraException ex) {
                if (ex.getCause() != null) {
                    LOGGER.error(ex + ex.getCause().getMessage());
                }
            }
        }
        return total.toString();
    }

    //Count of issue to Map
    private static HashMap<String, Integer> issueCountMap(Map<String, String> userJql) {
        HashMap<String, Integer> total = new HashMap<>();
        for (Map.Entry<String, String> entry : userJql.entrySet()) {
            try {
                Issue.SearchResult result = jiraConnect().searchIssues(entry.getValue(), "project");
                total.put(entry.getKey(), result.total);
            } catch (JiraException ex) {
                if (ex.getCause() != null) {
                    LOGGER.error(ex + ex.getCause().getMessage());
                }
            }
        }
        return total;
    }

    //в секундах , можно форматировать результат при выводе куда-либо
    public static Integer projectTimeCount(String jql) {
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
            if (ex.getCause() != null) {
                LOGGER.error(ex + ex.getCause().getMessage());
            }
        }
        LOGGER.info(jql + " - RESULT: " + time);
        return time;
    }

    private static String projectTimeCount(Map<String, String> userJql, String metric) {
        StringBuilder time = new StringBuilder();
        for (Map.Entry<String, String> entry : userJql.entrySet()) {
            int timeInSec = 0;
            try {
                Issue.SearchResult result = jiraConnect().searchIssues(entry.getValue());
                if (result.issues.isEmpty()) {
                    time.append(entry.getKey());
                    time.append(" : ");
                    time.append("0");
                    time.append(Consts.BR);
                    continue;
                }
                for (int i = 0; i < result.issues.size(); i++) {
                    timeInSec = timeInSec + result.issues.get(i).getTimeSpent();
                }
                time.append(entry.getKey());
                time.append(" : ");
                if (metric.equals("day")) {
                    int days = ((timeInSec / 3600) / 8);
                    time.append(days);
                } else {
                    time.append(timeInSec / 3600);
                }
                time.append(Consts.BR);
            } catch (JiraException ex) {
                if (ex.getCause() != null) {
                    LOGGER.error(ex + ex.getCause().getMessage());
                }
            }
        }
        return time.toString();
    }

    //Лидер по кол-ву решенных обращений за прошлый месяц
    private static String maxCountMonthRest(Map<String, String> userJql) {
        HashMap<String, Integer> arrayOfCounts = issueCountMap(userJql);
        int maxValueInMap = Collections.max(arrayOfCounts.values());
        String maxName = null;

        for (Map.Entry<String, Integer> entry : arrayOfCounts.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                maxName = entry.getKey();
            }
        }

        StringBuilder maxOfCounts = new StringBuilder();
        maxOfCounts.append(maxName).append(", ").append(maxValueInMap);

        LOGGER.info("Максимальное кол-во за прошлый месяц - " + maxOfCounts);

        return maxOfCounts.toString();
    }

    private JiraApiUtils() {
    }
}
