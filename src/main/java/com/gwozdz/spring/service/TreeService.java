package com.gwozdz.spring.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwozdz.hibernate.data.Tree;

@Component
public class TreeService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Tree> getAllTrees() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Tree.class).list();
	}
	
	@Transactional
	public Tree getTree(long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Tree) session.get(Tree.class, id);
	}
	
	@Transactional
	public Tree getTree(Tree tree) {
		Session session = sessionFactory.getCurrentSession();
		return (Tree) session.get(Tree.class, tree.getId());
	}
	
	@Transactional
	public void addTree(Tree tree) {
		Session session = sessionFactory.getCurrentSession();
		session.save(tree);
	}
	
	@Transactional
	public void updateTree(Tree tree) {
		Session session = sessionFactory.getCurrentSession();
		session.update(tree);
	}
	
	@Transactional
	public void deleteTree(Tree tree) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(tree);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
