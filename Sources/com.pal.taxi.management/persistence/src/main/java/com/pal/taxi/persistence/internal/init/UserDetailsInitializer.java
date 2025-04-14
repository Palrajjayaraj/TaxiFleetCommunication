package com.pal.taxi.persistence.internal.init;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;

import com.pal.taxi.persistence.entities.UserEntity;

/**
 * Initializer for user's table.
 * 
 * @author Palraj
 */
public class UserDetailsInitializer implements IInitializer{

	private final List<String> names = List.of("Palraj Jayaraj", "Ravi Raj", "Priya Rani", "Kavya Nair", "Vijay Kumar", "Anjali Ravi",
			"Senthil Kumar", "Mani Shankar", "Lakshmi Vasanth", "Bala Raj", "Sundar Kumar", "Keerthi Rani",
			"Rajesh Kumar", "Pooja Ram", "Saravanan P", "Vishnu Ravi", "Tharini Raj", "Karthik Sundaram",
			"Vasuki Kumar", "Pradeep Rani", "Sridhar V", "Anbu Ravi", "Priyanka R", "Ashok Shankar", "Aruna Raj",
			"Sangeetha Nair", "Krishna Iyer", "Shanthi Kumar", "Jayalakshmi M", "Gokul Ravi", "Nandhini Shankar");
	
	@Override
	public void initialize(Session session) {
		insertInitialUsers(session);
	}

	private void insertInitialUsers(Session session) {
		for (String name : names) {
			UserEntity entity = new UserEntity();
			entity.setUuid(UUID.randomUUID());
			entity.setName(name);
			session.persist(entity);
		}
	}
}
