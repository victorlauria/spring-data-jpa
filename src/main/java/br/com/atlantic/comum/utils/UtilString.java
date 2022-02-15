package br.com.atlantic.comum.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * @author sonda86 - Frederico Sousa Oliveira
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UtilString {

	//Log
	private static Log log = new Log("UtilString");
	
	public static String retirarCaracteresComProblema(String textoParaRetirar) {
	    
	    char charNaoReconhecido = 146;
	    textoParaRetirar = textoParaRetirar.replace(charNaoReconhecido, '�');
	    
	    return textoParaRetirar;
	}
	
	/**
	 * @param nomeServidor
	 * @return booleano indicando se nome e valido ou nao.
	 */
	public static boolean validarNome(String nomeOriginal) {
		
		/*
		 * Esse metodo tende a validar o nome do servidor ou dependente, 
		 * seguindo em principio as regras:
		 * 
		 * a) Um unico caracter seguido de espacos em branco.
		 * b) Um unico caracter seguido de ponto final.
		 * 
		 * Se ocorrer tais situacoes, indico tal nomeOriginal como invalido.
		 * 
		 */
		try {
			
			if (nomeOriginal != null) {
				if (!nomeOriginal.equals("")) {
					String parteNome1 = "";
					String parteNome2 = "";
					StringTokenizer st1 = new StringTokenizer(nomeOriginal);
					StringTokenizer st2;
				    while (st1.hasMoreTokens()) {
				    	parteNome1 = st1.nextToken(" ");
				    	st2 = new StringTokenizer(parteNome1);
				    	while (st2.hasMoreTokens()) {
				    		parteNome2 = st2.nextToken(".");
					    	if (parteNome2.length()==1) {
					    		return false;
					    	}
					    }
				    }
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		}
		catch (Exception e) {
			System.out.println("UtilString.validarNome:nomeOriginal: "+nomeOriginal);
			log.log("UtilString.validarNome:Exception: "+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	    return true;
	}
	
	/**
	 * @param origem
	 * @return String original se diferente de NULO ou "" se NULO.
	 */
	public static String isNull(String origem) {
		
		if (origem==null) {
			return "";	
		}
		else {
			return origem;
		}
	}
	
	/**
	 * @param origem
	 * @return 4 ultimos digitos de uma String.
	 */
	public static String retornaUltimos4Caracteres(String origem) {
		
		if (!origem.equals("")) {
			return origem.substring(origem.length()-4,origem.length());	
		}
		else return "";
	}
	
	/**
	 * @param origem com ponto e virgula.
	 * @return string sem ponto e virgula.
	 */
	public static String retirarPontosEVirgulas(String origem) {
		
		String destino = "";
		String origemRetornada = "";
		origemRetornada = UtilString.isNull(origem);
		
		if (!origemRetornada.equals("")) {
			try {
				StringTokenizer st1 = new StringTokenizer(origemRetornada);
				while (st1.hasMoreTokens()) {
			    	destino = destino + st1.nextToken(". ,");
			    }
			}
			catch (Exception e) {
				log.log("UtilString.retirarPontosEVirgulas:Exception: "+ e.getMessage());
				e.printStackTrace();
			}			
		}
		return destino;
	}
	
	/**
	 * Trata parâmetros da string de tela de origem. Substitui o separador !@ por &amp;.
	 * @param telaOrigemForm String (atributo telaOrigem de um BaseActionForm, por exemplo).
	 * @return String reformatada
	 */
	public static String trataParametrosTelaOrigem(String telaOrigemForm) {

	    String telaOrigem  = Utils.trim(telaOrigemForm);
	    String[] _telaOrigem = {};

	    if( "".equals(telaOrigem) ) {
		return telaOrigem;
	    }

	    if(telaOrigem != null){
		_telaOrigem = telaOrigem.split("!@");
		for(int t=0; t < _telaOrigem.length; t++){
		    if(t == 0){
			telaOrigem = _telaOrigem[t];
		    }else{
			telaOrigem = telaOrigem + "&amp;" + _telaOrigem[t];
		    }
		}
	    }

	    return telaOrigem;
	}
	
	@Deprecated
	public static String retornaStringComZerosAEsquerda(String origem, int tamanhoMaxOrigem) {
	    
	    if (origem.trim().length() == tamanhoMaxOrigem) {
		return origem;
	    } else if (origem.trim().length() < tamanhoMaxOrigem) {
		
		int i = 0;
		String stringComZero = "";
		while (i<tamanhoMaxOrigem-origem.trim().length()) {
		    i++;
		    stringComZero += "0";
		}
		return stringComZero+origem;
	    }
	    return origem;
	}
	
	public static String getNumeros(String value) {   
	        StringBuffer result = new StringBuffer();   
	  
	        for (int i = 0; i < value.length(); i++)   
	            if (Character.isDigit(value.charAt(i)))   
	                result.append(value.charAt(i));   
	  
	        return result.toString();   
	}
	
	public static String[] retornaListaComIndices(String valorString, String separador, String valorAProcurarExatamente) {
	    
	    if (valorString.length() > 0) {
		    String[] valores = valorString.split(separador);
		    
		    int j = 1;
		    for (int i=0; i<valores.length;i++) {

			if (!valores[i].equals(valorAProcurarExatamente)) {
			    valores[i] = "";
			} else {
			    valores[i] = String.valueOf(j);
			}
			j++;
		    }
		    return valores;
	    }
	    return null;
	}
	
	public static String[] retornaListaComIndices(String valorString, String separador, String valorAProcurarExatamente, String constanteParaSerConcatenada) {
	    
	    if (valorString.length() > 0) {
		    String[] valores = valorString.split(separador);
		    
		    int j = 1;
		    for (int i=0; i<valores.length;i++) {

			if (!valores[i].equals(valorAProcurarExatamente)) {
			    valores[i] = "";
			} else {
			    valores[i] = constanteParaSerConcatenada + String.valueOf(j);
			}
			j++;
		    }
		    return valores;
	    }
	    return null;
	}
	
	/**
	 * @param origem
	 * Exemplo: João da Silva Alves
	 * Exceções: para as exceções não podemos colocar a 1a letra em maiúscula. O restante, devemos!
	 * @return
	 */
	public static String capitalizeStringExceptConect(String origem) {
		String[] excecoes = new String[]{"da","das","de","di","do","dos"};
		String retorno = "";
		boolean concatena = true;
		if(origem != "" && origem != null){
			String[] nomes = origem.split(" ");
			//Tratamento
			for(int pos=0; pos<nomes.length; pos++){
				if(!nomes[pos].equals("")){
					for(String excecao: excecoes){
						if(nomes[pos].equalsIgnoreCase(excecao)){
							concatena = false;
							break;
						}else{
							concatena = true;
						}
					}
					//Faz parte das exceções?
					//Não
					if(concatena){
						if(nomes[pos].length() == 1){
							nomes[pos] = StringUtils.lowerCase(nomes[pos]);
							retorno += nomes[pos] + " ";
							continue;
						}else{
							nomes[pos] = StringUtils.capitalize(nomes[pos].substring(0,1)) + StringUtils.lowerCase(nomes[pos].substring(1));
							retorno += nomes[pos] + " ";
							continue;
						}
					}
					//Sim 
					else{
						nomes[pos] = StringUtils.lowerCase(nomes[pos]);
						retorno += nomes[pos] + " ";
					}
				}
			}
		}
		return retorno.trim();
	}
	
	public static void main(String[] args) {
	
	    String nome="cassiano DI giovanni";
	    System.out.println(capitalizeStringExceptConect(nome));
	    
	    
	    /*System.out.println(UtilString.retirarPontosEVirgulas(""));
	    System.out.println(UtilString.getNumeros("&&&AAã24444ççç-/sss"));
	    
	    String[] colunaAfastamentoAr = "Outros;Comprej".split(";");
		for (int i=0; i<colunaAfastamentoAr.length; i++) {
		    String colunaAfastamento = colunaAfastamentoAr[i];
		}
		
		String dataSolicitacao = "01/02/2010";
		String mes = dataSolicitacao.substring(3,5);
		String ano = dataSolicitacao.substring(6,10);
		
		System.out.println((int)(192 * 0.8));
	    
		String msg = "this";   
		  
	        // usando metodo depreciado   
	        String out = StringUtils.capitaliseAllWords( msg );   
	  
	        System.out.println( out );   
	  
	        // uma palavra por vez   
	        String[] words = msg.trim().split( " " );   
	        out = "";   
	        for ( int i = 0; i < words.length; i++ ) {   
	            words[ i ] = StringUtils.capitalise( words[ i ] );   
	            out += words[ i ] + " ";   
	        }   
	  
	        System.out.println( out );   
		
		
		System.out.println(UtilString.retornaListaComIndices("N,N,S,N,S",",","S"));*/
		
	}
	
	/**
	 * Esse método é responsável por receber uma lista de Strings e retornar os 
	 * valores separados por vírgula, porém o último registro terá o 'e ' em vez de vírgula! 
	 * 
	 * Exemplo com nenhum registro : ""
	 * Exemplo com 1 registro      : renan1
	 * Exemplo com 2 registros     : renan1 e renan2
	 * Exemplo com 3 registros     : renan1, renan2 e renan3
	 * Exemplo com 4 registros     : renan1, renan2, renan3 e renan4
	 * 
	 * Autor: Renan Watanabe, 12/09/2016.
	 */
    public static String obterValoresSeparadosPorVirgulaFinalizandoComE(List<String> listaValores){
    	
        String valores = "";
        if(listaValores != null && !listaValores.isEmpty()) {

            //Remover os espaços em branco nos valores da lista.
            List<String> listaValoresSemEspacos = new ArrayList<String>();
            for(String valor : listaValores){
                listaValoresSemEspacos.add(valor.trim());
            }

            valores = listaValoresSemEspacos.toString();
            valores = valores.substring(1);                       //Remover a barra Inicial '[' .
            valores = valores.substring(0, valores.length() - 1); //Remover a barra Final   ']' .

            if (listaValoresSemEspacos.size() > 1) {
                int posicaoUltimaVirgula = valores.lastIndexOf(",");

                valores = new StringBuilder(valores).replace(posicaoUltimaVirgula,
                                                             posicaoUltimaVirgula + 1,
                                                             " e").toString();
            }
        }
        return valores;
    }
    
    /**
     * [TASK 27413] - Readequação tela Comprev.
     * Autor: Renan Watanabe, 10/10/2016.
     * 
     * Esse método recebe um array, e dependendo do tamanho dele, retorna uma String com os valores de "?" para 
     * ser utilizado em query.
     * Exemplo com nenhum registro ou [0]  = retorna "";
     * Exemplo com tamanho [1]             = (?)
     * Exemplo com tamanho [2]             = (?,?)
     * Exemplo com tamanho [3]             = (?,?,?)
     */
    public static String obterInterrogacoesParametros(String[] array){
    	StringBuilder sb = new StringBuilder();
    	
    	if(array != null && array.length != 0){
    	    sb.append("(");
     	        for(int contador = 0; contador < array.length; contador++){
		    if (contador > 0){
		        sb.append(", ");
		    }
		    sb.append("?");
		}
    	    sb.append(")");
    	}
        return sb.toString();
    }
    
    public static String obterInterrogacoesParametros(List<String> lista){
	if(lista == null || lista.isEmpty()) {
	    return "";
	}else {
            String[] array = new String[lista.size()];
            return obterInterrogacoesParametros(lista.toArray(array));
	}
    }
    
    
    /**
     * [TASK 27413] - Readequação tela Comprev.
     * Exemplo com um registro = 'renan'
     * Exemplo com dois registros = 'renan1,renan2'
     * Exemplo com três registros = 'renan1,renan2,renan3'
     * Autor: Renan Watanabe, 10/10/2016.
     */
    public static String obterValoresSeparadosPorVirgula(String[] array){
    	StringBuilder sb = new StringBuilder();
    	
    	if(array != null && array.length != 0){
     	        for(int contador = 0; contador < array.length; contador++){
		    if (contador > 0){
		        sb.append(",");
		    }
		    sb.append(array[contador]);
		}
    	}
        return sb.toString();
    }
    
    
    /**
     * [TASK 27413] - Método utilitário que remove espaços em brancos que são desnecessários ou mal formatados.
     * Ou seja, apaga os espaços em brancos adicionais e os espaços em branco no início e fim da String.
     * 
     * Por exemplo : "    SELECT    *    FROM  DUAL   ";
     * Resultado   : "SELECT * FROM DUAL";
     * 
     * Autor:  Renan Watanabe, Data: 20/10/2016.   
     */
    public static String removerEspacosEmBrancoDesnecessarios(String stringComEspacosEmBranco){
	
	if(stringComEspacosEmBranco != null && !stringComEspacosEmBranco.isEmpty()){
		stringComEspacosEmBranco = stringComEspacosEmBranco.replaceAll("\\s+"," ");
		stringComEspacosEmBranco = stringComEspacosEmBranco.trim();
	}
	return stringComEspacosEmBranco;
    }
    
    public static String completarProtocoloComZeros(String numeroProtocolo) {
        if(numeroProtocolo == null || numeroProtocolo.isEmpty()) {
	    return "";
	}else {
			
	    numeroProtocolo = numeroProtocolo.trim();
			
	    if(numeroProtocolo.length() < 10) {
	        int qtdZerosFaltantes =  10 - numeroProtocolo.length();
				
		StringBuilder sbZeros = new StringBuilder();
		for(int contador = 0; contador < qtdZerosFaltantes; contador++) {
		    sbZeros.append("0");
		}
				
		numeroProtocolo = sbZeros.toString().concat(numeroProtocolo);
				
	    }
	}
		
	return numeroProtocolo;
    }
    
    
    public static String obterValoresSeparadosPorPontoVirgula(List<String> listaValores){
    	
	StringBuilder sb = new StringBuilder();
        if(listaValores != null && !listaValores.isEmpty()) {

            for(String valor : listaValores) {
        	sb.append(valor).append(";");
            }
  
        }
        return sb.toString();
    }
    
    public static String completarPVComZeros(String numeroProtocolo) {
        if(numeroProtocolo == null || numeroProtocolo.isEmpty()) {
	    return "";
	}else {
			
	    numeroProtocolo = numeroProtocolo.trim();
			
	    if(numeroProtocolo.length() < 2) {
	        int qtdZerosFaltantes =  2 - numeroProtocolo.length();
				
		StringBuilder sbZeros = new StringBuilder();
		for(int contador = 0; contador < qtdZerosFaltantes; contador++) {
		    sbZeros.append("0");
		}
				
		numeroProtocolo = sbZeros.toString().concat(numeroProtocolo);
				
	    }
	}
		
	return numeroProtocolo;
    }

    /**
     * Método que adiciona zeros a esquerda do valor.
     * Exemplos : 
     *       valor: "renan", tamanho : 10 -> retorna "00000renan"
     *       valor: "123" ,  tamanho : 5  -> retorna "00123"
     *       valor: "123" ,  tamanho : 3  -> retorna "123"
     *       valor: "123" ,  tamanho : 2  -> retorna "123"
     *       valor: "123" ,  tamanho : 0  -> retorna "123"
     *       valor: ""    ,  tamanho : 3  -> retorna "000"
     *       valor: null  ,  tamanho : 3  -> retorna "000"
     *       
     *   Autor: Renan Watanabe 
     *   Data: 17/04/2019.
     */
    public static String retornaValorComZerosAEsquerda(String valor, int tamanhoMaximoValor) {
	if(valor == null) {
	    valor = "";
	}
	
        valor = valor.trim();
        if(valor.length() >= tamanhoMaximoValor) {
            return valor;
	}
	    
	String zerosConcatenados = "";
        for(int contador = 0; contador < tamanhoMaximoValor - valor.length(); contador++) {
            zerosConcatenados += "0";
        }
            
        return zerosConcatenados + valor;
    }
    
    /**
     * Verifica se o valor informado é igual a um dos valores do array de String.
     */
    public static boolean isValorDentroDoArray(String valor, String[] array){
	if(array != null){
	    for(int i=0; i < array.length; i++){
		if(valor.trim().equals(array[i].trim())){
		    return true;
		}
	    }
	}
        return false;
    }
    
    public static String obterValorDecimalApenasPonto(String valor) {
	String valorFormatado = valor;
	if(valor.contains(".") && valor.contains(",")) { // Se tiver ponto e virgula
	    valorFormatado = valor.replace(".", "").replace(",", ".");
	}else if(valor.contains(".")) { //Se tiver apenas ponto, entao nao muda nada.
	    valorFormatado = valor; 
	}else if(valor.contains(",")) { //Se tiver apenas virgula.
	    valorFormatado = valor.replace(",", ".");
	}
	return valorFormatado;
    }
    
    public static String obterValorDecimalApenasVirgula(String valor) {
	String valorFormatado = valor;
	if(valor.contains(".") && valor.contains(",")) { // Se tiver ponto e virgula
	    valorFormatado = valor.replace(".", "");
	}else if(valor.contains(".")) { //Se tiver apenas ponto
	    valorFormatado = valor.replace(".", ",");
	}else if(valor.contains(",")) { //Se tiver apenas virgula. nao muda nada
	    valorFormatado = valor;
	}
	return valorFormatado;
    }
    
    public static String obterSimNaoVazio(String valor) {
	if(valor == null || valor.isEmpty()) {
	    return "";
	}else if(valor.equalsIgnoreCase("S")) {
	    return "Sim";
	}else if(valor.equalsIgnoreCase("N")) {
	    return "Não";
	}
	return valor;
    }
    
    
    public static String obterValoresSeparadosPorBarra(List<String> listaValores, boolean isDeveTirarUltimaBarra){
	String valorFinal = "";
	StringBuilder sb = new StringBuilder();
	if(listaValores != null && !listaValores.isEmpty()) {

	    for(String valor : listaValores) {
		sb.append(valor).append("/");
	    }

	}

	valorFinal = sb.toString();
	if(isDeveTirarUltimaBarra) {
	    if(valorFinal.length() != 0) {
		String ultimoCaractere = valorFinal.substring(valorFinal.length() -1 , valorFinal.length());
		if(ultimoCaractere.equals("/")) {
		    valorFinal = valorFinal.substring(0 , valorFinal.length() -1);
		}
	    }

	}
	return valorFinal;
    }
    
    public static String obterValoresSeparadosPorVirgula(List<String> listaValores){
	if(listaValores.isEmpty()) {
	    return "";
	}else {
	    return obterValoresSeparadosPorVirgula(listaValores.toArray(new String[listaValores.size()]));
	}
    }
    
    public static String obterEspacosVazios(int quantidadeEspacos) {
	StringBuilder sb = new StringBuilder();

	for(int contador = 0; contador < quantidadeEspacos; contador++){
	    sb.append(" ");
	}

	return sb.toString();
    }
}
