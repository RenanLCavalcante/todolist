package br.com.todolist.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.todolist.models.Lista;

@Repository
public class ListaDao {

	@PersistenceContext
	private EntityManager entityManager;

	public boolean inserir(Lista lista) {
		try {
			entityManager.persist(lista);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean alterar(Lista lista){
		try {
			entityManager.merge(lista);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
