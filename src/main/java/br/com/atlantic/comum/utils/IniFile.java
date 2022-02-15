package br.com.atlantic.comum.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class IniFile 
{
    String iniFileName;
    Vector inilines;

    public IniFile(String s) 
	{
        inilines = null;
        iniFileName = System.getProperty("user.dir")+ File.separator +s;
		
        if(!Utils.fileExists(iniFileName)) 
		{
            sysLog("File " + iniFileName + " not found in directory " + System.getProperty("user.dir"));
            return;
        }
        
		if(inilines == null)
            inilines = new Vector();
        
		try 
		{
            if(!fileExists(iniFileName))
			{
                File file = new File(s);
                file.createNewFile();
            } 
			else 
			{
                loadIniFile();
            }
        }
        catch(Exception exception) 
		{
            System.out.println(exception.getMessage());
        }
    }

	/**
	 * Metodo que verifica se um arquivo existe
	 * @param s � o nome do arquivo que se procura
	 * @return True se o arquivo existe, false caso n�o exista
	 */
    public static boolean fileExists(String s) 
	{
        boolean flag = (new File(s)).exists();
        return flag;
    }

	/**
	 * M�todo que carrega o arquivo de inicializa��o
	 * @return False caso o arquivo n�o seja encontrado para ser carregado, 
	 * true quando o arquivo estiver sido carregado
	 */
    public boolean loadIniFile() 
	{
        if(!fileExists(iniFileName))
            return false;

        if(inilines == null)
            inilines = new Vector();
		
        int i = 0;
		
        try 
		{
            BufferedReader bufferedreader = new BufferedReader(new FileReader(iniFileName));
            //boolean flag = false;
            String s1;
            while((s1 = bufferedreader.readLine()) != null)  
			{
                String s = s1.trim();
                inilines.addElement(s);
                i++;
            }
            
			bufferedreader.close();
			
        }
        catch(IOException ioexception) 
		{
            System.out.println(ioexception.getMessage());
            return false;
        }
        
		return true;
    }

	/**
	 * M�todo que l� valores do arquivo de inicializa��o
	 * @param s � o nome da se��o do arquivo inicializa��o
	 * @param s1 � o nome da vari�vel na se��o especificadoa no aruqivo de inicializa��o
	 * @param s2 � o valor de retorno caso n�o seja encontrada a se��o ou vari�vel de se��o
	 * @return O valor de retorno � a String lida no arquivo de inicializa��o, ou o valor 
	 * default caso n�o seja encontrada a se��o ou o nome da vari�vel de se��o
	 */
    public String readString(String s, String s1, String s2) 
	{
        if(inilines == null) 
		{
            sysLog("inifile.readString: file [" + iniFileName + "] not loaded! returning default value");
            return s2;
        }
        
		try 
		{
            boolean flag = false;
            for(int i = 0; i < inilines.size(); i++) 
			{
                String s3 = (String)inilines.elementAt(i);
                s3.trim();
                if(!s3.equals("")) 
				{
                    if(s3.charAt(0) == '[')
                        if(s3.toUpperCase().equals("[" + s.toUpperCase() + "]"))
                            flag = true;
                        else
                            flag = false;
                    if(flag) 
					{
                        int j = s3.indexOf(61);
                        if(j >= 0 && s3.substring(0, j).toUpperCase().trim().equals(s1.toUpperCase()))
                            return s3.substring(j + 1).trim();
                    }
                }
            }
        }
        catch(Exception exception) 
		{
            System.out.println(exception.getMessage());
        }
        
		return s2;
    }

    /**
     * M�todo que salva as altera��es feitas no arquivo de inicializa��o
     * @return True caso o arquivo seja salvo, false caso o arquivo n�o seja encontrado
     */
	public synchronized boolean saveIniFile() 
	{
        if(!fileExists(iniFileName))
            return false;
    
		try 
		{
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(iniFileName));
            for(int i = 0; i < inilines.size(); i++) 
			{
                String s = (String)inilines.elementAt(i);
                s.trim();
                printwriter.println(s);
            }

            printwriter.close();
			
        }
        catch(IOException ioexception) 
		{
            System.out.println(ioexception.getMessage());
            return false;
        }
        
		return true;
    }

    private void sysLog(String s) 
	{
        System.out.println(s);
    }

    /**
     * M�todo que escreve no arquivo de inicializa��o
     * @param s � o nome da se��o que ser� escrita no arquivo de inicializa��o
     * @param s1 � o nome da vari�vel que ser� escrita no arquivo de inicializa��o
     * @param s2 � o valor da vari�vel que ser� escrito no arquivo de inicializa��o
     */
	public synchronized boolean writeString(String s, String s1, String s2) 
	{
        if(inilines == null) 
		{
            sysLog("inifile.writeString: file [" + iniFileName + "] not loaded, nothing to do!");
            return false;
        }
        
		try 
		{
            String s4 = s1 + "=" + s2;
            int i = 0;
            boolean flag = false;
            for(int j = 0; j <= inilines.size() - 1; j++) 
			{
                String s3 = (String)inilines.elementAt(j);
                s3.trim();
                if(!s3.equals("")) {
                    if(s3.charAt(0) == '[' && s3.toUpperCase().equals("[" + s.toUpperCase() + "]"))
                        i = j + 1;
                    if(i > 0) 
					{
                        int k = s3.indexOf(61);
                        if(k >= 0 && s3.substring(0, k).toUpperCase().trim().equals(s1.toUpperCase())) {
                            flag = true;
                            inilines.removeElementAt(j);
                            if(!s3.substring(k + 1).trim().equals(""))
                                inilines.insertElementAt(s4, j);
                            else
                                System.out.println(s1 + " is empty");
                            return saveIniFile();
                        }
                    }
                }
            }

            if(i == 0) 
			{
                inilines.addElement("");
                inilines.addElement("[" + s + "]");
                inilines.addElement(s4);
            } 
			else if(!flag)
					if(i < inilines.size())
						inilines.insertElementAt(s4, i);
					else
						inilines.addElement(s4);
        }
        catch(Exception exception) 
		{
            System.out.println(exception.getMessage());
            return false;
        }
        
		saveIniFile();
		
        return true;
		
    }
}
