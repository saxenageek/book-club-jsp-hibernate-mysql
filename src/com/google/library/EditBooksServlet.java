package com.google.library;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.hibernate.DAO;

public class EditBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EditBooksServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int id = Integer.parseInt(request.getParameter("edit_id"));
		String b_name = request.getParameter("edit_name");
		String b_author = request.getParameter("edit_author");
		Double b_price = Double.parseDouble(request.getParameter("edit_price"));
		DAO.editBook(id, b_name, b_author, b_price);
		
		session.setAttribute("BookEdited", "Success");
		response.sendRedirect("index.jsp");
	}
}
