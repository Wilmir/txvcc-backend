package com.wilmir.txvcc.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wilmir.txvcc.model.Node;
import com.wilmir.txvcc.model.ServiceModel;

@Repository
public class ServiceDAO implements EntityDAO<ServiceModel>{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public ServiceModel getEntityById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		ServiceModel service = session.get(ServiceModel.class, id);
		
		return service;
	}

	@Override
	public void save(ServiceModel entity) {
		Session session = entityManager.unwrap(Session.class);
		
			Node node = session.get(Node.class, entity.getNode().getId());
			Node homingNode = session.get(Node.class, entity.getHomingNode().getId());

			node.addServiceModeltoNode(entity);
			homingNode.addServiceModeltoHomingNode(entity);
			
		
	}

	@Override
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from ServiceModel where id=:id");
		
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}


	public void deleteByNodeId(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from ServiceModel where node_id=:node_id");
		
		query.setParameter("node_id", id);
		
		query.executeUpdate();
		
	}

}
