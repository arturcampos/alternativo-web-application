package app.control;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import app.dao.UsuarioDAO;
import app.model.Usuario;
 
@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginManagedBean {
 
      private UsuarioDAO usuarioDAO = new UsuarioDAO();
      private Usuario usuario = new Usuario();
      
      public String login() {
    	  
    	  	boolean valido = usuarioDAO.ValidaUsuario(usuario.getNomeUsuario(), usuario.getSenha());
            if ( valido ) {
               
            	HttpSession session = SessionBean.getSession();
                session.setAttribute("nomeUsuario",usuario.getNomeUsuario() );
   
                  return "Inicio";
            } else {
            	
            	FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Usuario e Senha incorretos",
                                "Por favor retorne � p�gina de login"));
            	return "login";
            }
           
      }
 
      public Usuario getUsuario() {
            return usuario;
      }
 
      public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
      }
      
     public String logout() {
       HttpSession session = SessionBean.getSession();
       session.invalidate();
          return "login";
      }
      
}