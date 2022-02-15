package br.com.atlantic.comum.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.esocial.comum.dao.daogenerico.DAOGenerico;
import br.com.esocial.comum.dao.daogenerico.DataBaseTX;
import br.com.esocial.comum.dao.daogenerico.DataBaseTXInterface;
import br.com.esocial.comum.dao.daogenerico.LabelValueBean;
import br.com.esocial.comum.dao.daogenerico.SetTypes;
import br.com.esocial.comum.dao.daogenerico.SetTypesBuilder;


public class Utils {
    private static Log log = new Log("Utils");
    // private static String versao = "versao 2.00";
    // private static int cdLanguage = 2;

    public final static String MASK_DECIMAL = "#,##0.00";
    public final static String MASK_DECIMAL_4DECIMAIS = "#,##0.0000";
    public final static String MASK_DOUBLE = "###0.00";
    public final static String MASK_DOUBLE_4DECIMAIS = "###0.0000";
    public final static String MASK_MOEDA = "� #,##0.00";
    public final static String MASK_INTEIRO = "#####";
    public final static String MASK_MILHAR_INTEIRO = "###,##0";
    public final static Locale BRASIL = new Locale("pt", "BR");
    public final static Locale USA = Locale.US;
    public static final String DATA_EXTENSO = "dd 'de' MMMMM 'de' yyyy";
    public static final String DATA_HORA_EXTENSO = "EEEEE',' dd 'de' MMMMM 'de' yyyy', às' HH:mm:ss";
    public static final String DATA_EXTENSO_DIA_1_CARACTER = "d 'de' MMMMM 'de' yyyy";
    public static final String DATA_EXTENSO_MES = "MMMMM";
    public static final String DATA_EXTENSO_SEMANA = "EEEEE',' " + DATA_EXTENSO;
    public static final String DATA_ANO_4D = "yyyy";
    public static final String DATA_ANO_2D = "yy";
    public static final String DATA_MES_2D = "MM";
    public static final String DATA_MES_ANO_4D = "MMMMM yyyy";
    public static final String DATA_DEFAULT = "dd/MM/yyyy";
    public static final String DATA_COMPLETA = "dd/MM/yyyy HH:mm:ss";
    public static final String DATA_MES_2D_ANO_4D = "MM/yyyy";

    private static final SimpleDateFormat nomeMesFormat = new SimpleDateFormat("MMMMM"); // Janeiro,
    public static String montarExpressaoSqlParaRetirarEspacosEColocarVirgulaNoInicioENoFim(String campoSql) {
    	return new StringBuilder().append("','||REPLACE(").append(campoSql).append(", ' ', '')||','").toString();
    }

    public static double strFormatToDouble(String strNumero, char pDecSep, char pGroupSep) {
		log.log("Inicio do metodo strFormatToDouble");
		log.log("Numero recebido ", strNumero);
		log.log("Separador decimal ", pDecSep);
		log.log("Separador de grupo ", pGroupSep);
	
		strNumero = strNumero.replace(pGroupSep, ' ');
		strNumero = strNumero.replace(pDecSep, '.');
		String strNumAux = "";
	
		for (int i = 0; i < strNumero.length(); i++) {
		    if (!strNumero.substring(i, i + 1).equals(" ")) {
			strNumAux = strNumAux + strNumero.substring(i, i + 1);
		    }
		}
	
		log.log("Numero devolvido ", strToDouble(trim(strNumAux)));
		log.log("Fim do metodo strFormatToDouble");
	
		return strToDouble(trim(strNumAux));
    }

    public static String trunc(String texto, int maxCaracteres) {
	if (texto == null || texto.length() <= maxCaracteres) {
	    return texto;
	}

	return texto.substring(0, maxCaracteres);
    }

    /**
     * Retorna a parte inteira de um n�mero
     * 
     * @return Retorna a parte inteira de um n�mero.
     * @param v11
     *            � o valor do qual se quer a parte inteira
     */
    public static String integer(double vl1) {
	String valor1 = new Double((vl1)).toString();
	int posicao = valor1.indexOf(".");
	String inteiro = valor1.substring(0, posicao);

	return inteiro;
    }

    /**
     * Retorna a parte decimal de um n�mero com a quantidade de casas decimais informadas
     * 
     * @return Retorna a parte decimal de um n�mero com a quantidade de casas decimais informadas.
     * @param v11
     *            � o valor que se quer a parte decimal
     * @param pQtdCasas
     *            � a quantidade de casas decimais desejada
     */
    public static String decimal(double vl1, int pQtdCasas) {
	String valor1 = addRightChar((new Double((vl1)).toString()), (pQtdCasas + 25), "0");
	String decimal;

	int posicao = valor1.indexOf(".");
	decimal = valor1.substring(posicao + 1, posicao + 1 + pQtdCasas);

	return decimal;
    }

    /**
     * Retorna a soma entre dois doubles
     * 
     * @return Retorna a soma entre dois doubles
     * @param v11
     *            e v12 s�o o valores que ser�o somados
     */
    public static double add(double vl1, double vl2) {
	log.log("****add****");
	double result = vl1 + vl2;

	log.log("add inicial[" + result + "]");
	String resString = doubleFormat(result, 3, 3, "#0.00");

	String resString2 = strDoubleToMoney(resString, false, true);
	result = strToDouble(resString2);
	log.log("add final[" + result + "]");

	return (result);
    }

    /**
     * Retorna a soma entre dois doubles com o numero de casas decimais desejada
     * 
     * @return Retorna a soma entre dois doubles com o numero de casas decimais desejada
     * @param v11
     *            e v12 s�o o valores que ser�o somados
     * @param decimalNum
     *            � o n�mero de casas decimais desejada
     */
    public static double add(double vl1, double vl2, int decimalNum) {
	log.log("****add****");
	double result = vl1 + vl2;
	log.log("add inicial[" + result + "]");
	String resString = doubleFormat(result, decimalNum, decimalNum, "#0.00");
	result = strToDouble(resString);
	log.log("add final[" + result + "]");
	log.log("****add****");

	return (result);
    }

    /**
     * Retorna a soma entre tr�s doubles
     * 
     * @return Retorna a soma entre tr�s doubles
     * @param v11
     *            , v12 e v13 s�o o valores que ser�o somados
     */
    public static double add(double vl1, double vl2, double vl3) {
	return add(add(vl1, vl2), vl3);
    }

    /**
     * Retorna a soma entre quatro doubles
     * 
     * @return Retorna a soma entre quatro doubles
     * @param v11
     *            , v12, v13 e v14 s�o o valores que ser�o somados
     */
    public static double add(double vl1, double vl2, double vl3, double vl4) {
	return add(add(add(vl1, vl2), vl3), vl4);
    }

    /**
     * Retorna a subtra��o entre dois doubles
     * 
     * @return Retorna a subtra��o entre dois doubles
     * @param v11
     *            e v12 s�o o valores que ser�o subtra�dos
     */
    public static double subtract(double vl1, double vl2) {
	log.log("****subtract****");
	double result = vl1 - vl2;
	log.log("subtract inicial[" + result + "]");

	String resString = doubleFormat(result, 3, 3, "#0.00");
	String resString2 = strDoubleToMoney(resString, false, true);
	result = strToDouble(resString2);
	log.log("subtract final[" + result + "]");
	log.log("****subtract****");

	return (result);
    }

    /**
     * Retorna a subtra��o entre dois doubles com o numero de casas decimais desejada
     * 
     * @return Retorna a subtra��o entre dois doubles com o numero de casas decimais desejada
     * @param v11
     *            e v12 s�o o valores que ser�o subtra�dos
     * @param decimalNum
     *            � o n�mero de casas decimais desejada
     */
    public static double subtract(double vl1, double vl2, int decimalNum) {
	log.log("****subtract****");
	double result = vl1 - vl2;
	log.log("subtract inicial[" + result + "]");
	String resString = doubleFormat(result, decimalNum, decimalNum, "#0.00");
	result = strToDouble(resString);
	log.log("subtract final[" + result + "]");
	log.log("****subtract****");

	return (result);
    }

    /**
     * Retorna a subtra��o entre quatro doubles
     * 
     * @return Retorna a subtra��o entre quatro doubles
     * @param v11
     *            , v12, v13 e v14 s�o o valores que ser�o subtra�dos
     */
    public static double subtract(double vl1, double vl2, double vl3) {
	return subtract(subtract(vl1, vl2), vl3);
    }

    /**
     * Retorna a sutra��o entre quatro doubles
     * 
     * @return Retorna a sutra��o entre quatro doubles
     * @param v11
     *            , v12, v13 e v14 s�o o valores que ser�o subtra�dos
     */
    public static double subtract(double vl1, double vl2, double vl3, double vl4) {
	return subtract(subtract(subtract(vl1, vl2), vl3), vl4);
    }

    /**
     * Retorna a multiplica��o entre dois doubles
     * 
     * @return Retorna a multiplica��o entre dois doubles
     * @param v11
     *            e v12 s�o o valores que ser�o multilplicados
     */
    public static double multiplication(double vl1, double vl2) {
	log.log("****multiplication****");
	double result = vl1 * vl2;
	log.log("multiplication inicial[" + result + "]");

	String resString = doubleFormat(result, 3, 3, "#0.00");
	String resString2 = strDoubleToMoney(resString, false, true);
	result = strToDouble(resString2);
	log.log("multiplication final[" + result + "]");
	log.log("****multiplication****");

	return (result);
    }

    /**
     * Retorna a multiplica��o entre dois doubles
     * 
     * @return Retorna a multiplica��o entre dois doubles
     * @param v11
     *            e v12 s�o o valores que ser�o multilplicados
     * @param decimalNum
     *            � o n�mero de casas decimais desejada
     */
    public static double multiplication(double vl1, double vl2, int decimalNum) {
	log.log("****multiplication****");
	double result = vl1 * vl2;
	log.log("multiplication inicial[" + result + "]");
	String resString = doubleFormat(result, decimalNum, decimalNum, "#0.00");
	result = strToDouble(resString);
	log.log("multiplication final[" + result + "]");
	log.log("****multiplication****");

	return (result);
    }

    /**
     * Retorna a divis�o entre dois doubles
     * 
     * @return Retorna a divis�o entre dois doubles
     * @param v11
     *            e v12 s�o o valores que ser�o divididos
     */
    public static double division(double vl1, double vl2) {
	log.log("****division****");
	double result = vl1 / vl2;
	log.log("division inicial[" + result + "]");

	String resString = doubleFormat(result, 3, 3, "#0.00");
	String resString2 = strDoubleToMoney(resString, false, true);
	result = strToDouble(resString2);
	log.log("division final[" + result + "]");
	log.log("****division****");

	return (result);
    }

    /**
     * Retorna a divis�o entre dois doubles
     * 
     * @return Retorna a divis�o entre dois doubles
     * @param v11
     *            e v12 s�o o valores que ser�o divididos
     * @param decimalNum
     *            � o n�mero de casas decimais desejada
     */
    public static double division(double vl1, double vl2, int decimalNum) {
	log.log("****division****");
	double result = vl1 / vl2;
	log.log("division inicial[" + result + "]");
	String resString = doubleFormat(result, decimalNum, decimalNum, "#0.00");
	result = strToDouble(resString);
	log.log("division final[" + result + "]");
	log.log("****division****");

	return (result);
    }

    /**
     * Retorna a representa��o hexadecimal de caracteres bin�rios Ex.:"2��_��" para
     * "17183202fcf01e5faaab"
     * 
     * @return Retorna a representa��o hexadecimal de caracteres bin�rios Ex.:"2��_��" para
     *         "17183202fcf01e5faaab"
     * @param str
     *            � o valor bin�rio
     */
    public static String binToString(String str) {
	if (str == null)
	    return "";
	StringBuffer strb = new StringBuffer();
	String strChar;

	for (int i = 0; i < str.length(); i++) {
	    strChar = Integer.toHexString((int) str.charAt(i));
	    if (strChar.length() == 1)
		strb.append("0" + strChar);
	    else
		strb.append(strChar);
	}

	return strb.toString();
    }

    // Retorna o valor inteiro de um caracter hexadecimal
    private static int hex(char ch) {
	// int n;

	char ch1 = Character.toUpperCase(ch);
	if (Character.isDigit(ch)) {
	    return ((int) ch) - 48;
	}

	if (ch1 == 'A')
	    return 10;
	if (ch1 == 'B')
	    return 11;
	if (ch1 == 'C')
	    return 12;
	if (ch1 == 'D')
	    return 13;
	if (ch1 == 'E')
	    return 14;
	if (ch1 == 'F')
	    return 15;

	return 0;
    }

    /**
     * Retorna o valor inteiro de um hexadecimal
     * 
     * @return Retorna o valor inteiro de um hexadecimal
     * @param str
     *            � o valor hexadecimal
     */
    public static int hexToInt(String str) {
	char c1;
	char c2;
	c1 = str.charAt(0);
	c2 = str.charAt(1);

	return 16 * hex(c1) + hex(c2);
    }

    /**
     * Retorna o inverso do m�todo bintoString Ex: "17183202fcf01e5faaab" to "2��_��"
     * 
     * @return Retorna o inverso do m�todo bintoString Ex: "17183202fcf01e5faaab" to "2��_��"
     * @param str
     *            � o valor bin�rio
     */
    public static String stringToBin(String str) throws IOException {
	if (str == null)
	    return "";

	// String strChar;
	StringBuffer strb = new StringBuffer();
	int n;

	int end = str.length() / 2;
	for (int i = 0; i < end; i++) {
	    System.out.println(hexToInt(str.substring(i * 2, i * 2 + 2)));
	    n = hexToInt(str.substring(i * 2, i * 2 + 2));
	    strb.append((char) n);
	}

	return strb.toString();
    }

    /**
     * Retorna uma String com o caracter ' nas extremidades
     * 
     * @return Retorna uma String com o caracter ' nas extremidades Ex.: "teste" se transforma em
     *         "'teste'"
     */
    public static String quoted(String pStr) {
	return "'" + addApostrophe(pStr) + "'";
    }

    public static String quoted(int pInt) {
	return "'" + pInt + "'";
    }

    public static String quoted(long pLong) {
	return "'" + pLong + "'";
    }

    /**
     * Retira o acento de todas os caracteres de uma String
     * 
     * @return Retorna uma String sem acentua��o
     * @param linha
     *            s�o os caracteres que ser�o desecentuados
     */
    public static String accentOff(String linha) {
	if (linha == null)
	    return linha;

	char lineClean[] = "aaaaaeeeeiiiiooooouuuuncAAAAAEEEEIIIIOOOOOUUUUNC\"".toCharArray();
	char lineDirty[] = "������������������������������������������������'".toCharArray();

	for (int i = 0; i < lineClean.length; i++)
	    linha = linha.replace(lineDirty[i], lineClean[i]);

	return linha;
    }

    /**
     * Retorna a string sem espa�os em branco, tanto na direita quando a esquerda.
     * 
     * @return Retorna a string sem espa�os em branco, tanto na direita quando a esquerda.
     * @param pStr
     *            string a ser modificada
     */
    public static String trim(String pStr) {
	if (pStr == null)
	    return "";
	else
	    return pStr.trim();
    }

    /**
     * Retorna a string, sendo nula torna vazio
     * 
     * @return Retorna a string sem espa�os em branco, tanto na direita quando a esquerda.
     * @param pStr
     *            string a ser modificada
     */
    public static String nullToEmpty(String pStr) {
	if (pStr == null)
	    return "";
	else
	    return pStr;
    }

    /**
     * Retorna um float como String
     * 
     * @return Retorna um float como String
     * @param pNumber
     *            � o float a ser transformado
     */
    public static String floatToStr(float pNumber) {
	return Float.toString(pNumber);
    }

    /**
     * Retorna um int como String
     * 
     * @return Retorna um int como String
     * @param pNumber
     *            � o int a ser transformado
     */
    public static String intToStr(int pNumber) {
	return Integer.toString(pNumber);
    }

    /**
     * Retorna um long como String
     * 
     * @return Retorna um long como String
     * @param pNumber
     *            � o long a ser transformado
     */
    public static String longToStr(long pNumber) {
	return Long.toString(pNumber);
    }

    /**
     * Retorna um String como float
     * 
     * @return Retorna um String como float
     * @param pStr
     *            � a String a ser transformada
     */
    public static int strToInt(String pStr) {
	try {
	    if (pStr == null)
		pStr = "0";

	    if (pStr.equals(""))
		pStr = "0";

	    pStr = pStr.trim();

	    return Integer.parseInt(pStr);
	} catch (NumberFormatException e) {
	    return 0;
	}
    }

    /**
     * Retorna um String como tipo Integer
     * 
     * @return Retorna um String como tipo Integer
     * @param pStr
     *            � a String a ser transformada
     */
    public static Integer strToInteger(String pStr) {
	try {
	    if (pStr == null)
		pStr = "0";

	    if (pStr.equals(""))
		pStr = "0";

	    pStr = pStr.trim();

	    return Integer.valueOf(pStr);
	} catch (Exception e) {
	    return new Integer(0);
	}
    }

    /**
     * Retorna um tipo Integer como String
     * 
     * @return Retorna um tipo Integer como String
     * @param pNumber
     *            � o Integer a ser transformado
     */
    public static String integerToStr(Integer pNumber) {
	return pNumber.toString();
    }

    /**
     * Retorna uma String como int
     * 
     * @return Retorna uma String como int
     * @param str
     *            � a String a ser transformada
     * @param defaultRet
     *            � o valor default de retorno
     */
    public static int strToInt(String str, int defaultRet) {
	try {
	    return Integer.parseInt(str);
	} catch (NumberFormatException e) {
	    return defaultRet;
	}
    }

    /**
     * Retorna uma String como long
     * 
     * @return Retorna uma String como long
     * @param pStr
     *            � a String a ser transformada
     */
    public static long strToLong(String pStr) {
	try {
	    if (pStr == null)
		pStr = "0";

	    if (pStr.equals(""))
		pStr = "0";

	    pStr = pStr.trim();

	    return Long.parseLong(pStr);
	} catch (NumberFormatException e) {
	    return 0;
	}
    }

    /**
     * Retorna um tipo Long como String
     * 
     * @return Retorna um tipo Long como String
     * @param pLong
     *            � o Long a ser transformado
     */
    public static String longObjToStr(Long pLong) {
	try {
	    if (pLong == null)
		pLong = new Long(0);

	    return pLong.toString();
	} catch (NumberFormatException e) {
	    return "0";
	}
    }

    /**
     * Retorna uma String como tipo Long
     * 
     * @return Retorna uma String como tipo Long
     * @param pStr
     *            � a String a ser transformada
     */
    public static Long strToLongObj(String pStr) {
	try {
	    if (pStr == null)
		pStr = "0";

	    if (pStr.equals(""))
		pStr = "0";

	    pStr = pStr.trim();

	    return Long.valueOf(pStr);
	} catch (NumberFormatException e) {
	    return new Long(0);
	}
    }

    /**
     * Retorna um tipo Double como String
     * 
     * @return Retorna um tipo Double como String
     * @param pDouble
     *            � o Double a ser transformado
     */
    public static String doubleObjToStr(Double pDouble) {
	try {
	    if (pDouble == null)
		pDouble = new Double(0);

	    return pDouble.toString();
	} catch (NumberFormatException e) {
	    return "0";
	}
    }

    /**
     * Retorna uma String como tipo Double
     * 
     * @return Retorna uma String como tipo Double
     * @param pStr
     *            � a String a ser transformada
     */
    public static Double strToDoubleObj(String pStr) {
	try {
	    if (pStr == null)
		pStr = "0";

	    if (pStr.equals(""))
		pStr = "0";

	    pStr = pStr.trim();

	    return Double.valueOf(pStr);
	} catch (NumberFormatException e) {
	    return new Double(0);
	}
    }

    /**
     * Retorna uma String como float
     * 
     * @return Retorna uma String como float
     * @param pStr
     *            � a String a ser transformada
     */
    public static float strToFloat(String pStr) {
	try {
	    if (pStr == null)
		pStr = "0.0";

	    if (pStr.equals(""))
		pStr = "0.0";

	    pStr = pStr.trim();

	    return Float.valueOf(pStr).floatValue();
	} catch (NumberFormatException e) {
	    return 0.0f;
	}
    }

    /**
     * Retorna uma String como float
     * 
     * @return Retorna uma String como float
     * @param str
     *            � a String a ser transformada
     * @param defaultRet
     *            � o valor default de retorno
     */
    public static float strToFloat(String str, float defaultRet) {
	try {
	    return Float.parseFloat(str);
	} catch (NumberFormatException e) {
	    return defaultRet;
	}
    }

