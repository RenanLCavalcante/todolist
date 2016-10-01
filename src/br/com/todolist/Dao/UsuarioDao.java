package br.com.todolist.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.todolist.models.Usuario;

@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public boolean inserir(Usuario usuario) {
		this.em.persist(usuario);
		return true;
	}

	public List<Usuario> listar() {
		List<Usuario> usuarios = this.em.createQuery("FROM Usuario", Usuario.class).getResultList();
		return usuarios;
	}

	public Usuario contemUsuario(String login) {
		try {
			return this.em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
					.setParameter("login", login).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Usuario buscarPorId(Long idusuario) {
		try {
			return this.em.createQuery("SELECT u FROM Usuario u WHERE u.id = :idusuario", Usuario.class)
					.setParameter("idusuario", idusuario).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Usuario logar(Usuario usuario) {
		try {
			return this.em
					.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", Usuario.class)
					.setParameter("login", usuario.getLogin()).setParameter("senha", usuario.getSenha())
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
}
