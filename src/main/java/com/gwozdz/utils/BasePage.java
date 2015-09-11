package com.gwozdz.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class BasePage {
	
	protected void addMessage(String str) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, str, null));
	}
	
	protected void addWarning(String str) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, str, null));
	}
}
