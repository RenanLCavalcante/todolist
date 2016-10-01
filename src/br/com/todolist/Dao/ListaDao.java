package br.com.todolist.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.todolist.models.ItemLista;
import br.com.todolist.models.Lista;

@Repository
public class ListaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public boolean inserir(Lista lista) {

		entityManager.persist(lista);

		return true;
	}

	public List<Lista> listar() {
		List<Lista> listlista = entityManager.createQuery("FROM Lista", Lista.class).getResultList();

		return listlista;
	}

	@Transactional
	public boolean atualizar(Lista lista) {

		entityManager.merge(lista);

		return true;
	}

	@Transactional
	public boolean excluir(Long id) {

		Lista lista = entityManager.find(Lista.class, id);

		entityManager.remove(lista);

		return true;
	}
	
	@Transactional
	public boolean excluirItem(Long id){
		
		ItemLista item = this.entityManager.find(ItemLista.class, id);
		
		this.entityManager.remove(item);
		
		return true;
	}
	
	public Lista buscarPorId(Long id){
		Lista objLista = this.entityManager.find(Lista.class,id);
		return objLista;
	}
	
}
