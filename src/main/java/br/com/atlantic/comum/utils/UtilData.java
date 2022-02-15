package br.com.atlantic.comum.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sonda86 - Frederico Sousa Oliveira
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UtilData {
    
    	public static final String TIMESTAMP_PATTERN = "dd/MM/yyyy HH:mm:ss";
    	public static final String TIMESTAMP_BANCO = "yyyy-MM-dd HH:mm:ss.S";
    	
	private Calendar cal;
	private Date date;

	/**
	 * @param dataA formato dd/mm/yyyy
	 * @param dataB formato dd/mm/yyyy
	 * @return 
	 * 	   -1 se dataA � anterior a data B
	 * 	   0 se dataA � igual dataB
	 * 	   1 se dataA � maior q dataB
	 * 	   
	 */
	private static SimpleDateFormat simpleDateFormatParaComapreTo = new SimpleDateFormat("dd/MM/yyyy");
	
	private static SimpleDateFormat simpleDateFormatComHorario = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private static String formatoBR = "d' de 'MMMM' de 'yyyy"; 

	public static String getDataPorExtenso(Date data){
		SimpleDateFormat format = new SimpleDateFormat(formatoBR);
		return format.format(data);
	}

	public static int compareTo(String dataA, String dataB) throws ParseException {

		// ------------------
		GregorianCalendar dataA_AsCalendar = new GregorianCalendar();
		dataA_AsCalendar.setTime(simpleDateFormatParaComapreTo.parse(dataA));

		GregorianCalendar dataB_AsCalendar = new GregorianCalendar();
		dataB_AsCalendar.setTime(simpleDateFormatParaComapreTo.parse(dataB));

		return dataA_AsCalendar.compareTo(dataB_AsCalendar);
	}

	/**
	 * Construtor para a classe
	 */
	public UtilData() {
		cal = Calendar.getInstance();
		date = new Date();
		cal.setTime(date);
	}

	/**
	 * @return dia atual.
	 */
	public String getDiaAtual() {
		int diaInt = cal.get(Calendar.DAY_OF_MONTH);
		String dia = "";
		if (diaInt < 10) {
			dia = "0" + diaInt;
		} else {
			dia = String.valueOf(diaInt);
		}
		return dia;
	}

	/**
	 * @return mes atual.
	 */
	public String getMesAtual() {
		int mesInt = cal.get(Calendar.MONTH) + 1;
		String mes = "";
		if (mesInt < 10) {
			mes = "0" + mesInt;
		} else {
			mes = String.valueOf(mesInt);
		}
		return mes;
	}

	/**
	 * @return ano atual.
	 */
	public String getAnoAtual() {
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	/**
	 * @return hora atual.
	 */
	public String getHoraAtual() {
		int horaInt = cal.get(Calendar.HOUR_OF_DAY);
		String hora = "";
		if (horaInt < 10) {
			hora = "0" + horaInt;
		} else {
			hora = String.valueOf(horaInt);
		}
		return hora;
	}



	/**
	 * @return minuto atual.
	 */
	public String getMinutoAtual() {
		int minutoInt = cal.get(Calendar.MINUTE);
		String minuto = "";
		if (minutoInt < 10) {
			minuto = "0" + minutoInt;
		} else {
			minuto = String.valueOf(minutoInt);
		}
		return minuto;
	}

	/**
	 * @return segundo atual.
	 */
	public String getSegundoAtual() {
		int segundoInt = cal.get(Calendar.SECOND);
		String segundo = "";
		if (segundoInt < 10) {
			segundo = "0" + segundoInt;
		} else {
			segundo = String.valueOf(segundoInt);
		}
		return segundo;
	}

	/**
	 * @return ANO, MES e DIA atual concatenados.
	 */
	public String getAnoMesDiaAtual() {
		return this.getAnoAtual() + this.getMesAtual() + this.getDiaAtual();
	}
	
	/**
	 * @return DIA,MES E ANO atual concatenados.
	 */
	public String getDiaMesAnoAtual() {
		return this.getDiaAtual()  + this.getMesAtual()+ this.getAnoAtual();
	}

	public String getMesAnoAtual() {
		return this.getMesAtual() + "/" + this.getAnoAtual();
	}

	/**
	 * @return HORA, MINUTO, SEGUNDO atual concatendados.
	 */
	public String getHoraMinutoSegundoAtual() {
		return this.getHoraAtual() + this.getMinutoAtual() + this.getSegundoAtual();
	}

	/**
	 * 
	 * @param data (dd/MM/yyyy)
	 * @return 
	 * @throws Exception 
	 */
	public static int getAno(String data) throws Exception{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(simpleDateFormatParaComapreTo.parse(data));
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @param data (dd/MM/yyyy)
	 * @return (1 = Janeiro .. 12 = Dezembro)
	 * @throws Exception 
	 */
	public static int getMes(String data) throws Exception{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(simpleDateFormatParaComapreTo.parse(data));
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @param data (dd/MM/yyyy)
	 * @return 
	 * @throws Exception 
	 */
	public static int getDia(String data) throws Exception{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(simpleDateFormatParaComapreTo.parse(data));
		return cal.get(Calendar.DAY_OF_MONTH);
	}



	/**
	 * @param origem 1923-1-5 12:00:00.0
	 * @return 19130105 (AAAAMMDD)
	 */
	public static String retornaAAAAMMDD(String origem) {

		try {
			if (!origem.equals("")) {

				String diaRetornado = "";
				String mesRetornado = "";
				String anoRetornado = "";
				String horaRetornada = "";
				StringTokenizer st1 = new StringTokenizer(origem);
				while (st1.hasMoreTokens()) {

					anoRetornado = st1.nextToken("- ");
					if (anoRetornado.length() > 4) {
						anoRetornado = anoRetornado.substring(0, 4);
					}

					mesRetornado = st1.nextToken("- ");
					if (mesRetornado.length() == 1) {
						mesRetornado = "0" + mesRetornado;
					}

					diaRetornado = st1.nextToken("- ");
					if (diaRetornado.length() == 1) {
						diaRetornado = "0" + diaRetornado;
					}

					horaRetornada = st1.nextToken("- ");
				}
				return anoRetornado + mesRetornado + diaRetornado;
			} else
				return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Buscando um array que conter� anos, meses e dias (nessa ordem) em rela��o a uma determinada quantidade de dias.
	 * @param qtdeDias
	 * @return Array onde: 1a posi��o - anos, 2a posi��o - meses, 3a posi��o - dias.
	 */
	public static String[] retornaAnosMesesDias(long qtdeDias) {
		
		//Dias dos meses no ano segundo calend�rio de previd�ncia.
		int[] diasAno = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		String[] anosMesesDias = new String[3];

		//Buscando os anos -> Dividindo por 365 dias.
		long anos = qtdeDias / 365;
		anosMesesDias[0] = String.valueOf(anos);

		long resultAnosDias = anos * 365;
		long restante = qtdeDias - resultAnosDias;

		//Buscando os meses e dias.
		int numMeses = 0;
		long numDias = 0;
		for (int i = 0; i < diasAno.length; i++) {
			if (restante >= diasAno[i]) {
				numMeses++;
				restante = restante - diasAno[i];
			} else {
				numDias = restante;
				break;
			}
		}

		anosMesesDias[1] = String.valueOf(numMeses);
		anosMesesDias[2] = String.valueOf(numDias);

		return anosMesesDias;
	}
	
	/**
	 * [ TT25844 ] TASK3245 V7 TESTES
	 * http://www.guj.com.br/java/251985-algoritimo-para-calcular-a-idade-da-pessoaresolvido
	 * @param qtdeDias
	 * @return
	 */
	public static BigDecimal retornaAnos(long qtdeDias) {
	    BigDecimal ano = new BigDecimal(365.25);  
	    BigDecimal idade = new BigDecimal(qtdeDias).divide(ano,0, RoundingMode.DOWN); 
	    return idade;
	}
	
	/**
	 * Buscando um array que conter� anos, meses e dias (nessa ordem) em rela��o a uma determinada quantidade de dias.
	 * @param qtdeDias
	 * @return Array onde: 1a posi��o - anos, 2a posi��o - meses, 3a posi��o - dias.
	 * @deprecated
	 */
	public static String[] retornaAnosMesesDiasPm(long qtdeDias) {
		Long inicio = new Date().getTime();
		long dias = 0;
		long meses = 0;
		long anos = 0;
		Integer[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		for(long varFor1 = 0, varFor2 = 0; qtdeDias >= varFor2; ++varFor1){
			if(varFor1 == 12){
				varFor1 = 0;
				anos += 1;
			}
			dias = qtdeDias - varFor2;
			varFor2 += diasPorMes[(int)varFor1];
			meses = varFor1;
		}
		
		String[] retorno = new String[3];
		retorno[0] = String.valueOf(anos);
		retorno[1] = String.valueOf(meses);
		retorno[2] = String.valueOf(dias);
		
		Long fim = new Date().getTime(); 
		//System.out.println("Tempo de execu��o retornaAnosMesesDiasPm\t:" + (fim - inicio));
		return retorno;
	}
	
	
	public static Integer qtdadeAnosBissextos(Integer inicio, Integer fim){
		Integer anos = 0;
		Date data = new Date();

		if(isBissexto(inicio)){
			--inicio;
		}
		
		if(isBissexto(fim)){
			++fim;
		}
		
		while(inicio < fim){
			if(isBissexto(++inicio)){
				++anos;
			}
		}
		
		return anos;
	}
	
	public static Boolean isBissexto(Integer valor){
		return ((valor % 4 == 0) && (!(valor % 100 == 0)) || (valor % 400 == 0));
	}

	/**
	 * Buscando um array que conter� anos, meses e dias (nessa ordem) em rela��o a uma determinada quantidade de dias.
	 * Estaremos utilizando como padr�o meses de 30 dias e ano 365 dias.
	 * Mudan�a: 13/06/2011.
	 * @param qtdeDias
	 * @return Array onde: 1a posi��o - anos, 2a posi��o - meses, 3a posi��o - dias.
	 */
	public static String[] retornaNovoAnosMesesDias(int qtdeDias) {

		//Dias dos meses no ano segundo calend�rio de previd�ncia.
		//int[] diasAno = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int[] diasAno = { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };

		String[] anosMesesDias = new String[3];

		//Buscando os anos -> Dividindo por 365 dias.
		int anos = qtdeDias / 365;
		anosMesesDias[0] = String.valueOf(anos);

		int resultAnosDias = anos * 365;
		int restante = qtdeDias - resultAnosDias;

		//Buscando os meses e dias.
		int numMeses = 0;
		int numDias = 0;
		for (int i = 0; i < diasAno.length; i++) {
			if (restante >= diasAno[i]) {
				numMeses++;
				restante = restante - diasAno[i];
				/*
				 * [ TT23001 ] TESTE TASK3371
				 * Modifica��o: Frederico Oliveira, Data: 24/04/2015
				 */
				numDias = restante;
			} else {
				numDias = restante;
				break;
			}
		}

		anosMesesDias[1] = String.valueOf(numMeses);
		anosMesesDias[2] = String.valueOf(numDias);

		/*
		 * [ TT20230 ] Sigeprev - Ano
		 * Modifica��o: Frederico Oliveira, Data: 18/11/2014
		 * Fazendo uma transforma��o quando meses = 12 e dias = 0.
		 * Adiciona um ano e meses = 0.
		 */
		/*
		 * [ TASK3371 ] REVERS�O TT22748
		 * Modifica��o: Frederico Oliveira, Data: 24/04/2015
		 * N�o mais fazemos a transforma��o.
		 */
		/*
		if ( (numMeses == 12) && (numDias == 0) ) {
		    anos = anos + 1;
		    anosMesesDias[0] = String.valueOf(anos);
		    
		    numMeses = 0;
		    anosMesesDias[1] = String.valueOf(numMeses);
		}
		*/
		
		/*
		 * [ TASK3517 ] Restri��o e parametriza��o dos motivos da Evolu��o Funcional
		 * Modifica��o: Frederico Oliveira, Data: 11/09/2015
		 * Se meses = 12, ent�o somamos esta quantidade em dias nos dias que se encontram na parcela de dias. 
		 */
		if (anosMesesDias[1].equals("12")) {
		
		    //Seta o valor vazio.
		    anosMesesDias[1] = "";
		    
		    //Numero de dias = numero de dias + (12 meses * 30 dias).
		    numDias += (12 * 30);
		    anosMesesDias[2] = String.valueOf(numDias);
		}
		
		return anosMesesDias;
	}
	

	public static long calcDaysBetweenDates(Date startDate, Date endDate) {

		Calendar data = Calendar.getInstance();
		data.setTime(startDate);

		Calendar endData = Calendar.getInstance();
		endData.setTime(endDate);

		return ((endData.getTimeInMillis() - data.getTimeInMillis()) / 86400000);
	}

	public static double diferencaEmDias(Date dataInicial, Date dataFinal) {
		double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24; //resultado � diferen�a entre as datas em dias   
		long horasRestantes = (diferenca / 1000) / 60 / 60 % 24; //calcula as horas restantes   
		result = diferencaEmDias + (horasRestantes / 24d); //transforma as horas restantes em fra��o de dias   

		//return (int)Math.ceil(result);
		return result;
	}
	
	/**
	 * Retorna a diferen�a em segundos entre duas datas.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	public static long diferencaEmSegundos(Date dataInicial, Date dataFinal) {
	    long diferencaSegundos = (dataFinal.getTime() - dataInicial.getTime()) / (1000);
	    return diferencaSegundos;
	}

	public static int calcDaysBetween(String dateIni, String dateFim) throws Exception {

		Calendar inicio = Calendar.getInstance();
		inicio.setTime(Utils.strToDate(dateIni));

		Calendar fim = Calendar.getInstance();
		fim.setTime(Utils.strToDate(dateFim));

		if (dateIni.equals(dateFim)) {
			return 0;
		}

		if (inicio.compareTo(fim) == 1) {
			throw new RuntimeException("Erro: Data Inicio � maior que a Data de Fim. Favor verificar o per�odo de data.");
		}

		if (inicio.compareTo(fim) == 0) {
			return 0;
		}

		int totalDias = 0;

		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");

		int fimDia = fim.get(Calendar.DAY_OF_MONTH);
		int fimMes = fim.get(Calendar.MONTH);
		int fimAno = fim.get(Calendar.YEAR);

		while (true) {

			inicio.add(Calendar.DAY_OF_YEAR, 1);
			totalDias++;

			int dia = inicio.get(Calendar.DAY_OF_MONTH);
			int mes = inicio.get(Calendar.MONTH);
			int ano = inicio.get(Calendar.YEAR);

			if (mes == fimMes && ano == fimAno && dia == fimDia) {
				break;
			}
		}

		return totalDias;
	}


	/**
	 * Converte uma data do tipo String para Date
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String data) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		return format.parse(data);
	}

	/**
	 * Converte uma data para String ("dd/MM/yyyy")
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static String toString(Date data) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		return format.format(data);
	}
	
	/**
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static String toStringDataEHora(Date data) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		return format.format(data);
	}
	

	public static String subtrairDia(String data, int qtdDias) throws ParseException{
		String res = null;
		if("".equals(data))res = "";
		if(data!=null && !"".equals(data)){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(toDate(data));
			int day = cal.get(Calendar.DAY_OF_MONTH);
			day -= qtdDias;
			cal.set(Calendar.DAY_OF_MONTH, day);
			res = toString(cal.getTime());
		}
		return res;
	}

	public static String adicionarDia(String data, int qtdDias) throws ParseException{
		String res = null;
		if("".equals(data))res = "";
		if(data!=null && !"".equals(data)){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(toDate(data));
			int day = cal.get(Calendar.DAY_OF_MONTH);
			day += qtdDias;
			cal.set(Calendar.DAY_OF_MONTH, day);
			res = toString(cal.getTime());
		}
		return res;
	}
	
	/**
	 * [ TASK3245 ] VTC 3 - CONTAGEM GERAL
	 * @param data
	 * @param qtdAnos
	 * @return
	 * @throws ParseException
	 */
	public static String adicionarAno(String data, int qtdAnos) throws ParseException{
		String res = null;
		if("".equals(data))res = "";
		if(data!=null && !"".equals(data)){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(toDate(data));
			cal.add(Calendar.YEAR, qtdAnos);
			res = toString(cal.getTime());
		}
		return res;
	}

	/**
	 * @author Adriano Silva
	 * @category metodo para formatar a data para o relatorio FRCTS_Inatividade_PM
	 * n�o considera mes escrito de forma incorreta e dia "d" mes de ano (dia "de" mes de ano � v�lido) 
	 * @param data
	 * @return
	 */
	public static String formatDataFRCTS(String data) {
		String dia = data.substring(0, 2).trim();
	        
		//Modificado:David, Data:19/12/2014  TT20795
		//Inicio TT20795 -Estava com erro no mes de dezembro onde  removia todos os "de"
		//String dataFormatada = data.replaceAll(" ", "").replaceAll("de", "").toLowerCase();
		String dataFormatada = data.replaceAll(" de ","").replaceAll(" ", "").toLowerCase();
		//Fim TT20795
		String ano;

		//valida o dia para que retorne o dia com 2 digitos
		if(!dia.startsWith("0")){
			dia = dia.replaceAll("[a-z]", "");
			if(dia.length() < 2){
				dataFormatada  = "0".concat(dataFormatada);	
			}
		}

		//valida o ano para que retorne o ano com 2 digitos
		ano = dataFormatada.substring(dataFormatada.length() - 4);
		ano = ano.replaceAll("[a-z]", "");

		if(ano.length() > 2){
			ano = ano.substring(2);
			dataFormatada = dataFormatada.substring(0, dataFormatada.length() - 4);
			dataFormatada = dataFormatada.concat(ano);
		}else{
			dataFormatada = dataFormatada.substring(0, dataFormatada.length() - 2);
			dataFormatada = dataFormatada.concat(ano);
		}

		String mes = dataFormatada.substring(2, 5).toUpperCase();

		if(dataFormatada.contains("janeiro")){
			dataFormatada = dataFormatada.replaceAll("janeiro", mes);

		}else if(dataFormatada.contains("fevereiro")){
			dataFormatada = dataFormatada.replaceAll("fevereiro", mes);

		}else if(dataFormatada.contains("mar�o") ||  dataFormatada.contains("marco")){
			if(dataFormatada.contains("mar�o")){
				dataFormatada = dataFormatada.replaceAll("mar�o", mes);
			}else{
				dataFormatada = dataFormatada.replaceAll("marco", mes);				
			}

		}else if(dataFormatada.contains("abril")){
			dataFormatada = dataFormatada.replaceAll("abril", mes);

		}else if(dataFormatada.contains("maio")){
			dataFormatada = dataFormatada.replaceAll("maio", mes);

		}else if(dataFormatada.contains("junho")){
			dataFormatada = dataFormatada.replaceAll("junho", mes);

		}else if(dataFormatada.contains("julho")){
			dataFormatada = dataFormatada.replaceAll("julho", mes);

		}else if(dataFormatada.contains("agosto")){
			dataFormatada = dataFormatada.replaceAll("agosto", mes);

		}else if(dataFormatada.contains("setembro")){
			dataFormatada = dataFormatada.replaceAll("setembro", mes);

		}else if(dataFormatada.contains("outubro")){
			dataFormatada = dataFormatada.replaceAll("outubro", mes);

		}else if(dataFormatada.contains("novembro")){
			dataFormatada = dataFormatada.replaceAll("novembro", mes);

		}else{
			dataFormatada = dataFormatada.replaceAll("dezembro", mes);
		}

		return dataFormatada;
	}

	/**
	 * @author Adriano Silva
	 * @since 05/10/2012
	 * @param data
	 * @return
	 */
	public static String formatDataMilitar(String data){
		String dataFormatada;
		
		try{
			
		dataFormatada = data;
		String arrayData[] = data.contains("-")?data.split("-"):data.split("/");
		String mes = arrayData[1];        		
		String ano = arrayData[2];

		ano = ano.substring(2);

		if(mes.contains("01")){
			mes = "JAN";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("02")){
			mes = "FEV";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("03")){
			mes = "MAR";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("04")){
			mes = "ABR";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("05")){
			mes = "MAI";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("06")){
			mes = "JUN";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("07")){
			mes = "JUL";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("08")){
			mes = "AGO";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("09")){
			mes = "SET";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("10")){
			mes = "OUT";
			dataFormatada = arrayData[0] + mes + ano;

		}else if(mes.contains("11")){
			mes = "NOV";
			dataFormatada = arrayData[0] + mes + ano;

		}else{
			mes = "DEZ";
			dataFormatada = arrayData[0] + mes + ano;
		}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return dataFormatada;
	} 

	/**
	 * @author Adriano Silva
	 * @since 05/12/2012
	 * @param data
	 * @return
	 */
	public static String formatDataMilitar2(String data){
		String dataFormatada;
		String mes;
		String retorno = "";
		
		try{
			dataFormatada = data.replaceAll("/", "-");
			String arrayData[] = dataFormatada.split("-");
			int dia = Integer.parseInt(arrayData[0]);
			
			if(dia < 10){
				arrayData[0] = arrayData[0].substring(1);
			}
			String ano = arrayData[2];

			ano = ano.substring(2);
			arrayData[2] = ano;
			
			mes = dataFormatada.substring(2, 6);
			/*
			 * Modifica��o: Frederico Oliveira, Data: 11/08/2013
			 * [ TT18336 ] Data Lauda - Produ��o
			 */
			if ( (arrayData[1] != null) && (!arrayData[1].isEmpty()) && (Integer.parseInt(arrayData[1]) < 10) ) {
			    mes = mes.replace("0", "");
			}

			retorno = retorno.concat(arrayData[0] + mes + arrayData[2]);

		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return retorno;
	}
	
	/**
	 * Ver especifica��o TASK 1302.
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 * @throws Exception
	 */
	public static int retornaQuantidadeDiasTemposINSS(String dataInicio, String dataFim) throws Exception {

		/*
		 * 05/06/1990 � 10/04/1995
		 * 
		 * 1995   4    10
		 *-
		 * 1990   6     5
		 *------------------
		 * 4      10    6
		 * 
		 */
		
		//Verificando se veio dados nas datas de in�cio e fim.		
		if ( (dataInicio == null) || (dataInicio.trim().isEmpty()) )
			throw new Exception("Data de in�cio vazio.");
		if ( (dataFim == null) || (dataFim.trim().isEmpty()) )
			throw new Exception("Data de fim vazio.");
		
		//Validando as datas de inicio e fim.
        if (!Utils.isValidDateFormat(dataInicio))
            throw new Exception("Formato da data de in�cio inv�lido.");
        if (!Utils.isValidDate(dataInicio))
        	throw new Exception("Formato da data de in�cio inv�lido.");
		
        if (!Utils.isValidDateFormat(dataFim))
            throw new Exception("Formato da data de fim inv�lido.");
        if (!Utils.isValidDate(dataFim))
        	throw new Exception("Formato da data de fim inv�lido.");
        
		//Desmembrar a datas em ano, mes e dia.
		int diaInicio = getDia(dataInicio);
		int mesInicio = getMes(dataInicio);
		int anoInicio = getAno(dataInicio);
		
		int diaFim = getDia(dataFim);
		int mesFim = getMes(dataFim);
		int anoFim = getAno(dataFim);
		
		int diaRes = 0;
		int mesRes = 0;
		int anoRes = 0;

		//Vari�veis booleanas.
		boolean remover1Mes = false;
		boolean remover1Ano = false;
		
		//Tratamento para o m�s de fevereiro.
		if (mesFim == 2) {
			if ( (diaFim == 28) || (diaFim == 29) ) {
				diaFim = 30;
			}
		}
		
		//Para a conta final, s�o considerados todos os dias. Com isso, soma-se um dia no numerador antes de fazer a conta.
		diaFim += 1;
		
		//TRATAMENTO DOS DIAS.
		//Se o dia fim � maior ou igual ao dia de inicio, ent�o realizamos normalmente a subtra��o.
		if (diaFim >= diaInicio) {
			diaRes = diaFim - diaInicio;

		//Se o dia fim � menor ao dia de fim, ent�o somamos 30 dias. Depois, removemos 1 m�s da quantidade de meses.
		} else {
			
			diaFim += 30;
			diaRes = diaFim - diaInicio;
			
			remover1Mes = true;
		}

		//TRATAMENTO DOS MESES.
		if (remover1Mes) {
			mesFim -= 1;
		}
		if (mesFim >= mesInicio) {
			mesRes = mesFim - mesInicio;
		
		//Como o m�s fim � menor que o m�s inicio, ent�o devemos inserir 12 MESES no mes final e depois, remover 1 ano da data final.
		} else {
			mesFim += 12;
			mesRes = mesFim - mesInicio;
			
			remover1Ano = true;
		}
		
		//TRATAMENTO DOS ANOS.
		if (remover1Ano) {
			anoFim -= 1;
		}
		anoRes = anoFim - anoInicio;

		//Como o resultado � 30 dias, somar 1 ao m�s.
		if (diaRes == 30) {
			diaRes = 0;
			mesRes++;
		}
		
		//TRATAMENTO PARA O C�LCULO DOS DIAS.
		//Para calcular o n�mero de dias � neces�rio multiplicar o ano por 365, o m�s por 30 e somar o n�mero de dias ao final.
		return ( (anoRes * 365) + (mesRes * 30) + diaRes );
	}
	
	
	/**
	 * Retorna data no formato DD/MM/YYYY um par�metro passado como YYYYMMDD.
	 * @param dataFormatoYYYYMMDD
	 * @return
	 */
	public static String retornaDDMMYYYY(String dataFormatoYYYYMMDD) {
	
	    String krwtrDate = "";
	    Date tradeDate;
	    try {
		tradeDate = new SimpleDateFormat("yyyyMMdd").parse(dataFormatoYYYYMMDD);
		krwtrDate = new SimpleDateFormat("dd/MM/yyyy").format(tradeDate);
	    } catch (ParseException e) {
		e.printStackTrace();		
	    }
	    return krwtrDate;
	}
	
	public static String retornaYYYYMMDD(String dataFormatoDDMMYYYY) {
		
	    String krwtrDate = "";
	    Date tradeDate;
	    try {
		tradeDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataFormatoDDMMYYYY);
		krwtrDate = new SimpleDateFormat("yyyyMMdd").format(tradeDate);
	    } catch (ParseException e) {
		e.printStackTrace();		
	    }
	    return krwtrDate;
	}
	
	public static void main(String[] args) {
	   
	/*    
	System.out.println("Adicionando 8 anos � 28/02/2009...");
	try {
	    System.out.println(adicionarAno("28/02/2009", 8));
	} catch (ParseException e1) {
	    e1.printStackTrace();
	}
	*/  
	    
	    //data fim � maior que data inicio anterior
	    try {
		
		String datInicio = Utils.getDateTime();
		String datFim = Utils.getDateTime();
		System.out.println("datInicio: "+datInicio);
		Thread.sleep(10);
		System.out.println("datFim: "+datFim);
		System.out.println("diferencaEmSegundos: "+diferencaEmSegundos(Utils.strToDate(datInicio), Utils.strToDate(datFim)));
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }

		String[] tempo2 = retornaNovoAnosMesesDias(10950);
		System.out.println("retornaNovoAnosMesesDias - 10950\t" + tempo2[0] + " ano(s) " + (!tempo2[1].isEmpty() ? (tempo2[1] + " m�s(s) ") : "") + tempo2[2] + " dia(s)");
	    
		String[] tempo1 = retornaNovoAnosMesesDias(10949);
		System.out.println("retornaNovoAnosMesesDias - 10949\t" + tempo1[0] + " ano(s) " + (!tempo1[1].isEmpty() ? (tempo1[1] + " m�s(s) ") : "") + tempo1[2] + " dia(s)");
	    
		String[] tempo0 = retornaNovoAnosMesesDias(10948);
		System.out.println("retornaNovoAnosMesesDias - 10948\t" + tempo0[0] + " ano(s) " + (!tempo0[1].isEmpty() ? (tempo0[1] + " m�s(s) ") : "") + tempo0[2] + " dia(s)");

		String[] tempo3 = retornaNovoAnosMesesDias(10944);
		System.out.println("retornaNovoAnosMesesDias - 10944\t" + tempo3[0] + " ano(s) " + (!tempo3[1].isEmpty() ? (tempo3[1] + " m�s(s) ") : "") + tempo3[2] + " dia(s)");
		
		
	    	String data = "10/01/2013";
		System.out.println(data.substring(data.length()-2, data.length()));
		
		System.out.println("11148");
		String[] tempo = retornaNovoAnosMesesDias(11148);
		System.out.println("retornaNovoAnosMesesDias\t" + tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
		tempo = retornaAnosMesesDiasPm(11148);
		System.out.println("retornaAnosMesesDiasPm\t\t" + tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
		tempo = retornaAnosMesesDias(11148);
		System.out.println("retornaAnosMesesDias\t\t" + tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

		System.out.println("");
		System.out.println("12266");
		tempo = retornaNovoAnosMesesDias(12266);
		System.out.println("retornaNovoAnosMesesDias\t" + tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
		tempo = retornaAnosMesesDiasPm(12266);
		System.out.println("retornaAnosMesesDiasPm\t\t" + tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
		tempo = retornaAnosMesesDias(12266);
		System.out.println("retornaAnosMesesDias\t\t" + tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

		String trDate="20120106";    
		Date tradeDate;
		try {
		    tradeDate = new SimpleDateFormat("yyyyMMdd").parse(trDate);
		    String krwtrDate = new SimpleDateFormat("dd/MM/yyyy").format(tradeDate);
		    System.out.println(krwtrDate);
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		System.out.println(UtilData.retornaYYYYMMDD("22/06/2013"));
		
		System.out.println("ANOS: " + retornaAnos(22646));
		
		
		/*
		System.out.println(retornaAAAAMMDD("1923-1-5 12:00:00.0"));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		String[] ret = retornaAnosMesesDias(2027);
		System.out.println(ret[0] + " ano(s) " + ret[1] + " m�s(s) " + ret[2] + " dia(s)");

		String[] ret1 = retornaAnosMesesDias(13322);
		System.out.println(ret1[0] + " ano(s) " + ret1[1] + " m�s(s) " + ret1[2] + " dia(s)");

		//mes e dia
		try {

			tempo = UtilData.retornaNovoAnosMesesDias(99000);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
			tempo = UtilData.retornaAnosMesesDiasPm(99000);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

			tempo = UtilData.retornaNovoAnosMesesDias(544);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
			tempo = UtilData.retornaAnosMesesDiasPm(544);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

			tempo = UtilData.retornaNovoAnosMesesDias(243);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
			tempo = UtilData.retornaAnosMesesDiasPm(243);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

			tempo = UtilData.retornaNovoAnosMesesDias(115);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
			tempo = UtilData.retornaAnosMesesDiasPm(115);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

			tempo = UtilData.retornaNovoAnosMesesDias(268);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
			tempo = UtilData.retornaAnosMesesDiasPm(268);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

			tempo = UtilData.retornaNovoAnosMesesDias(402);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");
			tempo = UtilData.retornaAnosMesesDiasPm(402);
			System.out.println(tempo[0] + " ano(s) " + tempo[1] + " m�s(s) " + tempo[2] + " dia(s)");

			System.out.println(UtilData.retornaQuantidadeDiasTemposINSS("01/01/1990", "28/02/1991"));
			System.out.println(UtilData.retornaQuantidadeDiasTemposINSS("22/03/1972", "16/05/1972"));
			System.out.println(UtilData.retornaQuantidadeDiasTemposINSS("01/03/1983", "15/03/1983"));
			System.out.println(UtilData.retornaQuantidadeDiasTemposINSS("19/05/1983", "03/07/1986"));
			System.out.println(UtilData.retornaQuantidadeDiasTemposINSS("01/07/1988", "02/09/1989"));
			System.out.println(UtilData.retornaQuantidadeDiasTemposINSS("19/07/1990", "17/08/1990"));			
			
			int dia = UtilData.getDia("11/11/2010");
			int mes = UtilData.getMes("11/11/2010");
			String ano = new UtilData().getAnoAtual();

			String result = "";
			if (dia < 10) {
				result += "0"+dia;
			} else {
				result += dia;
			}
			result += "/";

			if (mes < 10) {
				result += "0"+mes;
			} else {
				result += mes;
			}
			result += "/";
			result += ano;
			System.out.println("result: " + result);

			System.out.println("Dia :" + getDia("01/01/2010"));
			System.out.println("M�s :" + getDia("01/01/2010"));
			System.out.println("Ano Atual: " + new UtilData().getAnoAtual());

			System.out.println("Data � maior que 21/12/1984");
			System.out.println(compareTo("22/12/1984", "21/12/1984"));

			System.out.println("Data de incio de afastamento � menor que 22/09/2003? SIM");
			System.out.println(compareTo("21/09/2003", "22/09/2003"));
			System.out.println("Data de fim de afastamento � maior que 22/09/2003? SIM");
			System.out.println(compareTo("23/09/2003", "22/09/2003"));

			System.out.println(getAno("17/12/1998"));

			System.out.println("");

			//1o parametro: data de inicio do cargo efetivo
			//2o parametro: data de inicio de afastamento
			//Data de incio do cargo � menor que a data de inicio do afastamento?
			System.out.println("Data de incio do cargo � menor que a data de inicio do afastamento? SIM");
			System.out.println(compareTo("02/01/2000", "03/01/2000"));

			System.out.println("");


			//1o parametro: data de inicio do cargo efetivo
			//2o parametro: data de inicio de afastamento
			//Data de incio do cargo � menor que a data de inicio do afastamento? N�O
			System.out.println("Data de incio do cargo � menor que a data de inicio do afastamento? N�O");
			System.out.println(compareTo("02/01/2000", "01/01/2000"));
			//Data de incio do cargo � menor que a data de fim do afastamento? SIM
			System.out.println("Data de incio do cargo � menor que a data de fim do afastamento? SIM");
			System.out.println(compareTo("02/01/2000", "04/01/2000"));

			System.out.println("");
			System.out.println("Data de incio do cargo � menor que a data de inicio do afastamento? N�O");
			System.out.println(compareTo("03/01/2000", "01/01/2000"));
			//Data de incio do cargo � menor que a data de fim do afastamento? SIM
			System.out.println("Data de incio do cargo � menor que a data de fim do afastamento? N�O");
			System.out.println(compareTo("03/01/2000", "02/01/2000"));

			System.out.println(calcDaysBetweenDates(format.parse("09/09/1996"), format.parse("09/10/1996")));
			System.out.println("O correto � 1246 dias");
			System.out.println("calcDaysBetweenDates: " + calcDaysBetweenDates(format.parse("01/11/1990"), format.parse("31/03/1994")));
			System.out.println("diferencaEmDias: " + Math.ceil(diferencaEmDias(format.parse("01/11/1990"), format.parse("31/03/1994"))));
			System.out.println("diferencaEmDiasSemArredondamento: " + diferencaEmDias(format.parse("01/11/1990"), format.parse("31/03/1994")));
			System.out.println("calcularDiferencaDias: " + CalculoData.calcularDiferencaDias(format.parse("01/11/1990"), format.parse("31/03/1994")));
			System.out.println("calcDaysBetween: " + calcDaysBetween("01/11/1990", "31/03/1994"));

			System.out.println();

			System.out.println("O correto � 480 dias");
			System.out.println("calcDaysBetweenDates: " + calcDaysBetweenDates(format.parse("08/07/1969"), format.parse("31/10/1970")));
			System.out.println("diferencaEmDias: " + Math.ceil(diferencaEmDias(format.parse("08/07/1969"), format.parse("31/10/1970"))));
			System.out.println("diferencaEmDiasSemArredondamento: " + diferencaEmDias(format.parse("08/07/1969"), format.parse("31/10/1970")));
			System.out.println("calcularDiferencaDias: " + CalculoData.calcularDiferencaDias(format.parse("08/07/1969"), format.parse("31/10/1970")));
			System.out.println("calcDaysBetween: " + calcDaysBetween("08/07/1969", "31/10/1970"));

			System.out.println("SOMA 1825: " + CalculoData.somarDias(format.parse("17/05/1988"), 1825));
			System.out.println("Somar Dias? " + CalculoData.somarDias(format.parse("01/01/1996"), 15));

			Calendar inicio = Calendar.getInstance();
	    	inicio.setTime(Utils.strToDate("17/08/1940"));
	    	System.out.println("Inicio: " + inicio);

	    	Calendar fim = Calendar.getInstance();
	    	fim.setTime(Utils.strToDate("17/08/2010"));
	    	System.out.println("fim: " + fim);

			String[] idadeDepoisDaLei = Utils.calcAgeBetween("17/08/1890", Utils.getDate("17/08/2010"));
			System.out.println(idadeDepoisDaLei[0] + " ano(s) " + idadeDepoisDaLei[1] + " m�s(s) " + idadeDepoisDaLei[2] + " dia(s)");

			System.out.println("70 anos em: " + format.format(CalculoData.somarAnos(format.parse("08/11/1939"), 70)));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * @author Adriano Silva
	 * @category Retorna a data no formato DD/MM/YYYY entrando como 
	 * parametro (ex: 01 de abril de 2013) = 01/04/2013
	 * @param data
	 * @return
	 */
	public static String formataDataExtenso(String data) {
		String dia = data.substring(0, 2).trim().toLowerCase();
		String dataFormatada = data.replaceAll(" de ", "").replaceAll(" ", "").toLowerCase();
		String ano;

		//valida o dia para que retorne o dia com 2 digitos
		if(!dia.startsWith("0")){
			dia = dia.replaceAll("[a-z]", "");
			if(dia.length() < 2){
				dataFormatada  = "0".concat(dataFormatada);	
			}
		}

		//valida o ano para que retorne o ano com 2 digitos
		ano = dataFormatada.substring(dataFormatada.length() - 4);
		ano = ano.replaceAll("[a-z]", "");

		if(dataFormatada.contains("janeiro")){
			dataFormatada = dataFormatada.replaceAll("janeiro", "/01/");

		}else if(dataFormatada.contains("fevereiro")){
			dataFormatada = dataFormatada.replaceAll("fevereiro", "/02/");

		}else if(dataFormatada.contains("mar�o") ||  dataFormatada.contains("marco")){
			if(dataFormatada.contains("mar�o")){
				dataFormatada = dataFormatada.replaceAll("mar�o", "/03/");
			}else{
				dataFormatada = dataFormatada.replaceAll("marco", "/03/");				
			}

		}else if(dataFormatada.contains("abril")){
			dataFormatada = dataFormatada.replaceAll("abril", "/04/");

		}else if(dataFormatada.contains("maio")){
			dataFormatada = dataFormatada.replaceAll("maio", "/05/");

		}else if(dataFormatada.contains("junho")){
			dataFormatada = dataFormatada.replaceAll("junho", "/06/");

		}else if(dataFormatada.contains("julho")){
			dataFormatada = dataFormatada.replaceAll("julho", "/07/");

		}else if(dataFormatada.contains("agosto")){
			dataFormatada = dataFormatada.replaceAll("agosto", "/08/");

		}else if(dataFormatada.contains("setembro")){
			dataFormatada = dataFormatada.replaceAll("setembro", "/09/");

		}else if(dataFormatada.contains("outubro")){
			dataFormatada = dataFormatada.replaceAll("outubro", "/10/");

		}else if(dataFormatada.contains("novembro")){
			dataFormatada = dataFormatada.replaceAll("novembro", "/11/");

		}else{
			dataFormatada = dataFormatada.replaceAll("dezembro", "/12/");
		}

		return dataFormatada;
	}
	
	/**
	 * @author Niedson Araujo 
	 * @since 18/11/2013
	 * @category Retorna a lista de anos YYYY, desde o anoInicial (parametro) ate o ano atual.
	 */
	
	public static ArrayList<String> listarAnos(int anoInicial) {
		Calendar calendario = Calendar.getInstance();  
		int anoAtual = Integer.parseInt(String.valueOf(calendario.get(Calendar.YEAR)));
		
		ArrayList<String> anos = new ArrayList();
		for (int i = anoInicial; i <= anoAtual; i++) {
			anos.add(String.valueOf(i));
		}
		return anos;
	}
	
	/**
	 * @author Niedson Araujo
	 * @since 21/11/2013 
	 * @param dataDDMMYYYY
	 * @return ultimoDiaDoMes
	 * @category Retorna o ultimo dia do mes, espera o parametro string com o formato de data "DD/MM/YYYY"
	 */
	public static String retornaUltimoDiaDoMes(String dataDDMMYYYY) throws ParseException {
		Calendar cal = GregorianCalendar.getInstance();
		Date dat = (new SimpleDateFormat("dd/MM/yyyy")).parse(dataDDMMYYYY);
		cal.setTime(dat);
		         
		int dia = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
		
		return Integer.toString(dia);
}
	
	//Criado:David, Data 25/07/2014, TASK2675 
	//Inicio TASK2675
	public static String retornaFormatDataMilitar(String dataDDMMMYY){
	    String dataFormatada="";
		
		try{
			
			  String dia = dataDDMMMYY.trim().substring(0,2);
			  String mes = dataDDMMMYY.trim().substring(2,5);
			  String ano = dataDDMMMYY.trim().substring(5,7);
			 
			if(mes.contains("JAN")){
				mes = "01";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("FEV")){
				mes = "02";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("MAR")){
				mes = "03";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("ABR")){
				mes = "04";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("MAI")){
				mes = "05";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("JUN")){
				mes = "06";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("JUL")){
				mes = "07";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("AGO")){
				mes = "08";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("SET")){
				mes = "09";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("OUT")){
				mes = "10";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else if(mes.contains("NOV")){
				mes = "11";
				dataFormatada = dia +"/"+ mes +"/"+ ano;

			}else{
				mes = "12";
				dataFormatada = dia +"/"+ mes +"/"+ ano;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return dataFormatada;
	} 
	//Fim TASK2675
	
	public static String formataAnoMesDiaComVirgulasELetraE(String[] tempoTotal) {
	    
	    String finalFormat = "";
	    if (tempoTotal.length == 3) {
		
		int qtdeAnos = Integer.parseInt(tempoTotal[0]);
		int qtdeMeses = Integer.parseInt(tempoTotal[1]);
		int qtdeDias = Integer.parseInt(tempoTotal[2]);
		
		//Verificando se a quantidade � == 0 ou == 1 ou > 1
		String descAnos = "";
		if (qtdeAnos == 0) {
		    descAnos = "";
		} else if (qtdeAnos == 1) {
		    descAnos = "ano";   
		} else if (qtdeAnos > 1) {
		    descAnos = "anos";   
		}

		String descMeses = "";
		if (qtdeMeses == 0) {
		    descMeses = "";
		} else if (qtdeMeses == 1) {
		    descMeses = "m�s";   
		} else if (qtdeMeses > 1) {
		    descMeses = "meses";   
		}
		
		String descDias = "";
		if (qtdeDias == 0) {
		    descDias = "";
		} else if (qtdeDias == 1) {
		    descDias = "dia";   
		} else if (qtdeDias > 1) {
		    descDias = "dias";   
		}
		
		if ( (qtdeAnos > 0) && (qtdeMeses > 0) && (qtdeDias > 0) ) {
		    finalFormat = (qtdeAnos + " " + descAnos) + ", " + (qtdeMeses + " " + descMeses) + " e " + (qtdeDias + " " + descDias);  
		} else {
		    
		    /*
		     * ANOS
		     */
		    boolean haRegistroAnterior = false;
		    if (qtdeAnos > 0) {
			finalFormat = (qtdeAnos + " " + descAnos);
			haRegistroAnterior = true;
		    }
		    
		    /*
		     * MESES
		     */
		    if (qtdeMeses > 0) {
			
			if (haRegistroAnterior) {
			    finalFormat += " e ";
			}
			finalFormat += (qtdeMeses + " " + descMeses);
			haRegistroAnterior = true;
		    } else {
			//haRegistroAnterior = false;
		    }
		    
		    /*
		     * DIAS
		     */
		    if (qtdeDias > 0) {
			
			if (haRegistroAnterior) {
			    finalFormat += " e ";
			}
			finalFormat += (qtdeDias + " " + descDias);
		    }
		}
	    }
	    
	    return finalFormat;
	}
	
	/**
	 * M�todo que retorna uma lista com as datas de um determinado per�odo ,ex : 08/2010, 09/2010, 10/2010,etc.
	 * Autor : Renan Watanabe, data : 15/10/2015
	 */
	public static List<String> obterMesesAnosFormatadosPorPeriodo(Date dataInicial, Date dataFinal){
		List<String> listaMesesAnos = new ArrayList<String>();
		Calendar calendarInicial = new GregorianCalendar();
		calendarInicial.setTime(dataInicial);
		Calendar calendarFinal = new GregorianCalendar();
		calendarFinal.setTime(dataFinal);
		
		StringBuilder sb = null;
		while(calendarInicial.before(calendarFinal)){
			sb = new StringBuilder();
			sb.append(adicionarZeroNaData(calendarInicial.get(Calendar.MONTH) + 1));
			sb.append("/");
			sb.append(calendarInicial.get(Calendar.YEAR));
			listaMesesAnos.add(sb.toString());
			calendarInicial.add(Calendar.MONTH, 1);
		};
		
		return listaMesesAnos;
	}
	
	/**
	 * M�todo para adicionar um '0' na frente do n�mero que seja menor que 10 e maior que 0.
	 * Autor : Renan Watanabe, data : 15/10/2015
	 * @param diaOuMes
	 * @return
	 */
	 public static String adicionarZeroNaData(int diaOuMes){
		String diaFormatado = "";
		    if(diaOuMes < 10 && diaOuMes > 0){
			diaFormatado = "0" + diaOuMes;
		    }else{
		        diaFormatado = String.valueOf(diaOuMes);
		    }
		return diaFormatado;
	 }

	 public static String adicionarZeroNaData(String diaOuMes){
		int diaMesConvertido = Integer.parseInt(diaOuMes);
		return adicionarZeroNaData(diaMesConvertido);
	 }
	 
	 /**
	    * M�todo onde recebe uma lista de datas no formato MM/yyyy e retorna uma String concatenada botando-as em per�odos.
	    * Exemplo de dados inseridos:   [ 11/2010, 12/2010, 01/2011, 02/2011, 06/2011, 08/2011, 10/2012, 11/2012 ]
	    * Exemplo de sa�da :            [ 11/2010 - 02/2011, 06/2011, 08/2011, 10/2012 - 11/2012 ]
	    * Observa��o: A lista recebida como argumento deve estar ordenada.
	    * Autor : Renan Watanabe
	    * Data : 02/12/2015.
	    */
	    public static String concatenarPeriodosYYMMMM(List<String> listaDatas){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		Calendar calendarInicial = null;
		Calendar calendarAtual = null;
		int contadorRange = 1;
		boolean existeRange = false;
		StringBuilder sb = new StringBuilder();
			    
		for(int contador = 0; contador < listaDatas.size(); contador++){
		    if(!listaDatas.get(contador).isEmpty()){
        		     if(calendarInicial == null){
        			   calendarInicial = obterCalendarMMYYYY(listaDatas.get(contador));
        		     }else{
        			   calendarAtual = obterCalendarMMYYYY(listaDatas.get(contador));	
        						
        			   Calendar calendarParaComparar = (Calendar)calendarInicial.clone();
        			   calendarParaComparar.set(Calendar.MONTH, calendarInicial.get(Calendar.MONTH) + contadorRange);
        						
        			   if(sdf.format(calendarParaComparar.getTime()).equals(sdf.format(calendarAtual.getTime()))){
        				existeRange = true;
        				++contadorRange;
        			   }else{
        				Calendar calendarDataAnterior = obterCalendarMMYYYY(listaDatas.get(contador - 1));
        							
        				if(existeRange){
        					sb.append(sdf.format(calendarInicial.getTime()) + " - " + sdf.format(calendarDataAnterior.getTime()) + ", ");
        				}else{
        					sb.append(sdf.format(calendarDataAnterior.getTime()) + ", ");
        				}
        				calendarInicial = obterCalendarMMYYYY(listaDatas.get(contador));
        				contadorRange = 1;
        				existeRange = false;
        			    }
        		     }
		    }
		}
				
		//Tratamento do �ltimo registro.
		if(existeRange == true){
			sb.append(sdf.format(calendarInicial.getTime()) + " - " + listaDatas.get(listaDatas.size() - 1));
		}else{
			sb.append(listaDatas.get(listaDatas.size() - 1));
		}
		 
		return sb.toString();
	    }
			  
			
	     private static Calendar obterCalendarMMYYYY(String yyMMMM){
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(Integer.parseInt(yyMMMM.substring(3,7)), Integer.parseInt(yyMMMM.substring(0,2)) - 1, 1, 0, 0, 0);
		  return calendar;
	     }
	     
	     
    /**
     * 	M�todo para comparar duas datas no formato dd/MM/yyyy e retornar um booleano.
     *  Exemplo m�todo data menor :  [ "10/10/2015" , "20/10/2016" ]  -> retorna true.   
     *                               [ "01/01/2015" , "01/01/2015" ]  -> retorna true.
     *                               [ "30/06/2015" , "01/01/2015" ]  -> retorna false.
     *  Autor : Renan Watanabe
     *  Data :  03/12/2015.                                
     */                              
    public static boolean isPrimeiraDataMenorOuIgualSegundaData(Calendar primeiraData, Calendar segundaData) throws ParseException{
			
	  boolean isPrimeiraDataMenorOuIgualSegundaData = false;
	 
	  int valor = primeiraData.compareTo(segundaData);
	  if(valor == 0){//Significa que as datas s�o iguais.
	      isPrimeiraDataMenorOuIgualSegundaData = true;
	  }else if(valor == -1){//Significa que a primeiraData � menor que a segundaData.
	      isPrimeiraDataMenorOuIgualSegundaData = true;
	  }else if(valor == 1){//Significa que a primeiraData � maior que a segundaData.
	      isPrimeiraDataMenorOuIgualSegundaData = false;
	  }
       return isPrimeiraDataMenorOuIgualSegundaData;
    }
		
    /**
     * 	M�todo para comparar duas datas no formato dd/MM/yyyy e retornar um booleano.
     *  Exemplo m�todo data maior :  [ "10/10/2015" , "20/10/2016" ]  -> retorna false.   
     *                               [ "01/01/2015" , "01/01/2015" ]  -> retorna true.
     *                               [ "30/06/2015" , "01/01/2015" ]  -> retorna true.
     *  Autor : Renan Watanabe
     *  Data :  03/12/2015.                                
     */         	
    public static boolean isPrimeiraDataMaiorOuIgualSegundaData(Calendar primeiraData, Calendar segundaData) throws ParseException{
	   boolean isPrimeiraDataMaiorOuIgualSegundaData = false;
	   int valor = primeiraData.compareTo(segundaData);
	   if(valor == 0){//Significa que as datas s�o iguais.
	       isPrimeiraDataMaiorOuIgualSegundaData = true;
	   }else if( valor == -1){//Significa que a primeiraData � menor que a segundaData.
	       isPrimeiraDataMaiorOuIgualSegundaData = false;
	   }else if(valor == 1){//Significa que a primeiraData � maior que a segundaData.
	       isPrimeiraDataMaiorOuIgualSegundaData = true;
	   }
	return isPrimeiraDataMaiorOuIgualSegundaData;
    }
    
    public static boolean isPrimeiraDataMaiorQueSegundaData(Calendar primeiraData, Calendar segundaData) throws ParseException{
	
	   boolean isPrimeiraDataMaiorQueSegundaData = false;
	   int valor = primeiraData.compareTo(segundaData);
	   if(valor == 0){//Significa que as datas s�o iguais.
	       isPrimeiraDataMaiorQueSegundaData = false;
	   }else if( valor == -1){//Significa que a primeiraData � menor que a segundaData.
	       isPrimeiraDataMaiorQueSegundaData = false;
	   }else if(valor == 1){//Significa que a primeiraData � maior que a segundaData.
	       isPrimeiraDataMaiorQueSegundaData = true;
	   }
	return isPrimeiraDataMaiorQueSegundaData;
 }
    
    public static boolean isPrimeiraDataMenorQueSegundaData(Calendar primeiraData, Calendar segundaData) throws ParseException{
	
	   boolean isPrimeiraDataMenorQueSegundaData = false;
	   int valor = primeiraData.compareTo(segundaData);
	   
	   if(valor == 0){//Significa que as datas s�o iguais.
	       isPrimeiraDataMenorQueSegundaData = false;
	   }else if( valor == -1){//Significa que a primeiraData � menor que a segundaData.
	       isPrimeiraDataMenorQueSegundaData = true;
	   }else if(valor == 1){//Significa que a primeiraData � maior que a segundaData.
	       isPrimeiraDataMenorQueSegundaData = false;
	   }
	return isPrimeiraDataMenorQueSegundaData;
   }
    
    /**
     * Este m�todo retorna uma data do Tipo Calendar do dia de hoje.
     * A diferen�a � que esse calendar n�o possui as horas, minutos, segundos e milissegundos.
     */
    public static Calendar obterCalendarSemHorario(String dataDDYYMMMM) throws ParseException{
     if(dataDDYYMMMM.isEmpty()){
    	     return null;
     }	
	 GregorianCalendar calendarSemHorario = new GregorianCalendar();
	 calendarSemHorario.setTime(simpleDateFormatParaComapreTo.parse(dataDDYYMMMM));
	 return calendarSemHorario;
    }
 
    /**
     * Este m�todo retorna uma data do Tipo Calendar do dia de hoje.
     */
    public static Calendar obterCalendarComHorario(String dataDDYYMMMMHHMMSS) throws ParseException{
     if(dataDDYYMMMMHHMMSS.isEmpty()){
     	   return null;
     }	
	 GregorianCalendar calendarSemHorario = new GregorianCalendar();
	 calendarSemHorario.setTime(simpleDateFormatComHorario.parse(dataDDYYMMMMHHMMSS));
	 return calendarSemHorario;
    }
    
    /**
     * Retorna a data de hoje no formato dd/MM/yyyy.
     * Autor : Renan Watanabe, data : 04/12/2014.
     */
    public static String obterDataAtual(){
	return simpleDateFormatParaComapreTo.format(new Date());
    }
    
    /**
     * Retorna a data de hoje no formato dd/MM/yyyy.
     * Autor : Renan Watanabe, data : 04/12/2014.
     */
    public static String obterDataHoraAtual(){
	return simpleDateFormatComHorario.format(new Date());
    }
    

/**
 * [TASK 3689 Parte II] Autor: Renan Watanabe, Data : 17/02/2016.
 * Este m�todo verifica se a data principal � concomitante relacionado ao per�odo.(Formato dd/MM/yyyy).
 * 
 * Primeiro Exemplo:
 * Data Principal: 30/06/1990;  Data In�cio Per�odo: 01/01/1990;  Data Fim Per�odo: 31/12/1990 
 * Ir� retornar true, pois a data principal est� entre as duas datas de per�odos.
 *            
 * Segundo Exemplo:
 * Data Principal: 01/10/2000;  Data In�cio Per�odo: 01/10/2000;  Data Fim Per�odo: 31/12/2000
 * Ir� retornar true, pois a data principal � igual � data de in�cio do per�odo(o mesmo acontece com a data fim do per�odo).
 * 
 * Terceiro Exemplo:
 * Data Principal: 01/05/2010;  Data In�cio Per�odo: 01/10/2000;  Data Fim Per�odo: 31/12/2000
 * Ir� retornar false.
 * 
 * Quarto Exemplo:
 * Data Principal: 01/01/2015;  Data In�cio Per�odo: 01/10/2000;  Data Fim Per�odo: vazio(null ou "").
 * Ir� retornar true, pois n�o existe data fim do per�odo, ou seja, tudo que � a partir da data 01/10/2000 ser� concomitante.
 * 
 */    
    public static boolean isDataConcomitante(String dataPrincipal, String dataInicioPeriodo, String dataFimPeriodo) throws ParseException{
	Calendar dataPrincipalCalendar = obterCalendarSemHorario(dataPrincipal);
	Calendar dataInicioPeriodoCalendar = obterCalendarSemHorario(dataInicioPeriodo);
	
	boolean isDataPrincipalConcomitante = false;
	
	if(dataFimPeriodo == null || dataFimPeriodo.isEmpty()){
	      if(isPrimeiraDataMaiorOuIgualSegundaData(dataPrincipalCalendar,dataInicioPeriodoCalendar)){
		   isDataPrincipalConcomitante = true;
	      }
	      
	}else{
    	      Calendar dataFimPeriodoCalendar = obterCalendarSemHorario(dataFimPeriodo);
    	
    	      if(isPrimeiraDataMaiorOuIgualSegundaData(dataPrincipalCalendar, dataInicioPeriodoCalendar)
    	    	      && isPrimeiraDataMenorOuIgualSegundaData(dataPrincipalCalendar, dataFimPeriodoCalendar)){
    	         isDataPrincipalConcomitante = true;
    	      }
	}
	
	return isDataPrincipalConcomitante;
    }
	/**
	 * @author Ederson da Silva
	 * @since 22/08/2016
	 * @param qtd quantidade de dias
	 * @return um array de string que cont�m quantos anos e quantos dias t�m 
	 * na quantidade de dias passados
	 */
	public static String[] retornaNovoAnosDias(int qtd) {
	    String anosDias[] = new String [2];
	    Integer anos = qtd / 365;
	    Integer meses = qtd % 365;
	    anosDias[0] = anos.toString();
	    anosDias[1] = meses.toString();
	    return anosDias;
	}

    
    /**
	 * @author Ederson da Silva
	 * @since 06/09/2016
	 * Converte uma data para String ("dd/MM/yyyy")
	 * @param data
	 * @return a data com o acrescimos dos mese(s)
	 * @throws ParseException
	 */
	public static String adicionarMes(String data, int qtdMeses) throws ParseException{
		String res = null;
		if("".equals(data))res = "";
		if(data!=null && !"".equals(data)){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(toDate(data));
			cal.add(Calendar.MONTH, qtdMeses);
			res = toString(cal.getTime());
		}
		return res;
	}
	
            /**
             * Converte uma String para um objeto Date. Caso a String seja vazia ou
             * nula, retorna null - para facilitar em casos onde formul�rios podem ter
             * campos de datas vazios.
             * 
             * @param data
             *            String no formato dd/MM/yyyy a ser formatada
             * @return Date Objeto Date ou null caso receba uma String vazia ou nula
             * @throws Exception
             *             Caso a String esteja no formato errado
             */
	    public static Date formataData(String data, String formato) throws Exception {
		if (data == null || data.equals(""))
		    return null;
		Date date = null;
		try {
		    DateFormat formatter = new SimpleDateFormat(formato);
		    date = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {
		    throw e;
		}
		return date;
	    }
	    
	    /**
	     * 
	     * @param dataFormatoAmericano [ yyyy-MM-dd HH:mm:ss.S ]
	     * @param formatoSaida [ definirSaida ]
	     * @return String
	     * @throws Exception
	     */
	    public static String formataDeDataAmericanaParaBrasil(String dataFormatoAmericano, String formatoSaida) throws Exception {
		if(dataFormatoAmericano == null || dataFormatoAmericano.trim().length() <= 0) {
		    throw new Exception("Erro EvoluCcomiGfuncionalDAO.FormataDeDataAmericanaParaBrasil : Data para conversao esta nula ou vazia !!!");
		}
		
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		DateFormat outputFormat = new SimpleDateFormat(formatoSaida);
	        String outputString = null;
	        
	        try {            
	         Date date = inputFormat.parse(dataFormatoAmericano);
	         outputString = outputFormat.format(date);

	        } catch (Exception e) {
	            throw new Exception("Erro EvoluCcomiGfuncionalDAO.FormataDeDataAmericanaParaBrasil : ", e);
	        }
	        return outputString;
	    }
	    
	    /**
	     * @author Jlourenco 13-06-2018
	     * TT-47678-JC
	     * INICIO
	     */
	    public static String[] calcularIdade(Date dataInicio, Date dataFim) {
	        Integer anos = 0;
	        Integer meses = 0;
	        Integer dias = 0;
	        Calendar dataAtual;
	        Calendar dataNascimento;
	        dataAtual = Calendar.getInstance();
	        dataNascimento = Calendar.getInstance();
	        dataNascimento.setTime(dataInicio);
	        dataAtual.setTime(dataFim);
	        anos = anos + (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));
	        meses = meses + (dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH));
	        dias = dias + (dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH));
	        if (dataAtual.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)) {
	            if (dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
	                dias = 30 + dias;
	                meses = 12 + meses;
	                anos = anos - 1;
	            }
	        } else if (dataAtual.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
	            if (dataAtual.get(Calendar.DAY_OF_MONTH) >= dataNascimento.get(Calendar.DAY_OF_MONTH)) {
	                meses = 12 + meses;
	                anos = anos - 1;
	            } else if (dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
	                dias = 30 + dias;
	                meses = 12 + meses;
	                anos = anos - 1;
	            }
	        } else if (dataAtual.get(Calendar.MONTH) > dataNascimento.get(Calendar.MONTH)) {
	            if (dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
	                dias = 30 + dias;
	                meses = meses - 1;
	            }
	        }
	        String[] AnoMesDia = { anos.toString(), meses.toString(), dias.toString() };
	        return AnoMesDia;
	    }

	    public static String formatarIdade(String ida) {
	        String idade = "";
	        String anos = null;
	        String meses = null;
	        String dias = null;
	        String CharArray[] = ida.split(" ");
	        anos = CharArray[0];
	        meses = CharArray[1];
	        dias = CharArray[2];
	        if (!anos.equals("00")) {
	            if (anos.equals("01")) {
	                idade = idade.concat((new StringBuilder()).append(anos).append(" Ano ").toString());
	            } else {
	                idade = idade.concat((new StringBuilder()).append(anos).append(" Anos ").toString());
	            }
	        }
	        if (!meses.equals("00")) {
	            if (meses.equals("01")) {
	                idade = idade.concat((new StringBuilder()).append(meses).append(" M52s ").toString());
	            } else {
	                idade = idade.concat((new StringBuilder()).append(meses).append(" Meses ").toString());
	            }
	        }
	        if (!dias.equals("00")) {
	            if (dias.equals("01")) {
	                idade = idade.concat((new StringBuilder()).append(dias).append(" Dia ").toString());
	            } else {
	                idade = idade.concat((new StringBuilder()).append(dias).append(" Dias").toString());
	            }
	        }
	        return idade;
	    }
	    /**
	     * @author Jlourenco 13-06-2018
	     * TT-47678-JC
	     * FIM
	     * @throws Exception 
	     */
	    
	    /**
	     * 
	     * @param dataEntrada
	     * @param formatoEntrada
	     * @param formatoSaida
	     * @return
	     * @throws Exception
	     */
	    public static String formataDataStringParaString(String dataEntrada, String formatoEntrada, String formatoSaida) throws Exception {
		if(!dataEntrada.equals("") && !formatoEntrada.equals("") && !formatoSaida.equals("")) {
			SimpleDateFormat sdfEntrada = new SimpleDateFormat(formatoEntrada);
			SimpleDateFormat sdfSaida = new SimpleDateFormat(formatoSaida);
			Date date = sdfEntrada.parse(dataEntrada);
			return sdfSaida.format(date);
		} else {
		    return "";
		}
	    }
		
		    public static boolean isDataValida(String dataDDMMYYYY) {
			
			if(dataDDMMYYYY == null || dataDDMMYYYY.isEmpty()) {
			    return false;
			}
			
			if(dataDDMMYYYY.trim().length() != 10) {
			    return false;
			}
			
		        try {
		            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		            //a m�gica desse m�todo acontece aqui, pois o setLenient() � usado para setar
		            //sua escolha sobre datas estranhas, quando eu seto para "false" estou dizendo
		            //que n�o aceito datas falsas como 31/02/2016
		            sdf.setLenient(false);
		            //aqui eu tento converter a String em um objeto do tipo date, se funcionar
		            //sua data � valida
		            sdf.parse(dataDDMMYYYY);
		            //se nada deu errado retorna true (verdadeiro)
		            return true;
		        } catch (ParseException ex) {
		            //se algum passo dentro do "try" der errado quer dizer que sua data � falsa, ent�o,
		            //retorna falso
		            return false;
		        }
		    }
		
	
		   
 
		    
    public static String retornaNovoAnosMesesDiasDescricao(int qtdeDias) {
	String descricaoDias = "";
	String descricaoMeses = "";
	String descricaoAnos = "";
	String[] anosMesesDias = retornaNovoAnosMesesDias(qtdeDias);

	int dias = Integer.parseInt(anosMesesDias[2]);
	if (dias == 0) {
	    descricaoDias = "";
	} else if (dias == 1) {
	    descricaoDias = "1 dia";
	} else {
	    descricaoDias = dias + " dias";
	}

	if(!anosMesesDias[1].isEmpty()) {
		int meses = Integer.parseInt(anosMesesDias[1]);

		if (meses == 0) {
		    descricaoMeses = "";
		} else if (meses == 1) {
		    descricaoMeses = "1 m�s";
		} else {
		    descricaoMeses = meses + " meses";
		}
	}


	int anos = Integer.parseInt(anosMesesDias[0]);
	if (anos == 0) {
	    descricaoAnos = "";
	} else if (anos == 1) {
	    descricaoAnos = "1 ano";
	} else {
	    descricaoAnos = anos + " anos";
	}

	List<String> listaAnosMesesDias = new ArrayList<String>();
	if (!descricaoAnos.isEmpty()) {
	    listaAnosMesesDias.add(descricaoAnos);
	}
	if (!descricaoMeses.isEmpty()) {
	    listaAnosMesesDias.add(descricaoMeses);
	}
	if (!descricaoDias.isEmpty()) {
	    listaAnosMesesDias.add(descricaoDias);
	}
	
	return UtilString.obterValoresSeparadosPorVirgulaFinalizandoComE(listaAnosMesesDias);
    }
	    
    /**
     * Encontra dentro de uma string todas as datas no formato DD/MM/YY
     * @param desc - Texto onde a data deve ser buscada
     * @return - array de string com todas as datas.
     */
    public static String[] findDateInString(String desc) {
	  int count=0;
	  String[] allMatches = new String[2];
	  Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.][0-9][0-9]").matcher(desc);
	  while (m.find()) {
	    allMatches[count] = m.group();
	    count++;
	  }
	  return allMatches;
	}
    
    
}