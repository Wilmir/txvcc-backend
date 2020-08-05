package com.wilmir.txvcc.controller;

import java.util.List;

public interface EntityController<T> {
	
	public List<T> findAll();
	
	public T getEntityById(int id);
	
	public T save(T entity);
	
	public T updateEntity(T entity);

	public void deleteEntity(int id);
	
}
