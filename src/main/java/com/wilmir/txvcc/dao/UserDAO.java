package com.wilmir.txvcc.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wilmir.txvcc.model.User;

@Repository
public class UserDAO{

	@Autowired
	private EntityManager entityManager;


	public User getEntityById(int id) {
		Session session = entityManager.unwrap(Session.class);

		User user = session.get(User.class, id);
		
		return user;
	}

	public User getEntityByUserName(String username) {
		Session session = entityManager.unwrap(Session.class);

		Query<User> query = session.createQuery("from User where username=:username", User.class);
		
		query.setParameter("username", username);

	
		return query.getSingleResult();
		
	}
	
	public void save(User entity) {
		Session session = entityManager.unwrap(Session.class);
		
		session.saveOrUpdate(entity);		
	}

	public void update(User entity) {
		Session session = entityManager.unwrap(Session.class);

		int entityId = entity.getId();

		String name = entity.getName();
		
		String username = entity.getUsername();
		
		String password = entity.getPassword();
				
		String email = entity.getEmail();
		
		Query query = session.createQuery("update User set name =:name, password =:password, email =:email, username=:username where id=:id");
		
		query.setParameter("name", name);
		query.setParameter("password", password);
		query.setParameter("email", email);
		query.setParameter("useranme", username);
		query.setParameter("id", entityId);
		
		query.executeUpdate();
	}
	
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from User where id=:id");
		
		query.setParameter("id", id);
		
		query.executeUpdate();		
	}

}
