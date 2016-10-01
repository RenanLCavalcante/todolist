package br.com.todolist.Controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.todolist.Dao.ListaDao;
import br.com.todolist.models.ItemLista;
import br.com.todolist.models.Lista;

@RestController
public class ListaRestController {

	@Autowired
	private ListaDao listaDao;

	@RequestMapping(value = "/lista", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Lista> inserir(@RequestBody String strLista) {
		try {

			JSONObject jsonObject = new JSONObject(strLista);

			Lista lista = new Lista();

			lista.setTitulo(jsonObject.getString("titulo"));

			List<ItemLista> itens = new ArrayList<>();

			JSONArray arrayItens = jsonObject.getJSONArray("itens");

			for (int i = 0; i < arrayItens.length(); i++) {
				ItemLista item = new ItemLista();
				item.setDescricao(arrayItens.getString(i));
				item.setLista(lista);
				itens.add(item);
			}
			lista.setItens(itens);

			listaDao.inserir(lista);

			URI location = new URI("/todo/" + lista.getId());

			return ResponseEntity.created(location).body(lista);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Lista> listar() {
		return listaDao.listar();
	}

	@RequestMapping(value = "/lista/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
		listaDao.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/item/{idItem}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removerItem(@PathVariable(value = "idItem") Long idItem) {
		listaDao.excluirItem(idItem);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/lista/{idItem}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Lista> buscarPorId(@PathVariable(value = "idItem") Long id) {
		
		Lista iLista = listaDao.buscarPorId(id);
		
		if (!iLista.equals(null)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(iLista);
	}
}
