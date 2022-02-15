package br.com.esocial.comum.dao.daogenerico;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Felipe Regalgo
 */
public class DtoConfiguration<Dto> {
    
    private Class<Dto> classeDoDto;
    String nomeDaTabela;
    List<ColunaDaTabela> colunasQueFormamChavePrimaria = new ArrayList<ColunaDaTabela>();
    List<ColunaDaTabela> colunasQueNaoSaoChavePrimaria = new ArrayList<ColunaDaTabela>();
    
    List<ColunaDaTabela> todosFieldsDaClasse = new ArrayList<ColunaDaTabela>();

    public String insertScript;
    List<ColunaDaTabela> colunasParaSeremPreenchidasNoInsert = new ArrayList<ColunaDaTabela>();

    public String updateScript;
    List<ColunaDaTabela> colunasParaSeremPreenchidasNoUpdate = new ArrayList<ColunaDaTabela>();
    private List<ColunaDaTabela> colunasParaSeremPreenchidasNoUpdateDinamico;
    public String updateScriptComPrimaryKeyAlterada;
    List<ColunaDaTabela> colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set = new ArrayList<ColunaDaTabela>();
    List<ColunaDaTabela> colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where = new ArrayList<ColunaDaTabela>();
    
    public String deleteScript;
    List<ColunaDaTabela> colunasParaSeremPreenchidasNoDelete = new ArrayList<ColunaDaTabela>();
    
    public String selectScript;
    
    public String selectByPrimaryKeyScript;
    List<ColunaDaTabela> colunasParaSeremPreenchidasNoSelectByPrimaryKey = new ArrayList<ColunaDaTabela>();
    
    List<ColunaDaTabela> colunasComSequenceCadastradas = new ArrayList<ColunaDaTabela>();
    
    public String createTableScript;
    public String ddlPk;
    
    ResultTransformer<Dto> resultTransformer;

    public DtoConfiguration(Class<Dto> klass) {
	this(klass, null);
    }
    
