package com.wilmir.txvcc.service;

import java.util.List;

public interface EntityService<T> {

	public List<T> findAll();
	
	public T getEntityById(int id);
	
	public void save(T entity);
	
	public void update(T entity);
	
	public void deleteById(int id);
}
