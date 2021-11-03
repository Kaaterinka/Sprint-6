package com.example.qwer

import java.time.Instant
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/login"])
class AuthServlet : HttpServlet() {
    private val login = "user"
    private val password = "user"

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/auth.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        if (login == req?.getParameter("login") && password == req.getParameter("password")) {
            val cookie = Cookie("auth", Instant.now().toString())
            resp!!.addCookie(cookie)
            resp.sendRedirect("/app/add")
        } else {
            resp!!.sendRedirect("/login")
        }
    }
}