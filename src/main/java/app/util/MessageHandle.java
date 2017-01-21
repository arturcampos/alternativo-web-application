package app.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class MessageHandle {

	/**
	 *
	 * @param message
	 */
	public static void info(String message) {
		FacesContext instance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = instance.getExternalContext();
		instance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Info", message));
		externalContext.getFlash().setKeepMessages(true);
	}

	/**
	 *
	 * @param message
	 */
	public static void warn(String message) {
		FacesContext instance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = instance.getExternalContext();
		instance.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, " Atencao!", message));
		externalContext.getFlash().setKeepMessages(true);
	}

	/**
	 *
	 * @param message
	 */
	public static void error(String message) {
		FacesContext instance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = instance.getExternalContext();
		instance.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, " Erro!", message));
		externalContext.getFlash().setKeepMessages(true);
	}

	/**
	 *
	 * @param message
	 */
	public static void fatal(String message) {
		FacesContext instance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = instance.getExternalContext();
		instance.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, " Fatal!", message));
		externalContext.getFlash().setKeepMessages(true);
	}
}
