package com.example.calculator;

import java.io.IOException;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CalculatorServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String currentExpression = (String) request.getSession().getAttribute("expr");
        if (currentExpression == null) currentExpression = "";

        String btn = request.getParameter("btn");

        if (btn.equals("CE")) {
            currentExpression = "";
        } else if (btn.equals("=")) {
            if (!currentExpression.isEmpty()) {
                currentExpression = eval(currentExpression);
            } else {
                currentExpression = "";
            }
        } else {
            currentExpression += btn;
        }

        request.getSession().setAttribute("expr", currentExpression);
        request.setAttribute("result", currentExpression);
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    private String eval(String expr) {
        try {
            return new java.text.DecimalFormat("#.##").format(
                new Object() {
                    int pos = -1, ch;

                    void nextChar() {
                        ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
                    }

                    boolean eat(int charToEat) {
                        while (ch == ' ') nextChar();
                        if (ch == charToEat) {
                            nextChar();
                            return true;
                        }
                        return false;
                    }

                    double parse() {
                        nextChar();
                        double x = parseExpression();
                        if (pos < expr.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                        return x;
                    }

                    double parseExpression() {
                        double x = parseTerm();
                        while (true) {
                            if (eat('+')) x += parseTerm();
                            else if (eat('-')) x -= parseTerm();
                            else return x;
                        }
                    }

                    double parseTerm() {
                        double x = parseFactor();
                        while (true) {
                            if (eat('*')) x *= parseFactor();
                            else if (eat('/')) x /= parseFactor();
                            else return x;
                        }
                    }

                    double parseFactor() {
                        if (eat('+')) return parseFactor();
                        if (eat('-')) return -parseFactor();

                        double x;
                        int startPos = this.pos;
                        if ((ch >= '0' && ch <= '9') || ch == '.') {
                            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                            x = Double.parseDouble(expr.substring(startPos, this.pos));
                        } else {
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        }

                        return x;
                    }
                }.parse());
        } catch (Exception e) {
            return "Error";
        }
    }
}
