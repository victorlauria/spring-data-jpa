package br.com.atlantic.comum.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Classe que gera o os arquivos de Log no caminho 
 * especificado no arquivo conf/className.par
 * 
 * @author Renato Corr�a
 * @version 2.00
 */

public class Log
{
	private static int cVersion = 0;						// vers�o do arquivo que foi lido
	private static Hashtable cLogCache = new Hashtable();	// cache rotativo de LogAttributes
	private static int cLogCacheMax = 1000;					// n�mero m�ximo de itens no cache cLogCache
	private static int cLogSwap = 0;						// n�mero de vezes que um item do log foi substituido por outro
	private static byte[] cPulaLinha = { '\r', '\n' };
	private static byte[] cTraco = { ' ', '-', ' ' };
	
	private LogAttributes attributes;						// atributos de uma inst�ncia de log
	
	/**
	 * Construtor.
	 * @param pFileName Nome do arquivo em que o log deve ser feito.
	 */
	public Log( String pFileName )
	{
		loadAttributes( pFileName );
		attributes.dateTime = new GregorianCalendar();
		attributes.dateTime.add( GregorianCalendar.SECOND, -1 );
	}

	/**
	 * Informa ao Log que o arquivo de configura��es foi modificado.
	 */
	public static void newVersion()
	{
		cVersion++;
	}
	
	public static int getCacheMax()
	{
		return cLogCacheMax;
	}
	
	public static int getCacheSize()
	{
		return cLogCache.size();
	}
	
	public static int getCacheSwap()
	{
		return cLogSwap;
	}
	
	public static int getCacheVersion()
	{
		return cVersion;
	}
	
	public static void resetCacheSwap()
	{
		cLogSwap = 0;
	}
	
	public static Hashtable getCache()
	{
		return cLogCache;
	}
	
	public static void addCacheMax( int pAdd )
	{
		int lMax = cLogCacheMax;
		lMax += pAdd;
		if( lMax <= 0 )	return;
		cLogCacheMax = lMax;
	}
	
	/**
	 * Carrega os atributos do cache ou cria um novo caso n�o encontre no cache.
	 * @param pFileName Nome do arquivo onde ser� feito o log
	 */
	private void loadAttributes( String pFileName )
	{
		LogAttributes lAttributes = (LogAttributes) cLogCache.get( pFileName );
		if( lAttributes != null )	// se encontrou objeto no cache
		{
			attributes = lAttributes;
			return;
		}
		// se n�o encontrou no cache, cria novo objeto
		lAttributes = new LogAttributes();
		if( cLogCache.size() >= cLogCacheMax )	// se o cache estiver cheio, elimina item menos utilizado
		{
			removeOldLogItem();
		}
		cLogCache.put( pFileName, lAttributes );
		attributes = lAttributes;
		attributes.fileName = pFileName;
		attributes.nowDate = 1;
		attributes.lastDate = 0;
		attributes.nowHour = "00:00:00.000".getBytes();
	}

	private synchronized void removeOldLogItem()
	{
		cLogSwap++;
		GregorianCalendar minDate = new GregorianCalendar();	// menor data
		Object minObject = null;								// objeto que contem a menor data
		Iterator values = cLogCache.values().iterator();		// iterator do cache
		while( values.hasNext() )								// pesquisa item de menor data do cache
		{
			LogAttributes lItem = (LogAttributes) values.next();
			if( minDate.after( lItem.dateTime ) )
			{
				minDate = lItem.dateTime;
				minObject = lItem;
			}
		}
		cLogCache.values().remove( minObject );					// remove item de menor data do cache
	}
	
	/**
	 * M�todo que loga no arquivo especificado
	 * @param String com o valor a ser logado
	 */
	private void logWrite( String pValor )
	{
		setNowDate();		// pega data atual
		try
		{
			if( attributes.printFileLog )	// se deve gravar log em arquivo
			{
				if( attributes.nowDate != attributes.lastDate )	// se a data mudou
				{
					createLogPath();							// criar novo arquivo e diret�rio se necess�rio.
					attributes.lastDate = attributes.nowDate;	// salvar data atual
				}
				// gravar log no arquivo
				FileOutputStream lOut = new FileOutputStream( attributes.fileFullName, true );
				lOut.write( cPulaLinha );
				lOut.write( attributes.nowHour );
				lOut.write( cTraco );
				lOut.write( pValor.getBytes() );
				lOut.flush();
				lOut.close();
			}

			if( attributes.printScreen )	// se deve enviar log para console
			{
				System.out.println( attributes.fileLog + " - " + attributes.nowDate + "-[" + pValor + "]" );
			}
		}
		catch( IOException e )
		{
			System.out.println( "Exception 2" );	  
			System.out.println( e );	  
		}
	}

