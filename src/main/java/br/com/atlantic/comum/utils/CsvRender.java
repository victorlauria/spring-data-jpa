package br.com.atlantic.comum.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * @author Renato Corr�a
 * Renderiza uma sa�da CSV para os dados de entrada
 */
public class CsvRender
{
	private StringBuffer oOutput;
	private char oSeparator;
	private boolean oUseQuotes;
	private Hashtable oColumnNames = null;
	private boolean oHeader = false;
	private Collection oList = null;
	
	public CsvRender( char pSeparator, Collection pList ) throws Exception
	{
		this.oSeparator = pSeparator;
		oList = pList;
	}

	public void setColumnsName( Hashtable pColumnsName )
	{
		oColumnNames = pColumnsName;
	}
	
	public void setHeader( boolean pHeader )
	{
		oHeader = pHeader;
	}

	public String show() throws Exception
	{
		this.reset();
		this.transformVector( oList );

		return oOutput.toString();
	}
	
	/**
	 * Converte o vetor de DataTypeBean para o formato CSV.
	 * 
	 * @param pVetor � o vetor a ser convertido.
	 * @throws Exception
	 */
	private void transformVector( Collection pList ) throws Exception
	{
		boolean lCabec = false;
		Iterator lIterator = pList.iterator(); 

		while( lIterator.hasNext() )
		{
			Object lBean = lIterator.next();
			if( oHeader && !lCabec )
			{
				lCabec = true;
				this.transformNames( lBean );
			}
			this.transformBean( lBean );
		}
	}
	
	/**
	 * Converte todos os campos do bean especificado para o formato CSV.
	 * 
	 * @param pBean � o bean a ser convertido.
	 * @throws Exception
	 */
	private void transformBean( Object pBean ) throws Exception
	{
		Field lFields[] = pBean.getClass().getDeclaredFields();
		for( int i = 0; i < lFields.length; i++ )
		{
			Field lField = lFields[i];
			if( lField.getType().getSuperclass().isAssignableFrom( String.class ) )
			{
				String lCampo = (String) this.callGetter( pBean, lField.getName() );
				if( i == 0 ) this.oOutput.append( "\n" );
				this.oOutput.append( this.transformField( lCampo ) );
				this.oOutput.append( this.oSeparator );
			}
		}
	}

	/**
	 * Converte o campo especificado para o formato CSV.
	 * @param pCampo � o campo a ser convertido.
	 */
	private StringBuffer transformField( String pField ) throws Exception
	{
		this.oUseQuotes = false;
		
		StringBuffer lOutput = new StringBuffer();
		if( pField == null ) return lOutput;

		for( int i = 0; i < pField.length(); ++i )
		{
			this.appendCaracter( pField.charAt( i ), lOutput );
		}
		if( this.oUseQuotes )
		{
			lOutput.insert( 0, "\"" );
			lOutput.append( "\"" );
		}
		return lOutput;
	}
	
	private void reset()
	{
		this.oOutput = new StringBuffer();
	}

	/**
	 * Adiciona o caractere especificado ao buffer.
	 * 
	 * @param pCaracter
	 * @throws IOException
	 */
	private void appendCaracter( char pCaracter, StringBuffer pOutput )
	{
		if ( pCaracter == '"' )
		{
			this.oUseQuotes = true;
			// duas aspas
			pOutput.append( pCaracter + pCaracter );
			return;
		}
		if ( pCaracter == this.oSeparator )
		{
			this.oUseQuotes = true;
			pOutput.append( pCaracter );
			return;
		}
		pOutput.append( pCaracter );
	}

	private void transformNames( Object pBean ) throws Exception
	{
		Field lFields[] = pBean.getClass().getDeclaredFields();

		for( int i = 0; i < lFields.length; i++ )
		{
			String lColumnName;
			String lFieldName;
			Field lField = lFields[i];

			if( !lField.getType().getSuperclass().isAssignableFrom( String.class ) ) continue;

			lFieldName = lField.getName();
			
			lColumnName = null;
			if( oColumnNames != null ) lColumnName = (String) oColumnNames.get( lFieldName );
			if( lColumnName == null ) lColumnName = lFieldName;
			
			if( i == 0 ) this.oOutput.append( "\n" );
			this.oOutput.append( this.transformField( lColumnName ) );
			this.oOutput.append( this.oSeparator );
		}
	}
	
	/**
	 * Invoca o m�todo getter para o nome de campo especificado.
	 * 
	 * @param pBean � o bean que cont�m o m�todo getter.
	 * @param pFieldName � o nome do campo a ser obtido o valor.
	 * @return Retorna o valor do campo getter.
	 * @throws Exception
	 */
	private Object callGetter( Object pBean, String pFieldName ) throws Exception
	{
		Method lMethod = pBean.getClass().getMethod( "get" + pFieldName.substring(0, 1).toUpperCase() + pFieldName.substring(1), (Class[]) null );
		return lMethod.invoke( pBean, (Object[]) null );
	}
}
