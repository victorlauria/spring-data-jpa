// TAG_PREENCHIMENTO_AUTOMATICO_V1
package br.com.atlantic.comum.utils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Contexto implements Cloneable, Serializable {
	
    private String login           =   "";
    private String cpfUsuario      =   "";
    private String nomeUsuario     =   "";
    private String codPerfil       =   ""; 
    private List   perfis          = null; 

    // (para montar menu)
    private String codIns          = "";
    private String desIns          = "";
    private String codNum          = "";
    private List modulo            = null;
    private List listaDeInstitutos = null;
    private String cidade = "";
    
    private String estiloCss       = null;
    private String urlImagens      = null;
    private String tema            = null;
    private String ipRequisicao    = "";
    
    private String sistema         = "";
    private Properties properties  = null;

    File pastaParaGerenciarArquivosAssinados;

    //Vers\u00E3o do sistema obtido atraves da tabela TB_CONTROLE_VERSAO.
    private String versao          = "";

    //Recupera o nome do Servidor e a porta do weblogic corrente/em execu\u00E7\u00E3o. 
    private String nameServer = "";

    public Contexto() {
	
    }

    public String getLogin() {
    	return login;
    }

    public void setLogin(String newLogin) {
    	login = newLogin;
    }

    public String getCpfUsuario() {
    	return cpfUsuario;
    }

    public void setCpfUsuario(String newCpfUsuario) {
    	cpfUsuario = newCpfUsuario;
    }

    public String getNomeUsuario() {
    	return nomeUsuario;
    }

    public void setNomeUsuario(String newNomeUsuario) {
    	nomeUsuario = newNomeUsuario;
    }

    public String getCodPerfil() {
    	return codPerfil;
    }

    public void setCodPerfil(String newCodPerfil) {
    	codPerfil = newCodPerfil;
    }

    public List getModulo() {
    	return modulo;
    }

    public void setListaDeInstitutos(List listaDeInstitutos) {
    	this.listaDeInstitutos = listaDeInstitutos;
    }

    public List getListaDeInstitutos() {
    	return listaDeInstitutos;
    }

    public void setModulo(List newModulo) {
    	modulo = newModulo;
    }

    public String getCodIns() {
    	return codIns;
    }

    public void setCodIns(String newCodIns) {
    	codIns = newCodIns;
    }

    public String getDesIns() {
    	return desIns;
    }

    public void setDesIns(String newDesIns) {
    	desIns = newDesIns;
    }

    public String getCodNum() {
    	return codNum;
    }

    /**
     * Seta o c\u00F3digo referente ao usu\u00E1rio
     * 
     * @param newCodNum
     *            o c\u00F3digo referente ao usu\u00E1rio
     */
    public void setCodNum(String newCodNum) {
	codNum = newCodNum;
    }

    /**
     * @return Returns the cidade.
     */
    public String getCidade() {
	return cidade;
    }

    /**
     * @param cidade
     *            The cidade to set.
     */
    public void setCidade(String cidade) {
	this.cidade = cidade;
    }

    /**
     * @return the perfis
     */
    public final List getPerfis() {
	return perfis;
    }

    /**
     * @param perfis
     *            the perfis to set
     */
    public final void setPerfis(List perfis) {
	this.perfis = perfis;
    }

    List<String> listPerfisComVirgula;
    private boolean _possuiPerfil(String perfil) {
		// ------------------
		if (listPerfisComVirgula == null) {
		    listPerfisComVirgula = new ArrayList<String>(perfis.size());
		    for (Object p : perfis) {
			listPerfisComVirgula.add(","+p+",");
		    }
		}	
		// ------------------
		String perfilParaVerificarComVirgula = "," + perfil.replaceAll(" ", "") + ",";
		for (String pUsuarioComVirgula : listPerfisComVirgula) {
		    if (pUsuarioComVirgula.contains(perfilParaVerificarComVirgula)) {
			return true;
		    }
		}	
		// ------------------
		return false;
    }

    public boolean possuePerfil(String... perfilsParaVerificar) {
		// ------------------
		for (String pParaVerificar : perfilsParaVerificar) {
		    if(_possuiPerfil(pParaVerificar)) {
			return true;
		    }
		}	
		// ------------------
		return false;
    }

    public String getEstiloCss() {
    	return estiloCss;
    }

    public void setEstiloCss(String estiloCss) {
    	this.estiloCss = estiloCss;
    }

    public String getUrlImagens() {
    	return urlImagens;
    }

    public void setUrlImagens(String urlImagens) {
	this.urlImagens = urlImagens;
    }

    public final String getTema() {
	return tema;
    }

    public final void setTema(String tema) {
	this.tema = tema;
    }

    public String getIpRequisicao() {
	return ipRequisicao;
    }

    public void setIpRequisicao(String ipRequisicao) {
	this.ipRequisicao = ipRequisicao;
    }

    public String getSistema() {
	return sistema;
    }

    public void setSistema(String sistema) {
	this.sistema = sistema;
    }

    public Properties getProperties() {
	return properties;
    }

    public void setProperties(Properties properties) {
	this.properties = properties;
    }

    public File getPastaParaGerenciarArquivosAssinados() {
	return pastaParaGerenciarArquivosAssinados;
    }

    public void setPastaParaGerenciarArquivosAssinados(File pastaParaGerenciarArquivosAssinados) {
	this.pastaParaGerenciarArquivosAssinados = pastaParaGerenciarArquivosAssinados;
    }

    public String getVersao() {
	return versao;
    }

    public void setVersao(String versao) {
	this.versao = versao;
    }

    public String getNameServer() {
	return nameServer;
    }

    public void setNameServer(String nameServer) {
	this.nameServer = nameServer;
    }
    
}