package br.com.atlantic.comum.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GnuPG {
	
    	public GnuPG(){
    	    
    	}
    	private Process p;

	private String gpg_result;
	private String gpg_err;
	
	public void encrypt(String pathArquivo, String chavePublica) throws Exception {
		
		File file = new File(pathArquivo);
		if(file.exists() == false){
			throw new Exception("Arquivo n\u00E3o encontrado...");
		}
		System.out.print("Encrypting... "+pathArquivo);
		
		try {
			p = Runtime.getRuntime().exec("gpg -e -r " + chavePublica+" "+pathArquivo);
		} catch (IOException io) {
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN", p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR", p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

		try {
			out.write(pathArquivo);
			out.close();
		} catch (IOException io) {
		}

		try {
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		} catch (InterruptedException i) {
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();

		System.out.println("Done.");
	}

	public void decrypt(String pathArquivoEntrada, String pathArquivoSaida, String passPhrase) throws Exception {
		
		File f = new File(pathArquivoEntrada);
		File f2 = new File(pathArquivoSaida);
		if(f.exists() == false){
			throw new Exception("Arquivo nao encontrado...");
		}
		if(f2.exists()){
			f2.delete();
		}
		
		System.out.print("Decrypting from: " + pathArquivoEntrada+"\n");

		try {
			p = Runtime.getRuntime().exec("gpg --passphrase-fd 0 --batch --output "+pathArquivoSaida+" --decrypt "+ pathArquivoEntrada);
		} catch (IOException io) {
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN", p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR", p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

		try {
			out.write(passPhrase);
			out.close();
		} catch (IOException io) {
		}

		try {
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		} catch (InterruptedException i) {
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();

		System.out.println("Done.");
	}
	
	public String getError() {
		return gpg_err;
	}
	
	public String getResult() {
		return gpg_result;
	}
	
}

