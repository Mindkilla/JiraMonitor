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
 * @date 15.01.2018
 * Открытые закрытые
 */
@WebServlet("/monitor/issueCurrMonth")
public class IssueCurrMonthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.setAttribute("issueCurrMonth", JiraApiUtils.getIssueCurrMonth());
        response.getWriter().print(request.getAttribute("issueCurrMonth").toString());
    }
}
