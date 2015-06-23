package com.jiaoya.dao;

import com.jiaoya.line.dao.TestDAO;
import com.jiaoya.line.dao.TestDAOImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.jiaoya.line.dao.entity.Person;

public class Test {
	public static void main(String[] args) {

        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:spring-hibernate.xml");

        TestDAO testDAO = (TestDAOImpl)beanFactory.getBean("testDao");
        Person person = testDAO.get(1L);

        System.out.println(person);
        System.out.println(testDAO.loadAll());
	}

}