    /**
     * Retorna uma String como double
     * 
     * @return Retorna uma String como double
     * @param pStr
     *            � a String a ser transformada
     */
    public static double strToDouble(String pStr) {
	try {
	    log.log("****strToDouble****");
	    log.log("pStr[" + pStr + "]");

	    if (pStr == null)
		pStr = "0.0";

	    if (pStr.equals(""))
		pStr = "0.0";

	    pStr = pStr.trim();
	    log.log("pStr final[" + Double.parseDouble(pStr) + "]");
	    log.log("****strToDouble****");

	    return Double.parseDouble(pStr);
	} catch (NumberFormatException e) {
	    return 0.0d;
	}
    }

    /**
     * Retorna um tipo String como double
     * 
     * @return Retorna um tipo String como double
     * @param str
     *            � a String a ser transformada
     * @param defaultRet
     *            � o valor default de retorno
     */
    public static double strToDouble(String str, double defaultRet) {
	try {
	    return Double.parseDouble(str);
	} catch (NumberFormatException e) {
	    return defaultRet;
	}
    }

    /**
     * Retorna double como String
     * 
     * @return Retorna double como String
     * @param pNumber
     *            � o double a ser transformado
     */
    public static String doubleToStr(double pNumber) {
	return doubleFormat(pNumber, 7, 7, "#0.00#");
    }

    /**
     * Retorna uma string com um n�mero especificado de caracteres a esquerda
     * 
     * @return Retorna uma string com um n�mero especificado de caracteres a esquerda
     * @param sWord
     *            � a String a ser modificada
     * @param iLength
     *            � o tamnho da string final
     * @param sChar
     *            � uma String que indica o carater que ser� adicionado a esquerda da string inicial
     */
    public static String addLeftChar(String sWord, int iLength, String sChar) {
	try {
	    StringBuffer result = new StringBuffer(trim(sWord));

	    if ((sWord.length() > iLength) || (iLength <= 0) || (sChar.length() == 0))
		return sWord;

	    while (result.length() < iLength)
		result.insert(0, sChar);

	    return result.toString();
	} catch (Exception e) {
	    return "";
	}
    }

    /**
     * Retorna uma string com um n�mero especificado de caracteres a direita
     * 
     * @return Retorna uma string com um n�mero especificado de caracteres a direita
     * @param sWord
     *            � a String a ser modificada
     * @param iLength
     *            � o tamnho da string final
     * @param sChar
     *            � uma String que indica o carater que ser� adicionado a direita da string inicial
     */
    public static String addRightChar(String sWord, int iLength, String sChar) {
	try {
	    StringBuffer result = new StringBuffer(trim(sWord));

	    if ((sWord.length() > iLength) || (iLength <= 0) || (sChar.length() == 0))
		return sWord;

	    while (result.length() < iLength)
		result.append(sChar);

	    return result.toString();
	} catch (Exception e) {
	    return "";
	}
    }

    /**
     * True se a data especificada estiver no formato 99/99/9999 ou 99-99-9999
     * 
     * @return True se a data especificada estiver no formato 99/99/9999 ou 99-99-9999
     * @param str
     *            � a data a ser verificada
     */
    public static boolean isValidDateFormat(String str) {
	if ((str.length() != 10)
		|| (!((str.charAt(2) == '/') || (str.charAt(2) == '-')))
		|| (!((str.charAt(5) == '/') || (str.charAt(5) == '-')))
		|| (Character.isDigit(str.charAt(0)) == false)
		|| (Character.isDigit(str.charAt(1)) == false)
		|| (Character.isDigit(str.charAt(3)) == false)
		|| (Character.isDigit(str.charAt(4)) == false)
		|| (Character.isDigit(str.charAt(6)) == false)
		|| (Character.isDigit(str.charAt(7)) == false)
		|| (Character.isDigit(str.charAt(8)) == false)
		|| (Character.isDigit(str.charAt(9)) == false))
	    return false;
	else
	    return true;
    }

    /**
     * True se a hora especificada estiver no formato 99:99:99
     * 
     * @return True se a hora especificada estiver no formato 99:99:99
     * @param str
     *            � a hora a ser verificada
     */
    public static boolean isValidTimeFormat(String str) {
	if ((str.length() != 8)
		|| (!((str.charAt(2) == ':')))
		|| (!((str.charAt(5) == ':')))
		|| (Character.isDigit(str.charAt(0)) == false)
		|| (Character.isDigit(str.charAt(1)) == false)
		|| (Character.isDigit(str.charAt(3)) == false)
		|| (Character.isDigit(str.charAt(4)) == false)
		|| (Character.isDigit(str.charAt(6)) == false)
		|| (Character.isDigit(str.charAt(7)) == false))
	    return false;

	int hora = strToInt(str.substring(0, 2));
	int min = strToInt(str.substring(3, 5));
	int seg = strToInt(str.substring(6, 8));
	if ((hora < 0) || (hora > 23))
	    return false;
	if ((min < 0) || (hora > 59))
	    return false;
	if ((seg < 0) || (seg > 59))
	    return false;

	return true;
    }

    /**
     * True se a data e hora especificada estiver no formato dd/mm/yyyy hh:mi:ss
     * 
     * @return True se a data e hora especificada estiver no formato dd/mm/yyyy hh:mi:ss
     * @param str
     *            � a data e hora a ser verificada
     */
    public static boolean isValidDateTime(String str) throws Exception {
	String strdate = str.substring(0, 10);
	String strtime = str.substring(11);
	if (isValidTimeFormat(trim(strtime)) == false)
	    return false;

	if (isValidDate(trim(strdate)) == false)
	    return false;

	return true;
    }

    /**
     * Retorna Data da string no formato: "dd/mm/yyyy"
     * 
     * @return Retorna Data da string no formato: "dd/mm/yyyy"
     * @param str
     *            � a String ser modificada
     */
    public static Date strToDate(String str) throws Exception {
	int y;
	int m;
	int d;

	log.log("Data: " + str);
	if (!isValidDateFormat(str))
	    throw new Exception("Invalid date format");

	if (!isValidDate(str))
	    throw new Exception("Invalid date format");

	d = strToInt(str.substring(0, 2));
	m = strToInt(str.substring(3, 5));
	y = strToInt(str.substring(6));

	Calendar data = Calendar.getInstance();

	data.set(y, m - 1, d, 0, 0, 0);

	return data.getTime();
    }

    /**
     * Retorna a String da Data no formato: "dd/mm/yyyy"
     * 
     * @return Retorna a String da Data no formato: "dd/mm/yyyy"
     * @param dt
     *            � o tipo Date a ser transformado
     */
    public static String dateToStr(Date dt) throws Exception {
	if (dt == null)
	    return "";

	Calendar data = Calendar.getInstance();
	data.setTime(dt);
	StringBuffer y = new StringBuffer(new Integer(data.get(Calendar.YEAR)).toString());
	StringBuffer m = new StringBuffer(new Integer(data.get(Calendar.MONTH) + 1).toString());
	while (m.length() < 2)
	    m.insert(0, '0');
	StringBuffer d = new StringBuffer(new Integer(data.get(Calendar.DAY_OF_MONTH)).toString());
	while (d.length() < 2)
	    d.insert(0, '0');

	return d.toString() + "/" + m.toString() + "/" + y.toString();
    }

    /**
     * Retorna um tipo Date como String no formato: "ddmmyyyy"
     * 
     * @return Retorna um tipo Date como String no formato: "ddmmyyyy"
     * @param dt
     *            � o tipo Date a ser transformado
     */
    public static String dateToStrNumber(Date dt) {
	Calendar data = Calendar.getInstance();
	data.setTime(dt);
	StringBuffer y = new StringBuffer(new Integer(data.get(Calendar.YEAR)).toString());
	StringBuffer m = new StringBuffer(new Integer(data.get(Calendar.MONTH) + 1).toString());
	while (m.length() < 2)
	    m.insert(0, '0');
	StringBuffer d = new StringBuffer(new Integer(data.get(Calendar.DAY_OF_MONTH)).toString());
	while (d.length() < 2)
	    d.insert(0, '0');

	return y.toString() + m.toString() + d.toString();
    }

    /**
     * Retorna o n�mero de dias entre 2 datas
     * 
     * @return Retorna o n�mero de dias entre 2 datas
     * @param startDate
     *            � a data de in�cio
     * @param endDate
     *            � a data final
     * 
     *            Modifica��o: Frederico Oliveira, Data: 06/07/2011
     * @deprecated Utilizar UtilData.calcDaysBetween(...)
     */
    public static long calcDaysBetweenDates(Date startDate, Date endDate) {
	return ((endDate.getTime() - startDate.getTime()) / 86400000);
    }

    /**
     * Retorna uma data somada por um determinado n�mero de dias
     * 
     * @return Retorna uma data somada por um determinado n�mero de dias
     * @param days
     *            � on�mero de dias que ser� somado a data inicial
     * @param startDate
     *            � a data de in�cio
     */
    public static Date calcDatePlusDaysPeriod(Date startDate, int days) {
	long timeCalcDate =
	    new Double(
		    ((startDate.getTime() / 86400000.0) + days) * 86400000
	    ).longValue();
	return new Date(timeCalcDate);
    }

    /**
     * Verifica se o arquivo existe
     * 
     * @return True se o arquivo existe
     * @param filename
     *            � o nome do arquivo procurado
     */
    public static boolean fileExists(String filename) {
	return new File(filename).exists();
    }

    /**
     * Retorna o valor da vari�vel especificada do arquivo requisitado
     * 
     * @return Retorna o valor da vari�vel especificada do arquivo requisitado
     * @param iniFileName
     *            � o nome do arquivo
     * @param sectionName
     *            � o nome da se��o
     * @param keyName
     *            � o nome da vari�vel que se ir� pegar o valor
     * @param defaultValue
     *            � o valor default de retorno
     */
    public static String readIniString(
	    String iniFileName,
	    String sectionName,
	    String keyName,
	    String defaultValue) {
	try {
	    
	    
	    //[Importante - IN�CIO] deixar esse if �bvio aqui, ele � utilizado para substitui��o dos contextos de homologa��o
	    //e pr�-produ��o de acordo com esse coment�rio comentado com a chave [substituirContextoVersao], Renan Watanabe, 11/10/2019.
	    if(iniFileName.equals("conf" + File.separator + "param" + ".par")) {
		iniFileName = "conf" + File.separator + "param.par";//[substituirContextoVersao]
	    }
	    //[Importante - FIM]
	    
	    if (fileExists(iniFileName) == false) {
		System.out.println(
			"file "
			+ iniFileName
			+ " not found! The current working directory is "
			+ System.getProperty("user.dir"));
		return defaultValue;
	    }

	    BufferedReader bufReader = new BufferedReader(new FileReader(iniFileName));
	    String lineStr;
	    String line;
	    boolean sectionFound = false;
	    while ((line = bufReader.readLine()) != null) {
		lineStr = line.trim();
		if (!lineStr.equals("")) {
		    if (lineStr.charAt(0) == '[') {
			if (lineStr.toUpperCase().equals("[" + sectionName.toUpperCase() + "]"))
			    sectionFound = true;
			else
			    sectionFound = false;
		    }

		    if (sectionFound == true) {
			int n = lineStr.indexOf('=');
			if (n >= 0) {
			    if (lineStr
				    .substring(0, n)
				    .toUpperCase()
				    .trim()
				    .equals(keyName.toUpperCase()))
				return lineStr.substring(n + 1).trim();
			}
		    }
		}
	    }

	    bufReader.close();

	} catch (IOException ioException) {
	    System.out.println(ioException.getMessage());
	}

	return (defaultValue);
    }

    /**
     * Verifica se a data � v�lida
     * 
     * @return True se a data for v�lida, false caso contr�rio
     * @param str
     *            � a data que ser� verificada
     */
    public static boolean isValidDate(String str) throws Exception {
	log.log("Data: " + str);
	if (!isValidDateFormat(str))
	    throw new Exception("Invalid date format");

	int d = strToInt(str.substring(0, 2));
	int m = strToInt(str.substring(3, 5));
	int y = strToInt(str.substring(6));

	return isValidDay1(y, m, d);
    }

    /**
     * Verifica se a data � v�lida
     * 
     * @return True se a data for v�lida, false caso contr�rio
     * @param dt
     *            � a data que se ir� verificar
     */
    public static boolean isValidDay(Date dt) {

	Calendar data = Calendar.getInstance();
	data.setTime(dt);
	int y = (new Integer(data.get(Calendar.YEAR))).intValue();
	int m = (new Integer(data.get(Calendar.MONTH) + 1)).intValue();
	int d = (new Integer(data.get(Calendar.DAY_OF_MONTH))).intValue();

	return isValidDay1(y, m, d);
    }

    /**
     * Returns if if a valid date
     */
    private static boolean isValidDay1(int ano, int mes, int dia) {
	// int diasMes[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	if ((mes > 12) || (mes < 1) || (dia < 1) || (dia > 31) || (ano < 1)) {
	    return false;
	}
	int fim = 31;
	switch (mes) {
	// fevereiro

	case 2: {
	    if (ano % 4 == 0) // ano bisexto
		fim = 29;
	    else
		fim = 28;
	}
	break;
	// meses que tem 30 dias
	case 4:
	case 6:
	case 9:
	case 11:
	    fim = 30;
	    break;
	}

	if (dia > fim)
	    return false;

