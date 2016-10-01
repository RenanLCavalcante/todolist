package br.com.todolist.Controller;

import java.net.URI;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import br.com.todolist.Dao.UsuarioDao;
import br.com.todolist.models.Usuario;

@RestController
public class UsuarioRestController {

	public static final String SECRET = "todolist";
	public static final String ISSUER = "http://www.sp.senai.com.br";

	@Autowired
	UsuarioDao usuarioDao;

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
		try {

			if (this.usuarioDao.contemUsuario(usuario.getLogin()) != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			this.usuarioDao.inserir(usuario);

			URI location = new URI("/usuario/" + usuario.getId());

			return ResponseEntity.created(location).body(usuario);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @RequestMapping(value = "/usuario/{idusuario}", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	// public ResponseEntity<Usuario> buscarPorId (@PathVariable Long
	// idusuario){
	//
	// }

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> logar(@RequestBody Usuario usuario) {
		try {

			if (usuarioDao.logar(usuario) != null) {

				// iat = Horário que foi emitido
				long iat = System.currentTimeMillis() / 1000;

				// data de expiração
				long exp = iat + 60;
				
				//Gerador do token
				JWTSigner signer = new JWTSigner(SECRET);
				
				
				HashMap<String, Object> claims = new HashMap<>();
					
				//adiciona no token
				claims.put("iss", ISSUER);
				
				//adiciona no token
				claims.put("exp", exp);
				
				//adiciona no token
				claims.put("iat", iat);
				
				String jwt = signer.sign(claims);
				
				JSONObject token = new JSONObject();
				
				token.put("token",jwt);
				
				return ResponseEntity.ok(token.toString());
				
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
