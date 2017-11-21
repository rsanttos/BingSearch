package br.com.ufrn.protocolos.bingsearch.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Buscador {

	String urlPrincipal = "http://www.bing.com/search?";

	public void realizaBusca(String textoBusca, int qtdResultados) {
		try {
			URL u = new URL(geraURLCompleta(urlPrincipal, textoBusca, qtdResultados));
			InputStream in = u.openStream();
			int c;
			String htmlPagina = "";
			while ((c = in.read()) != -1) {
				//System.out.print((char) c);
				htmlPagina += (char) c;
			}
			in.close();
			
			System.out.println("***********************************");
			System.out.println(htmlPagina.indexOf("class=\"b_algo\""));
			System.out.println(htmlPagina.lastIndexOf("class=\"b_algo\""));
			System.out.println(htmlPagina.substring(htmlPagina.indexOf("class=\"b_algo\""), htmlPagina.lastIndexOf("class=\"b_algo\"")));
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public String trataTextoBusca(String textoBusca) {
		if (textoBusca.contains(" ")) {
			return textoBusca.replace(" ", "+");
		} else {
			return textoBusca;
		}
	}
	
	public String geraURLCompleta(String urlPrincipal, String textoBusca, int qtdResultados) {
		String urlCompleta = "";
		
		urlCompleta = urlPrincipal + "q=" + trataTextoBusca(textoBusca) + "&count=" + qtdResultados;
		
		return urlCompleta;
	}

}
