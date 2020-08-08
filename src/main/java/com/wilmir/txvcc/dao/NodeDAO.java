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
public class NodeDAO implements EntityDAO<Node>{

	@Autowired
	private EntityManager entityManager;

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
	
	@Override
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from Node where id=:id");
		
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}
}
