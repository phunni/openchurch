package uk.co.monkeypower.core.webmodule.spec;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class GenericOpenChurchWebModule implements OpenChurchWebModule {
	
	private Map<String, String> properties;
	private String name;
	
	public GenericOpenChurchWebModule(String name){
		this.name = name;
	}

	public abstract void render(HttpServletRequest request, HttpServletResponse resonse);

	public abstract void process(HttpServletRequest request, HttpServletResponse resonse);

	public String getWebModuleName() {
		return name;
	}

	public void setWebModuleName(String name) {
		this.name = name;
		
	}

	public Map<String, String> getProperties() {
		return properties;
	}

}
