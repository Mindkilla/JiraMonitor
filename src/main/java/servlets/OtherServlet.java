package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mindkilla on 02.01.2017.
 */
@WebServlet("/other")
public class OtherServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //Отпуска
        request.setAttribute("countVacationL", JiraApi.projectTimeCount(Consts.VACATION_CURRENT_YEAR+Consts.LEVAS ));
        request.setAttribute("countVacationE", JiraApi.projectTimeCount(Consts.VACATION_CURRENT_YEAR+Consts.ESIES ));
        request.setAttribute("countVacationK", JiraApi.projectTimeCount(Consts.VACATION_CURRENT_YEAR+Consts.KUAAE ));
        //Простои
        request.setAttribute("countInactivityL", JiraApi.projectTimeCount(Consts.INACTIVITY_CURRENT_YEAR+Consts.LEVAS ));
        request.setAttribute("countInactivityE", JiraApi.projectTimeCount(Consts.INACTIVITY_CURRENT_YEAR+Consts.ESIES ));
        request.setAttribute("countInactivityK", JiraApi.projectTimeCount(Consts.INACTIVITY_CURRENT_YEAR+Consts.KUAAE ));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/other.jsp");
        dispatcher.forward(request, response);
    }
}
