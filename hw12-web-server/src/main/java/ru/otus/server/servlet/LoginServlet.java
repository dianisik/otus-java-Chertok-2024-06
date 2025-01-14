package ru.otus.server.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import ru.otus.server.security.UserAuthenticationService;
import ru.otus.server.template.TemplateProcessor;

@SuppressWarnings("java:S1989")
public class LoginServlet extends HttpServlet {
    private static final String LOGIN_TEMPLATE = "login.html";
    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";
    private static final int MAX_INACTIVE_INTERVAL_SECONDS = 30;

    private final transient TemplateProcessor templateProcessor;
    private final transient UserAuthenticationService userService;

    public LoginServlet(TemplateProcessor templateProcessor, UserAuthenticationService userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write(templateProcessor.renderPage(LOGIN_TEMPLATE, Map.of()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter(USERNAME_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);

        if (userService.isAuthenticated(username, password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL_SECONDS);
            resp.sendRedirect("/client");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
