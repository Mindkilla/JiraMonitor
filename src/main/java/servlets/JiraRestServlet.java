package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/s")
public class JiraRestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //Открытых\Закрытых обращений за сегодня
        request.setAttribute("issueNewToday", JiraApi.issueNewToday());
        request.setAttribute("issueClosedToday", JiraApi.issueClosedToday());
        //Кол-во АКТИВНЫХ обращений на данный момент для каждого
        request.setAttribute("Levas", JiraApi.issueActiveNow(Consts.NAME_ALEX, Consts.LEVAS));
        request.setAttribute("Smiana", JiraApi.issueActiveNow(Consts.NAME_ANDREY, Consts.SMIANA));
        request.setAttribute("Esies", JiraApi.issueActiveNow(Consts.NAME_JOHN, Consts.ESIES));
        request.setAttribute("Kuaae", JiraApi.issueActiveNow(Consts.NAME_AIDAR, Consts.KUAAE));
        //Кол-во ЗАКРЫТЫХ обращений за текущий месяц для каждого
        request.setAttribute("LevasС", JiraApi.issueClosedMonth(Consts.NAME_ALEX, Consts.LEVAS));
        request.setAttribute("SmianaС", JiraApi.issueClosedMonth(Consts.NAME_ANDREY, Consts.SMIANA));
        request.setAttribute("EsiesС", JiraApi.issueClosedMonth(Consts.NAME_JOHN, Consts.ESIES));
        request.setAttribute("KuaaeС", JiraApi.issueClosedMonth(Consts.NAME_AIDAR, Consts.KUAAE));
        //Лидер по кол-ву решенных обращений за прошлый месяц
        request.setAttribute("Max",JiraApi.maxCountMonthRest());
        //Открытые закрытые
        request.setAttribute("issueOpenCurmonth", JiraApi.issueOpenCurmonth());
        request.setAttribute("issueClosedCurmonth", JiraApi.issueClosedCurmonth());
        //ЗАКРЫТЫЕ Дефекты консультации пожелания
        request.setAttribute("defectClosedMonth", JiraApi.defectClosedMonth(Consts.DEFECT_NAME, Consts.DEFECT));
        request.setAttribute("consultClosedMonth", JiraApi.defectClosedMonth(Consts.CONSULT_NAME, Consts.CONSULT));
        request.setAttribute("whishClosedMonth", JiraApi.defectClosedMonth(Consts.WHISH_NAME, Consts.WHISH));
        //Кол-во ОТКРЫТЫХ дефектов, консультаций
        request.setAttribute("defectOpenNow", JiraApi.defectOpenedNow(Consts.DEFECT_NAME, Consts.DEFECT));
        request.setAttribute("consultOpenNow", JiraApi.defectOpenedNow(Consts.CONSULT_NAME, Consts.CONSULT));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
