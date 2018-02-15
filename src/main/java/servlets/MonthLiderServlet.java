package servlets;

import org.json.JSONObject;
import utils.DateUtils;
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
 * Лидер по кол-ву решенных обращений за прошлый месяц
 */
@WebServlet("/monitor/monthLider")
public class MonthLiderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        jsonObject.put("month", DateUtils.getMonthName());
        jsonObject.put("max", JiraApiUtils.getLider());
        response.getWriter().print(jsonObject);
    }
}
