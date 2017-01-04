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
@WebServlet("/other")
public class OtherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //Отпуска
        request.setAttribute("countVacationL", JiraApi.projectTimeCount(Consts.VACATION_CURRENT_YEAR + Consts.LEVAS) / 3600 / 8);
        request.setAttribute("countVacationE", JiraApi.projectTimeCount(Consts.VACATION_CURRENT_YEAR + Consts.ESIES) / 3600 / 8);
        request.setAttribute("countVacationK", JiraApi.projectTimeCount(Consts.VACATION_CURRENT_YEAR + Consts.KUAAE) / 3600 / 8);
        //Простои
        request.setAttribute("countInactivityL", JiraApi.projectTimeCount(Consts.INACTIVITY_CURRENT_YEAR + Consts.LEVAS) / 3600);
        request.setAttribute("countInactivityE", JiraApi.projectTimeCount(Consts.INACTIVITY_CURRENT_YEAR + Consts.ESIES) / 3600);
        request.setAttribute("countInactivityK", JiraApi.projectTimeCount(Consts.INACTIVITY_CURRENT_YEAR + Consts.KUAAE) / 3600);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/other.jsp");
        dispatcher.forward(request, response);
    }
}
