/**
 *
 */
package br.com.atlantic.comum.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author mfsixel
 *
 */
public final class ZipFile {

	public ZipFile () {
	}


	/**
	 * @param fileZip
	 * @param arquivos
	 * @throws IOException
	 */
	public void extrairArquivoZIP(File fileZip, File[] arquivos) throws IOException {
		ZipInputStream zipInputStream = null;
		ZipEntry zipEntry;
		byte[] readBuffer = new byte[2156];
	    int bytesIn = 0;
	    long tamArquivo;
	    int posLocalizado=-1;

	    try { //abrir o stream do arquivo zip
	    	zipInputStream = new ZipInputStream(new FileInputStream(fileZip));
	        while((zipEntry=zipInputStream.getNextEntry())!=null) {
	        	posLocalizado = -1;
	        	for (int contador=0; contador<arquivos.length; contador++) {
	        		if (zipEntry.getName().equals(arquivos[contador].getName())) posLocalizado = contador;
	        	}

	            if(posLocalizado != -1) { //foi localizado um arquivo para descompactacao
	            	FileOutputStream fos=null;
	            	try {//abrir o stream do zip e colocar os dados no stream do arquivo final
	            		if (arquivos[posLocalizado].exists()) arquivos[posLocalizado].delete();
	            		fos = new FileOutputStream(arquivos[posLocalizado]);

	            		tamArquivo = zipEntry.getSize();

			            while((bytesIn = zipInputStream.read(readBuffer)) != -1) {
			            	if (tamArquivo!=-1 && bytesIn>tamArquivo) bytesIn = (int)tamArquivo;

			            	fos.write(readBuffer, 0, bytesIn);
			            	if (tamArquivo!=-1) tamArquivo-=bytesIn;
			            }
	            	} finally {
			            if (fos!=null) try {fos.close();} catch(IOException e) {}
	            	}
	            }
	        }
	    } finally {
	    	if (zipInputStream!=null) try {zipInputStream.close();} catch(IOException e) {}
	    }
	}

	/**
	 * @param filename
	 * @throws IOException
	 */
	public static void criarArquivoZIP(String fileZip, File[] arquivos, String comentarioZip) throws IOException {
		ZipOutputStream zipOutputStream = null;
		byte[] readBuffer = new byte[2156];
	    int bytesIn = 0;

	    try { //abrir o stream do arquivo zip
			zipOutputStream = new ZipOutputStream(new FileOutputStream(fileZip));
			//zipOutputStream.setComment("Backup ISS Digital. Vers\u00E3o:" + contexto.getVersao());
			zipOutputStream.setComment(comentarioZip);
	        for(int i=0; i<arquivos.length; i++) {
	            if(!arquivos[i].isDirectory()) {
	            	FileInputStream fis=null;
	            	try {//abrir o stream do arquivo e colocar os dados no stream zip
			            fis = new FileInputStream(arquivos[i]);
			            ZipEntry anEntry = new ZipEntry(arquivos[i].getName());
			            zipOutputStream.putNextEntry(anEntry);
			            while((bytesIn = fis.read(readBuffer)) != -1) {
			            	zipOutputStream.write(readBuffer, 0, bytesIn);
			            }
	            	} finally {
			            if (fis!=null) try {fis.close();} catch(IOException e) {}
	            	}
	            }
	        }
	    } finally {
	    	if (zipOutputStream!=null) try {zipOutputStream.close();} catch(IOException e) {}
	    }
	}
	
	public static class ArquivoParaZipar {
	    public String nome;
	    public byte[] bytes;
	    public ArquivoParaZipar(String nome, byte[] bytes) {
		this.nome = nome;
		this.bytes = bytes;
	    }
	    public ArquivoParaZipar() {
	    }
	}
	
	public static byte[] criarArquivoZIP(List<ArquivoParaZipar> arquivos, String comentarioZip) throws Exception {
	    ZipOutputStream zipOutputStream = null;

	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
	    zipOutputStream.setComment(comentarioZip);

	    for (ArquivoParaZipar arquivo : arquivos) {
		zipOutputStream.putNextEntry(new ZipEntry(arquivo.nome));
		zipOutputStream.write(arquivo.bytes);
	    }
	    zipOutputStream.close();
	    return byteArrayOutputStream.toByteArray();
	}
	
	public static void main(String[] args) throws Exception {
	    List<ArquivoParaZipar> arquivos = new ArrayList<>();
	    arquivos.add(new ArquivoParaZipar("cop.txt", Utils.fileRetornaArrayDeBytes(new File("C:\\temp\\felipe - Cópia.txt"))));
	    arquivos.add(new ArquivoParaZipar("orig.txt", Utils.fileRetornaArrayDeBytes(new File("C:\\temp\\felipe.txt"))));
	    
	    byte[] arquivoZip = criarArquivoZIP(arquivos, null);
	    FileOutputStream out = new FileOutputStream(new File("c:/temp/out.zip"));
	    out.write(arquivoZip);
	    out.close();
	    
	    System.err.println("FIM");
	}


	public static byte[] criarArquivoZIP(String comentarioZip, ArquivoParaZipar... arrayArquivosParaZipar) throws Exception {
	    List<ArquivoParaZipar> list = new ArrayList<ZipFile.ArquivoParaZipar>();
	    for (ArquivoParaZipar arquivoParaZipar : arrayArquivosParaZipar) {
		list.add(arquivoParaZipar);
	    }
	    return criarArquivoZIP(list, comentarioZip);
	}

}










