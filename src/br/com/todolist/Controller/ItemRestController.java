package br.com.todolist.Controller;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.todolist.Dao.ItemDao;
import br.com.todolist.models.ItemLista;

@Controller
public class ItemRestController {

	@Autowired
	private ItemDao itemDao;

	@RequestMapping(value = "/item/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> marcarFeito(@PathVariable("id") Long id, @RequestBody String feito) {
		try {
			JSONObject jasonObject = new JSONObject(feito);

			itemDao.marcarFeito(id, jasonObject.getBoolean("feito"));

			HttpHeaders responseHeader = new HttpHeaders();

			URI location = new URI("/item/" + id);

			responseHeader.setLocation(location);

			return new ResponseEntity<Void>(responseHeader, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/lista/{idLista}/item", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ItemLista> addItem(@PathVariable Long idLista,@RequestBody ItemLista itemLista) {
		try {
			itemDao.inserir(idLista, itemLista);
			
			URI location = new URI("/item/" + itemLista.getId());
			
			return ResponseEntity.created(location).body(itemLista);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ItemLista>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
