package com.wilmir.txvcc.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wilmir.txvcc.model.Link;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.Node;


@Repository
public class LinkDAO implements EntityDAO<Link>{

	@Autowired
	private EntityManager entityManager;

	@Override
	public Link getEntityById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Link link = session.get(Link.class, id);
		
		return link;
	}

	@Override
	public void save(Link entity) {
		Session session = entityManager.unwrap(Session.class);

		Node source = session.get(Node.class, entity.getSource().getId());

		Node target = session.get(Node.class, entity.getTarget().getId());

		Network network = session.get(Network.class, entity.getNetwork().getId());

		source.addOutgoingLink(entity);

		target.addIncomingLink(entity);

		network.addLink(entity);

	}

	@Override
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<?> query = session.createQuery("delete from Link where id=:id");
		
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}

}
