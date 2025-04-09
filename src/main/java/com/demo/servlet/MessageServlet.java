package com.demo.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.demo.messaging.MQProducer;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {

    @Inject
    MQProducer producer;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // index.htmlを表示するためにリダイレクト
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String message = request.getParameter("message");

        try {
            String result = producer.sendMessage(message);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>" + result + "</h1>");
            out.println("<a href=\"MessageServlet\">Back</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException("Error sending message to JMS queue", e);
        }
    }
}
