<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.google.hibernate.DAO"%>
<%@page import="com.google.library.Books"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="chrome=1" />
<!-- Optimistically rendering in Chrome Frame in IE. -->
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<c:set var="context" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${context}/css/index.css">
<title>Add New Books</title>
<%
	if (session.isNew()) {
		session.setAttribute("BookAdded", "");
		session.setAttribute("BookDeleted", "");
		session.setAttribute("BookEdited", "");
	} else {
%>
<script type="text/javascript">
	setTimeout(function() {
		$('#message').fadeOut('slow');
	}, 2000);
</script>
<%
	}
%>
<script type="text/javascript">
function deleteBook(id){
	var ans = confirm("Are you sure you want to delete this book?");
	if (ans == true)
	{
		document.getElementsByName("id")[0].value = id;
		document.forms["DeleteBooks"].submit();
	}
	else
	{
	  	return;
	}
}

function editBook(id){
	var b_name = document.getElementById(id).cells[1].innerText;
	var b_author = document.getElementById(id).cells[2].innerText;
	var b_price = document.getElementById(id).cells[3].innerText;
	
	document.getElementById(id).cells[1].innerHTML = "<input type=\"text\" value=\""+ b_name +"\"/>";
	document.getElementById(id).cells[2].innerHTML = "<input type=\"text\" value=\""+ b_author +"\" />";
	document.getElementById(id).cells[3].innerHTML = "<input type=\"text\" value=\""+ b_price +"\" />";
	document.getElementById(id).cells[4].innerHTML = "<img src=\"images/tick.png\" style=\"cursor:pointer;\" onclick=\"saveBook("+id+");\"/>";
	document.getElementById(id).cells[5].innerHTML = "<img src=\"images/cancel.jpg\" style=\"cursor:pointer;\" onclick=\"cancelEdit("+id+",'"+b_name+"','"+b_author+"','"+b_price+"');\" />";
}

function cancelEdit(id,b_name,b_author,b_price){
	document.getElementById(id).cells[1].innerHTML = b_name;
	document.getElementById(id).cells[2].innerHTML = b_author;
	document.getElementById(id).cells[3].innerHTML = b_price;
	document.getElementById(id).cells[4].innerHTML = "<img src=\"images/cancel.png\" style=\"cursor: pointer;\"	onclick=\"deleteBook("+id+");\" />";
	document.getElementById(id).cells[5].innerHTML = "<img src=\"images/edit.jpg\" style=\"cursor: pointer;\" onclick=\"editBook("+id+");\" />";
}

function saveBook(id){
	var b_name = document.getElementById(id).cells[1].childNodes[0].value;
	var b_author = document.getElementById(id).cells[2].childNodes[0].value;
	var b_price = document.getElementById(id).cells[3].childNodes[0].value;

	var ans = confirm("Are you sure you want to save this book?");
	if (ans == true)
	{
		document.getElementsByName("edit_id")[0].value = id;
		document.getElementsByName("edit_name")[0].value = b_name;
		document.getElementsByName("edit_author")[0].value = b_author;
		document.getElementsByName("edit_price")[0].value = b_price;
		document.forms["EditBooks"].submit();
	}
	else
	{
	  	return;
	}
}
</script>
</head>
<body>
<form action="AddBooksServlet" method="post"><label>Add
New Books</label> <br />
<br />
<label for="name">Name:</label> <input type="text" name="name" required
	placeholder="Name" /> <label for="author">Author:</label> <input
	type="text" name="author" required placeholder="Author" /> <label
	for="price">Price:</label> <input type="text" name="price" required
	placeholder="Price" /> <input type="submit" value="Save it Now !!" />
</form>

<%
	if (session.getAttribute("BookAdded").equals("Success")) {
%>
<span id="message" style="margin-left: 20px;">Book Added
Successfully.</span>
<%
	session.setAttribute("BookAdded", "");
	} else if (session.getAttribute("BookAdded").equals("Failed")) {
%>
<span id="message" style="margin-left: 20px;">Database Error.
Please try again.</span>
<%
	session.setAttribute("BookAdded", "");
	}
%>

<%
int pageNumber=1;
if(request.getParameter("page") != null) {
  session.setAttribute("page", request.getParameter("page"));
  pageNumber = Integer.parseInt(request.getParameter("page"));
  pageContext.setAttribute("pageNumber", pageNumber);	
} else {
  session.setAttribute("page", "1");
}
String nextPage = (pageNumber +1) + "";
pageContext.setAttribute("booklist", DAO.listAllBooks(pageNumber));
int size = DAO.listAllBooks(pageNumber).size();
if(size != 0){
	String myUrl = "index.jsp?page=" + nextPage;
	pageContext.setAttribute("myUrl", myUrl);	
}
if(Integer.parseInt(nextPage) > 2){
	int prevPage = Integer.parseInt(nextPage) - 2;
	String prevUrl = "index.jsp?page=" + prevPage;
	pageContext.setAttribute("prevUrl", prevUrl);
}
%>

<form action="DeleteBooksServlet" method="post" name="DeleteBooks">
<input type="hidden" name="id" /></form>

<%
	if (session.getAttribute("BookDeleted").equals("Success")) {
%>
<span id="message" style="margin-left: 20px;">Book Deleted
Successfully.</span>
<%
	session.setAttribute("BookDeleted", "");
	}
%>

<form action="EditBooksServlet" method="post" name="EditBooks"><input
	type="hidden" name="edit_id" /> <input type="hidden" name="edit_name" />
<input type="hidden" name="edit_author" /> <input type="hidden"
	name="edit_price" /></form>

<%
	if (session.getAttribute("BookEdited").equals("Success")) {
%>
<span id="message" style="margin-left: 20px;">Book Updated
Successfully.</span>
<%
	session.setAttribute("BookEdited", "");
	}
%>
<table class="features-table">
	<tbody>
		<thead>
			<tr>
				<td>SNo</td>
				<td>Name</td>
				<td>Author</td>
				<td>Price</td>
			</tr>
		</thead>
		<c:forEach var="books" items="${pageScope.booklist}"
			varStatus="bookLoop">
			<tr id="${books.id}">
				<td>${bookLoop.index + 1}</td>
				<td><c:out value="${books.b_name}" /></td>
				<td><c:out value="${books.b_author}" /></td>
				<td>
				<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${books.b_price}" />
				</td>
				<td><img src="images/cancel.png" style="cursor: pointer;"
					onclick="deleteBook(${books.id});" /></td>
				<td><img src="images/edit.jpg" style="cursor: pointer;"
					onclick="editBook(${books.id});" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="2"></td>
			<td colspan="2"><a href="${pageScope.prevUrl}">prevPage</a></td>
			<td colspan="2"><a href="${pageScope.myUrl}">nextPage</a></td>
		</tr>
	</tbody>
</table>
</body>
</html>