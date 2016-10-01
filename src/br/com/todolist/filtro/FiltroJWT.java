package br.com.todolist.filtro;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.auth0.jwt.JWTVerifier;

import br.com.todolist.Controller.UsuarioRestController;


@WebFilter("/*")
public class FiltroJWT implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (req.getRequestURI().contains("login")) {
			chain.doFilter(request,response);
		}
		
		String token = req.getHeader("Authorization");
		
		try {
			
			JWTVerifier verifier = new JWTVerifier(UsuarioRestController.SECRET);
			
			Map<String,Object> claims = verifier.verify(token);
			
			System.out.println(claims);
			
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			if (token == null) {
				resp.sendError(HttpStatus.UNAUTHORIZED.value());
			}else{
				resp.sendError(HttpStatus.FORBIDDEN.value());
			}
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
