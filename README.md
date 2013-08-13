This is just a one page application containing simple implementation of 

1. jstl
2. hibernate (add, update, delete)
3. hibernate using mysql
4. hibernate with POJO
5. dynamic data updation using javascript
6. a bit of CSS
7. servlets
8. the actual path where hibernate configuration files should be stored
9. also contains all the jars required for jstl and hibernate
10. pagination using hibernate

For running this project you require mysql.

You also need to create a table named "books" in a database named "library".

CREATE TABLE `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `b_name` varchar(200) DEFAULT NULL,
  `b_author` varchar(200) DEFAULT NULL,
  `b_price` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8$$;

I have used Tomcat 6 for this project.

