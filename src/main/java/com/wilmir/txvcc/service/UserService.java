package com.wilmir.txvcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilmir.txvcc.dao.NetworkDAO;
import com.wilmir.txvcc.dao.UserDAO;
import com.wilmir.txvcc.model.Network;
import com.wilmir.txvcc.model.User;

@Service
public class UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private NetworkDAO networkDAO;


	@Transactional
	public User getEntityById(int id) {
		return userDAO.getEntityById(id);
	}
	
	@Transactional
	public User getEntityByUserName(String name) {
		return userDAO.getEntityByUserName(name);
	}
	
	@Transactional
	public void save(User entity) {
		userDAO.save(entity);
	}

	@Transactional
	public void update(User entity) {
		userDAO.update(entity);
	}

	@Transactional
	public void deleteById(int id) {
		userDAO.deleteById(id);
	}
	
	@Transactional
	public List<Network> findByUserId(int userId){
		return networkDAO.findByUserId(userId);
	}
	
}
