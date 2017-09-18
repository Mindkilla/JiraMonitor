package servlets;

import utils.DateUtils;

/**
 * @author Andrey Smirnov
 */
public class Consts
{

    //Jira Api properties
    public static final String JIRA_URL = "https://jira.bssys.com";
    public static final String JIRA_USER = "";
    public static final String JIRA_PASS = "";

    //Имена и логины
    public static final String LEVAS = " LevAS ";
    public static final String SMIANA = " SmiAnA ";
    public static final String ESIES = " EsiES ";
    public static final String KUAAE = " KuaAE ";
    public static final String ZHEEV = " ZheEV ";

    public static final String NAME_ALEX = "Саня";
    public static final String NAME_ANDREY = "Андрей";
    public static final String NAME_JOHN = "Женя";
    public static final String NAME_AIDAR = "Айдар";
    public static final String NAME_ZH = "Жека";

    //Типы обращений
    static final String ISSUE_NAME = "Обращений : ";
    static final String DEFECT_NAME = "Дефектов";
    static final String CONSULT_NAME = "Консультаций";
    static final String WHISH_NAME = "Пожеланий";
    static final String DEFECT = "(10300)";
    static final String CONSULT = "(10304)";
    static final String WHISH = "(10405)";
    static final String NEW = "Новых";
    static final String CLOSED = "Закрыто";
    static final String OPENED = "Открытые";

    //JQL-запросы
    //Открытых\Закрытых обращений за сегодня
    static final String ISSUE_NEW_TODAY = "project = SUPP AND cf[14303]=7 AND createdDate >= -10h";
    static final String ISSUE_CLOSED_TODAY = "project = SUPP AND cf[14303]=7 AND resolutiondate >= -10h AND Status in (6, 10504)";

    //Кол-во АКТИВНЫХ обращений на данный момент для каждого
    static final String ISSUE_ACTIVE_NOW = "project=SUPP AND cf[14303]=7 AND status not in(6, 10504, 10503) AND assignee =";

    //Кол-во ЗАКРЫТЫХ обращений (10405) за текущий месяц для каждого
    static final String ISSUE_CLOSED_MONTH = "project=SUPP AND resolution=10407 and updatedDate>=startOfMonth(1d) AND updateddate<= endOfMonth() AND type not in (10405) AND assignee =";

    //Кол-во ЗАКРЫТЫХ обращений (10405) за прошлый месяц для каждого
    public static final String ISSUE_CLOSED_PRMONTH = "project=SUPP AND resolution=10407 and updatedDate>=" + DateUtils.dataBegin() + " AND updateddate<=" + DateUtils.dataEnd() + " AND type not in (10405) AND assignee =";

    //Все Открытые\Закрытые обращения за месяц
    static final String ISSUE_OPEN_CURMONTH = "project = SUPP AND cf[14303] = 7 AND type not in (10405) AND resolution = Unresolved";
    static final String ISSUE_CLOSED_CURMONTH = "project = SUPP AND cf[14303] = 7 AND type not in (10405) AND resolution = 10407 AND updatedDate >= startOfMonth() AND updateddate <= endOfMonth()";

    //Кол-во ЗАКРЫТЫХ дефектов (10300), консультаций(10304), пожеланий (10304) за месяц
    static final String DEFECT_CLOSED_MONTH = "project = SUPP AND cf[14303] = 7 AND resolution = 10407 AND updatedDate >= startOfMonth() AND updateddate <= endOfMonth() AND type in ";

    //Кол-во ОТКРЫТЫХ дефектов(10300), консультаций(10304)
    static final String DEFECT_OPENED_NOW = "project = SUPP AND cf[14303] = 7 AND type not in (10405) AND resolution = Unresolved AND type in ";

    static final String VACATION_CURRENT_YEAR = "project = SUPP_2_VACATION_DEIS AND resolutiondate <=endOfYear()  AND resolutiondate >=startOfYear() AND assignee =";
    static final String INACTIVITY_CURRENT_YEAR = "project = SUPP_2_INACTIVITY_DEIS AND resolutiondate <=endOfMonth()  AND resolutiondate >=startOfMonth() AND assignee =";

    private Consts()
    {
    }
}
