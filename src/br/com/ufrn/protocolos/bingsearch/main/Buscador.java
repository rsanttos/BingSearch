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
			
			percorreHTML(htmlPagina);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	public void percorreHTML(String html) {

		html = html.substring(html.indexOf("class=\"b_algo\""), html.lastIndexOf("class=\"b_algo\""));
		String trechoPadrao = "<a href=\"";
		int posicaoInicial = html.indexOf(trechoPadrao) + trechoPadrao.length();
		
		int contador = 0;
		String stringTeste = "";
		while(contador < 1 && posicaoInicial < html.length()) {
			if(html.charAt(posicaoInicial + 1) == '"') {
				contador++;
			}
			stringTeste += html.charAt(posicaoInicial);
			posicaoInicial++;
		}
		
		System.out.println(stringTeste);
		
		html = html.replaceFirst(trechoPadrao, "");
		
		if(html.indexOf(trechoPadrao) > 0) {
			percorreHTML(html);
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
