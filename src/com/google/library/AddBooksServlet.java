package com.google.library;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.hibernate.DAO;

public class AddBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AddBooksServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String b_name = request.getParameter("name");
		String b_author = request.getParameter("author");
		double b_price = Double.parseDouble(request.getParameter("price"));
		
		int res = 0;
		try {
			res = DAO.addNewBooks(b_name, b_author, b_price);
		} catch (Exception e) {
			System.out.println("Error:\t"+e);
		}
		if(res > 1){
			session.setAttribute("BookAdded", "Success");
			response.sendRedirect("index.jsp");
		}
		else{
			session.setAttribute("BookAdded", "Failed");
			response.sendRedirect("index.jsp");
		}
	}
}
