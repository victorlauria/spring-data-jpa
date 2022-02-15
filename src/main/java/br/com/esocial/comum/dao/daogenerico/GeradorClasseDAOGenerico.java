package br.com.esocial.comum.dao.daogenerico;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GeradorClasseDAOGenerico {
    
    String owner = "USER_IPESP";
    static String tab = "    ";
    
    public static void main(String[] args) throws Exception {
	new GeradorClasseDAOGenerico().imprimir("TB_PESSOA_FISICA");
    }

    public void imprimir(String nomeTabela) throws Exception {

	ClassesGeradas gerarClasses = gerarClasses(nomeTabela);

	System.err.println("----------------------------------------------------");
	System.err.println(gerarClasses.daoGerado);
	System.err.println("\n----------------------------------------------------");
	System.err.println(gerarClasses.voGerado);
    }

    public ClassesGeradas gerarClasses(String nomeTabela) throws Exception {
	nomeTabela = nomeTabela.toUpperCase();

	boolean tabelaExiste = isTabelaExiste(nomeTabela);
	if (tabelaExiste == false) {
	    throw new Exception("Tabela n\u00E3o foi encontrada.");
	}

	DataBaseTX dataBase = null;
	try {
	    dataBase = new DataBaseTX();
	    if (!dataBase.connect()) {
		throw new Exception("Erro na conex\u00E3o com o banco de dados.");
	    }
	    
	    DatabaseMetaData metaData = dataBase.getConnection().getMetaData();

	    // ------------------
	    List<String> pks = new ArrayList<String>();
	    ResultSet pksRS = metaData.getPrimaryKeys(null, null, nomeTabela);
	    while (pksRS.next()) {
		if (pksRS.getString("TABLE_SCHEM").equals(owner)) {
		    pks.add(pksRS.getString("COLUMN_NAME"));
		}
	    }

	    // ------------------
	    List<CampoTabela> colunas = new ArrayList<CampoTabela>();

	    ResultSet colunasRS = metaData.getColumns(null, null, nomeTabela, null);
	    while (colunasRS.next()) {
		if (colunasRS.getString("TABLE_SCHEM").equals(owner)) {

		    CampoTabela campoTabela = new CampoTabela();
		    campoTabela.nomeCampoTabela = colunasRS.getString("COLUMN_NAME");
		    campoTabela.tipoBanco = colunasRS.getString("TYPE_NAME");
		    campoTabela.possuiCasasDecimais = colunasRS.getString("DECIMAL_DIGITS") != null && Integer.parseInt(colunasRS.getString("DECIMAL_DIGITS")) > 0;
		    campoTabela.pk = pks.contains(campoTabela.nomeCampoTabela);

		    colunas.add(campoTabela);
		}
	    }

	    colocarPkEmCima(colunas);
	    String nomeClasse = translateNameTabela(nomeTabela);

	    String vo = criarClasseVO(nomeClasse, colunas);
	    String dao = criarClasseDAO(nomeClasse);

	    ClassesGeradas classesGeradas = new ClassesGeradas();
	    classesGeradas.daoGerado = dao;
	    classesGeradas.voGerado = vo;

	    return classesGeradas;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException();
	} finally {
	    if (dataBase != null) {
		try {
		    dataBase.disconnect();
		} catch (Exception e1) {
		    throw e1;
		}
	    }
	}
    }

    private boolean isTabelaExiste(String nomeTabela) throws Exception {
	
	StringBuilder query = new StringBuilder();
	query.append(" select count(*) ");
	query.append(" from all_objects do ");
	query.append(" where do.OWNER = ? ");
	query.append("       and do.OBJECT_NAME = upper(?)");
	query.append("       and OBJECT_TYPE = 'TABLE'");
	
	SetTypes setTypes = SetTypesBuilder.iniciarCriacaoObjetoSetTypes()
		.addBindingParameter(owner)
		.addBindingParameter(nomeTabela)
		.finalizarCriacao();
	
	String resultado = DAOGenerico.executeQueryForUniqueValue(query.toString(), setTypes, ResultTransformerForUniqueAlias.RESULT_TRANSFORMER);
	return Integer.parseInt(resultado) > 0;
    }

    private String criarClasseDAO(String nomeClasse) {
	return 	"import com.novaprev.comum.dao.daogenerico.DAOGenerico;" +
		"\nimport com.novaprev.comum.Contexto;" +
		"\n" +
		"\npublic class " + nomeClasse + "DAO extends DAOGenerico<" + nomeClasse + "> {" +
		"\n" + 
		"\n    public "+nomeClasse+"DAO() {" +
		"\n    }" + 
		"\n" + 
		"\n    public "+nomeClasse+"DAO(Contexto contexto) {" + 
		"\n        super(contexto);" + 
		"\n    }" + 
		"\n" + 
		"\n}";
    }

    private String criarClasseVO(String nomeClasse, List<CampoTabela> colunas) {

	String classe = "import com.novaprev.comum.dao.daogenerico.Coluna; \n" + 
			"import com.novaprev.comum.dao.daogenerico.TipoDaColuna;" +
			"\n" +
			"\npublic class " + nomeClasse + " {" + "\n";

	for (CampoTabela campoTabela : colunas) {
	    classe += "\n" + campoTabela.toStringAsCodigoJava();
	}
	
	for (CampoTabela campoTabela : colunas) {
	    classe += "\n"+campoTabela.toStringGetSet();
	}
	classe += "\n}";

	return classe;
    }

    public static class CampoTabela {
	public String nomeCampoTabela;
	public String tipoBanco;
	public boolean pk;
	public boolean possuiCasasDecimais;

	public String toStringGetSet() {
	    
	    String nomeCampoJava = getNomeCampoJava();
	    String nomeCampoJavaPrimLetraMaiuscula = nomeCampoJava.substring(0, 1).toUpperCase() + nomeCampoJava.substring(1);
	    
	    String setGet = 
		"\n" + 
		tab+"public void set"+nomeCampoJavaPrimLetraMaiuscula+"(String "+nomeCampoJava+") { \n" + 
		tab+tab+"this."+nomeCampoJava+" = "+nomeCampoJava+"; \n" + 
		tab+"} \n" + 
		"\n" + 
		tab+"public String get"+nomeCampoJavaPrimLetraMaiuscula+"() { \n" + 
		tab+tab+"return "+nomeCampoJava+"; \n" + 
		tab+"}";
	    return setGet;
	}
	
	public String toStringAsCodigoJava() {
	    String annotacao = "";

	    if (isPossuiAnotacao()) {

		String attributos = "";
		if (pk) {
		    attributos += (attributos.equals("") ? "" : ", ") + "pk = true";
		}
		if (getTipoCampoEnumJava() != TipoDaColuna.TEXTO) {
		    attributos += (attributos.equals("") ? "" : ", ") + "tipoDaColuna = TipoDaColuna." + getTipoCampoEnumJava();
		}
		if (isNomeCampoPossuiNumero()) {
		    attributos += (attributos.equals("") ? "" : ", ") + "nomeDaColunaNoBanco = \"" + nomeCampoTabela + "\"";
		}
		annotacao = "@Coluna(" + attributos + ")";
	    }

	    return (annotacao.length() > 0 ? tab + annotacao + "\n" : "") + tab+"public String " + getNomeCampoJava() + ";";
	}

	private boolean isPossuiAnotacao() {
	    return pk || getTipoCampoEnumJava() != TipoDaColuna.TEXTO || isNomeCampoPossuiNumero();
	}

	private boolean isNomeCampoPossuiNumero() {
	    return nomeCampoTabela.matches(".*[0-9].*");
	}

	public String getNomeCampoJava() {
	    return converterNomeBancoParaJava(nomeCampoTabela);
	}

	public TipoDaColuna getTipoCampoEnumJava() {

	    if (tipoBanco.contains("NUMBER")) {
		if (possuiCasasDecimais) {
		    return TipoDaColuna.NUMERO_REAL;
		} else {
		    return TipoDaColuna.NUMERO_INTEIRO;
		}
	    } else if (tipoBanco.contains("CHAR")) {
		return TipoDaColuna.TEXTO;
	    } else if (tipoBanco.contains("DATE")) {
		if (getNomeCampoJava().equals("datIng") || getNomeCampoJava().equals("datUltAtu")) {
		    return TipoDaColuna.DATA_E_HORA;
		} else {
		    return TipoDaColuna.DATA;
		}
	    } else {
		throw new RuntimeException("Tipo n\u00E3o identificado");
	    }
	}

	public String converterNomeBancoParaJava(String coluna) {
	    String javaName = "";
	    StringTokenizer st = new StringTokenizer(coluna, "_");
	    while (st.hasMoreTokens()) {
		String parte = st.nextToken();
		javaName = javaName + parte.substring(0, 1).toUpperCase() + parte.substring(1).toLowerCase();
	    }

	    return javaName.substring(0, 1).toLowerCase() + javaName.substring(1);
	}
    }

    private String translateNameTabela(String tabela) {
	StringBuffer ret = new StringBuffer("");
	String list[];
	if (tabela.toUpperCase().startsWith("TAB_") || tabela.toUpperCase().startsWith("TB_")) {
	    list = tabela.substring(tabela.indexOf("_") + 1).split("_");
	    if (list.length > 0 && list[0].equals("ISS")) {
		list = tabela.substring(tabela.indexOf("ISS_") + 4).split("_");
	    }
	} else {
	    list = tabela.split("_");
	}

	for (int i = 0; i < list.length; ++i) {
	    ret.append(Character.toUpperCase(list[i].charAt(0)) + list[i].substring(1).toLowerCase());
	}

	return ret.toString();
    }

    private void colocarPkEmCima(List<CampoTabela> colunas) {
	List<CampoTabela> pks = new ArrayList<CampoTabela>();
	List<CampoTabela> naoPks = new ArrayList<CampoTabela>();
	for (CampoTabela campoTabela : colunas) {
	    if (campoTabela.pk) {
		pks.add(campoTabela);
	    } else {
		naoPks.add(campoTabela);
	    }
	}
	colunas.clear();
	colunas.addAll(pks);
	colunas.addAll(naoPks);
    }
}
