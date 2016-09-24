package br.com.todolist.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.todolist.models.ItemLista;
import br.com.todolist.models.Lista;

@Repository
public class ItemDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public boolean marcarFeito(Long id, boolean feito){
		ItemLista item = this.entityManager.find(ItemLista.class,id);
		
		item.setFeito(feito);
		
		this.entityManager.merge(item);
		
		return true;
	}
	
	@Transactional
	public boolean inserir(Long idLista, ItemLista item){
		
		item.setLista(this.entityManager.find(Lista.class,idLista));
		
		this.entityManager.persist(item);
		
		return true;
	}

}
