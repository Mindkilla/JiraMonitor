package servlets;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Mindkilla on 10.12.2016.
 */
public class JiraApi {
    private static final Logger log = Logger.getLogger(JiraApi.class);

    //Connect to jira rest api
    private static JiraClient jiraConnect(){
        BasicCredentials creds = new BasicCredentials(Consts.JIRA_USER, Consts.JIRA_PASSWORD);
        return new JiraClient(Consts.JIRA_URL, creds);
    }

    //Count of issue NAME for period
    public static String issueCountRest(String jql) {
        JiraClient conect = jiraConnect();
        Integer total = null;
        try {
            Issue.SearchResult result = conect.searchIssues(jql,"project");
            total = result.total;
        } catch (JiraException ex) {
            if (ex.getCause() != null)
                log.error(ex.getCause().getMessage());
        }
        log.info(jql+" - RESULT: "+total);
        return Integer.toString (total) ;
    }

    //Открытых\Закрытых обращений за сегодня
    public static String issueNewToday(){
        return "Новых : "+issueCountRest(Consts.ISSUE_NEW_TODAY);
    }

    public static String issueClosedToday(){
        return "Закрыто: "+issueCountRest(Consts.ISSUE_CLOSED_TODAY);
    }

    //Кол-во АКТИВНЫХ обращений на данный момент для каждого
    public static String issueActiveNow(String fName, String name){
        return fName+" : "+issueCountRest(Consts.ISSUE_ACTIVE_NOW+name);
    }

    //Кол-во ЗАКРЫТЫХ обращений за текущий месяц для каждого
    public static String issueClosedMonth(String fName, String name){
        return fName+" : "+issueCountRest(Consts.ISSUE_CLOSED_MONTH+name);
    }

    //Все Открытые\Закрытые обращения за месяц
    public static String issueOpenCurmonth(){
        return "Открытые: "+issueCountRest(Consts.ISSUE_OPEN_CURMONTH);
    }

    public static String issueClosedCurmonth(){
        return "Закрытые: "+issueCountRest(Consts.ISSUE_CLOSED_CURMONTH);
    }


    //Кол-во ЗАКРЫТЫХ дефектов (10300), консультаций(10304), пожеланий (10405) за месяц
    public static String defectClosedMonth(String name, String jql){
        return name+issueCountRest(Consts.DEFECT_CLOSED_MONTH+jql);
    }

    //Кол-во ОТКРЫТЫХ дефектов(10300), консультаций(10304)
    public static String defectOpenedNow(String name, String jql){
        return name+issueCountRest(Consts.DEFECT_OPENED_NOW+jql);
    }

    //Кол-во ЗАКРЫТЫХ обращений за предыдущий месяц для каждого
    public static String issueClosedPMonth(String name){
        return issueCountRest(Consts.ISSUE_CLOSED_PRMONTH+name);
    }

    //Лидер по кол-ву решенных обращений за прошлый месяц
    public static StringBuilder maxCountMonthRest()
    {
        Hashtable<String, Integer> arrayOfCounts  = new Hashtable<String, Integer>();
        arrayOfCounts.put(Consts.NAME_ALEX, Integer.parseInt(issueClosedPMonth(Consts.LEVAS)));
        arrayOfCounts.put(Consts.NAME_ANDREY, Integer.parseInt(issueClosedPMonth(Consts.SMIANA)));
        arrayOfCounts.put(Consts.NAME_JOHN, Integer.parseInt(issueClosedPMonth(Consts.ESIES)));
        arrayOfCounts.put(Consts.NAME_AIDAR, Integer.parseInt(issueClosedPMonth(Consts.KUAAE)));

        int maxValueInMap=(Collections.max(arrayOfCounts.values()));
        String maxName = null;

        for(Map.Entry<String,Integer> entry : arrayOfCounts.entrySet()) {
            if (entry.getValue() == maxValueInMap)
            {
                maxName = entry.getKey();
            }
        }

        StringBuilder maxOfCounts = new StringBuilder();
        maxOfCounts.append(maxName).append(", ").append(maxValueInMap);

        log.info("Максимальное кол-во за прошлый месяц - "+ maxOfCounts);

        return maxOfCounts;
    }
}
