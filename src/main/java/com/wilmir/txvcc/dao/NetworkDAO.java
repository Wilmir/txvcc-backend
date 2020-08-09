package com.wilmir.txvcc.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.User;

@Repository
public class NetworkDAO  implements EntityDAO<Network>{

	@Autowired
	private EntityManager entityManager;
	
	
	public List findAll() {
		Session session = entityManager.unwrap(Session.class);
		
		Query<Network> query = session.createQuery("select id, networkName, description, lastUpdated from Network");
		
		List networks = query.getResultList();
		
		return networks;
	}

	
	public List findAllByUserId(int id) {
	Session session = entityManager.unwrap(Session.class);
		
		Query<Network> query = session.createQuery("select id, networkName, description, lastUpdated from Network where user_id=:id");
		
		query.setParameter("id", id);
		
		List networks = query.getResultList();
		
		return networks;
	}
	
	
	public List findByUserId(int userId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Network> query = session.createQuery("select id, networkName, description, lastUpdated "
														+ "from Network "
														+ "where user_id=:userId");
		
		query.setParameter("userId", userId);
		
		List networks = query.getResultList();
		
		return networks;
	}
	
	@Override
	public Network getEntityById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Network network = session.get(Network.class, id);
		
		return network;
	}

	@Override
	public void save(Network entity) {
		System.out.println("HELLO FROM SAVE NETWORK");
		Session session = entityManager.unwrap(Session.class);	
		User user = session.get(User.class, entity.getUser().getId());
		System.out.println(user.getName());
		
		
		user.addNetwork(entity);
	}
	
	public void update(Network entity) {
		Session session = entityManager.unwrap(Session.class);

		int entityId = entity.getId();

		String name = entity.getNetworkName();
		
		String description = entity.getDescription();
		
		Query query = session.createQuery("update Network set networkName =:networkName, description =:description where id=:networkId");
		
		query.setParameter("networkName", name);
		query.setParameter("description", description);
		query.setParameter("networkId", entityId);
		
		query.executeUpdate();
		
	}

	@Override
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from Network where id=:id");
		
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}


}