	/**
	 * Carrega par�metros de log do arquivo de inicializa��o.
	 */
	private void load()
	{
		attributes.version = cVersion;				// carrega a vers�o do arquivo que foi lido.
		attributes.separator = File.separator;
		attributes.lastDate = 0;
		
		IniFile lIniClassName;            // class inifine to access className.ini
		lIniClassName = new IniFile( "conf" + attributes.separator + "className.par" );
		
		//Modifica��o: Frederico Oliveira, Data: 15/03/2011.
		//Caso n�o encontre a classe na lista de classes a serem logadas, ent�o n�o devemos logar.
		String lInLogClass = lIniClassName.readString( "LogFile", attributes.fileName, "0" );
		
		String lSystemOut = lIniClassName.readString( "LogScreen", "SystemOut", "1" );
		attributes.pathName = lIniClassName.readString( "LogPath", "path", "previdencia_log" );
		
		//Verifica se pode logar na Tela do servidor web
		if( lSystemOut.equals( "1" ) )
			attributes.printScreen = true;   
		else
			attributes.printScreen = false;
		
		// verifica se est� ligado
		if( lInLogClass.equals( "1" ) )
		{
			attributes.printFileLog = true;   
		}
		else
			attributes.printFileLog = false;
	}
	
	/**
	 * Retorna se o log est� ativado ou n�o.
	 * @return True - Est� ativado ou False - N�o est� ativado.
	 */
	public boolean isOn()
	{
		if( cVersion != attributes.version ) load();
		if( attributes.printScreen )	return true;
		if( attributes.printFileLog )	return true;
		return false;
	}

	/**
	 * M�todo que loga no arquivo especificado
	 * @param pCampo � o campo a ser logado 
	 * @param pValor � o valor a ser logado
	 */
	public void log( String pCampo, String pValor )
	{
		if( !isOn() )	return;
		String aux = pCampo + "=[" + pValor + "]";
		logWrite( aux );
	}
	
	/**
	 * M�todo que loga no arquivo especificado
	 * @param pCampo � o campo a ser logado 
	 * @param pValor � o valor a ser logado
	 */
	public void log( String pCampo, int pValor )
	{
		if( !isOn() )	return;
		String aux = pCampo + "=[" + pValor + "]";
		logWrite( aux );
	}
	
	/**
	 * M�todo que loga no arquivo especificado
	 * @param pCampo � o campo a ser logado 
	 * @param pValor � o valor a ser logado
	 */
	public void log( String pCampo, long pValor )
	{
		if( !isOn() )	return;
		String aux = pCampo + "=[" + (new Long(pValor).toString()) + "]";
		logWrite( aux );
	}
	
	/**
	 * M�todo que loga no arquivo especificado
	 * @param pCampo � o campo a ser logado 
	 * @param pValor � o valor a ser logado
	 */
	public void log( String pCampo, double pValor )
	{
		if( !isOn() )	return;
		String aux = pCampo + "=[" + (new Double(pValor).toString()) + "]";
		logWrite( aux );
	}
	
	/**
	 * M�todo que loga no arquivo especificado
	 * @param pCampo � o campo a ser logado 
	 * @param pValor � o valor a ser logado
	 */
	public void log( String pCampo, Object pValor )
	{
		if( !isOn() )	return;
		String aux = pCampo + "=[" + pValor.toString() + "]";
		logWrite( aux );
	}
	
	public void log( String pValor )
	{
		if( !isOn() )	return;
		logWrite( pValor );
	}

	/**
	 * Cria os diret�rios necess�rios para o log da data atual
	 */
	private void createLogPath()
	{
		File file = new File( attributes.pathName );
		if( !file.exists() )	file.mkdir();
		
		File fileDay = new File( attributes.pathName + attributes.separator + attributes.nowDate );
		if( !fileDay.exists() )		fileDay.mkdir();

		attributes.fileLog	= attributes.fileName + "_" + attributes.nowDate + ".log";
		attributes.fileFullName = attributes.pathName + attributes.separator + attributes.nowDate + attributes.separator + attributes.fileLog;
	}

	/**
	 * Pega a data atual e salva na classe.
	 */
	private void setNowDate()
	{
		GregorianCalendar lDate = new GregorianCalendar();
		if( lDate.equals( attributes.dateTime ) )	return;
		
		attributes.dateTime = lDate;
		
		attributes.nowDate = 	( lDate.get( GregorianCalendar.YEAR ) * 10000 ) + 
								( ( lDate.get( GregorianCalendar.MONTH ) + 1 ) * 100 ) +
								( lDate.get( GregorianCalendar.DAY_OF_MONTH ) );

		byte[] lNowHour = attributes.nowHour;
		String lHour = String.valueOf( lDate.get( GregorianCalendar.HOUR ) + 100 );
		String lMinute = String.valueOf( lDate.get( GregorianCalendar.MINUTE ) + 100 );
		String lSecond = String.valueOf( lDate.get( GregorianCalendar.SECOND ) + 100 );
		String lMili = String.valueOf( lDate.get( GregorianCalendar.MILLISECOND ) + 1000 );
		lNowHour[0] = (byte) lHour.charAt( 1 );
		lNowHour[1] = (byte) lHour.charAt( 2 );
		lNowHour[3] = (byte) lMinute.charAt( 1 );
		lNowHour[4] = (byte) lMinute.charAt( 2 );
		lNowHour[6] = (byte) lSecond.charAt( 1 );
		lNowHour[7] = (byte) lSecond.charAt( 2 );
		lNowHour[9] = (byte) lMili.charAt( 1 );
		lNowHour[10] = (byte) lMili.charAt( 2 );
		lNowHour[11] = (byte) lMili.charAt( 3 );
	}
}