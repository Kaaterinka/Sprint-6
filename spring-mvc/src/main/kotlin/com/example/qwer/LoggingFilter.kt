package com.example.qwer

import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class LoggingFilter : HttpFilter() {

    private lateinit var context: ServletContext


    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {

        val requestURL = request!!.requestURL
        this.context.log("Request URL: $requestURL")
        try {
            chain!!.doFilter(request, response)
        } finally {
            val status = response!!.status
            this.context.log("Response status: $status")
        }
    }
}