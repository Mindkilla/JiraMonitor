package servlets;

import utils.JiraApiUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andrey Smirnov
 * @date 16.01.2018
 * Отпуска
 */
@WebServlet("/other/vacation")
public class VacationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.setAttribute("vacation", JiraApiUtils.getVacation());
        response.getWriter().print(request.getAttribute("vacation").toString());
    }
}
