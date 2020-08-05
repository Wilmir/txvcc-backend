package com.wilmir.txvcc.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;

@Repository
public class NodeDAO implements EntityDAO<Node> {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Node> findAll() {
		Session session = entityManager.unwrap(Session.class);
		
		Query<Node> query = session.createQuery("from Node", Node.class);
		
		List<Node> nodes = query.getResultList();
		
		return nodes;
	}

	@Override
	public Node getEntityById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Node node = session.get(Node.class, id);		
		
		return node;
	}

	@Override
	public void save(Node entity) {
		
		Session session = entityManager.unwrap(Session.class);
		
		if(entity.getId() == 0) {
			Network network = session.get(Network.class, entity.getNetwork().getId());
			
			network.addNode(entity);
		
		}else {
			session.merge(entity);
		}
	}
	
	
	public void update(Node entity) {
		Session session = entityManager.unwrap(Session.class);
		
		System.out.println("We are in NodeDAO");
		
		Query query = session.createQuery("update Node "
				+ "set name=:name, isHoming=:isHoming, network_id = :networkId "
				+ "where id=:id");
	
		query.setParameter("name", entity.getName());
		query.setParameter("isHoming", entity.isHoming());
		query.setParameter("networkId", entity.getNetwork().getId());
		query.setParameter("id", entity.getId());
		
		query.executeUpdate();
		
	}
	
	
	@Override
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from Node where id=:id");
		
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}


	

}