	return true;

    }

    /**
     * Verifica a exist�ncia de valor na String
     * 
     * @return True se a string for nula ou vazia
     * @param value
     *            � a String que ser� verificada
     */
    public boolean notExistenceOf(String value) {
	if (value == null || value.length() == 0)
	    return true;
	else
	    return false;
    }

    /**
     * Verifica se a String recebida � um int v�lido
     * 
     * @return True se a string recebida n�o for um int v�lido, false caso seja
     * @param value
     *            � a string verificada
     */
    public static boolean isNotIntNumber(String value) {
	try {
	    Integer.parseInt(value);
	} catch (NumberFormatException nfe) {
	    return true;
	}

	return false;
    }

    /**
     * Verifica se a String recebida � um n�mero v�lido
     * 
     * @return True se a string recebida for um n�mero v�lido
     */
    public static boolean isNumber(String value) {
	char ch;
	for (int i = 0; i < value.length(); i++) {
	    ch = value.charAt(i);
	    if (Character.isDigit(ch) == false) {
		if ((ch != '.') && (ch != ','))
		    return false;
	    }
	}

	return true;

    }

    /**
     * verifica se a String recebida � um float v�lido
     * 
     * @return True se a string recebida n�o for um float v�lido, false caso seja
     * @param value
     *            � a string verificada
     */
    public static boolean isNotFloatNumber(String value) {
	try {
	    Float.parseFloat(value);
	} catch (NumberFormatException nfe) {
	    return true;
	}

	return false;

    }

    /**
     * Retorna um float no formato de String
     * 
     * @return Retorna um float no formato de String
     * @param inValue
     *            � o double que ser� formatada
     * @param precision
     *            � a precis�o de arredondamento
     * @param use_comma
     *            � o indicador se o retorno ser� feito com ponto ou com virgula.
     */
    public static String floatToStrFormt(float inValue, int precision, boolean use_comma) {
	boolean trailing_zero;
	float absval = Math.abs(inValue); // obtem parte positiva

	if (precision < 0) {
	    precision = -precision;
	    trailing_zero = false;
	} else
	    trailing_zero = true;

	String signStr = "";

	if (inValue < 0)
	    signStr = "-";

	long intDigit = (long) Math.floor(absval); // obtem parte inteira

	String intDigitStr = String.valueOf(intDigit);

	if (use_comma) {
	    int intDigitStrLen = intDigitStr.length();
	    int dig_index = (intDigitStrLen - 1) % 3;

	    dig_index++;

	    String intCommaDigitStr = intDigitStr.substring(0, dig_index);

	    while (dig_index < intDigitStrLen) {
		intCommaDigitStr += "." + intDigitStr.substring(dig_index, dig_index + 3);
		dig_index += 3;
	    }

	    intDigitStr = intCommaDigitStr;
	}

	String precDigitStr = "";

	long precDigit = Math.round((absval - intDigit) * Math.pow(10, precision));

	precDigitStr = String.valueOf(precDigit);

	String zeroFilling = "";

	for (int i = 0; i < precision - precDigitStr.length(); i++)
	    zeroFilling += "0";

	precDigitStr = zeroFilling + precDigitStr;

	if (!trailing_zero) {
	    int lastZero;

	    for (lastZero = precDigitStr.length() - 1; lastZero >= 0; lastZero--)
		if (precDigitStr.charAt(lastZero) != '0')
		    break;

	    precDigitStr = precDigitStr.substring(0, lastZero + 1);
	}

	if (precDigitStr.equals(""))
	    return signStr + intDigitStr;
	else
	    return signStr + intDigitStr + "," + precDigitStr;
    }

    public static String doubleToStrFormt(double inValue, int precision, boolean point) {
	return (doubleToStrFormt(inValue, false, false));
    }

    public static String doubleToStrFormt(String inValue, int precision, boolean point) {
	return (doubleToStrFormt(strToDouble(inValue), false, false));
    }

    /**
     * Retorna um double no formato de String
     * 
     * @return Retorna um double no formato de String
     * @param value
     *            � o double que ser� formatada
     * @param roundIt
     *            � o indicador de arredondamento
     * @param point
     *            � o indicador se o retorno ser� feito com ponto ou com virgula.
     */
    public static String doubleToStrFormt(double inValue, boolean roundIt, boolean point) {
	String result = "";
	String sepEnd;
	String sepMidle;
	String strToWork;

	if (point == true) {
	    sepEnd = ".";
	    sepMidle = ",";
	} else {
	    sepEnd = ",";
	    sepMidle = ".";
	}

	String strDouble = strDoubleToMoney(doubleToStr(inValue), roundIt, point);

	String strEnd = strDouble.substring((strDouble.length() - 2), (strDouble.length()));

	String strStart = strDouble.substring(0, (strDouble.length() - 3));

	int posNeg = strStart.indexOf('-');

	if (posNeg > -1) {
	    result = "-";
	    strToWork = strStart.substring(1, strStart.length());
	} else
	    strToWork = strStart;

	int lenStrToWork = strToWork.length();
	int dig_index = (lenStrToWork - 1) % 3;

	dig_index++;

	result += strToWork.substring(0, dig_index);

	while (dig_index < lenStrToWork) {
	    result += sepMidle + strToWork.substring(dig_index, dig_index + 3);
	    dig_index += 3;
	}

	result += sepEnd + strEnd;

	return result;

    }

    /**
     * Faz a compara��o entre duas datas
     * 
     * @return 0(zero) se date2==date1, 1 se date2 > date1, -1 se date2 < date1 ou 999 se a fun��o
     *         falhar
     * @deprecated
     */
    public static int dateCompare(Date date2, Date date1) {
	try {
	    int d1 = strToInt(dateToStrNumber(date1));
	    int d2 = strToInt(dateToStrNumber(date2));
	    if (d2 > d1)
		return 1;
	    else if (d2 == d1)
		return 0;
	    else
		return -1;
	} catch (Exception e) {
	    log.log(e.getMessage());
	}
	return 999;
    }

    /**
     * Faz a compara��o entre duas datas
     * 
     * @return 0(zero) se date1==date2, menor que 0(zero) se date1 < date1 maior que 0(zero) se
     *         date2 > date1
     */
    public static int dateCompare(String date1, String date2) throws Exception {

	return dateCompare(strToDate(date1), strToDate(date2));
    }

    /**
     * @param strDate
     * @param strDateIni
     * @param strDateFim
     * @param comparaIgual
     * @return
     * @throws Exception
     */
    public static boolean dateBetween(
	    String strDate,
	    String strDateIni,
	    String strDateFim,
	    boolean comparaIgual) throws Exception {

	if (strDate.length() > 10)
	    strDate = strDate.substring(0, 10);
	if (strDateIni.length() > 10)
	    strDateIni = strDateIni.substring(0, 10);
	if (strDateFim.length() > 10)
	    strDateFim = strDateFim.substring(0, 10);

	Date date = Utils.strToDate(strDate);
	Date dateIni = (Utils.strToDate(strDateIni));
	Date dateFim = (Utils.strToDate(strDateFim.equals("") ? "31/12/2199" : strDateFim));

	return (dateBetween(date, dateIni, dateFim, comparaIgual));
    }

    /**
     * @param date
     * @param dateIni
     * @param dateFim
     * @param comparaIgual
     * @return
     * @throws Exception
     */
    public static boolean dateBetween(
	    Date date,
	    Date dateIni,
	    Date dateFim,
	    boolean comparaIgual) throws Exception {

	boolean ret = false;
	long time = date.getTime();
	long timeIni = dateIni.getTime();
	long timeFim = dateFim.getTime();

	if (comparaIgual) {
	    if (time >= timeIni && time <= timeFim) {
		ret = true;
	    }
	} else {
	    if (time > timeIni && time < timeFim) {
		ret = true;
	    }
	}
	return (ret);
    }

    /**
     * @param per1Ini
     * @param per1Fim
     * @param per2Ini
     * @param per2Fim
     * @return
     * @throws Exception
     */
    public static boolean periodosSobrepostos(
	    String per1Ini,
	    String per1Fim,
	    String per2Ini,
	    String per2Fim) throws Exception {

	if (per1Ini.length() > 10)
	    per1Ini = per1Ini.substring(0, 10);
	if (per1Fim.length() > 10)
	    per1Fim = per1Fim.substring(0, 10);
	if (per2Ini.length() > 10)
	    per2Ini = per2Ini.substring(0, 10);
	if (per2Fim.length() > 10)
	    per2Fim = per2Fim.substring(0, 10);

	Date datePer1Ini = (Utils.strToDate(per1Ini));
	Date datePer1Fim = (Utils.strToDate(per1Fim.equals("") ? "31/12/2199" : per1Fim));
	Date datePer2Ini = (Utils.strToDate(per2Ini));
	Date datePer2Fim = (Utils.strToDate(per2Fim.equals("") ? "31/12/2199" : per2Fim));

	return (periodosSobrepostos(datePer1Ini, datePer1Fim, datePer2Ini, datePer2Fim));
    }

    /**
     * @param datePer1Ini
     * @param datePer1Fim
     * @param datePer2Ini
     * @param datePer2Fim
     * @return
     * @throws Exception
     */
    public static boolean periodosSobrepostos(
	    Date datePer1Ini,
	    Date datePer1Fim,
	    Date datePer2Ini,
	    Date datePer2Fim) throws Exception {

	boolean ret = false;
	long per1Ini = datePer1Ini.getTime();
	long per1Fim = datePer1Fim.getTime();
	long per2Ini = datePer2Ini.getTime();
	long per2Fim = datePer2Fim.getTime();

	if ((per1Ini <= per2Ini && per1Fim >= per2Fim) ||
		(per1Ini >= per2Ini && per1Fim <= per2Fim) ||
		(per1Ini >= per2Ini && per1Ini <= per2Fim) ||
		(per1Fim >= per2Ini && per1Fim <= per2Fim)) {
	    ret = true;
	}

	return (ret);
    }

    /**
     * Converte um periodo no formato YYYYMM em data no formato DD/MM/YYYY, com o dia '01'
     * 
     * @param periodo
     * @return
     */
    public static String periodoToDataIni(String periodo) {
	return ("01/" + periodo.substring(4) + "/" + periodo.substring(0, 4));
    }

    /**
     * Converte uma data no formato DD/MM/YYYY ou DD-MM-YYYY no periodo correspondente no formato
     * YYYYMM
     * 
     * @param date
     * @return
     */
    public static String dataToPeriodo(String date) throws Exception {
	if (!isValidDateFormat(date))
	    throw new Exception("Invalid date format");

	if (!isValidDate(date))
	    throw new Exception("Invalid date format");

	return (date.substring(6) + date.substring(3, 5));

    }

    /* vers�o 1.5 do java */
    /*
	public static int dateCompare(String date1, String date2, String pattern) throws ParseException{

	        SimpleDateFormat sdf = new SimpleDateFormat();
	        sdf.applyPattern(pattern);

	        Date d1, d2 = null;
	        try{
	        	d1 = sdf.parse(date1);
	        	d2 = sdf.parse(date2);
	        }
	        catch (ParseException pe){
	        	sdf.applyPattern(DATA_COMPLETA);
	        	d1 = sdf.parse(date1);
	        	d2 = sdf.parse(date2);
	        }

	        Calendar c1 = Calendar.getInstance();
	        c1.setTimeInMillis(d1.getTime());

	        Calendar c2 = Calendar.getInstance();
	        c2.setTimeInMillis(d2.getTime());

	        return c1.compareTo(c2);
	}
     */

    /**
     * Retorna uma string no formato de dinheiro
     * 
     * @return Retorna uma string no formato de dinheiro
     * @param value
     *            � o double que ser� formatada
     * @param roundIt
     *            � o indicador de arredondamento
     * @param point
     *            � o indicador se o retorno ser� feito com ponto ou com virgula.
     */
    public static String strDoubleToMoney(double value, boolean roundIt, boolean point) {
	log.log("strDoubleToMoney -> valor recebido", value);
	return (strDoubleToMoney(doubleToStr(value), roundIt, point));
    }

    /**
     * Retorna uma string no formato de dinheiro
     * 
     * @return Retorna uma string no formato de dinheiro
     * @param strDouble1
     *            � a string que ser� formatada
     * @param roundIt
     *            � o indicador de arredondamento
     * @param point
     *            � o indicador se o retorno ser� feito com ponto ou com virgula.
     */
    public static String strDoubleToMoney(String strDouble1, boolean roundIt, boolean point) {
	log.log("****strDoubleToMoney****");
	log.log("strDouble1[" + strDouble1 + "]");

	String strFloat;
	double aux = strToDouble(strDouble1);
	strDouble1 = doubleToStr(aux);

	int posPoint = strDouble1.indexOf('.');
	if (posPoint > -1)
	    strFloat = strDouble1 + "0000";
	else
	    strFloat = strDouble1 + ".0000";

	posPoint = strFloat.indexOf('.');

	double x = strToDouble(strDouble1);

	if (roundIt == true) {
	    if (strFloat.charAt(posPoint + 3) >= '5')
		x = x + 0.01;
	}

	StringBuffer sb = new StringBuffer(doubleToStr(x));
	int pos = sb.toString().indexOf('.');

	if (pos > -1)
	    sb.append("0000");
	else
	    sb.append(".0000");

	if (point == true)
	    sb.setCharAt(pos, '.');
	else
	    sb.setCharAt(pos, ',');

	if (pos + 1 > sb.length() - 1) {
	    log.log("retorno [" + sb.toString() + "]");
	    log.log("****strDoubleToMoney****");
	    return sb.toString();
	} else {
	    log.log("retorno [" + sb.toString().substring(0, pos + 3) + "]");
	    log.log("****strDoubleToMoney****");
	    return sb.toString().substring(0, pos + 3);
	}
    }

    /**
     * Retorna uma string no formato de dinheiro
     * 
     * @return Retorna uma string no formato de dinheiro
     * @param strFloat1
     *            � a string que ser� formatada
     * @param roundIt
     *            � o indicador de arredondamento
     * @param point
     *            � o indicador se o retorno ser� feito com ponto ou com virgula.
     */
    public static String strFloatToMoney(String strFloat1, boolean roundIt, boolean point) {
	String strFloat;

	int posPoint = strFloat1.indexOf('.');
	if (posPoint > -1)
	    strFloat = strFloat1 + "0000";
	else
	    strFloat = strFloat1 + ".0000";

	posPoint = strFloat.indexOf('.');

	double x = strToDouble(strFloat1);
	if (roundIt == true) {
	    if (strFloat.charAt(posPoint + 3) >= '5') {
		x = x + 0.01;
	    }
	}

	StringBuffer sb = new StringBuffer(Double.toString(x));
	int pos = sb.toString().indexOf('.');
	if (pos > -1)
	    sb.append("0000");
	else
	    sb.append(".0000");

	if (point == true)
	    sb.setCharAt(pos, '.');
	else
	    sb.setCharAt(pos, ',');

	if (pos + 1 > sb.length() - 1)
	    return sb.toString();
	else
	    return sb.toString().substring(0, pos + 3);
    }

    /**
     * Retorna uma string no formato de dinheiro
     * 
     * @return Retorna uma string no formato de dinheiro
     * @param strFloat1
     *            � o float que ser� formatada
     * @param roundIt
     *            � o indicador de arredondamento
     * @param point
     *            � o indicador se o retorno ser� feito com ponto ou com virgula.
     */
    public static String strFloatToMoney(float f1, boolean roundIt, boolean point) {
	// String strFloat;
	return strFloatToMoney(floatToStr(f1), roundIt, point);
    }

    /**
     * Retorna o diret�rio local que a aplica��o est� sendo executada
     * 
     * @return Retorna o diret�rio local que a aplica��o est� sendo executada
     */
    public static String getCurrentDir() {
	return System.getProperty("user.dir");
    }

    /**
     * Remove caracteres de uma string.
     * 
     * @param Par�metro
     *            pOrigem � a string onde vai ser procurarado o caracter a ser removido.
     * @param Par�metro
     *            pChar � o caracter que se deseja remover.
     * @param Par�metro
     *            pInitialIndex � a partir de que posi��o deseja remover o caracter. Se n�o for
     *            passado o pInitialIndex, come�o a remover do in�cio da string.
     * @return Retorna a string sem o caracter que foi indicado a ser removido.
     */
    public static String removeChar(String pOrigem, char pChar, int pInitialIndex) {
	int pos;
	String result = "";

	pos = pOrigem.indexOf(pChar, pInitialIndex);
	while (pOrigem.length() > 0) {
	    if (pos >= 0) {
		result = result + pOrigem.substring(0, pos);

		if ((pos + 1) == pOrigem.length())
		    pOrigem = "";
		else
		    pOrigem = pOrigem.substring(pos + 1, pOrigem.length());
	    } else {
		result = result + pOrigem;
		pOrigem = "";
	    }
	    pos = pOrigem.indexOf(pChar);
	}

	return result;
    }

    /**
     * Remove caracteres de uma string.
     * 
     * @param Par�metro
     *            pOrigem � a string onde vai ser procurarado o caracter a ser removido.
     * @param Par�metro
     *            pChar � o caracter que se deseja remover.
     * @return Retorna a string sem o caracter que foi indicado a ser removido.
     */
    public static String removeChar(String pOrigem, char pChar) {
	return removeChar(pOrigem, pChar, 0);
    }

    /**
     * M�todo tokenize
     * 
     * @return Este m�todo separa um string em tokens. Exemplo: String line =
     *         "01|Aluno|Av.Rio Branco"; Neste caso o separador � "|". tokenize(line , "|");
     * 
     *         O resultado seria um vetor com 01 Aluno Av.Rio Branco
     * @param pOrigem
     *            � a string que se desesa desmembrar
     * @param separator
     *            � o separador dos Tokens
     */
    public static Vector tokenize(String pOrigem, String separator) {
	int pos;
	Vector result = new Vector();

	pos = pOrigem.indexOf(separator);
	while (pos >= 0) {
	    result.addElement(pOrigem.substring(0, pos));
	    pOrigem = pOrigem.substring(pos + separator.length(), pOrigem.length());
	    pos = pOrigem.indexOf(separator);
	}
	result.addElement(pOrigem);

	return result;
    }

    /**
     * O m�todo isValidDateFormat verifica se a data � v�lida recebe 2 par�metros. O 1�. recebe uma
     * data somente nos formatos ==> DD/MM/YYYY, DD-MM-YYYY, YYYYMMDD. O 2�. recebe uma conex�o.
     * retorna true se a data for v�lida e false para inv�lida
     */
    /*public static boolean isValidDateFormat(String str,  DataBaseTX MyConex)throws MsgExcept
	{
	      boolean retorno = true;
		  String crc = "";

		  log.log("Entrou no m�todo isValidDateFormat ");
		  log.log("**********************************");
		  log.log("Data Recebida � ==>"+ str);
		  log.log("**********************************");


		  try
		  {

			  if ( (str.length() > 10) || (str.length() < 8) )
			  {
		          //log.log("Data deve ter 10 ou 8 caracteres...");
				  retorno = false;
				  log.log("## Retorno recebeu false ##");
				  throw new MsgExcept(2016, cdLanguage);
			  }

			  else
			  if (str.length() == 8)
			  {
				  	 log.log("Data tem tamanho de 8....");

					 log.log("charAt(0)==>"+str.charAt(0));
					 log.log("charAt(1)==>"+str.charAt(1));
					 log.log("charAt(2)==>"+str.charAt(2));
					 log.log("charAt(3)==>"+str.charAt(3));
					 log.log("charAt(4)==>"+str.charAt(4));
					 log.log("charAt(5)==>"+str.charAt(5));
					 log.log("charAt(6)==>"+str.charAt(6));
					 log.log("charAt(7)==>"+str.charAt(7));

					 if ( ( Character.isDigit(str.charAt(0))== false )||
				          ( Character.isDigit(str.charAt(1))== false )||
				          ( Character.isDigit(str.charAt(3))== false )||
				          ( Character.isDigit(str.charAt(4))== false )||
				          ( Character.isDigit(str.charAt(6))== false )||
				          ( Character.isDigit(str.charAt(7))== false )

						 )
					 {
						    retorno = false;
							log.log("## Retorno recebeu false ##");

							log.log("Parametro data inv�lido.....");
						    throw new MsgExcept(2016, cdLanguage);
					 }

					 else
					 {
					    log.log("Data tamanho 8 OK.....");
						log.log("Olha a data OK!!!");

						StringBuffer SQLCommand = new StringBuffer(
						"SELECT TO_DATE('"+ str +"', 'YYYYMMDD') FROM DUAL ");

						if (MyConex.execute(SQLCommand) != 0)
						{
							log.log("Comando data 8 com erro ==>"+SQLCommand);
							retorno = false;
							log.log("## Retorno recebeu false ##");
							throw new MsgExcept(2016, cdLanguage);
						}
						else
						{
							log.log("Select da data OK.");

							String vRow = MyConex.getRow("%s");

							if (vRow.length() == 0 )
							{
								retorno = false;
								log.log("## Retorno recebeu false ##");
								throw new MsgExcept(2016, cdLanguage);
							}
							else
							   retorno = true;
							   log.log("DATA tam 8 passou select OK!!!");
						}
					 }
			  }
			  else
			  {
				  log.log("data tem tam 10...");

				  if ( (str.charAt(2)=='-')&&(str.charAt(5)=='-') )
				  	 crc = "-";
				  else
				  if ( (str.charAt(2)=='/')||(str.charAt(5)=='/') )
				     crc = "/";
				  else
				  {
					retorno = false;
					log.log("## Retorno recebeu false ##");
					log.log("A data deve ter (/) ou (-) ");
					throw new MsgExcept(2016, cdLanguage);
				  }
				  log.log("O CARACTER P/ FORMATO �===("+ crc +")");

				  if ( ( Character.isDigit(str.charAt(0))==false )||
			           ( Character.isDigit(str.charAt(1))==false )||
			           ( Character.isDigit(str.charAt(3))==false )||
			           ( Character.isDigit(str.charAt(4))==false )||
			           ( Character.isDigit(str.charAt(6))==false )||
			           ( Character.isDigit(str.charAt(7))==false )||
			           ( Character.isDigit(str.charAt(8))==false )||
			           ( Character.isDigit(str.charAt(9))==false )
			         )
				  {
						retorno = false;
						log.log("## Retorno recebeu false ##");
						throw new MsgExcept(2016, cdLanguage);
				  }
			      else
				  {
				      log.log("data tam 10 ok, vou fazer select....");

					  StringBuffer SQLCommand = new StringBuffer(
				   	  "SELECT TO_DATE(" +quoted(str) + ", 'DD"+crc+"MM"+crc+"YYYY') FROM DUAL");

					  if (MyConex.execute(SQLCommand) != 0)
					  {
					  	    log.log("Comando data 10 com erro===>"+SQLCommand);
							retorno = false;
							log.log("## Retorno recebeu false ##");
							throw new MsgExcept(2016, cdLanguage);
					  }
					  else
					  	  retorno = true;

				  }
			  }
		  }
		  finally
		  {
		  	log.log("O retorno da data � ===>"+ retorno);
			return retorno;
		  }

	}*/

    /**
     * Este m�todo recebe 2 par�metros. O 1�. � a data de Entrada O 2�. � a data de Sa�da(o formato
     * que se deseja ter a data.) No par�metro de entrada aceito somente os seguintes formatos ==>
     * (DD/MM/YYYY HH24:MI:SS, DD/MM/YYYY, DD-MM-YYYY, YYYYMMDD) No par�metro de sa�da eu retorno
     * somente os seguintes formatos ==> (DD/MM/YYYY, DD-MM-YYYY, MM/DD/YYYY, MM-DD-YYYY, YYYYMMDD)
     */

    /*public static String ReturnDateFormat (String dateInput, String dateOutPut) throws MsgExcept
	{
	     String dayi          = "";
		 String monthi        = "";
		 String yeari         = "";

		 String dayo          = "";
		 String montho        = "";
		 String yearo         = "";

		 String FormatInput  = "";
		 String FormatOutPut = "";

		 Log log = new Log("ReturnDateFormat");

		 String crci = "";
		 String crco = "";

		 //16/01/2001 12:09:25 ==> tam 19

		 /////////////////////// FORMATO DA DATA DE ENTRADA ///////////////////////////

		 if ( (dateInput.length() > 19) || (dateInput.length() < 8) )
		 {
		 	log.log("Data Inv�lida. A data de entrada deve ter apenas 10 ou 8 caracteres...");
			throw new MsgExcept(2016, cdLanguage);
		 }
		 else
		 if (dateInput.length() == 8)
		 {
			 if (( Character.isDigit(dateInput.charAt(0))==false )||
				 ( Character.isDigit(dateInput.charAt(1))==false )||
				 ( Character.isDigit(dateInput.charAt(3))==false )||
				 ( Character.isDigit(dateInput.charAt(4))==false )||
				 ( Character.isDigit(dateInput.charAt(6))==false )||
				 ( Character.isDigit(dateInput.charAt(7))==false )
				)
			 {
				  log.log("Erro no formato da data de entrada...===>"+ dateInput);
				  throw new MsgExcept(2016, cdLanguage);
			 }
			 else
			 {
			 	log.log("Data de entrada tem tamanho 8 no formato===>"+dateInput);

				yeari  = dateInput.charAt(0) +""+ dateInput.charAt(1) +""+
						 dateInput.charAt(2) +""+ dateInput.charAt(3);
				log.log("yeari==>"+yeari);

				monthi = dateInput.charAt(4) +""+ dateInput.charAt(5);
				log.log("monthi==>"+monthi);

				dayi   = dateInput.charAt(6) +""+ dateInput.charAt(7);
				log.log("dayi==>"+dayi);



				FormatInput = yeari + monthi + dayi;
				log.log("O formato da data de entrada � ===>"+FormatInput);
			 }
		 }//fim data com tam 8.
		 else
		 if (dateInput.length() == 10)
		 {
			 log.log("Data de entrada tem tamanho 10 no formato===>"+dateInput);

			 if ( (dateInput.charAt(2)=='-')&&(dateInput.charAt(5)=='-') )
			 	crci = "-";
			 else
			 if ( (dateInput.charAt(2)=='/')&&(dateInput.charAt(5)=='/') )
			 	crci = "/";
			 else
			 {
			 	log.log("Data de entrada deve est� separada por (/) ou (-)...");
				throw new MsgExcept(2016, cdLanguage);
			 }

			 if (( Character.isDigit(dateInput.charAt(0))==false )||
				 ( Character.isDigit(dateInput.charAt(1))==false )||
				 ( Character.isDigit(dateInput.charAt(3))==false )||
				 ( Character.isDigit(dateInput.charAt(4))==false )||
				 ( Character.isDigit(dateInput.charAt(6))==false )||
				 ( Character.isDigit(dateInput.charAt(7))==false )||
				 ( Character.isDigit(dateInput.charAt(8))==false )||
				 ( Character.isDigit(dateInput.charAt(9))==false )
				)
				 {
					  log.log("Erro no formato da data de entrada...===>"+ dateInput);
					  throw new MsgExcept(2016, cdLanguage);
				 }
			 else
			 {
			 	dayi   = dateInput.charAt(0) +""+ dateInput.charAt(1);
				log.log("dayi==>"+dayi);

				monthi = dateInput.charAt(3) +""+ dateInput.charAt(4);
				log.log("monthi==>"+monthi);

				yeari  = dateInput.charAt(6) +""+ dateInput.charAt(7) +""+
						 dateInput.charAt(8) +""+ dateInput.charAt(9);
				log.log("yeari==>"+yeari);

				FormatInput = dayi + crci + monthi + crci + yeari;
				log.log("O formato da data de entrada � ===>"+FormatInput);
			 }
		 }//fim da data tam 10
		 else
		 {
		 	log.log("Data de entrada tem tamanho 19 no formato===>"+dateInput);

			if ( (dateInput.charAt(2)=='-')&&(dateInput.charAt(5)=='-') )
			 	crci = "-";
			 else
			 if ( (dateInput.charAt(2)=='/')&&(dateInput.charAt(5)=='/') )
			 	crci = "/";
			 else
			 {
			 	log.log("Data de entrada deve est� separada por (/) ou (-)...");
				throw new MsgExcept(2016, cdLanguage);
			 }

			 if (( Character.isDigit(dateInput.charAt(0))==false )||
				 ( Character.isDigit(dateInput.charAt(1))==false )||
				 ( Character.isDigit(dateInput.charAt(3))==false )||
				 ( Character.isDigit(dateInput.charAt(4))==false )||
				 ( Character.isDigit(dateInput.charAt(6))==false )||
				 ( Character.isDigit(dateInput.charAt(7))==false )||
				 ( Character.isDigit(dateInput.charAt(8))==false )||
				 ( Character.isDigit(dateInput.charAt(9))==false )
				)
				 {
					  log.log("Erro no formato da data de entrada...===>"+ dateInput);
					  throw new MsgExcept(2016, cdLanguage);
				 }
			 else
			 {
			 	dayi   = dateInput.charAt(0) +""+ dateInput.charAt(1);
				log.log("dayi==>"+dayi);

				monthi = dateInput.charAt(3) +""+ dateInput.charAt(4);
				log.log("monthi==>"+monthi);

				yeari  = dateInput.charAt(6) +""+ dateInput.charAt(7) +""+
						 dateInput.charAt(8) +""+ dateInput.charAt(9);
				log.log("yeari==>"+yeari);

				FormatInput = dayi + crci + monthi + crci + yeari;
				log.log("O formato da data de entrada sem a hora � ===>"+FormatInput);
			 }
		 }//fim da data tam 19
		 //////////////////////////////////////////////////////////////////////////////

		 ////////////////////// FORMATO DA DATA DE SA�DA //////////////////////////////
		 if ( (dateOutPut.length() > 10) || (dateOutPut.length() < 8) )
		 {
		 	log.log("Data Inv�lida. A data de sa�da deve ter apenas 10 ou 8 caracteres...");
			throw new MsgExcept(2016, cdLanguage);
		 }
		 else
		 if (dateOutPut.length() == 8)
		 {
			 if (( !Character.isDigit(dateOutPut.charAt(0))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(1))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(3))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(4))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(6))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(7))==false )
				)
			 {
				  log.log("Erro no formato da data de sa�da...===>"+ dateOutPut);
				  throw new MsgExcept(2016, cdLanguage);
			 }
			 else
			 {
			 	log.log("Data de sa�da tem tamanho 8 no formato===>"+dateOutPut);

				yeari  = dateInput.charAt(6) +""+ dateInput.charAt(7) +""+
						 dateInput.charAt(8) +""+ dateInput.charAt(9);
				log.log("yeari==>"+yeari);

				monthi = dateInput.charAt(3) +""+ dateInput.charAt(4);
				log.log("monthi==>"+monthi);

				dayi   = dateInput.charAt(0) +""+ dateInput.charAt(1);
				log.log("dayi==>"+dayi);

				FormatOutPut = yeari +""+ monthi +""+ dayi;
				log.log("O formato da data de sa�da � ===>"+FormatOutPut);
			 }
		 }//Data com 8 pos.
		 else
		 {
			 log.log("Data de sa�da tem tamanho 10 no formato===>"+dateOutPut);

			 if ( (dateOutPut.charAt(2)=='-')&&(dateOutPut.charAt(5)=='-') )
			 	crco = "-";
			 else
			 if ( (dateOutPut.charAt(2)=='/')&&(dateOutPut.charAt(5)=='/') )
			 	crco = "/";
			 else
			 {
			 	log.log("A data de sa�da deve est� separada por (/) ou (-)...");
				throw new MsgExcept(2016, cdLanguage);
			 }

			log.log("Caracter de separa��o==>"+crco);

	//			log.log("dateOutPut.charAt(0)"+dateOutPut.charAt(0));
	//			log.log("dateOutPut.charAt(1)"+dateOutPut.charAt(1));
	//			log.log("dateOutPut.charAt(3)"+dateOutPut.charAt(3));
	//			log.log("dateOutPut.charAt(4)"+dateOutPut.charAt(4));
	//			log.log("dateOutPut.charAt(6)"+dateOutPut.charAt(6));
	//			log.log("dateOutPut.charAt(7)"+dateOutPut.charAt(7));
	//			log.log("dateOutPut.charAt(8)"+dateOutPut.charAt(8));
	//			log.log("dateOutPut.charAt(9)"+dateOutPut.charAt(9));


			 if (( !Character.isDigit(dateOutPut.charAt(0))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(1))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(3))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(4))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(6))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(7))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(8))==false )||
				 ( !Character.isDigit(dateOutPut.charAt(9))==false )
				)
				 {
					  log.log("Erro no formato da data de sa�da(***)...===>"+ dateOutPut);
					  throw new MsgExcept(2016, cdLanguage);
				 }
			 else
			 {
			 	if ( (dateOutPut.charAt(0) == 'd') || (dateOutPut.charAt(1) == 'D')||
					 (dateOutPut.charAt(0) == 'D') || (dateOutPut.charAt(1) == 'd')
				   )
				{
					dayo = "DD";
					log.log("A 1�. e 2�. posi��o vai ser ==>> DD");
				}
				else
				if ( (dateOutPut.charAt(0) == 'm') || (dateOutPut.charAt(1) == 'M')||
					 (dateOutPut.charAt(0) == 'M') || (dateOutPut.charAt(1) == 'm')
				   )
				{
					dayo = "MM";
					log.log("A 1�. e 2�. posi��o vai ser ==>> MM");
				}
				else
				{
					log.log("Erro no formato da data de sa�da. O 1�. e 2�. caracter devem ser==>> MM ou DD");
					throw new MsgExcept(2016, cdLanguage);
				}

				//<==================================================================>

				if ( (dateOutPut.charAt(3) == 'd') || (dateOutPut.charAt(4) == 'D')||
					 (dateOutPut.charAt(3) == 'D') || (dateOutPut.charAt(4) == 'd')
				   )
				{
					montho = "DD";
					log.log("A 3�. e 4�. posi��o vai ser ==>> DD");
				}
				else
				if ( (dateOutPut.charAt(3) == 'm') || (dateOutPut.charAt(4) == 'M')||
					 (dateOutPut.charAt(3) == 'M') || (dateOutPut.charAt(4) == 'm')
				   )
				{
					montho = "MM";
					log.log("A 3�. e 4�. posi��o vai ser ==>> MM");
				}
				else
				{
					log.log("Erro no formato da data de sa�da. O 3�. e 4�. caracter devem ser==>> MM ou DD");
					throw new MsgExcept(2016, cdLanguage);
				}

				if ( (dayo.equals("DD")) && (montho.equals("MM")) )
				{
					FormatOutPut = dayi + crco + monthi + crco + yeari;
					log.log("O formato da data de sa�da � ===>"+FormatOutPut);
				}
				else
				{
					FormatOutPut = monthi + crco + dayi + crco + yeari;
					log.log("O formato da data de sa�da � ===>"+FormatOutPut);
				}
			 }
		 }//fim da data tam 10
		 //////////////////////////////////////////////////////////////////////////////

		 return FormatOutPut;
	}/*

	////////////////////////////////////////////////////////////////////////////
	/** Este m�todo recebe 3 par�metros.
     *  O 1�. param � a data de Entrada
     *  O 2�. param � o formato da data de entrada Ex.: DD/MM/YYYY
     *  O 3�. � o formato de sa�da Ex.: DD/MM/YYYY
     *  retorno � a data no formato escolhido
     */

    public static String returnDateFormat(
	    String dtInput,
	    String dtInputFormat,
	    String dtOutPutFormat)
    throws Exception {
	String dayi = "";
	String monthi = "";
	String yeari = "";
	String hr = "";
	String mi = "";
	String ss = "";

	String dateOutPutAux = "";
	// String dtInputAux = "";

	Log log = new Log("Utils");

	// ///////////////////////// FORMATO DA DATA DE ENTRADA ////////////////////////////////

	if ((dtInput.length() < dtInputFormat.length())) {

	    log.log("Data de Entrada maior do que formato...");
	    dateOutPutAux = "";
	    throw new Exception("Formato de data inv�lida");
	}

	if (dtInputFormat.equalsIgnoreCase("DDMMYYYY")) {

	    dayi = dtInput.substring(0, 2);
	    monthi = dtInput.substring(2, 4);
	    yeari = dtInput.substring(4, 8);
	} else if ((dtInputFormat.equalsIgnoreCase("DD/MM/YYYY"))
		|| (dtInputFormat.equalsIgnoreCase("DD-MM-YYYY"))) {

	    dayi = dtInput.substring(0, 2);
	    monthi = dtInput.substring(3, 5);
	    yeari = dtInput.substring(6, 10);
	} else if ((dtInputFormat.equalsIgnoreCase("DD/MM/YYYY HH:MI:SS"))
		|| (dtInputFormat.equalsIgnoreCase("DD-MM-YYYY HH:MI:SS"))) {

	    dayi = dtInput.substring(0, 2);
	    monthi = dtInput.substring(3, 5);
	    yeari = dtInput.substring(6, 10);
	    hr = dtInput.substring(11, 13);
	    mi = dtInput.substring(14, 16);
	    ss = dtInput.substring(17, 19);
	} else if ((dtInputFormat.equalsIgnoreCase("MM-DD-YYYY"))
		|| (dtInputFormat.equalsIgnoreCase("MM/DD/YYYY"))) {

	    monthi = dtInput.substring(0, 2);
	    dayi = dtInput.substring(3, 5);
	    yeari = dtInput.substring(6, 10);
	} else if (dtInputFormat.equalsIgnoreCase("YYYY-MM-DD HH:MI:SS.D")) {

	    yeari = dtInput.substring(0, 4);
	    monthi = dtInput.substring(5, 7);
	    dayi = dtInput.substring(8, 10);
	    hr = dtInput.substring(11, 13);
	    mi = dtInput.substring(14, 16);
	    ss = dtInput.substring(17, 19);
	} else if (dtInputFormat.equalsIgnoreCase("YYYYMMDD HH:MI:SS")) {

	    yeari = dtInput.substring(0, 4);
	    monthi = dtInput.substring(4, 6);
	    dayi = dtInput.substring(6, 8);
	    hr = dtInput.substring(9, 11);
	    mi = dtInput.substring(12, 14);
	    ss = dtInput.substring(15, 17);
	} else if (dtInputFormat.equalsIgnoreCase("YYYYMMDD HHMISS")) {
	    yeari = dtInput.substring(0, 4);
	    monthi = dtInput.substring(4, 6);
	    dayi = dtInput.substring(6, 8);
	    hr = dtInput.substring(9, 11);
	    mi = dtInput.substring(11, 13);
	    ss = dtInput.substring(13, 15);
	} else if (dtInputFormat.equalsIgnoreCase("YYYYMMDD")) {

	    yeari = dtInput.substring(0, 4);
	    monthi = dtInput.substring(4, 6);
	    dayi = dtInput.substring(6, 8);
	} else if (dtInputFormat.equalsIgnoreCase("HHMISS")) {

	    hr = dtInput.substring(0, 2);
	    mi = dtInput.substring(2, 4);
	    ss = dtInput.substring(4, 6);
	} else if (dtInputFormat.equalsIgnoreCase("HH:MI:SS")) {
	    hr = dtInput.substring(0, 2);
	    mi = dtInput.substring(3, 5);
	    ss = dtInput.substring(6, 8);
	} else {
	    log.log("ERRO NO FORMATO DE ENTRADA==>" + dtInputFormat);
	    throw new Exception("formato de data inv�lida");
	}

//	log.log("Formato de Entrada � ==>>" + dtInputFormat);
//	log.log("yeari==>" + yeari);
//	log.log("monthi==>" + monthi);
//	log.log("dayi==>" + dayi);
//	log.log("hr==>" + hr);
//	log.log("mi==>" + mi);
//	log.log("ss==>" + ss);

	// //////////////////////// FORMATO DA DATA DE SA�DA ///////////////////////////////
	if (dtOutPutFormat.equalsIgnoreCase("DDMMYYYY")) {
	    dateOutPutAux = dayi + monthi + yeari;
	} else if (dtOutPutFormat.equalsIgnoreCase("DD/MM/YYYY")) {
	    dateOutPutAux = dayi + "/" + monthi + "/" + yeari;
	} else if (dtOutPutFormat.equalsIgnoreCase("MM/YYYY")) {
	    dateOutPutAux = monthi + "/" + yeari;
	} else if (dtOutPutFormat.equalsIgnoreCase("DD-MM-YYYY")) {
	    dateOutPutAux = dayi + "-" + monthi + "-" + yeari;
	} else if (dtOutPutFormat.equalsIgnoreCase("DD/MM/YYYY HH:MI:SS")) {
	    dateOutPutAux = dayi + "/" + monthi + "/" + yeari + " " + hr + ":" + mi + ":" + ss;
	} else if (dtOutPutFormat.equalsIgnoreCase("DD-MM-YYYY HH:MI:SS")) {
	    dateOutPutAux = dayi + "-" + monthi + "-" + yeari + " " + hr + ":" + mi + ":" + ss;
	} else if ((dtOutPutFormat.equalsIgnoreCase("MM-DD-YYYY"))
		|| (dtOutPutFormat.equalsIgnoreCase("MM/DD/YYYY"))) {
	    dateOutPutAux = monthi + "-" + dayi + "-" + yeari;
	} else if (dtOutPutFormat.equalsIgnoreCase("MM/DD/YYYY HH:MI:SS")) {
	    dateOutPutAux = monthi + "-" + dayi + "-" + yeari + " " + hr + ":" + mi + ":" + ss;
	} else if (dtOutPutFormat.equalsIgnoreCase("YYYYMMDD")) {
	    dateOutPutAux = yeari + monthi + dayi;
	} else if (dtOutPutFormat.equalsIgnoreCase("YYYYMM")) {
	    dateOutPutAux = yeari + monthi;
	} else if (dtOutPutFormat.equalsIgnoreCase("YYYYMMDD HH:MI:SS")) {
	    dateOutPutAux = yeari + monthi + dayi + " " + hr + ":" + mi + ":" + ss;
	} else if (dtOutPutFormat.equalsIgnoreCase("YYYYMMDD HHMISS")) {
	    dateOutPutAux = yeari + monthi + dayi + " " + hr + mi + ss;
	} else if (dtOutPutFormat.equalsIgnoreCase("HHMISS")) {
	    dateOutPutAux = hr + mi + ss;
	} else if (dtOutPutFormat.equalsIgnoreCase("HH:MI:SS")) {
	    dateOutPutAux = hr + ":" + mi + ":" + ss;
	} else {
	    log.log("ERRO NO FORMATO DE SA�DA==>" + dtOutPutFormat);
	    throw new Exception("formato de data inv�lida");
	}

//	log.log("Formato de Sa�da escolhido � ==>>" + dtOutPutFormat);
//	log.log("Data no Formato escolhido � ==>>" + dateOutPutAux);
//	log.log("yeari==>" + yeari);
//	log.log("monthi==>" + monthi);
//	log.log("dayi==>" + dayi);
//	log.log("hr==>" + hr);
//	log.log("mi==>" + mi);
//	log.log("ss==>" + ss);

	return dateOutPutAux;
    }

    // //////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna a data e hora do servidor web
     * 
     * @return Retorna a data e hora do servidor web
     */
    public static String getDateTime() {
	// ESSE METODO N�O PODE TER LOG
	GregorianCalendar data = new GregorianCalendar();

	String dateTime = "";

	dateTime =
	    addLeftChar(intToStr(data.get(GregorianCalendar.DAY_OF_MONTH)), 2, "0")
	    + "/"
	    + addLeftChar(intToStr((data.get(GregorianCalendar.MONTH) + 1)), 2, "0")
	    + "/"
	    + data.get(GregorianCalendar.YEAR)
	    + " "
	    + addLeftChar(intToStr((data.get(GregorianCalendar.HOUR_OF_DAY))), 2, "0")
	    + ":"
	    + addLeftChar(intToStr((data.get(GregorianCalendar.MINUTE))), 2, "0")
	    + ":"
	    + addLeftChar(intToStr((data.get(GregorianCalendar.SECOND))), 2, "0");

	return dateTime;
    }

    /**
     * Retorna a data do servidor web
     * 
     * @return Retorna a data do servidor web
     */
    public static String getDate() {
	// ESSE METODO N�O PODE TER LOG
	GregorianCalendar data = new GregorianCalendar();

	String dateTime = "";

	dateTime =
	    addLeftChar(intToStr(data.get(GregorianCalendar.DAY_OF_MONTH)), 2, "0")
	    + "/"
	    + addLeftChar(intToStr((data.get(GregorianCalendar.MONTH) + 1)), 2, "0")
	    + "/"
	    + data.get(GregorianCalendar.YEAR)
	    + " "
	    + addLeftChar(intToStr((data.get(GregorianCalendar.DAY_OF_WEEK))), 2, "0");

	return dateTime;
    }

    /**
     * Retorna a data do servidor web
     * 
     * @return Retorna a data do servidor web
     */
    public static String getDateNow() {
	// ESSE METODO N�O PODE TER LOG
	GregorianCalendar data = new GregorianCalendar();

	String dateTime = "";

	dateTime =
	    addLeftChar(intToStr(data.get(GregorianCalendar.DAY_OF_MONTH)), 2, "0")
	    + "/"
	    + addLeftChar(intToStr((data.get(GregorianCalendar.MONTH) + 1)), 2, "0")
	    + "/"
	    + data.get(GregorianCalendar.YEAR);

	return dateTime;
    }

    /**
     * Verifica se o CPF � v�lido
     * 
     * @return True se o CPF � v�lido
     * @param a
     *            CPF
     */
    public static boolean validaCPF(String s) {
	String c;
	int d = 0;
	int e = 0;
	int x = 0;
	String y = "";
	int soma = 0;
	int dig1 = 0;
	int dig2 = 0;
	String texto = "";
	String numcpf1 = "";
	String numcpf = "";
	int len = 0;

	for (int i = 0; i < s.length(); i++) {
	    c = s.substring(i, i + 1);
	    if (isNumber(c))
		numcpf = numcpf + c;
	}

	if (numcpf.length() != 11)
	    return false;

	for (int i = 0; i < 11; i++) {
	    if (i == 10)
		return false;
	    d = strToInt(numcpf.substring(i, i + 1));

	    if (i > 0) {
		e = strToInt(numcpf.substring(i - 1, i));
		if (d == e)
		    continue;
		else
		    break;
	    }
	}

	len = numcpf.length();
	x = len - 1;

	for (int i = 0; i <= len - 3; i++) {
	    y = numcpf.substring(i, i + 1);
	    soma = soma + (strToInt(y) * x);
	    x = x - 1;
	    texto = texto + y;
	}

	dig1 = 11 - (soma % 11);
	if (dig1 == 10)
	    dig1 = 0;
	if (dig1 == 11)
	    dig1 = 0;
	numcpf1 = numcpf.substring(0, len - 2) + dig1;
	x = 11;
	soma = 0;

	for (int i = 0; i <= len - 2; i++) {
	    soma = soma + (strToInt(numcpf1.substring(i, i + 1)) * x);
	    x = x - 1;
	}

	dig2 = 11 - (soma % 11);

	if (dig2 == 10)
	    dig2 = 0;
	if (dig2 == 11)
	    dig2 = 0;

	if (strToInt(dig1 + "" + dig2) == strToInt(numcpf.substring(len - 2, len)))
	    return true;

	return false;
    }

    public static String tirarMascara(String s) {
	String num = "";
	String c;
	for (int i = 0; i < s.length(); i++) {
	    c = s.substring(i, i + 1);
	    if (!isNotIntNumber(c))
		num = num + c;
	}
	return num;
    }

    /**
     * Verifica se o CNPJ � v�lido
     * 
     * @return True se o CNPJ � v�lido
     * @param a
     *            CNPJ
     */
    public static boolean validaCNPJ(String s) {
	String c;
	int x = 2;
	String y = "";
	int soma = 0;
	int dig1 = 0;
	int dig2 = 0;
	String numcgc1 = "";
	String numcgc = "";
	int len = 0;

	if (s.trim().length() < 14)
	    return false;

	for (int i = 0; i < s.length(); i++) {
	    c = s.substring(i, i + 1);
	    if (isNumber(c))
		numcgc = numcgc + c;
	}

	len = numcgc.length();
	for (int i = len - 3; i >= 0; i--) {
	    y = numcgc.substring(i, i + 1);
	    soma = soma + (strToInt(y) * x);

	    if (x == 9)
		x = 2;
	    else
		x = x + 1;
	}

	dig1 = 11 - (soma % 11);
	if (dig1 == 10)
	    dig1 = 0;
	if (dig1 == 11)
	    dig1 = 0;
	numcgc1 = numcgc.substring(0, len - 2) + dig1;
	x = 2;
	soma = 0;
	for (int i = len - 2; i >= 0; i--) {
	    soma = soma + (strToInt(numcgc1.substring(i, i + 1)) * x);
	    if (x == 9)
		x = 2;
	    else
		x = x + 1;
	}

	dig2 = 11 - (soma % 11);
	if (dig2 == 10)
	    dig2 = 0;
	if (dig2 == 11)
	    dig2 = 0;
	if (strToInt(dig1 + "" + dig2) == strToInt(numcgc.substring(len - 2, len)))
	    return true;

	return false;
    }

    /*public static boolean checkWeekend(String sDate, int cdLanguage) throws MsgExcept
	{
		DataBaseTX MyConex = new DataBaseTX();
		if (MyConex.dbConnect() != 0)
		{
			throw new MsgExcept(1000, cdLanguage);
		}


		try
		{
			StringBuffer SQLCommand = new StringBuffer("SELECT TO_DATE('" + sDate + "', 'D') FROM DUAL");

			if (MyConex.execute(SQLCommand) != 0)
			{
				throw new MsgExcept(1002, cdLanguage);
			}

			MyConex.getRow("%s");

			if (MyConex.getColumn(1, "%s").equals("7") || MyConex.getColumn(1, "%s").equals("1"))
			{
				return false;
			}

			return true;
		}
		finally
		{
			MyConex.disconnect();
		}
	}*/

    /**
     * Formats a double.
     * 
     * @param double to be formated.
     * @param num
     *            max of decimal digits.
     * @param num
     *            min of decimal digits.
     * @param format
     *            to be applied on the double.
     */
    public static String doubleFormat(
	    double number,
	    int maxDecimal,
	    int minDecimal,
	    String format) {
	return (doubleFormat(number, maxDecimal, minDecimal, format, '.', ','));
    }

    /**
     * Retorna um double formatado.
     * 
     * @return Retorna um double formatado.
     * @param number
     *            � o double a ser formatado.
     * @param maxDecimal
     *            � o numero maximo de digitos decimais.
     * @param minDecimal
     *            n�mero m�nimo de digitos decimais.
     * @param format
     *            � o formato a ser aplicado no double.
     * @param pDecSep
     *            � o separador decimal.
     * @param pGroupSep
     *            � o separator de grupos.
     */
    public static String doubleFormat(
	    double number,
	    int maxDecimal,
	    int minDecimal,
	    String format,
	    char pDecSep,
	    char pGroupSep) {
	try {
	    log.log("****doubleFormat****");
	    log.log("number recebido[" + number + "]");

	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setDecimalSeparator(pDecSep);
	    dfs.setGroupingSeparator(pGroupSep);

	    DecimalFormat numFormat = new DecimalFormat(format);
	    numFormat.setMaximumFractionDigits(maxDecimal);
	    numFormat.setMinimumFractionDigits(minDecimal);
	    numFormat.setDecimalFormatSymbols(dfs);
	    log.log("str number devolvido[" + numFormat.format(number) + "]");
	    log.log("****doubleFormat****");

	    return (numFormat.format(number));
	} catch (Exception e) {
	    return ("");
	}
    }

    /**
     * Retorna um double com apenas 2 decimais, truncando.
     * 
     * @return Retorna um double com apenas 2 decimais, truncando.
     * @param number
     *            � o valor a ser modificado
     */
    public static double double2double(double number) {
	log.log("****double2double****");
	log.log("number recebido[" + number + "]");
	double result;
	String resString = doubleFormat(number, 7, 7, "#0.00");
	String resString2 = strDoubleToMoney(resString, false, true);
	result = strToDouble(resString2);
	log.log("number devolvido[" + result + "]");
	log.log("****double2double****");
	return (result);
    }

    /**
     * Retorna uma String formatada como YYYYMM
     * 
     * @return Retorna uma String formatada como YYYYMM correspondente ao ultimo m�s ou vazio caso
     *         ocorra algum erro.
     * @param dtSyst
     *            � data do sistema
     * @param lastMonth
     *            diz se � o ultimo m�s do ano
     */
    public static String getYearMonth(String dtSyst, String lastMonth) {
	log.log("");
	log.log("####### getYearMonth ");
	log.log("");

	log.log("dtSyst:[" + dtSyst + "]");

	if (dtSyst.equals("")) {
	    log.log("dtSyst = VAZIO");
	    return ("");
	}

	log.log("lastMonth:[" + lastMonth + "]");
	if (!((lastMonth.equals("S")) || (lastMonth.equals("N")))) {
	    log.log("Erro lastMonth");
	    return ("");
	}

	int tam = dtSyst.length();
	log.log("tam:[" + tam + "]");

	if (tam != 10) {
	    log.log("Erro tam");
	    return ("");
	}

	String month = dtSyst.substring(3, 5);
	log.log("month:[" + month + "]");
	String year = dtSyst.substring(6, 10);
	log.log("year:[" + year + "]");

	if (lastMonth.equals("N")) {
	    log.log("return:/" + year + month + "/");
	    return (year + month);
	}

	int intMonth = strToInt(month);
	log.log("intMonth:[" + intMonth + "]");
	int intYear = strToInt(year);
	log.log("intYear:[" + intYear + "]");

	if (intMonth == 1) {
	    intMonth = 12;
	    log.log("intMonth2:[" + intMonth + "]");
	    intYear = intYear - 1;
	    log.log("intYear2:[" + intYear + "]");
	} else {
	    intMonth = intMonth - 1;
	    log.log("intMonth3:[" + intMonth + "]");
	}

	month = addLeftChar(intToStr(intMonth), 2, "0");
	year = addLeftChar(intToStr(intYear), 4, "0");

	log.log("return:/" + year + month + "/");

	return (year + month);
    }

    /*public static String getValidDate (String yearMonth, int day, DataBaseTX  MyConex)throws MsgExcept
	{
	    log.log("");
	    log.log("####### getValidDate ");
	    log.log("");

	    log.log("day:/"+day+"/");
	    if ((day < 1) || (day > 31))
	    {
	      log.log("Erro dia");
	      return ("");
	    }

	    int tam = yearMonth.length();
	    log.log("tam:["+tam+"]");

	    if ((tam <= 0) || (tam > 6))
	    {
	      log.log("Erro tam");
	      return("");
	    }

	    log.log("yearMonth:/"+yearMonth+"/");
	    String year = yearMonth.substring(0, 4);
	    log.log("year:["+year+"]");

	    String month = yearMonth.substring(4, 6);
	    log.log("month:["+month+"]");


	    NormalWorkDay normalWorkDay = new NormalWorkDay();

	    boolean calcNewDate = true;

	    String strDate = "";

	    while (calcNewDate == true)
	    {
	      try
	      {
	        strDate = addLeftChar(intToStr(day), 2, "0") +"/"+month+"/"+year;
	        log.log("");
	        log.log("strDate:["+strDate+"]");
	        day = day - 1;
	        log.log("day:/"+day+"/");
	        if ((day < 1) || (day > 31))
	        {
	          log.log("Erro dia");
	          return ("");
	        }
	  	    normalWorkDay.isValidDate(strDate, MyConex);
	        calcNewDate = false;
	      }
	      catch(Exception e)
	      {
	  	     log.log("ERRO NA DATA: "+e);
	         log.log("calcNewDate:["+calcNewDate+"]");
	      }
	    }
	    log.log("strDate2:["+strDate+"]");
	    return(strDate);
	}*/

    /**
     * Retorna o n�mero do m�s especificado no idioma pedido 1 = portugu�s, 2 = ingl�s
     * 
     * @return Retorna o n�mero do m�s especificado no idioma pedido 1 = portugu�s, 2 = ingl�s
     * @param month
     *            � o m�s descritivo
     * @param cdLang
     *            � o idioma
     */
    public static int getMonthNumber(String monthDesc, int cdLang) {
	log.log("");
	log.log("####### getMonthNumber ");
	log.log("");

	log.log("month:" + monthDesc);
	log.log("cdLang:" + cdLang);

	monthDesc = monthDesc.toUpperCase();
	int monthNumber = 0;

	if (((cdLang == 1 || cdLang == 2) && (monthDesc.indexOf("JAN") >= 0))) {
	    monthNumber = 1;
	} else if (((cdLang == 1) && (monthDesc.indexOf("FEV") >= 0)) || (cdLang == 2 && monthDesc.indexOf("FEB") >= 0)) {
	    monthNumber = 2;
	} else if (((cdLang == 1 || cdLang == 2) && (monthDesc.indexOf("MAR") >= 0))) {
	    monthNumber = 3;
	} else if (((cdLang == 1) && (monthDesc.indexOf("ABR") >= 0)) || (cdLang == 2 && monthDesc.indexOf("APR") >= 0)) {
	    monthNumber = 4;
	} else if (((cdLang == 1) && (monthDesc.indexOf("MAI") >= 0)) || (cdLang == 2 && monthDesc.indexOf("MAY") >= 0)) {
	    monthNumber = 5;
	} else if (((cdLang == 1 || cdLang == 2) && (monthDesc.indexOf("JUN") >= 0))) {
	    monthNumber = 6;
	} else if (((cdLang == 1 || cdLang == 2) && (monthDesc.indexOf("JUL") >= 0))) {
	    monthNumber = 7;
	} else if (((cdLang == 1) && (monthDesc.indexOf("AGO") >= 0)) || (cdLang == 2 && monthDesc.indexOf("AUG") >= 0)) {
	    monthNumber = 8;
	} else if (((cdLang == 1) && (monthDesc.indexOf("SET") >= 0)) || (cdLang == 2 && monthDesc.indexOf("SEP") >= 0)) {
	    monthNumber = 9;
	} else if (((cdLang == 1) && (monthDesc.indexOf("OUT") >= 0)) || (cdLang == 2 && monthDesc.indexOf("OCT") >= 0)) {
	    monthNumber = 10;
	} else if (((cdLang == 1 || cdLang == 2) && (monthDesc.indexOf("NOV") >= 0))) {
	    monthNumber = 11;
	} else if (((cdLang == 1) && (monthDesc.indexOf("DEZ") >= 0)) || (cdLang == 2 && monthDesc.indexOf("DEC") >= 0)) {
	    monthNumber = 12;
	}

	log.log("monthNumber:" + monthNumber);
	return (monthNumber);
    }

    /**
     * Retorna a descri��o do m�s especificado no idioma pedido 1 = portugu�s, 2 = ingl�s
     * 
     * @return Retorna a descri��o do m�s especificado no idioma pedido 1 = portugu�s, 2 = ingl�s
     * @param month
     *            � o m�s
     * @param cdLang
     *            � o idioma
     */
    public static String getMonthDesc(int month, int cdLang) {
	log.log("");
	log.log("####### getMonthDesc ");
	log.log("");

	log.log("month:" + month);
	log.log("cdLang:" + cdLang);

	String monthDesc = "";

	switch (month) {
	case 1: {
	    if (cdLang == 1)
		monthDesc = "Janeiro";
	    else
		monthDesc = "January";
	    break;
	}
	case 2: {
	    if (cdLang == 1)
		monthDesc = "Fevereiro";
	    else
		monthDesc = "February";
	    break;
	}
	case 3: {
	    if (cdLang == 1)
		monthDesc = "Mar�o";
	    else
		monthDesc = "March";
	    break;
	}
	case 4: {
	    if (cdLang == 1)
		monthDesc = "Abril";
	    else
		monthDesc = "April";
	    break;
	}
	case 5: {
	    if (cdLang == 1)
		monthDesc = "Maio";
	    else
		monthDesc = "May";
	    break;
	}
	case 6: {
	    if (cdLang == 1)
		monthDesc = "Junho";
	    else
		monthDesc = "June";
	    break;
	}
	case 7: {
	    if (cdLang == 1)
		monthDesc = "Julho";
	    else
		monthDesc = "July";
	    break;
	}
	case 8: {
	    if (cdLang == 1)
		monthDesc = "Agosto";
	    else
		monthDesc = "August";
	    break;
	}
	case 9: {
	    if (cdLang == 1)
		monthDesc = "Setembro";
	    else
		monthDesc = "September";
	    break;
	}
	case 10: {
	    if (cdLang == 1)
		monthDesc = "Outubro";
	    else
		monthDesc = "October";
	    break;
	}
	case 11: {
	    if (cdLang == 1)
		monthDesc = "Novembro";
	    else
		monthDesc = "November";
	    break;
	}
	case 12: {
	    if (cdLang == 1)
		monthDesc = "Dezembro";
	    else
		monthDesc = "December";
	    break;
	}

	default:
	    monthDesc = "";
	}

	log.log("monthDesc:" + monthDesc);
	return (monthDesc);
    }

    /**
     * Retorna o n�mero do CEP no formato 99999-999
     * 
     * @return Retorna o n�mero do CEP no formato 99999-999
     * @param cep
     */
    public static String formataCEP(String cep) {
	if (cep == null) {
	    return null;
	}
	if (cep.length() == 8) {
	    cep = cep.substring(0, 5) + "-" + cep.substring(5);
	}
	return cep;
    }

    /**
     * Retorna o n�mero do CPF no formato 999.999.999-99
     * 
     * @return Retorna o n�mero do CPF no formato 999.999.999-99
     * @param CPF
     */
    public static String formatCPF(String CPF) {
	String CPF2 = "";

	CPF = addLeftChar(CPF, 11, "0");

	CPF2 =
	    CPF.substring(0, 3)
	    + "."
	    + CPF.substring(3, 6)
	    + "."
	    + CPF.substring(6, 9)
	    + "-"
	    + CPF.substring(9, 11);
	return (CPF2);
    }
    
    public static String formatCEP(String cep) {
    	
    	String cep2 = "";
    	
    	cep2 = cep.substring(0, 2)
    		   + "."
    		   + cep.substring(2, 5)
    		   + "-"
    		   + cep.substring(5, 8);
    	
    	return cep2;    	
    }

    /**
     * Retorna o n�mero do CGC no formato 99.9999.9999/9999-99
     * 
     * @return Retorna o n�mero do CGC no formato 99.9999.9999/9999-99
     * @param CGC
     */
    public static String formatCGC(String CGC) {
	log.log("CGC:/" + CGC + "/");

	String CGC2 = "";

	CGC = addLeftChar(CGC, 14, "0");

	CGC2 =
	    CGC.substring(0, 2)
	    + "."
	    + CGC.substring(2, 5)
	    + "."
	    + CGC.substring(5, 8)
	    + "/"
	    + CGC.substring(8, 12)
	    + "-"
	    + CGC.substring(12, 14);
	log.log("CGC2:/" + CGC2 + "/");
	return (CGC2);
    }

    /**
     * Retorna o n�mero do RG no formato 99.999.999-9
     * 
     * @return Retorna o n�mero do RG no formato 99.999.999-9
     * @param RG
     */
    public static String formatRG(String RG) {
	String RG2 = "";

	RG = addLeftChar(RG, 9, "0");

	RG2 =
	    RG.substring(0, 2)
	    + "."
	    + RG.substring(2, 5)
	    + "."
	    + RG.substring(5, 8)
	    + "-"
	    + RG.substring(8, 9);
	return (RG2);
    }
    
    public static String getStackTrace(Throwable throwable, int maxLength) {
	String stacktrace = getStackTrace(throwable);
	if (stacktrace.length() <= maxLength) {
	    return stacktrace;
	} else {
	    return stacktrace.substring(0, maxLength);
	}
    }

    public static String getStackTrace(Throwable throwable) {
	ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
	PrintStream printstream = new PrintStream(bytearrayoutputstream);
	throwable.printStackTrace(printstream);
	return bytearrayoutputstream.toString();
    }

    /**
     * Retorna uma string com o caracter ' ou % duplicados para que possam ser inseridos no banco de
     * dados ORACLE
     * 
     * @return Retorna uma string com o caracter ' ou % duplicados para que possam ser inseridos no
     *         banco de dados ORACLE
     * @param textVar
     *            � a string que ser� modificada
     */
    public static String addApostrophe(String textVar) {
	String varAux = "";
	log.log("Parametro Recebido", textVar);

	if (textVar == null) {
	    textVar = "";
	}

	if (!textVar.equals("") && textVar != null) {
	    for (int i = 0; i < textVar.length(); i++) {
		if (textVar.substring(i, i + 1).equals("'")) {
		    varAux = textVar.substring(i);
		    log.log("varAux", varAux);

		    textVar = textVar.substring(0, i);
		    log.log("textVar", textVar);

		    textVar = textVar + "'";
		    log.log("textVar", textVar);

		    textVar = textVar + varAux;
		    log.log("textVar", textVar);
		    i++;
		} else if (textVar.substring(i, i + 1).equals("%")) {
		    varAux = textVar.substring(i);
		    log.log("varAux", varAux);

		    textVar = textVar.substring(0, i);
		    log.log("textVar", textVar);

		    textVar = textVar + "%";
		    log.log("textVar", textVar);

		    textVar = textVar + varAux;
		    log.log("textVar", textVar);
		    i++;
		}
	    }
	} else {
	    log.log("Vou mover espa�o");
	    textVar = "";
	}

	log.log("Vou devolver", textVar);
	return textVar;
    }

    /**
     * Return atual Date from server
     */

    /*	public static Date getDateNow() throws Exception
		{
			return getDateDataBaseNow().getTime();
		}
     */
    /**
     * Return atual Date in following format: "dd/mm/yyyy"
     */
    /*	public static String getStrDateNow() throws Exception
		{
			return  dateToStr(getDateNow());
		}
     */
    /**
     * @deprecated
     * <b>Utilizar <code>UtilData.obterDataAtual()</code></b>
     * Return atual Date in following format: "dd/mm/yyyy:hh:mi:ss:mss"
     */
    public static String getStrDateTimeNow() throws Exception {
	Calendar data = getDateDataBaseNow();

	StringBuffer y = new StringBuffer(new Integer(data.get(Calendar.YEAR)).toString());
	StringBuffer m = new StringBuffer(new Integer(data.get(Calendar.MONTH) + 1).toString());
	while (m.length() < 2)
	    m.insert(0, '0');
	StringBuffer d = new StringBuffer(new Integer(data.get(Calendar.DAY_OF_MONTH)).toString());
	while (d.length() < 2)
	    d.insert(0, '0');

	StringBuffer hh = new StringBuffer(new Integer(data.get(Calendar.HOUR_OF_DAY)).toString());
	while (hh.length() < 2)
	    hh.insert(0, '0');
	StringBuffer mi = new StringBuffer(new Integer(data.get(Calendar.MINUTE)).toString());
	while (mi.length() < 2)
	    mi.insert(0, '0');
	StringBuffer ss = new StringBuffer(new Integer(data.get(Calendar.SECOND)).toString());
	while (ss.length() < 2)
	    ss.insert(0, '0');
	StringBuffer mss = new StringBuffer(new Integer(data.get(Calendar.MILLISECOND)).toString());
	while (mss.length() < 3)
	    mss.insert(0, '0');

	return d.toString()
	+ "/"
	+ m.toString()
	+ "/"
	+ y.toString()
	+ ":"
	+ hh.toString()
	+ ":"
	+ mi.toString()
	+ ":"
	+ ss.toString()
	+ ":"
	+ mss.toString();

    }

    /**
     * Return atual Date from Database in 24H format
     */
    public static Calendar getDateDataBaseNow24H() throws Exception {
	String strDate = null;
	Calendar data = Calendar.getInstance();
	long deltaTime;
	long serverTime;
	int yy;
	int mm;
	int dd;
	int hh;
	int mi;
	int ss;

	DataBaseTXInterface MyConex = new DataBaseTX("Utils");

	if (dataBaseLoginTime == 0) {
	    System.out.println("peguei da base");

	    try {

		if (!MyConex.connect()) {
		    log.log("Erro na conex�o...");
		    throw new Exception("Erro na conex�o...");
		}

		String sql = "select to_char(sysdate,'yyyymmddhh24miss') from dual";

		MyConex.prepareStatement(sql);

		if (MyConex.execute() < 0) {
		    log.log("Erro na execu��o do comando...");
		    throw new Exception("Erro na execu��o do comando...");
		}
	    } finally {
		log.log("Vou desconectar...");
		MyConex.disconnect();
	    }

	    yy = strToInt(strDate.substring(0, 4));
	    mm = strToInt(strDate.substring(4, 6));
	    dd = strToInt(strDate.substring(6, 8));
	    hh = strToInt(strDate.substring(8, 10));
	    mi = strToInt(strDate.substring(10, 12));
	    ss = strToInt(strDate.substring(12, 14));

	    data.set(yy, mm - 1, dd, hh, mi, ss);
	    dataBaseLoginTime = data.getTime().getTime();
	    serverLoginTime = new Date().getTime();

	} else {
	    serverTime = new Date().getTime();
	    deltaTime = serverTime - serverLoginTime;
	    data.setTime(new Date(dataBaseLoginTime + deltaTime));
	}

	return data;
    }

    static long dataBaseLoginTime = 0;
    static long serverLoginTime = 0;

    /**
     * Return atual Date from Database
     *  @deprecated 
     * Utilizar<code>UtilData.obterDataHoraAtual()</code></b>
     */
    public static Calendar getDateDataBaseNow() throws Exception {
	String strDate = null;
	Calendar data = Calendar.getInstance();
	long deltaTime;
	long serverTime;
	int yy;
	int mm;
	int dd;
	int hh;
	int mi;
	int ss;

	DataBaseTXInterface MyConex = new DataBaseTX("Utils");

	if (dataBaseLoginTime == 0) {
	    System.out.println("peguei da base");

	    try {
		if (!MyConex.connect()) {
		    log.log("Erro na conex�o...");
		    throw new Exception("Erro na conex�o...");
		}

		String sql = "select to_char(sysdate,'yyyymmddhh24miss') from dual";

		MyConex.prepareStatement(sql);

		if (MyConex.execute() < 0) {
		    log.log("Erro na execu��o do comando...");
		    throw new Exception("Erro na execu��o do comando...");
		}
	    } finally {
		log.log("Vou desconectar...");
		MyConex.disconnect();
	    }

	    yy = strToInt(strDate.substring(0, 4));
	    mm = strToInt(strDate.substring(4, 6));
	    dd = strToInt(strDate.substring(6, 8));
	    hh = strToInt(strDate.substring(8, 10));
	    mi = strToInt(strDate.substring(10, 12));
	    ss = strToInt(strDate.substring(12, 14));

	    data.set(yy, mm - 1, dd, hh, mi, ss);
	    dataBaseLoginTime = data.getTime().getTime();
	    serverLoginTime = new Date().getTime();

	} else {
	    serverTime = new Date().getTime();
	    deltaTime = serverTime - serverLoginTime;
	    data.setTime(new Date(dataBaseLoginTime + deltaTime));
	}

	return data;
    }

    /**
     * Copia arquivos fisicamente
     * 
     * @param inFile
     *            Caminho completo do arquivo de origem
     * @param outFile
     *            Caminho completo do arquivo de destino
     * @return true se a c�pia do arquivo for realizada com sucesso
     */
    public boolean copyFile(String inFile, String outFile) {
	InputStream is = null;
	OutputStream os = null;
	byte[] buffer;
	boolean success = true;
	try {
	    is = new FileInputStream(inFile);
	    os = new FileOutputStream(outFile);
	    buffer = new byte[is.available()];
	    is.read(buffer);
	    os.write(buffer);
	} catch (IOException e) {
	    success = false;
	} catch (OutOfMemoryError e) {
	    success = false;
	} finally {
	    try {
		if (is != null) {
		    is.close();
		}
		if (os != null) {
		    os.close();
		}
	    } catch (IOException e) {
	    }
	}
	return success;
    }

    /**
     * Formata o n�mero de telefone para (xx)xxxx-xxxx
     * 
     * @param sTel
     *            � o n�mero do telefone a ser formatado
     * @return o n�mero formatado
     */
    public static String formatTel(String sTel) {
	String sTelAux = "";

	for (int i = 0; i < sTel.length(); ++i) {
	    String c = sTel.substring(i, i + 1);
	    if (i == 0 && c.equals("0"))
		continue;
	    if (isNumber(c))
		sTelAux = sTelAux + c;
	}

	int size = sTelAux.length();

	if (size == 7) {
	    sTelAux = sTelAux.substring(0, 3) + "-" + sTelAux.substring(3, 7);
	} else if (size == 8) {
	    sTelAux = sTelAux.substring(0, 4) + "-" + sTelAux.substring(4, 8);
	} else if (size == 9) {
	    sTelAux =
		"("
		+ sTelAux.substring(0, 2)
		+ ")"
		+ sTelAux.substring(2, 5)
		+ "-"
		+ sTelAux.substring(5, 9);
	} else if (size == 10) {
	    sTelAux =
		"("
		+ sTelAux.substring(0, 2)
		+ ")"
		+ sTelAux.substring(2, 6)
		+ "-"
		+ sTelAux.substring(6, 10);
	} else {

	    return sTel;
	}

	return sTelAux;
    }

    /**
     * Transforma uma quantidade de minutos em horas
     * 
     * @param min
     *            � o n�mero de minutos
     * @return valor dos minutos em horas
     */
    public static String minutesToHours(String min) {
	int hour = strToInt(min) / 60;
	int restMin = strToInt(min) % 60;

	String retorno = hour + ":" + Utils.addLeftChar(Utils.intToStr(restMin), 2, "0");

	return retorno;
    }

    /**
     * Transforma uma quantidade de horas em minutos
     * 
     * @param horas
     *            � o n�mero de horas
     * @return valor das horas em minutos
     */
    public static String hoursToMinutes(String hor) {
	int min =
	    Utils.strToInt(hor.substring(hor.length() - 2, hor.length()))
	    + (Utils.strToInt(hor.substring(0, hor.length() - 3)) * 60);

	return Utils.intToStr(min);
    }

    /**
     * Calcula a quantidade de horas existentes entre duas horas especificadas
     * 
     * @param mIni
     *            � a hora inicial
     * @param mFim
     *            � a hora final
     * @return a quantidade de horas existentes entre duas horas especificadas
     */
    public static String calculaHoras(String mIni, String mFim) {

	int minIni = Utils.strToInt(Utils.hoursToMinutes(mIni));
	int minFim = Utils.strToInt(Utils.hoursToMinutes(mFim));

	int calculo = 0;

	if (minIni > minFim) {
	    calculo = (minFim + 1440) - minIni;
	} else {
	    calculo = minFim - minIni;
	}

	return Utils.intToStr(calculo);
    }

    /**
     * Soma uma quantidade de meses a data especificada
     * 
     * @param date
     *            � a data base para calculo
     * @param qtdMonth
     *            � a quantidade de meses que ser�o somados
     * @return a data calculada
     */
    public static String calcAfterMonth(String date, int qtdMonth) throws Exception {

	Calendar calendar = Calendar.getInstance();

	calendar.setTime(Utils.strToDate(date));
	calendar.add(Calendar.MONTH, qtdMonth);

	return Utils.dateToStr(calendar.getTime());
    }

    /**
     * Calcula o �ltimo dia do m�s
     * 
     * @param mes
     * @param ano
     * @return o dia calculado no formato dd
     */
    public static String calcUltimoDiaMes(int mes, int ano) {

	GregorianCalendar gc = new GregorianCalendar(ano, mes - 1, 1);
	return addLeftChar(String.valueOf(gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)), 2, "0");
    }

    /**
     * Calcula a �ltima data do m�s
     * 
     * @param mesAno
     *            - formato mes/ano
     * @return data do m�s no formato dd/mm/yyyy
     */
    public static String calcUltimaDataMes(String mesAno) {

	int posSeparador = mesAno.indexOf("/");
	int mes = Integer.parseInt(mesAno.substring(0, posSeparador));
	int ano = Integer.parseInt(mesAno.substring(posSeparador + 1));
	return calcUltimoDiaMes(mes, ano) + "/" + addLeftChar(String.valueOf(mes), 2, "0") + "/" + ano;
    }

    public static int calcMonthsBetween(String dateIni, String dateFim) throws Exception {

	/*Calendar inicio = Calendar.getInstance();
		inicio.setTime(Utils.strToDate(dateIni));

		Calendar fim = Calendar.getInstance();
		fim.setTime(Utils.strToDate(dateFim));

		boolean eMenor = true;
		int meses = 0;

		while (eMenor){
			inicio.add(Calendar.MONTH, 1);

			if (inicio.getTimeInMillis() < fim.getTimeInMillis()){
				meses++;
			}
			else{
		    	eMenor = false;
			}
		}

		return meses;*/

	Calendar inicio = Calendar.getInstance();
	inicio.setTime(Utils.strToDate(dateIni));
	// System.out.println("Inicio: " + inicio);

	Calendar fim = Calendar.getInstance();
	fim.setTime(Utils.strToDate(dateFim));
	// System.out.println("FIm: " + fim);

	if (fim.compareTo(inicio) < 0) {
	    return -1;
	}
	boolean eMenor = true;
	int meses = 0;

	SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");

	String inicioFormatado = sm.format(inicio.getTime());
	// System.err.println(inicioFormatado);
	String fimFormatado = sm.format(fim.getTime());

	int fimDia = fim.get(Calendar.DAY_OF_MONTH);
	int fimMes = fim.get(Calendar.MONTH);
	int fimAno = fim.get(Calendar.YEAR);

	int dia = inicio.get(Calendar.DAY_OF_MONTH);
	int mes = inicio.get(Calendar.MONTH);
	int ano = inicio.get(Calendar.YEAR);

	if (mes == fimMes && ano == fimAno) {
	    return 0;
	}

	while (eMenor) {

	    inicio.add(Calendar.MONTH, 1);
	    // String inicioAtualizadoFormatado = sm.format(inicio.getTime());

	    dia = inicio.get(Calendar.DAY_OF_MONTH);
	    mes = inicio.get(Calendar.MONTH);
	    ano = inicio.get(Calendar.YEAR);

	    if (mes == fimMes && ano == fimAno) {
		if (dia <= fimDia) {
		    meses++;
		}
		break;
	    } else {
		meses++;
	    }

	    // System.err.println(inicioAtualizadoFormatado);

	    /*if (inicioAtualizadoFormatado.equals(fimFormatado)) {
				break;
			}*/

	    /*if (inicio.getTimeInMillis() < fim.getTimeInMillis()){
				meses++;
			}
			else{
				eMenor = false;
			}*/
	}

	// System.err.println("Meses: " + meses);
	// System.err.println(meses / 12);

	return meses;
    }

    public static String[] calcAgeBetween(String dateIni, String dateFim) throws Exception {

	/*
	 * [ TT22270 ] A/C Giovana
	 * Modifica��o: Frederico Oliveira, Data: 19/03/2015
	 * Alterando a forma de buscar os anos, meses e dias para um intervalo de data.
	 * A l�gica antiga retornava numeros negativos.
	 * Ainda: calcDaysBetweenDates j� estava deprecado!
	 */
	/*
	int meses = calcMonthsBetween(dateIni, dateFim);
	int dias = (int) calcDaysBetweenDates(strToDate(calcAfterMonth(dateIni, meses)), strToDate(dateFim));
	int anos = meses / 12;
	meses = meses % 12;

	String[] retorno = { intToStr(anos), intToStr(meses), intToStr(dias) };
	*/
	
//	int dias = UtilData.calcDaysBetween(dateIni, dateFim);
//	String[] retorno = UtilData.retornaAnosMesesDias(dias);
//	
//	return retorno;
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        Date dataIni = df.parse(dateIni);
        Date dataFim = df.parse(dateFim);
	String[] retorno = UtilData.calcularIdade(dataIni, dataFim);
	return retorno;
    }
    /** TT-47678
     * @author Jlourenco - Novo metodo calcula [ Ano, Mes e Dia ]
     * @param dateIni
     * @param dateFim
     * @return Array[] = Ano , mes , dia
     * @throws Exception
     */
    public static String[] calcAgeBetweenNew(String dateIni, String dateFim) throws Exception {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        Date dataIni = df.parse(dateIni);
        Date dataFim = df.parse(dateFim);
        
        Calendar subtrairUmDia = Calendar.getInstance();
        subtrairUmDia.setTime(dataFim);
        subtrairUmDia.add(Calendar.DAY_OF_MONTH, -1);        
        
	String[] retorno = UtilData.calcularIdade(dataIni, subtrairUmDia.getTime());
	return retorno;
    }
    
    /**
     * [ TT25844 ] TASK3245 V7 TESTES
     * @param dateIni
     * @param dateFim
     * @return
     * @throws Exception
     */
    public static String calcAnosEntre(String dateIni, String dateFim) throws Exception {
	
	/*
	 * 38342 / Ticket / Problema grave: SIGEPREV
	 * Modifica��o: Frederico Oliveira, Data:  14/03/2017
	 * https://clairtonluz.github.io/blog/2014/04/2014042498.html
	 */
	/*
	int dias = UtilData.calcDaysBetween(dateIni, dateFim) + 1;
	return UtilData.retornaAnos(dias).toString();
	*/
	if ( (!dateIni.isEmpty()) && (!dateFim.isEmpty()) ) {
	    String[] dateIniArray = dateIni.split("/");
	    String[] dateFimArray = dateFim.split("/");
	    
	    if ( (dateIniArray.length == 3) && (dateFimArray.length == 3) ) {
		LocalDate antes = LocalDate.of(Integer.parseInt(dateIniArray[2]), Integer.parseInt(dateIniArray[1]), Integer.parseInt(dateIniArray[0]));
		LocalDate depois = LocalDate.of(Integer.parseInt(dateFimArray[2]), Integer.parseInt(dateFimArray[1]), Integer.parseInt(dateFimArray[0]));
		return String.valueOf(ChronoUnit.YEARS.between(antes, depois));
	    }
	}
	return "";
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Calendar transformarDataTextoEmCalendar(String dataFormatoTexto_DD_MM_YYYY) {

	if (dataFormatoTexto_DD_MM_YYYY == null || dataFormatoTexto_DD_MM_YYYY.trim().equals("")) {
	    return null;
	}

	try {
	    Date date = dateFormat.parse(dataFormatoTexto_DD_MM_YYYY);
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    return calendar;
	} catch (ParseException e) {
	    throw new RuntimeException(e);
	}
    }

    public static String[] calcCurrentAge(String dateIni) throws Exception {
	return calcAgeBetween(dateIni, getDateNow());
    }

    public static Timestamp strToTimestamp(String dt) throws Exception {
	dt = trim(dt);
	if (dt.equals("")) {
	    Timestamp timestamp = null;
	    return timestamp;
	} else {
	    if (dt.length() == 10) {
		if (isValidDateFormat(dt)) {
		    // return new Timestamp(strToDate(dt).getTime());
		    // Timestamp tm = new Timestamp(strToDate(dt).getTime());
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(Utils.strToDate(dt));
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		    return new Timestamp(calendar.getTimeInMillis());

		} else {
		    throw new Exception("Invalid date format.");
		}
	    } else if (dt.length() == 19) {
		if (isValidDateTime(dt)) {
		    // Timestamp tm = new Timestamp(strToDate(dt.substring(0, 10)).getTime());
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(Utils.strToDate(dt.substring(0, 10)));
		    calendar.set(Calendar.HOUR_OF_DAY, strToInt(dt.substring(11, 13)));
		    calendar.set(Calendar.MINUTE, strToInt(dt.substring(14, 16)));
		    calendar.set(Calendar.SECOND, strToInt(dt.substring(17, 19)));
		    calendar.set(Calendar.MILLISECOND, 0);
		    return new Timestamp(calendar.getTimeInMillis());
		} else {
		    throw new Exception("Invalid date/hour format.");
		}
	    } else if (dt.length() == 21) {
		if (isValidDateTime(dt.substring(0, 19))) {
		    // Timestamp tm = new Timestamp(strToDate(dt.substring(0, 10)).getTime());
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(Utils.strToDate(dt.substring(0, 10)));
		    calendar.set(Calendar.HOUR_OF_DAY, strToInt(dt.substring(11, 13)));
		    calendar.set(Calendar.MINUTE, strToInt(dt.substring(14, 16)));
		    calendar.set(Calendar.SECOND, strToInt(dt.substring(17, 19)));
		    calendar.set(Calendar.MILLISECOND, strToInt(dt.substring(20, 21)));
		    return new Timestamp(calendar.getTimeInMillis());
		} else {
		    throw new Exception("Invalid date/hour format.");
		}
	    } else {
		throw new Exception("Invalid date format.");
	    }
	}
    }

    public static String getDate(Timestamp dt) {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	if (dt != null) {
	    return sdf.format(dt);
	} else {
	    return "";
	}
    }

    public static String getDate(String dt) throws Exception {
	return getDate(Utils.strToTimestamp(dt));
    }

    public static String getTime(Timestamp dt) {

	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	if (dt != null) {
	    return sdf.format(dt);
	} else {
	    return "";
	}
    }

    public static String getTime(String dt) throws Exception {
	return getTime(Utils.strToTimestamp(dt));
    }

    public static String getDateTime(Timestamp dt) {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	if (dt != null) {
	    return sdf.format(dt);
	} else {
	    return "";
	}
    }

    public static String getDateTime(String dt) throws Exception {
	return getDateTime(Utils.strToTimestamp(dt));
    }

    public static BigDecimal strToBigDecimal(String value) {
	if (value == null || trim(value).equals("")) {
	    return new BigDecimal("0");
	} else {
	    double d = strToDouble(formataDecimal(value));
	    return new BigDecimal(d);
	}
    }

    /**
     * 
     * Este m�todo transforma String em BigDecimal.
     * 
     * Se difere do m�todo {@link #strToBigDecimal(value)} por passar uma String como par�metro do
     * BigDecimal ao inv�s de double, pr�tica que pode causar erro de precis�o das casas decimais.
     * 
     * @author Lucas Prestes
     * @param value
     * @return
     */
    public static BigDecimal strToBigDecimal2(String value) {
	if (value == null || trim(value).equals("")) {
	    return new BigDecimal("0");
	} else {

	    return new BigDecimal(formataDecimal(value));
	}
    }

    public static BigDecimal strToBigDecimalSemFormatacao(String value) {
	if (value == null || trim(value).equals("")) {
	    return new BigDecimal("0");
	} else {

	    return new BigDecimal(value);
	}
    }
    
    
	/**
	 * 
	 * @param data1
	 * @param data2
	 * @param includeEquals - se true, resultado retorna true se datas forem iguais
	 * @return true se data1 > data2,false se data1 <data2
	 * @throws Exception
	 */
	
	public static boolean compareDateTime(String data1,String data2,boolean includeEquals) throws Exception {
	
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(Utils.strToTimestamp(data1));
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(Utils.strToTimestamp(data2));
			
		
			
			
			
			if(includeEquals) {
				return calendar1.after(calendar2)|| calendar1.equals(calendar2);
			}else {
				return calendar1.after(calendar2);
			}
	
	
	}
    
    
    
    public static int[] daysToYearMonthDay(int dias) {

	int ano = dias / 365;
	int dia = dias - (ano * 365);
	int mes = 0;
	if (dia > 30) {
	    mes = dia / 30;
	    dia = dia - (mes * 30);
	}

	int[] result = { ano, mes, dia };

	return result;
    }

    public static int yearMonthDayToDays(int anos, int meses, int dias) {
	int result = (anos * 365);
	result = result + (meses * 30);
	result = result + dias;

	return result;
    }

    public static Collection storeLabelValueBean(
	    Collection collection,
	    String nameMethodCode,
	    String nameMethodDescription,
	    boolean addSelectAsFirstPosition)
    throws Exception {

	Iterator iterator = collection.iterator();
	Collection vReturn = new Vector();

	if (addSelectAsFirstPosition == true) {
	    vReturn.add(new LabelValueBean("Selecione", ""));
	}

	while (iterator.hasNext()) {
	    Object o = iterator.next();

	    Class c = o.getClass();
	    Method methodCode = c.getMethod(nameMethodCode, null);
	    Method methodDescription = c.getMethod(nameMethodDescription, null);

	    vReturn.add(
		    new LabelValueBean(
			    methodDescription.invoke(o, null).toString(),
			    methodCode.invoke(o, null).toString()));

	}

	return vReturn;
    }
    
    public static List<LabelValueBean> storeLabelValueBeanGenerico(Map<String, String> listaMapValores, boolean addSelecione){
	List<LabelValueBean> listaCombo = new ArrayList<>();
	
	if(addSelecione) {
	    listaCombo.add(new LabelValueBean("Selecione", ""));
	}
	
	for(Map.Entry<String, String> s : listaMapValores.entrySet()) {
	    listaCombo.add(new LabelValueBean(s.getKey(), s.getValue()));
	}
	
	return listaCombo;
    }
    
    @SuppressWarnings("rawtypes")
    public static Collection storeLabelValueBeanForString(
	    List<String> listValue,
	    boolean addSelectAsFirstPosition)
    throws Exception {

	Collection vReturn = new Vector();

	if (addSelectAsFirstPosition == true) {
	    vReturn.add(new LabelValueBean("Selecione", ""));
	}

	
	for(String value : listValue) {
	    vReturn.add(new LabelValueBean(value, value));
	}

	return vReturn;
    }
    
    /**
     * Retorna um combo onde a descri��o tamb�m possui o c�digo.
     * Formato Exemplo :   C�digo - Descri��o.
     * Autor : Renan Watanabe, Data : 09/11/2015.
     */
    public static Collection storeLabelCodeAndValueBean(
                          Collection collection,
                          String nameMethodCode,
                          String nameMethodDescription,
                          boolean addSelectAsFirstPosition,
                          String delimitador)
       throws Exception {

      Iterator iterator = collection.iterator();
      Collection vReturn = new Vector();

      if (addSelectAsFirstPosition == true) {
          vReturn.add(new LabelValueBean("Selecione", ""));
      }

      StringBuilder codigoComValorSB = null;
      String codigo = "";
      String valor = "";
      
      while (iterator.hasNext()) {
      codigoComValorSB = new StringBuilder();
      Object o = iterator.next();

      Class c = o.getClass();
      Method methodCode = c.getMethod(nameMethodCode, null);
      Method methodDescription = c.getMethod(nameMethodDescription, null);

      codigo = methodCode.invoke(o, null).toString();
      valor =  methodDescription.invoke(o, null).toString();
      codigoComValorSB.append(codigo).append(delimitador).append(valor);
      
      vReturn.add(
        new LabelValueBean(
          codigoComValorSB.toString(),
          codigo));

      }

      return vReturn;
   }
    
    /**
     * Retorna um BigDecimal formatado
     * Valor de entrada : 1.290,30
     * Retorna : BigDecimal com valor 1290.30
     */
    public static BigDecimal obterBigDecimalFormatado(String numero){
        BigDecimal numeroFormatado = null;
        if(numero == null || numero.isEmpty()){
           numeroFormatado = new BigDecimal(0);
        }else{
           numero = tiraPonto(numero);
            numeroFormatado = new BigDecimal(numero);
        }
       return numeroFormatado;
    }

    /**
     * Este m�todo transforma uma lista em um aninhado de "option" que ser� utilizado dentro de um
     * "select" nas tags HTML. Utilizar este m�todo junto com o AJAX.
     * 
     * @author zampieri
     * @since 04/10/2011
     * @param collection
     * @param valueOption
     * @param descricaoOption
     * @param addSelecioneAoOption
     * @return
     * @throws Exception
     */

    public static String createTagOptionForAJAX(
	    Collection collection,
	    String valueOption,
	    String descricaoOption,
	    boolean addSelecioneAoOption,
	    boolean encodeStringEmISO88591)
    throws Exception {
	List<?> lista = Arrays.asList(collection.toArray());
	StringBuilder sb = new StringBuilder();

	if (addSelecioneAoOption) {
	    sb.append("<option value=\"\">Selecione</option>");
	}

	for (Object o : lista) {
	    Class c = o.getClass();

	    Method methodCode = c.getMethod(valueOption, null);
	    Method methodDescription = c.getMethod(descricaoOption, null);

	    sb.append("<option value=\"");
	    sb.append(methodCode.invoke(o, null).toString());
	    sb.append("\">");
	    sb.append(methodDescription.invoke(o, null).toString());
	    sb.append("</option>");
	}

	String resultado = "";
	// Esta altera��o de codifica��o � obrigat�ria por que o AJAX s�
	// aceita a codifica��o "ISO-8859-1", a codifica��o padr�o de String
	// � "UTF-8" e na p�gina html os acentos n�o s�o interpretados
	// corretamente.Ex: Produ��o - No HTML fica assim --> Produ__o, onde
	// no lugar de "__" existem caracteres especiais
	if (encodeStringEmISO88591) {
	    byte[] bytes = sb.toString().getBytes("UTF-8");
	    resultado = new String(bytes, "ISO-8859-1");
	} else {
	    resultado = sb.toString();
	}

	return resultado;
    }

    @SuppressWarnings("unchecked")
    public static String createTagOptionForAJAX(Collection collection, String valueOption, String descricaoOption, boolean addSelecioneAoOption) throws Exception {
	return createTagOptionForAJAX(collection, valueOption, descricaoOption, addSelecioneAoOption, true);
    }

    public static String formataNumero(String num, String mask, Locale locale) {
	if (num == null || num.trim().equals("")) {
	    return "";
	}
	NumberFormat nf = NumberFormat.getInstance(locale);
	DecimalFormat decFormat = (DecimalFormat) nf;
	decFormat.applyPattern(mask);
	String aux;
	try {
	    aux = decFormat.format(Double.parseDouble(num));
	} catch (NumberFormatException n) {
	    aux = "0";
	}
	return aux;
    }

    public static String formataNumero(double num, String mask, Locale locale) {

	NumberFormat nf = NumberFormat.getInstance(locale);
	DecimalFormat decFormat = (DecimalFormat) nf;
	decFormat.applyPattern(mask);
	String aux;
	try {
	    aux = decFormat.format(num);
	} catch (NumberFormatException n) {
	    aux = "0";
	}
	return aux;
    }

    /*
     * pega uma numero no formato americano 1,000.01
     * e devolve no formato brasileiro 1.000,01
     */
    public static String formataDecimalBrasil(String num) {
	return formataNumero(num, MASK_DECIMAL, BRASIL);
    }

    public static String formataDecimal(String num) {
	num = tiraPonto(num);
	return formataNumero(num, MASK_DOUBLE, USA);
    }

    /**
     * Formatando decimal a partir de uma m�scara e de um locale.
     * 
     * @param num
     * @param mask
     * @param locale
     * @return
     */
    public static String formataDecimal(String num, String mask, Locale locale) {
	num = tiraPonto(num);
	return formataNumero(num, mask, locale);
    }

    public static String formataDecimalUSA(String num) {
	num = tiraPonto(num);
	return formataNumero(num, MASK_DECIMAL, USA);
    }

    public static String formataDecimalBrasil(double num) {
	return formataNumero(num, MASK_DECIMAL, BRASIL);
    }

    public static String formataDecimal(double num) {
	return formataNumero(num, MASK_DOUBLE, USA);
    }

    public static String formataDecimalUSA(double num) {
	return formataNumero(num, MASK_DECIMAL, USA);
    }

    public static String tiraPonto(String numero) {
	if (numero == null || numero.indexOf(',') == -1)
	    return numero;
	StringTokenizer token = new StringTokenizer(numero, ".");
	String valor = "";
	while (token.hasMoreElements()) {
	    valor += token.nextToken();
	}
	return valor.replace(',', '.');
    }
    
    /**
     * [ TASK3248 ] VTC 6 - ALTERA��ES ESTRUTURAIS FLUXO DE APOSENTADORIA E VTC.
     * @param numero
     * @return
     */
    public static String colocaVirgulaNoLugarDoPonto(String numero) {
	if (numero == null || numero.indexOf('.') == -1)
	    return numero;
	return numero.replace(".", ",");
    }
    
    public static String colocaPontoNoLugarDaVirgula(String numero) {
	if (numero == null || numero.indexOf(',') == -1)
	    return numero;
	return numero.replace(",", ".");
    }

    /**
     * Copia as propriedades equivalentes de um bean para o outro
     * */
    public static void copyProperties(Object dest, Object orig) throws Exception {

	// Class cOrig = orig.getClass();
	Method[] mOrig = orig.getClass().getDeclaredMethods();

	// Class cDest = dest.getClass();
	Method[] mDest = dest.getClass().getDeclaredMethods();

	for (int i = 0; i < mOrig.length; ++i) {
	    String methodNameOrig = mOrig[i].getName();

	    if (methodNameOrig.startsWith("get")) {
		methodNameOrig = methodNameOrig.substring(3);
		log.log("nome origem - " + methodNameOrig);

		for (int j = 0; j < mDest.length; ++j) {
		    String methodNameDest = mDest[j].getName();

		    if (methodNameDest.startsWith("set")) {
			methodNameDest = methodNameDest.substring(3);
			log.log("nome destino - " + methodNameDest);

			int indexDest = methodNameDest.indexOf(methodNameOrig);
			int indexOrig = methodNameOrig.indexOf(methodNameDest);
			boolean isOk = false;
			if (indexDest != -1) {
			    if (methodNameDest
				    .substring(indexDest)
				    .equalsIgnoreCase(methodNameOrig)) {
				isOk = true;
			    }
			} else if (indexOrig != -1) {
			    if (methodNameOrig
				    .substring(indexOrig)
				    .equalsIgnoreCase(methodNameDest)) {
				isOk = true;
			    }
			}

			// para o caso de objetos com termina��o identica. Ex.: setPerIdePer/
			// getEmpIdePer
			if (!isOk) {
			    if (methodNameOrig.length() > 3 && methodNameDest.length() > 3) {
				if (methodNameDest
					.substring(3)
					.equalsIgnoreCase(methodNameOrig.substring(3))) {
				    isOk = true;
				}
			    }
			}

			if (isOk) {
			    Object[] param = new Object[1];

			    Object objAux = mOrig[i].invoke(orig, null);

			    Class paramTypes[] = mDest[j].getParameterTypes();
			    String nameParam = paramTypes[0].getName();

			    if (nameParam.equalsIgnoreCase("java.lang.String")) {
				if (objAux instanceof Timestamp) {
				    param[0] =
					objAux == null
					? null
						: Utils.getDateTime((Timestamp) objAux);
				} else {
				    param[0] = objAux == null ? null : objAux.toString();
				}
			    } else if (nameParam.equalsIgnoreCase("java.math.BigDecimal")) {
				param[0] =
				    objAux == null ? null : new BigDecimal(objAux.toString());
			    } else if (nameParam.equalsIgnoreCase("java.sql.Timestamp")) {
				if (objAux instanceof Timestamp) {
				    param[0] = objAux;
				} else {
				    param[0] =
					objAux == null
					? null
						: Utils.strToTimestamp(objAux.toString());
				}
			    }

			    try {
				mDest[j].invoke(dest, param);
				break;
			    } catch (InvocationTargetException e) {
				if (objAux.equals("")) {
				    continue;
				} else if (objAux.toString().length() > 9) {
				    if (!Utils.isValidDate(objAux.toString().substring(0, 10))) {
					throw e;
				    }
				} else {
				    throw e;
				}
			    }
			}
		    }
		}
	    }

	}
    }

    private static String formataData(Date d, String pattern) {
	SimpleDateFormat sd = new SimpleDateFormat();
	sd.applyPattern(pattern);
	return sd.format(d);
    }

    public static String getDataHoraExtenso(String datahora) throws Exception {
    	return formataData(Utils.strToTimestamp(datahora), DATA_HORA_EXTENSO);
    }
    
    
    public static String getDataExtenso(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_EXTENSO);
    }

    public static String getDataExtensoDiaUnicoCaracterQuandoMenor10(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_EXTENSO_DIA_1_CARACTER);
    }

    public static String getDataExtensoMes(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_EXTENSO_MES);
    }

    public static String getDataExtenso(Date d) {
	return formataData(d, DATA_EXTENSO);
    }

    public static String getNomeMesByNumeroMes(int numeroMes) throws Exception {
	return (getNomeMesByNumeroMes(Integer.toString(numeroMes)));
    }

    public static String getNomeMesByNumeroMes(String numeroMes) throws Exception {
	return (getNomeMes("01/" +
		addLeftChar(numeroMes, 2, "0") +
	"/1900"));
    }

    public static String getNomeMes(String s) throws Exception {
	return (getNomeMes(Utils.strToDate(s)));
    }

    public static String getNomeMes(Date d) {
	return (nomeMesFormat.format(d));
    }

    public static String getDataExtensoSemana(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_EXTENSO_SEMANA);
    }

    public static String getDataExtensoSemana(Date d) {
	return formataData(d, DATA_EXTENSO_SEMANA);
    }

    public static String getDataAno4Digito(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_ANO_4D);
    }

    public static String getDataAno4Digito(Date d) {
	return formataData(d, DATA_ANO_4D);
    }

    public static String getDataAno2Digito(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_ANO_2D);
    }

    public static String getDataAno2Digito(Date d) {
	return formataData(d, DATA_ANO_2D);
    }

    public static String getDataMes2Digito(Date d) throws Exception {
	return formataData(d, DATA_MES_2D);
    }

    public static String getDataMes2Digito(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_MES_2D);
    }

    public static String getDataMesAno4Digito(String d) throws Exception {
	return formataData(Utils.strToDate(d), DATA_MES_ANO_4D);
    }

    public static String getDataMesAno4Digito(Date d) {
	return formataData(d, DATA_MES_ANO_4D);
    }


    /**
     * @author Lucas Prestes
     * @param d - data
     * @return data formatada no padr�o MM/yyyy
     */
    public static String getDataMes2DigitoAno4Digito(String d) throws Exception {

	return formataData(Utils.strToDate(d), DATA_MES_2D_ANO_4D);
    }

    /**
     * @author Lucas Prestes
     * @param d - data
     * @return data formatada no padr�o MM/yyyy
     */
    public static String getDataMes2DigitoAno4Digito(Date d) {
	return formataData(d, DATA_MES_2D_ANO_4D);
    }

    public static String soma(String s1, String s2) {
	return soma(s1, s2, 2);
    }

    public static String soma(String s1, String s2, int scale) {
	return strToBigDecimal(s1).add(strToBigDecimal(s2)).setScale(scale, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    public static String subtrai(String s1, String s2) {
	return subtrai(s1, s2, 2);
    }

    public static String subtrai(String s1, String s2, int scale) {
	return strToBigDecimal(s1).subtract(strToBigDecimal(s2)).setScale(scale, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    public static String multiplica(String s1, String s2) {
	return multiplica(s1, s2, 2);
    }

    public static String multiplica(String s1, String s2, int scale) {
	return strToBigDecimal(s1).multiply(strToBigDecimal(s2)).setScale(scale, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    public static String divide(String s1, String s2) {
	return divide(s1, s2, 2);
    }

    public static String divide(String s1, String s2, int scale) {
	return strToBigDecimal(s1).divide(strToBigDecimal(s2), scale, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    public static String getValorEmMilhar(String s1) {
	if (trim(s1).equals("") || (strToBigDecimal(s1).compareTo(new BigDecimal(1000)) < 0 && strToBigDecimal(s1).compareTo(new BigDecimal(-1000)) > 0))
	    return " - ";
	else
	    return formataNumero(divide(s1, "1000"), MASK_MILHAR_INTEIRO, BRASIL);
    }

    public static String getValorEmMilhar(double d1) {
	return getValorEmMilhar(doubleToStr(d1));
    }

    public static String getDescricaoBimestre(int i) {
	return i - (i / 2) + "� Bimestre";
    }

    public static Collection storeLabelValueBean(
	    Collection collection,
	    String value[],
	    String descricao[],
	    boolean addSelectAsFirstPosition,
	    boolean addTodosAsFirstPosition,
	    String separadorValores,
	    String separadorDesc) throws Exception {

	Collection novoCollection = new ArrayList();
	if (collection.size() == 0) {
	    novoCollection.add(new LabelValueBean("Sem Informa��o", ""));
	} else {
	    if (addTodosAsFirstPosition) {
		novoCollection.add(new LabelValueBean("Todos", ""));
	    } else {
		if (addSelectAsFirstPosition) {
		    novoCollection.add(new LabelValueBean("Selecione", ""));
		}
	    }
	    Iterator iterator = collection.iterator();
	    StringBuffer valTemp, desTemp;

	    while (iterator.hasNext()) {
		Object o = iterator.next();
		valTemp = new StringBuffer();
		desTemp = new StringBuffer();

		Class c = o.getClass();
		for (int i = 0; i < value.length; i++) {
		    if (i > 0) {
			valTemp.append(separadorValores);
		    }
		    Method methodCode = c.getMethod(value[i], null);
		    valTemp.append(methodCode.invoke(o, null));
		}
		for (int i = 0; i < descricao.length; i++) {
		    if (i > 0) {
			desTemp.append(separadorDesc);
		    }
		    try {
			Method methodDescription = c.getMethod(descricao[i], null);
			desTemp.append(methodDescription.invoke(o, null));
		    } catch (NoSuchMethodException nm) {
			desTemp.append(descricao[i]);
		    }

		}
		novoCollection.add(new LabelValueBean(desTemp.toString(), valTemp.toString()));
	    }
	}
	return novoCollection;
    }

    /**
     * @param origem
     * @return
     */
    public static String buscaPrimeiroDiaMes(String origem) {
	String[] a = new String[2];
	StringTokenizer st = new StringTokenizer(origem);

	String dia = "";
	String mes = "";
	String ano = "";

	while (st.hasMoreTokens()) {
	    a[0] = st.nextToken("/");

	    if (dia.equals("")) {
		dia = a[0];
	    } else {
		if (mes.equals("")) {
		    mes = a[0];
		} else {
		    ano = a[0];
		}
	    }
	}

	// String DATE_FORMAT = "yyyy-MM-dd";
	// java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
	Calendar c1 = Calendar.getInstance();
	c1.set(Integer.parseInt(ano), Integer.parseInt(mes) - 1, Integer.parseInt("01")); // 1999
	// jan
	// 20

	String DATE_FORMAT1 = "dd/MM/yyyy";
	java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat(DATE_FORMAT1);

	return sdf1.format(c1.getTime());
    }

    /**
     * @param origem
     * @return
     */
    public static String buscaPrimeiroDiaProximoMes(String origem) {

	String[] a = new String[2];
	StringTokenizer st = new StringTokenizer(origem);

	String dia = "";
	String mes = "";
	String ano = "";

	while (st.hasMoreTokens()) {
	    a[0] = st.nextToken("/");

	    if (dia.equals("")) {
		dia = a[0];
	    } else {
		if (mes.equals("")) {
		    mes = a[0];
		} else {
		    ano = a[0];
		}
	    }
	}

	// String DATE_FORMAT = "yyyy-MM-dd";
	// java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
	Calendar c1 = Calendar.getInstance();
	c1.set(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt("01")); // 1999 jan 20

	String DATE_FORMAT1 = "dd/MM/yyyy";
	java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat(DATE_FORMAT1);

	return sdf1.format(c1.getTime());
    }

    /**
     * @param origem
     * @return
     */
    public static String buscaUltimoDiaMes(String origem) {

	String[] a = new String[2];
	StringTokenizer st = new StringTokenizer(origem);

	String dia = "";
	String mes = "";
	String ano = "";

	while (st.hasMoreTokens()) {
	    a[0] = st.nextToken("/");

	    if (dia.equals("")) {
		dia = a[0];
	    } else {
		if (mes.equals("")) {
		    mes = a[0];
		} else {
		    ano = a[0];
		}
	    }
	}

	// String DATE_FORMAT = "yyyy-MM-dd";
	// java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
	Calendar c1 = Calendar.getInstance();
	c1.set(Integer.parseInt(ano), Integer.parseInt(mes), 1); // 1999 jan 20
	c1.add(Calendar.DAY_OF_MONTH, -1); // adiciona 1 mes.

	String DATE_FORMAT1 = "dd/MM/yyyy";
	java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat(DATE_FORMAT1);

	return sdf1.format(c1.getTime());
    }

    public static String getListStringToString(List<String> lista, String separador, boolean flgTipoString) {
	String strlista = "";

	if (lista != null) {

	    int contador = 0;
	    Iterator it = lista.iterator();

	    for (String chave : lista) {
		// Se n�o � a primeira vez
		if (contador > 0)
		    strlista += (separador + (flgTipoString == true ? "'" : "") + chave + (flgTipoString == true ? "'" : ""));
		else
		    strlista += ((flgTipoString == true ? "'" : "") + chave + (flgTipoString == true ? "'" : ""));

		contador++;
	    }
	}

	return strlista;
    }

    public static String inserirBarraAoFinalSeNecessario(String caminho) {

	if (caminho == null || caminho.trim().equals("")) {
	    return caminho;
	}

	String barra = caminho.substring(caminho.length() - 1, caminho.length());
	if (!barra.equals("\\") && !barra.equals("/")) {
	    caminho += "\\";
	}
	return caminho;
    }

    public static String fileRetornaString(File arquivo) throws Exception {
	return new String(fileRetornaArrayDeBytes(arquivo));
    }

    public static byte[] fileRetornaArrayDeBytes(File arquivo) {

	FileInputStream fileEmBytes = null;
	try {
	    fileEmBytes = new FileInputStream(arquivo);
	    byte[] arquivoEmBytes = new byte[fileEmBytes.available()];
	    fileEmBytes.read(arquivoEmBytes);
	    // fileEmBytes.close();
	    return arquivoEmBytes;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	} finally {
	    if (fileEmBytes != null) {
		try {
		    fileEmBytes.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}

    }

    public static void ordenar(List listaParaOrdenar, String... camposParaOrdenar) {

	try {
	    // ------------------
	    if (listaParaOrdenar.isEmpty()) {
		return;
	    }

	    // ------------------
	    final Field[] fields = new Field[camposParaOrdenar.length];
	    for (int i = 0; i < camposParaOrdenar.length; i++) {
		String campo = camposParaOrdenar[i];

		Field field = listaParaOrdenar.get(0).getClass().getDeclaredField(campo);
		field.setAccessible(true);

		fields[i] = field;
	    }

	    Comparator<Object> comparator = new Comparator<Object>() {
		@Override
		public int compare(Object o1, Object o2) {

		    for (Field field : fields) {

			int resultado = comparar(o1, o2, field);
			if (resultado != 0) {
			    return resultado;
			}
		    }

		    return 0;
		}

		private int comparar(Object o1, Object o2, Field field) {
		  try {
				Object valor1 = field.get(o1);
				Object valor2 = field.get(o2);
	
				if (valor1 == null) {
				    return 1;
				}
				if (valor1 instanceof Comparable && valor2 instanceof Comparable) {
				    Comparable c1 = (Comparable) valor1;
				    Comparable c2 = (Comparable) valor2;
				    return c1.compareTo(c2);
				} else {
				    new RuntimeException("Todos parametros devem ser Comparable");
				}
	
			    } catch (Exception e) {
				e.printStackTrace();
			    }
	
			    throw new RuntimeException();
			}
		    };
	
		    Collections.sort(listaParaOrdenar, comparator);
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
    }

    /**
     * @category metodo que realiza a formatacao do numero sem casa decimais
     * @since 25/09/2012
     * @author Adriano Silva
     * @param num
     * @return
     */
    public static String formataNumeroSemDecimal(String num) {
	if (num == null || num.trim().equals("")) {
	    return "";
	}
	NumberFormat nf = NumberFormat.getNumberInstance();
	String aux;
	try {
	    aux = nf.format(Double.parseDouble(num));
	} catch (NumberFormatException n) {
	    aux = "0";
	}
	return aux;
    }

    /**
     * @author Adriano Silva
     * @since 08/05/2013
     * @category Preenche a direita ou a esquerda de determiando texto com o valor que passar
     * @param linha_a_preencher
     * @param letra
     * @param tamanho
     * @param direcao
     *            (1 = Esquerda / 2 = Direita)
     * @return
     */
    public static String preencheCom(String linha_a_preencher, String letra, int tamanho, int direcao) {
	if (linha_a_preencher == null || linha_a_preencher.trim() == "") {
	    linha_a_preencher = "";
	}
	StringBuffer sb = new StringBuffer(linha_a_preencher);
	if (direcao == 1) {
	    for (int i = sb.length(); i < tamanho; i++) {
		sb.insert(0, letra);
	    }
	} else if (direcao == 2) {
	    for (int i = sb.length(); i < tamanho; i++) {
		sb.append(letra);
	    }
	}
	return sb.toString();
    }

    public static void limitarMaxLengthDaListaDeObjetos(List listObject) {
	for (Object object : listObject) {
	    limitarMaxLengthDoObjeto(object);
	}
    }

    public static void limitarMaxLengthDoObjeto(Object object) {

	try {

	    Field[] todosOsCamposDoObjeto = object.getClass().getDeclaredFields();
	    for (Field field : todosOsCamposDoObjeto) {

		if (field.isAnnotationPresent(MaxLength.class)) {
		    field.setAccessible(true);

		    // ------------------
		    Object valor = field.get(object);
		    if (valor == null || valor instanceof String == false) {
			continue;
		    }

		    // ------------------
		    String valorAsString = valor.toString();
		    int maxLength = field.getAnnotation(MaxLength.class).length();
		    if (valorAsString.length() <= maxLength) {
			continue;
		    }

		    // ------------------
		    String novaStringComTamanhoLimitado = valorAsString.substring(0, maxLength);
		    field.set(object, novaStringComTamanhoLimitado);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public static Object getValorDoMetodoUsandoReflection(Object object, String nomeMetodo) throws Exception {
	Method method = ((Class) object.getClass()).getMethod(nomeMetodo);
	Object valor = method.invoke(object);
	return valor;
    }

    public static boolean isPossivelRenomearArquivo(File file) {
		// ------------------
		File arquivoTemp = new File(file.getParent() + File.separator + file.getName() + ".testeUtilsRename");
	
		// ------------------
		boolean renameToTeste = file.renameTo(arquivoTemp);
		if (renameToTeste == false || arquivoTemp.exists() == false) {
		    return false;
		}
	
		// ------------------
		boolean voltarRename = arquivoTemp.renameTo(file);
		if (voltarRename == false || file.exists() == false) {
		    return false;
		}
	
		// ------------------
		return true;
    }

    /**
     * @author Niedson Araujo
     * @since 23/08/2013
     * @category Recebe uma lista com data no formato YYYYmm e concatena por per�odos. Ex.:
     *           lista("201001", "201009", "201010", "201011", "201012", 201101, "201102", "201103",
     *           "201110", "201112"); Retorno("01/2010, 09/2010 - 03/2011, 10/2011, 12/2011");
     * @param lista_periodos_YYYYmm
     * @return lista_concatenada
     * @deprecated 
     * [TASK 3049] Renan - Este m�todo n�o funciona em caso de ranges de dois, por exemplo :
     * [09/2015, 01/2015, 03/2015]  retorna 09/2015 - 01/2015, 03/2015.
     * Utilizar o m�todo UtilData.concatenarPeriodosYYMMMM.
     */

    public static String concatenaYYYYmm(ArrayList<String> listaPeriodosYYYYmm) {

	try {
	    String periodosConcatenados = "";
	    ArrayList<String> removerDuplicados = new ArrayList();

	    for (int i = 0; i < listaPeriodosYYYYmm.size() - 1; i++) {
		if (!listaPeriodosYYYYmm.get(i).equals(listaPeriodosYYYYmm.get(i + 1))) {
		    removerDuplicados.add(listaPeriodosYYYYmm.get(i));
		}
	    }
	    removerDuplicados.add(listaPeriodosYYYYmm.get(listaPeriodosYYYYmm.size() - 1));
	    listaPeriodosYYYYmm.clear();
	    listaPeriodosYYYYmm.addAll(removerDuplicados);

	    for (int i = 0; i < listaPeriodosYYYYmm.size() - 1; i++) {
		Integer ano = Integer.parseInt(listaPeriodosYYYYmm.get(i).substring(0, 4));
		Integer anoSubSequente = Integer.parseInt(listaPeriodosYYYYmm.get(i + 1).substring(0, 4));

		Integer mes = Integer.parseInt(listaPeriodosYYYYmm.get(i).substring(4, 6));
		Integer mesSubSequente = Integer.parseInt(listaPeriodosYYYYmm.get(i + 1).substring(4, 6));

		if ((mesSubSequente.equals(mes + 1)) && (ano.equals(anoSubSequente))) { // Sequencia
		    if (!periodosConcatenados.endsWith(" - ")) {
			periodosConcatenados += String.valueOf(String.format("%02d", mes)) + "/" + String.valueOf(String.format("%04d", ano)) + " - ";
		    }
		} else if (!mes.equals(12)) {
		    periodosConcatenados += String.valueOf(String.format("%02d", mes)) + "/" + String.valueOf(String.format("%04d", ano)) + ", ";
		} else if ((mes.equals(12)) && (!periodosConcatenados.endsWith(" - "))) {
		    periodosConcatenados += String.valueOf(String.format("%02d", mes)) + "/" + String.valueOf(String.format("%04d", ano)) + " - ";
		}
	    }
	    periodosConcatenados += listaPeriodosYYYYmm.get(listaPeriodosYYYYmm.size() - 1).substring(4, 6) + "/"
	    + listaPeriodosYYYYmm.get(listaPeriodosYYYYmm.size() - 1).substring(0, 4);

	    return periodosConcatenados;
	} catch (Exception e) {
	    return "Caracteres invalidos";
	}
    }

    /**
     * @author Niedson Araujo
     * @since 23/08/2013
     * @category Recebe uma lista com data no formato YYYYmm e concatena por per�odos. Ex.:
     *           lista("201001", "201009", "201010", "201011", "201012", 201101, "201102", "201103",
     *           "201110", "201112"); Retorno("01/2010, 09/2010 - 03/2011, 10/2011, 12/2011");
     * @param lista_periodos_YYYYmm
     * @return lista_concatenada
     * @deprecated 
     * [TASK 3049] Renan - Este m�todo n�o funciona em caso de ranges de dois, por exemplo :
     * [09/2015, 01/2015, 03/2015]  retorna 09/2015 - 01/2015, 03/2015.
     * Utilizar o m�todo UtilData.concatenarPeriodosYYMMMM.
     * 
     */

    public static String concatenaMmYYYYY(ArrayList<String> listaPeriodosMmYYYY) {

	try {
	    String periodosConcatenados = "";
	    ArrayList<String> removerDuplicados = new ArrayList();

	    for (int i = 0; i < listaPeriodosMmYYYY.size() - 1; i++) {
		if (!listaPeriodosMmYYYY.get(i).equals(listaPeriodosMmYYYY.get(i + 1))) {
		    removerDuplicados.add(listaPeriodosMmYYYY.get(i));
		}
	    }
	    removerDuplicados.add(listaPeriodosMmYYYY.get(listaPeriodosMmYYYY.size() - 1));
	    listaPeriodosMmYYYY.clear();
	    listaPeriodosMmYYYY.addAll(removerDuplicados);

	    for (int i = 0; i < listaPeriodosMmYYYY.size() - 1; i++) {
		Integer ano = Integer.parseInt(listaPeriodosMmYYYY.get(i).substring(3, 7));
		Integer anoSubSequente = Integer.parseInt(listaPeriodosMmYYYY.get(i + 1).substring(3, 7));

		Integer mes = Integer.parseInt(listaPeriodosMmYYYY.get(i).substring(0, 2));
		Integer mesSubSequente = Integer.parseInt(listaPeriodosMmYYYY.get(i + 1).substring(0, 2));

		if ((mesSubSequente.equals(mes + 1)) && (ano.equals(anoSubSequente))) { // Sequencia
		    if (!periodosConcatenados.endsWith(" - ")) {
			periodosConcatenados += String.valueOf(String.format("%02d", mes)) + "/" + String.valueOf(String.format("%04d", ano)) + " - ";
		    }
		} else if (!mes.equals(12)) {
		    periodosConcatenados += String.valueOf(String.format("%02d", mes)) + "/" + String.valueOf(String.format("%04d", ano)) + ", ";
		} else if ((mes.equals(12)) && (!periodosConcatenados.endsWith(" - "))) {
		    periodosConcatenados += String.valueOf(String.format("%02d", mes)) + "/" + String.valueOf(String.format("%04d", ano)) + " - ";
		}
	    }
	    periodosConcatenados += listaPeriodosMmYYYY.get(listaPeriodosMmYYYY.size() - 1).substring(0, 2) + "/"
	    + listaPeriodosMmYYYY.get(listaPeriodosMmYYYY.size() - 1).substring(3, 7);

	    return periodosConcatenados;
	} catch (Exception e) {
	    return "Caracteres invalidos";
	}
    }
    
    // [ TASK3594 ] Niedson Araujo, Data: 31/08/2015 INI
    public static boolean verificaSeCepEstaEmAreaDeRisco(String codIns, String cep) throws Exception{

	boolean cepEstaEmAreaDeRisco = false;
	
	StringBuilder query = new StringBuilder();
	
	query.append("SELECT *                              ");
	query.append("FROM TB_DETALHE_CEP_RISCO DCR         ");
	query.append("    ,TB_CONTROLE_CEP_RISCO CCR        ");
	query.append("WHERE CCR.COD_INS = ?                 ");
	query.append("  AND CCR.FLG_STATUS = 'V'            ");
	query.append("  AND CCR.NUM_CARGA = DCR.NUM_CARGA   ");
	query.append("  AND CCR.COD_INS = DCR.COD_INS       ");
	query.append("  AND DCR.NUM_CEP_INICIAL <= ?        ");
	query.append("  AND DCR.NUM_CEP_FINAL   >= ?        ");
	
	SetTypes setTypes = SetTypesBuilder.iniciarCriacaoObjetoSetTypes()
		.addBindingParameter(codIns)
		.addBindingParameter(cep)
		.addBindingParameter(cep)
		.finalizarCriacao();
	
	List<Map<String, String>> objetosEncontradosNoBancoComCEP = DAOGenerico.executeQuery(query.toString(), setTypes);
	    
	if (objetosEncontradosNoBancoComCEP.size() > 0) {
	    cepEstaEmAreaDeRisco = true;
	}

	return cepEstaEmAreaDeRisco;
    }
    // [ TASK3594 ] Niedson Araujo, Data: 31/08/2015 FIM
    /**
     * [ TASK ? ] Autor: Ederson da Silva, Data: 23/06/2016
     * Utilizado para verificar se o objeto � null
     * Quando for nulo ele retorna true
     * caso contr�rio false
     */
    public static <T> boolean isNull(T t){
	return (t == null);
    }
    /**
     * [ TASK ? ] Autor: Ederson da Silva, Data: 23/06/2016
     * Utilizado para verificar se o objeto n�o � null
     * Quando for n�o for nulo ele retorna true
     * caso contr�rio false
     */
    public static <T> boolean isNotNull(T t){
	return !isNull(t);
    }
    /** Fim Task - Ederson 23/06/2016  */
    
    
    /**
     * M�todo para receber um valor e transform�-lo em m�scara de dinheiro.
     * Exemplos:
     *  0       -> Retorna R$ 0,00
     *  0.1     -> Retorna R$ 0,10
     *  1       -> R$ 1,00
     *  100     -> R$ 100,00
     *  1000    -> R$ 1.000,00
     *  1000000 -> R$ 1.000.000,00
     * Autor: Renan Watanabe 30/12/2016.
     */
    public static String converterDoubleParaDinheiro(double valor){
	 Locale locale = new Locale("pt", "BR");      
	 NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
	 return currencyFormatter.format(valor);
    }
    
    public static String converterParaDinheiro(String valor) {
	if(valor == null || valor.isEmpty()) {
	    return "";
	}else {
	    return converterDoubleParaDinheiro(Double.valueOf(valor));
	}
    }
    
    /**
     * @author Ederson 
     * @since 30/05/2017
     * Este m�todo � utulizado para formatar e arredondar 
     * o valores para duas casas decimais
     * @param valor
     * @return double arredondado
     */
    public static double arredondarPercentual(double valor){
	BigDecimal valorBD = new BigDecimal(valor);
	valorBD = valorBD.setScale(2, BigDecimal.ROUND_HALF_UP);
	
	return valorBD.doubleValue();
    }
    
    /**
     * @author Ederson 
     * @since 30/05/2017
     * Este m�todo � usado para substituir a v�rgula por ponto de String com n�meros decimais
     * e transforma em String 
     * @param n�mero decimal com v�rgula
     * @return double
     */
    public static double converterDecimalBrasilParaDouble(String numero){
	return Double.parseDouble(numero.replace(",", ".")) ;
    }
    
    
    public static boolean isNumeroIgualZeroOuVazio(String numero) {
		
       if(numero == null || numero.trim().isEmpty()) {
	  return true;
       }
		
       if(Long.parseLong(numero.replaceAll("\\.", "").replaceAll(",", "")) == 0) {
	   return true;
       }
		
       return false;
    }    
    
    public static LocalDate parse(String data, String pattern) {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
	return LocalDate.parse(data, formatter);
    }
    
    /**
     * @author JLourenco TC-44952-JC Criado metodo particular para solucionar problema do dia primeiro
     */
    public static String formatarDataParaStringSemHora(String dt) {
	dt = trim(dt);
	if (dt.equals("")) {
	    return "";
	} else {
	    if (dt.length() == 10) {
		if (isValidDateFormat(dt)) {
		      return dt;
		} else {
		    return dt.substring(0, 10);
		}
	    }
	}    
	return null;
    }
    
    public static int compareTime(String startTimeStr, String endTimeStr) {

	Pattern p = Pattern.compile("^([0-2][0-9]):([0-5][0-9]):([0-5][0-9])$"); //Regex is used to validate time format (HH:MM:SS)

	int hhS = 0;
	int mmS = 0;
	int ssS = 0;
	
	int hhE = 0;
	int mmE = 0;
	int ssE = 0;

	Matcher m = null;

	m = p.matcher(startTimeStr);
	if (m.matches()) {
		String hhStr = m.group(1);
		String mmStr = m.group(2);
		String ssStr = m.group(3);

		hhS = Integer.parseInt(hhStr);
		mmS = Integer.parseInt(mmStr);
		ssS = Integer.parseInt(ssStr);

	}
	else {
		System.out.println("Invalid start time");
		System.exit(0);
	}

	m = p.matcher(endTimeStr);
	if (m.matches()) {
		String hhStr = m.group(1);
		String mmStr = m.group(2);
		String ssStr = m.group(3);

		hhE = Integer.parseInt(hhStr);
		mmE = Integer.parseInt(mmStr);
		ssE = Integer.parseInt(ssStr);
	}
	else {
		System.out.println("Invalid End time");		
	}

	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR_OF_DAY, hhS); // Start hour
	cal.set(Calendar.MINUTE, mmS); // Start Mintue
	cal.set(Calendar.SECOND, ssS); // Start second

	Time startTime = new Time(cal.getTime().getTime());
	// System.out.println("your time: "+sqlTime3);

	cal.set(Calendar.HOUR_OF_DAY, hhE); // End hour
	cal.set(Calendar.MINUTE, mmE); // End Mintue
	cal.set(Calendar.SECOND, ssE); // End second

	Time endTime = new Time(cal.getTime().getTime());

	if (startTime.equals(endTime)) {
		return 0; // tempos iguais
	} else if (startTime.before(endTime)) {
		return -1; //tempo inicial menor q o final
	}
	else
		return 1; //tempo incial maior q o final
    	}
    
    
    
    @SuppressWarnings("unchecked")
    public static List<LabelValueBean> storeLabelValueBeanArrayList(
	    Collection collection,
	    String nameMethodCode,
	    String nameMethodDescription,
	    boolean addSelectAsFirstPosition)
    throws Exception {

	Iterator iterator = collection.iterator();
	List<LabelValueBean> vReturn = new ArrayList<LabelValueBean>();

	if (addSelectAsFirstPosition == true) {
	    vReturn.add(new LabelValueBean("Selecione", ""));
	}

	while (iterator.hasNext()) {
	    Object o = iterator.next();

	    Class c = o.getClass();
	    Method methodCode = c.getMethod(nameMethodCode, null);
	    Method methodDescription = c.getMethod(nameMethodDescription, null);

	    vReturn.add(
		    new LabelValueBean(
			    methodDescription.invoke(o, null).toString(),
			    methodCode.invoke(o, null).toString()));

	}

	return vReturn;
    }
    
    public static boolean isEmptyTrim(String string) {
    	return string == null || string.trim().isEmpty();
    }
    
    public static Collection storeLabelValueBean(
        Collection collection,
        String nameMethodCode,
        String nameMethodDescription,
        boolean addSelectAsFirstPosition,
        boolean addAllOptionAsSecondPosition)
        throws Exception {

        Iterator iterator = collection.iterator();
        Collection vReturn = new Vector();

        if (addSelectAsFirstPosition == true) {
            vReturn.add(new LabelValueBean("Selecione", ""));
        }
        
        if (addAllOptionAsSecondPosition == true) {
            vReturn.add(new LabelValueBean("TODOS", ""));
        }

        while (iterator.hasNext()) {
            Object o = iterator.next();

            Class c = o.getClass();
            Method methodCode = c.getMethod(nameMethodCode, null);
            Method methodDescription = c.getMethod(nameMethodDescription, null);

            vReturn.add(
                new LabelValueBean(
                    methodDescription.invoke(o, null).toString(),
                    methodCode.invoke(o, null).toString()));

        }

        return vReturn;
    }
    
    public static String getExtensaoArquivo(String nomeArquivo) {
        String extensaoArquivo = "";
        int i = nomeArquivo.lastIndexOf('.');
        if (i > 0) 
            extensaoArquivo = nomeArquivo.substring(i+1);       
        
        return extensaoArquivo;
    }
}
