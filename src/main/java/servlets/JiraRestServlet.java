package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Andrey Smirnov
 */
@WebServlet("/monitor")
public class JiraRestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //Открытых\Закрытых обращений за сегодня
        request.setAttribute("issueNewToday", JiraApi.issueCount(Consts.NEW, Consts.ISSUE_NEW_TODAY));
        request.setAttribute("issueClosedToday", JiraApi.issueCount(Consts.CLOSED, Consts.ISSUE_CLOSED_TODAY));
        //Кол-во АКТИВНЫХ обращений на данный момент для каждого
        request.setAttribute("Levas", JiraApi.issueCount(Consts.NAME_ALEX, Consts.ISSUE_ACTIVE_NOW + Consts.LEVAS));
        request.setAttribute("Esies", JiraApi.issueCount(Consts.NAME_JOHN, Consts.ISSUE_ACTIVE_NOW + Consts.ESIES));
        request.setAttribute("Kuaae", JiraApi.issueCount(Consts.NAME_AIDAR, Consts.ISSUE_ACTIVE_NOW + Consts.KUAAE));
        //Кол-во ЗАКРЫТЫХ обращений за текущий месяц для каждого
        request.setAttribute("LevasС", JiraApi.issueCount(Consts.NAME_ALEX, Consts.ISSUE_CLOSED_MONTH + Consts.LEVAS));
        request.setAttribute("EsiesС", JiraApi.issueCount(Consts.NAME_JOHN, Consts.ISSUE_CLOSED_MONTH + Consts.ESIES));
        request.setAttribute("KuaaeС", JiraApi.issueCount(Consts.NAME_AIDAR, Consts.ISSUE_CLOSED_MONTH + Consts.KUAAE));
        //Лидер по кол-ву решенных обращений за прошлый месяц
        request.setAttribute("Month", Date.getMonthName());
        request.setAttribute("Max", JiraApi.maxCountMonthRest());
        //Открытые закрытые
        request.setAttribute("issueOpenCurmonth", JiraApi.issueCount(Consts.OPENED, Consts.ISSUE_OPEN_CURMONTH));
        request.setAttribute("issueClosedCurmonth", JiraApi.issueCount(Consts.CLOSED, Consts.ISSUE_CLOSED_CURMONTH));
        //ЗАКРЫТЫЕ Дефекты консультации пожелания
        request.setAttribute("defectClosedMonth", JiraApi.issueCount(Consts.DEFECT_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.DEFECT));
        request.setAttribute("consultClosedMonth", JiraApi.issueCount(Consts.CONSULT_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.CONSULT));
        request.setAttribute("whishClosedMonth", JiraApi.issueCount(Consts.WHISH_NAME, Consts.DEFECT_CLOSED_MONTH + Consts.WHISH));
        //Кол-во ОТКРЫТЫХ дефектов, консультаций
        request.setAttribute("defectOpenNow", JiraApi.issueCount(Consts.DEFECT_NAME, Consts.DEFECT_OPENED_NOW + Consts.DEFECT));
        request.setAttribute("consultOpenNow", JiraApi.issueCount(Consts.CONSULT_NAME, Consts.DEFECT_OPENED_NOW + Consts.CONSULT));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
