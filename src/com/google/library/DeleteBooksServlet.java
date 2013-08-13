package com.google.library;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.hibernate.DAO;

public class DeleteBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteBooksServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int id = Integer.parseInt(request.getParameter("id"));
		DAO.deleteBook(id);
		
		session.setAttribute("BookDeleted", "Success");
		response.sendRedirect("index.jsp");
	}
}
