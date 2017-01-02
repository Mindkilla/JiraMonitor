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
@WebServlet("/vacation")
public class VacationServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        request.setAttribute("countVacationL", JiraApi.countVacation(Consts.VACATION_CURRENT_YEAR+Consts.LEVAS ));
        request.setAttribute("countVacationE", JiraApi.countVacation(Consts.VACATION_CURRENT_YEAR+Consts.ESIES ));
        request.setAttribute("countVacationK", JiraApi.countVacation(Consts.VACATION_CURRENT_YEAR+Consts.KUAAE ));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/vacation.jsp");
        dispatcher.forward(request, response);
    }
}
