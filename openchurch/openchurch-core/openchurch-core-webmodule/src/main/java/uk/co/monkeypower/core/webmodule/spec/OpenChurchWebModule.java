package uk.co.monkeypower.core.webmodule.spec;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OpenChurchWebModule {
	
	public String getWebModuleName();
	public void setWebModuleName(String name);
	
	public Map<String, String> getProperties();
	
	public void render(HttpServletRequest request, HttpServletResponse resonse);
	public void process(HttpServletRequest request, HttpServletResponse resonse);

}
