package com.wilmir.txvcc.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wilmir.txvcc.model.Network;

@Repository
public class NetworkDAO implements EntityDAO<Network> {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List findAll() {
		Session session = entityManager.unwrap(Session.class);
		
		Query<Network> query = session.createQuery("select id, networkName, description, lastUpdated from Network");
		
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
		Session session = entityManager.unwrap(Session.class);
		
		session.saveOrUpdate(entity);
		
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
