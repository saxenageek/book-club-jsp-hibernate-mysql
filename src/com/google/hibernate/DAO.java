package com.google.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.library.Books;

public class DAO {
	private static SessionFactory sessionFactory; 
	private static ServiceRegistry serviceRegistry;

	private static void configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static int addNewBooks(String b_name, String b_author, double b_price){
		try{
			configureSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer bookID = null;
		try{
			tx = session.beginTransaction();
			Books book = new Books(b_name, b_author, b_price);
			bookID = (Integer) session.save(book); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return bookID;	
	}

	@SuppressWarnings("unchecked")
	public static List<Books> listAllBooks(int pageNumber){
		int pageSize = 3;
		List<Books> listBooks = null;

		try{
			configureSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Books");
			query = query.setFirstResult(pageSize * (pageNumber - 1));
			query.setMaxResults(pageSize);
			listBooks = query.list();
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null){ 
				tx.rollback();
				listBooks = null;
				e.printStackTrace();
			}
		}finally {
			session.close(); 
		}
		return listBooks;
	}

	public static void deleteBook(int id){
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try{
			configureSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}

		try{
			tx = session.beginTransaction();
			Books book = 
				(Books)session.get(Books.class, id); 
			session.delete(book); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	public static void editBook(int id, String b_name, String b_author,
			Double b_price) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try{
			configureSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}

		try{
			tx = session.beginTransaction();
			Books book = 
				(Books)session.get(Books.class, id); 
			book.setB_name(b_name);
			book.setB_author(b_author);
			book.setB_price(b_price);
			session.update(book); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
}