    public DtoConfiguration(Class<Dto> klass, String nomeDaTabela) {
 
	this.classeDoDto = klass;
	
	popularNomeDaTabela(klass, nomeDaTabela);

	popularListasDasColunas(klass);
	
	popularCamposParaCreate();
	popularCamposParaDDL_PrimaryKey();
	popularCamposParaInsert();
	popularCamposParaUpdate();
	popularCamposParaUpdateScriptComPrimaryKeyAlterada();
	popularCamposParaSelect();
	popularCamposParaSelectByPrimaryKeyScript();
	popularCamposParaDelete();
	popularCamposComSequenceCadastradas();
	
	resultTransformer = new ResultTransformer<Dto>() {
	    public Dto createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
		Dto t = classeDoDto.newInstance();
		popularDTO(t, conn);
		return t;
	    }
	};
    }

    private void popularCamposParaDDL_PrimaryKey() {
	
	// ------------------
	String nomeDaPk = "";
	if (nomeDaTabela.toUpperCase().startsWith("TB_")) {
	    nomeDaPk = "PK_" + nomeDaTabela.substring(3);
	} else {
	    nomeDaPk = "PK_" + nomeDaTabela;
	}
	
	// ------------------
	ddlPk = "alter table "+nomeDaTabela+" add constraint "+nomeDaPk+" primary key (";

	if (colunasQueFormamChavePrimaria.size() > 0) {
	    for (int i = 0; i < colunasQueFormamChavePrimaria.size(); i++) {
		if (i > 0) {
		    ddlPk += ", ";
		}
		ddlPk += colunasQueFormamChavePrimaria.get(i).getNomeDaColuna();
	    }
	} else {
	    ddlPk += "[nao existe chave primaria definida no dto, utilize a annota\u00E7\u00E3o @Coluna(pk=true) no atributo]";
	}
	
	ddlPk += ")";
    }

    private void popularNomeDaTabela(Class<Dto> klass, String nomeDaTabelaDefault) {

	if (klass.isAnnotationPresent(Tabela.class)) {
	    Tabela tabelaAnnotation = klass.getAnnotation(Tabela.class); 

	    // Validar
	    if (tabelaAnnotation.nomeDaTabelaNoBanco() == null || tabelaAnnotation.nomeDaTabelaNoBanco().trim().length() == 0) {
		throw new RuntimeException("Campo 'nomeDaTabelaNoBanco' deve ser preenchido para a annota\u00E7\u00E3o 'Tabela' na classe '" + klass.getSimpleName() + "'");
	    }

	    this.nomeDaTabela = tabelaAnnotation.nomeDaTabelaNoBanco();

	} else {
	    if (nomeDaTabelaDefault != null && nomeDaTabelaDefault.trim().length() > 0) {
		this.nomeDaTabela = nomeDaTabelaDefault;
	    } else {
		this.nomeDaTabela = "TB_" + DAOGenerico.converterPadraoJavaParaPadraoDoBanco(klass.getSimpleName());
	    }
	}
    }
    
    private void popularCamposParaUpdateScriptComPrimaryKeyAlterada() {

	updateScript = 	"UPDATE " + nomeDaTabela + " \r\n" +
			"SET \r\n";

	List<ColunaDaTabela> todasColunas = getTodasColunas();
	updateScript += "\t" + todasColunas.get(0).getNomeDaColuna() + " = ?";
	colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set.add(todasColunas.get(0));

	for (int i = 1; i < todasColunas.size(); i++) {
	    updateScript += ",\r\n\t" + todasColunas.get(i).getNomeDaColuna() + " = ?";
	    colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set.add(todasColunas.get(i));
	}

	updateScript += "\r\nWHERE \r\n\t";
	
	if (colunasQueFormamChavePrimaria.size() > 0) {
	    updateScript += colunasQueFormamChavePrimaria.get(0).getNomeDaColuna() + " = ?";
	    colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where.add(colunasQueFormamChavePrimaria.get(0));
	    for (int i = 1; i < colunasQueFormamChavePrimaria.size(); i++) {
		updateScript += "\r\n\tAND " + colunasQueFormamChavePrimaria.get(i).getNomeDaColuna() + " = ?";
		colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where.add(colunasQueFormamChavePrimaria.get(i));
	    }
	} else {
	    updateScript += "[nao existe chave primaria definida no dto, utilize a annota\u00E7\u00E3o @Coluna(pk=true) no atributo]";
	}
    }
    /**
     * Este m�todo gera um script de update dinamico, excluindo do script os campos especificados na chamada do m�todo
     * @author Lucas Prestes
     * 
     * @param camposNaoAtualizaveis - Campos a serem excluidos do script no padr\u00E3o java(ex: codIns,codIdeCli)
     * @return script de update
     */
    public String createDynamicUpdate( String...camposNaoAtualizaveis){
    	
    	colunasParaSeremPreenchidasNoUpdateDinamico = new ArrayList<ColunaDaTabela>();
    	
    	String updateScript = "UPDATE "+ nomeDaTabela+" \r\n"+
    	"SET \r\n";
    	List<ColunaDaTabela> todasColunas = getTodasColunas();
    	List<ColunaDaTabela>colunasSelecionadas = new ArrayList<ColunaDaTabela>();
    	for(String campoNaoAtualizavel :camposNaoAtualizaveis){
    	for(ColunaDaTabela col:todasColunas){
    		if(!col.getNomeDoCampoNaClasse().equals(campoNaoAtualizavel)){
    			colunasSelecionadas.add(col);
    		}
    	}
    	}
    	updateScript += "\t" + colunasSelecionadas.get(0).getNomeDaColuna() + " = ?";
    	colunasParaSeremPreenchidasNoUpdateDinamico.add(colunasSelecionadas.get(0));

    	for (int i = 1; i < colunasSelecionadas.size(); i++) {
    	    updateScript += ",\r\n\t" + colunasSelecionadas.get(i).getNomeDaColuna() + " = ?";
    	    colunasParaSeremPreenchidasNoUpdateDinamico.add(colunasSelecionadas.get(i));
    	}

    	updateScript += "\r\nWHERE \r\n\t";
    	
    	if (colunasQueFormamChavePrimaria.size() > 0) {
    	    updateScript += colunasQueFormamChavePrimaria.get(0).getNomeDaColuna() + " = ?";
    	    colunasParaSeremPreenchidasNoUpdateDinamico.add(colunasQueFormamChavePrimaria.get(0));
    	    for (int i = 1; i < colunasQueFormamChavePrimaria.size(); i++) {
    		updateScript += "\r\n\tAND " + colunasQueFormamChavePrimaria.get(i).getNomeDaColuna() + " = ?";
    		colunasParaSeremPreenchidasNoUpdateDinamico.add(colunasQueFormamChavePrimaria.get(i));
    	    }
    	} else {
    	    updateScript += "[nao existe chave primaria definida no dto, utilize a annota\u00E7\u00E3o @Coluna(pk=true) no atributo]";
    	}
    	return updateScript;
    }
    private void popularCamposParaCreate() {
	
	createTableScript = 
	    "CREATE TABLE " + nomeDaTabela + "\r\n" +
	    "(\r\n";

	List<ColunaDaTabela> todasColunas = getTodasColunas();
	for (int i = 0; i < todasColunas.size(); i++) {
	    ColunaDaTabela colunaDaTabela = todasColunas.get(i);   
	    
	    String tipoNoBanco = colunaDaTabela.getTipo().getTipoNoBanco(colunaDaTabela.getLength());
	    
	    boolean porVirgula = i < (todasColunas.size() - 1);
	    createTableScript += "\t" + colunaDaTabela.getNomeDaColuna() + " \t\t\t" + tipoNoBanco + (porVirgula ? "," : "") +"\r\n";
	}
	
	createTableScript += ");";
    }

    private void popularCamposParaSelectByPrimaryKeyScript() {

	selectByPrimaryKeyScript = 	"SELECT * FROM " + nomeDaTabela + "\r\n" + 
					"WHERE\r\n\t";
	
	if (colunasQueFormamChavePrimaria.size() > 0) {
	    selectByPrimaryKeyScript += colunasQueFormamChavePrimaria.get(0).getNomeDaColuna() + " = ?";
	    colunasParaSeremPreenchidasNoSelectByPrimaryKey.add(colunasQueFormamChavePrimaria.get(0));
	    for (int i = 1; i < colunasQueFormamChavePrimaria.size(); i++) {
		selectByPrimaryKeyScript += "\r\n\tAND " + colunasQueFormamChavePrimaria.get(i).getNomeDaColuna() + " = ?";
		colunasParaSeremPreenchidasNoSelectByPrimaryKey.add(colunasQueFormamChavePrimaria.get(i));
	    }
	} else {
	    selectByPrimaryKeyScript += "[nao existe chave primaria definida no dto, utilize a annota\u00E7\u00E3o @Coluna(pk=true) no atributo]";
	}
    }

    public void popularDTO(Dto dto, DataBaseTXInterface conn) {
	
	for (ColunaDaTabela colunaDaTabela : getTodasColunas()) {
	    colunaDaTabela.setValorNoObjeto(dto, conn);
	}
	
    }

    private void popularListasDasColunas(Class<Dto> klass) {

	for (Field field : klass.getDeclaredFields()) {
	    
	    if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
		continue;
	    }
	    
	    if (field.isAnnotationPresent(Coluna.class)) {
		Coluna c = field.getAnnotation(Coluna.class);
		if (c.gravarCampoNaTabela() == false) {
		    continue;
		}

		ColunaDaTabela coluna;

		if (c.nomeDaColunaNoBanco().trim().length() == 0) {
		    coluna = new ColunaDaTabela(field.getName(), c.tipoDaColuna(), c.length());
		} else {
		    coluna = new ColunaDaTabela(c.nomeDaColunaNoBanco(), field.getName(), c.tipoDaColuna(), c.length());
		}
		
		coluna.setSequenceAssociada(c.sequence());

		// ---------------------
		if (c.pk()) {
		    colunasQueFormamChavePrimaria.add(coluna);
		} else {
		    colunasQueNaoSaoChavePrimaria.add(coluna);
		}

	    } else {
		colunasQueNaoSaoChavePrimaria.add(new ColunaDaTabela(field.getName(), 1));
	    }
	}
	
	if (colunasQueFormamChavePrimaria.isEmpty()) {
	    //throw new RuntimeException("Classe " + klass.getSimpleName() + " n\u00E3o possui chave primaria.");
	    System.err.println("Classe " + klass.getSimpleName() + " n\u00E3o possui chave primaria.");
	}
    }

    private void popularCamposParaSelect() {
	selectScript = "SELECT * FROM " + nomeDaTabela;
    }

    private void popularCamposParaUpdate() {

	updateScript = 	"UPDATE " + nomeDaTabela + " \r\n" +
			"SET \r\n";

	List<ColunaDaTabela> todasColunas = getTodasColunas();
	updateScript += "\t" + todasColunas.get(0).getNomeDaColuna() + " = ?";
	colunasParaSeremPreenchidasNoUpdate.add(todasColunas.get(0));

	for (int i = 1; i < todasColunas.size(); i++) {
	    updateScript += ",\r\n\t" + todasColunas.get(i).getNomeDaColuna() + " = ?";
	    colunasParaSeremPreenchidasNoUpdate.add(todasColunas.get(i));
	}

	updateScript += "\r\nWHERE \r\n\t";
	
	if (colunasQueFormamChavePrimaria.size() > 0) {
	    updateScript += colunasQueFormamChavePrimaria.get(0).getNomeDaColuna() + " = ?";
	    colunasParaSeremPreenchidasNoUpdate.add(colunasQueFormamChavePrimaria.get(0));
	    for (int i = 1; i < colunasQueFormamChavePrimaria.size(); i++) {
		updateScript += "\r\n\tAND " + colunasQueFormamChavePrimaria.get(i).getNomeDaColuna() + " = ?";
		colunasParaSeremPreenchidasNoUpdate.add(colunasQueFormamChavePrimaria.get(i));
	    }
	} else {
	    updateScript += "[nao existe chave primaria definida no dto, utilize a annota\u00E7\u00E3o @Coluna(pk=true) no atributo]";
	}
    }

    private void popularCamposParaDelete() {

	deleteScript = 	"DELETE FROM " + nomeDaTabela + "\r\n" + 
			"WHERE\r\n\t";

	if (colunasQueFormamChavePrimaria.size() > 0) {
	    deleteScript += colunasQueFormamChavePrimaria.get(0).getNomeDaColuna() + " = ?";
	    colunasParaSeremPreenchidasNoDelete.add(colunasQueFormamChavePrimaria.get(0));
	    for (int i = 1; i < colunasQueFormamChavePrimaria.size(); i++) {
		deleteScript += "\r\n\tAND " + colunasQueFormamChavePrimaria.get(i).getNomeDaColuna() + " = ?";
		colunasParaSeremPreenchidasNoDelete.add(colunasQueFormamChavePrimaria.get(i));
	    }
	} else {
	    deleteScript += "[nao existe chave primaria definida no dto, utilize a annota\u00E7\u00E3o @Coluna(pk=true) no atributo]";
	}
    }

    private void popularCamposParaInsert() {

	insertScript = 	"INSERT INTO " + nomeDaTabela + " \r\n" +
			"(\r\n";

	// ---------------------
	List<ColunaDaTabela> todasColunas = getTodasColunas();
	insertScript += "\t" + todasColunas.get(0).getNomeDaColuna();
	colunasParaSeremPreenchidasNoInsert.add(todasColunas.get(0));

	for (int i = 1; i < todasColunas.size(); i++) {
	    insertScript += ",\r\n\t" + todasColunas.get(i).getNomeDaColuna();
	    colunasParaSeremPreenchidasNoInsert.add(todasColunas.get(i));
	}

	insertScript += "\r\n) VALUES (?";
	for (int i = 1; i < todasColunas.size(); i++) {
	    insertScript += ", ?";
	}
	insertScript += ")";
    }

    public List<ColunaDaTabela> getTodasColunas() {
	
	List<ColunaDaTabela> l = new ArrayList<ColunaDaTabela>();
	l.addAll(colunasQueFormamChavePrimaria);
	l.addAll(colunasQueNaoSaoChavePrimaria);
	
	return l;
    }

    public String getInsertScript() {
        return insertScript;
    }

    public void setInsertScript(String insertScript) {
        this.insertScript = insertScript;
    }

    public String getUpdateScript() {
        return updateScript;
    }

    public void setUpdateScript(String updateScript) {
        this.updateScript = updateScript;
    }

    public String getSelectScript() {
        return selectScript;
    }

    public void setSelectScript(String selectScript) {
        this.selectScript = selectScript;
    }

    public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoInsert() {
        return colunasParaSeremPreenchidasNoInsert;
    }

    public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoUpdate() {
        return colunasParaSeremPreenchidasNoUpdate;
    }

    public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoDelete() {
        return colunasParaSeremPreenchidasNoDelete;
    }

    public String getDeleteScript() {
        return deleteScript;
    }

    public void setDeleteScript(String deleteScript) {
        this.deleteScript = deleteScript;
    }

    public String getNomeDaTabela() {
        return nomeDaTabela;
    }

    public void setNomeDaTabela(String nomeDaTabela) {
        this.nomeDaTabela = nomeDaTabela;
    }

    public ResultTransformer<Dto> getResultTransformer() {
        return resultTransformer;
    }

    public void setResultTransformer(ResultTransformer<Dto> resultTransformer) {
        this.resultTransformer = resultTransformer;
    }

    public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoSelectByPrimaryKey() {
        return colunasParaSeremPreenchidasNoSelectByPrimaryKey;
    }

    public String getSelectByPrimaryKeyScript() {
        return selectByPrimaryKeyScript;
    }

    public void setSelectByPrimaryKeyScript(String selectByPrimaryKeyScript) {
        this.selectByPrimaryKeyScript = selectByPrimaryKeyScript;
    }

    public String getCreateTableScript() {
        return createTableScript;
    }

    public void setCreateTableScript(String createTableScript) {
        this.createTableScript = createTableScript;
    }

    public String getUpdateScriptComPrimaryKeyAlterada() {
        return updateScriptComPrimaryKeyAlterada;
    }

    public void setUpdateScriptComPrimaryKeyAlterada(String updateScriptComPrimaryKeyAlterada) {
        this.updateScriptComPrimaryKeyAlterada = updateScriptComPrimaryKeyAlterada;
    }

    public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set() {
        return colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set;
    }

    public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where() {
        return colunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where;
    }

    public List<ColunaDaTabela> getColunasQueNaoSaoChavePrimaria() {
        return colunasQueNaoSaoChavePrimaria;
    }

    public String getScriptForSelectByExample(Object dto) {
	
	List<ColunaDaTabela> colunasParaWhere = getColunasQueEstaoPreenchidas(dto);
	
	if (colunasParaWhere.isEmpty()) {
	    throw new RuntimeException("Algum campo deve ser preenchido.");
	}
	
	StringBuilder sb = new StringBuilder();
	sb.append(" select * from " + nomeDaTabela + " where ");
	sb.append(colunasParaWhere.get(0).getNomeDaColuna() + " = ? ");
	
	for (int i = 1; i < colunasParaWhere.size(); i++) {
	    sb.append(" and " + colunasParaWhere.get(i).getNomeDaColuna() + " = ? ");
	}
	
	return sb.toString();
    }
    
    public List<ColunaDaTabela> getColunasForSelectByExample(Object dto) {
	return getColunasQueEstaoPreenchidas(dto);
    }

    private List<ColunaDaTabela> getColunasQueEstaoPreenchidas(Object dto) {
	
	List<ColunaDaTabela> colunasParaWhere = new ArrayList<ColunaDaTabela>();
	
	try {
	    
	    for (ColunaDaTabela colunaDaTabela : getTodasColunas()) {

		// ------------------
		Method method = dto.getClass().getMethod(colunaDaTabela.getMetodoGet());
		String value = (String) method.invoke(dto);

		// ------------------
		boolean valorFoiPreenchido = value != null && value.length() > 0;
		if (valorFoiPreenchido) {
		    colunasParaWhere.add(colunaDaTabela);
		}

	    }

	    return colunasParaWhere;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }


    private void popularCamposComSequenceCadastradas() {
	colunasComSequenceCadastradas.clear();
	
	for (ColunaDaTabela colunaDaTabela : getTodasColunas()) {
	    if (colunaDaTabela.isSequenceCadastradas()) {
		colunasComSequenceCadastradas.add(colunaDaTabela);
	    }
	}
    }
    
    public List<ColunaDaTabela> getColunasComSequenceCadastradas() {
	return colunasComSequenceCadastradas;
    }


	public List<ColunaDaTabela> getColunasParaSeremPreenchidasNoUpdateDinamico() {
		return colunasParaSeremPreenchidasNoUpdateDinamico;
	}
}

















