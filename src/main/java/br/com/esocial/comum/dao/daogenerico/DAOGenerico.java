package br.com.esocial.comum.dao.daogenerico;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.atlantic.comum.utils.Contexto;
import br.com.atlantic.comum.utils.Log;
import br.com.atlantic.comum.utils.MsgExcept;
import br.com.atlantic.comum.utils.Utils;;

/**
<pre>

Esta classe tem basicamente duas func�es:
1- Servir como classe utilitaria para executar buscas
2- Servir como classe base e ser extendida por um DAO agregando a sua subclasse metodos como:
	insert, update, insertOrUpdate, delete, find, findAll, findByPrimaryKey

Caso 1: Utilizada como classe Utilit�ria

Exemplo:
	String query = "select nome, idade, sexo, rg, data_nascimento from tb_pessoa";

	Map< String, String> result = DAOGenerico.executeQueryForUniqueValue(query);
	List< Map< String, String>> listResult = DAOGenerico.executeQuery(query);

Neste caso retorna um map onde a chave s\u00E3o as colunas (ou alias) do retorno do select.
Por tanto poderia ser feito:

	String nome = result.get("nome");

caso queira retornar um objeto especifico pode ser especificado um ResultTransformer

Exemplo:
    String query =  " select pes.nome, pes.idade, pes.sexo, rg, end.rua, end.cidade " +
    		    " from tb_pessoa pes, tb_endereco end" +
    		    " where pes.nome like '%felipe%' and pes.id_endereco = end.id";

Supondo que fosse necessario popular o seguinte objeto com o retorno.
    public class PessoaEndereco {
    	String nomePessoa;
    	String idadePessoa;
    	String sexoPessoa;
    	String rua;
    	String cidade;

    	(.. metodos set e get omitidos...)
    }

essa busca poderia ser feita da seguinte forma:

	ResultTransformer< PessoaEndereco> resultTransformer = new ResultTransformer< PessoaEndereco>() {

	    public PessoaEndereco createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
		PessoaEndereco p = new PessoaEndereco();

		p.setNomePessoa(conn.getColumn("nome"));
		p.setIdadePessoa(conn.getColumn("idade"));
		p.setSexoPessoa(conn.getColumn("sx"));
		p.setRua(conn.getColumn("rua"));
		p.setCidade(conn.getColumn("cidade"));

		return p;
	    }
	};

	String query =  " select pes.nome, pes.idade, pes.sexo sx, rg, end.rua, end.cidade " +
        		" from tb_pessoa pes, tb_endereco end" +
        		" where pes.nome like '%felipe%' and pes.id_endereco = end.id";

	List< PessoaEndereco> listaDePessoas = DAOGenerico.executeQuery(query, resultTransformer);

existem tb alguns ResultTransformer personalizados, como 'ResultTransformerForUniqueAlias',
'ResultTransformerForMapResult' e 'ResultTransformerForDtoResult'

fora os metodos de busca tb existe o metodo de execu\u00E7\u00E3o como por exemplo

    String sql =	"UPDATE TB_INDICE_JUD \r\n" +
                	"SET	FLG_STATUS = " + "a" + "	\r\n" +
                	"WHERE 	TIPO_ACAO = " + "b" + " 	\r\n" +
                	"	AND COD_INS = " + "1" + " \r\n" +
                	"	AND SEQ_VIG = " + "1" + " ";
    DAOGenerico.executeSql(sql);

ou ent�o poderia ser feito assim

	String sql =	"UPDATE TB_INDICE_JUD \r\n" +
                	"SET	FLG_STATUS = ?	\r\n" +
                	"WHERE 	TIPO_ACAO = ? 	\r\n" +
                	"	AND COD_INS = ? \r\n" +
                	"	AND SEQ_VIG = ? ";

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(DataBaseTXInterface conn) throws Exception {
		int index = 1;
		conn.setType("P", "%s", index++, "a");
		conn.setType("P", "%s", index++, "b");
		conn.setType("P", "%s", index++, "1");
		conn.setType("P", "%s", index++, "1");
	    }
	};

	DAOGenerico.executeSql(sql, setTypes);

Obs: Outro metodo interessante desta classe seria DAOGenerico.getCreateScriptForClass()
que gera o script sql "create" para a classe de parametro.
____________________________________________________________________________________

Caso 2: O segundo modo de utiliza\u00E7\u00E3o seria a classe DaoGenerico como superclasse fornecendo
automaticamente metodos como insert, update e delete para sua subclasse.

supondo a seguinte tabela

    table tb_pessoa {
    	cod_ins		>> number (pk)
    	cpf		>> varchar (pk)
    	nome		>> number
    	sexo		>> number
    	rg		>> number
    	idade		>> number
    	DAT_NASC	>> date
    }

poderia ser criada a seguinte classe Dto

    public class Pessoa {

        (a)Coluna(pk = true)
        String codIns; // codIns � uma primary key

        (a)Coluna(pk = true)
        String cpf; // cpf � uma primary key

        String nome;
        String sexo;
        String rg;

        (a)Coluna(nomeDaColunaNoBanco="idade")
        String idadePessoa;

        (a)Coluna(tipoDaColuna=TipoDaColuna.DATA, nomeDaColunaNoBanco="DAT_NASC")
        String dataNascimento; // mostra q no banco este campo esta com tipo date e sofrer� convers\u00E3o

        (a)Coluna(gravarCampoNaTabela=false)
        String count; // campo ser\u00E1 desconsiderado, seria o conceito de 'transient'

        (... metodos set & gets omitidos ...)
    }
    obs: aonde esta (a)Coluna substituir (a) por 'arroba', pois Coluna � uma Anota\u00E7\u00E3o...
    (n\u00E3o foi colocado o caracter arroba neste tutorial, pois ele � um caracter especial no javadoc)

para criar o DAO poderia ser feito o seguinte

    public class PessoaDao extends DAOGenerico< Pessoa> {
    }

com isso para persistir a classe Pessoa do banco poderia ser feito o seguinte

    public static void main(String[] args) throws Exception {

	Pessoa pessoa = new Pessoa();

	pessoa.setNome("felipe");
	pessoa.setIdadePessoa("23");
	(...)
	pessoa.setDataNascimento("04/07/1985");

	// gravando pessoa
	PessoaDao dao = new PessoaDao();
	dao.insert(pessoa);

	// e para buscar todas pessoa
	List< Pessoa> todasPessoa = dao.findAll();

	// buscar pessoa com 'where'
	List< Pessoa> pessoaComWhere = dao.find("select * from tb_pessoa where nome like 'f%'");

	// fazer busca onde s\u00F3 se deseja trazer o prim resultado da lista
	Pessoa p = dao.findUnique("select * from tb_pessoa where nome like 'f%'");

	// atualizar pessoa
	p.setNome("Felipe Regalgo");
	dao.update(p);

	// deleter pessoa
	dao.delete(p);
    }

    Detalhe importante q da mesma forma q existe a anota\u00E7\u00E3o Coluna tb existe uma annota\u00E7\u00E3o para alterar o nome da tabela (a)Tabela
</pre>
 * @author Felipe Regalgo
 */
public abstract class DAOGenerico<Dto> {

    private static Map<Class, DtoConfiguration> dtoConfigurationsCache = new HashMap<Class, DtoConfiguration>();
    protected DtoConfiguration<Dto> dtoConfiguration;

    protected String codIns;
    protected Contexto contexto;

    public DAOGenerico() {
	construtor("TB_" + converterPadraoJavaParaPadraoDoBanco(getClasseDoDto().getSimpleName()));
    }

    public DAOGenerico(Contexto contexto) {
	this();
	this.contexto = contexto;
	this.codIns = contexto.getCodIns();
    }

    public DAOGenerico(Contexto contexto, String nomeDaTabela) {
	this(nomeDaTabela);
	this.contexto = contexto;
	this.codIns = contexto.getCodIns();
    }

    public DAOGenerico(String nomeDaTabela) {
	construtor(nomeDaTabela);
    }

    public DAOGenerico(String nomeDaTabela, String codIns) {
	this.codIns = codIns;
	construtor(nomeDaTabela);
    }

    public void construtor(String nomeDaTabela) {

	// iniciar dtoConfiguration
	if (dtoConfigurationsCache.get(getClasseDoDto()) != null) {
	    dtoConfiguration = dtoConfigurationsCache.get(getClasseDoDto());
	} else {
	    dtoConfiguration = new DtoConfiguration<Dto>(getClasseDoDto(), nomeDaTabela);
	    dtoConfigurationsCache.put(getClasseDoDto(), dtoConfiguration);
	}
    }

    public static ClassesGeradas gerarClassesFromNomeTabela(String nomeTabela) throws Exception {
	ClassesGeradas classesGeradas = new GeradorClasseDAOGenerico().gerarClasses(nomeTabela);
	return classesGeradas;
    }

    public static void imprimirCreateScriptForClass(Class klass) {
	System.err.println(getCreateScriptForClass(klass));
    }

    public static String getCreateScriptForClass(Class klass) {
	return getCreateScriptForClass(klass, "TB_" + converterPadraoJavaParaPadraoDoBanco(klass.getSimpleName()));
    }

    public static String getCreateScriptForClass(Class klass, String nomeDaTabela) {
	DtoConfiguration dtoConfiguration2 = new DtoConfiguration(klass, nomeDaTabela);
	return dtoConfiguration2.createTableScript +
		"\n" +
		"\n"+dtoConfiguration2.ddlPk;
    }

    public static void executeSql(String sql) throws Exception {
	executeSql(sql, new EmptySetTypes());
    }

    public static void executeSql(String sql, DataBaseTXInterface conn) throws Exception {
	executeSql(sql, null, conn);
    }

    public void update(final Dto dto) throws Exception {

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(DataBaseTXInterface conn) throws Exception {
		executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoUpdate(), conn);
	    }
	};

	executeSql(dtoConfiguration.getUpdateScript(), setTypes);
    }

    public void store(final Dto dto) throws Exception {
	store(dto, null);
    }
    public void store(final Dto dto, DataBaseTXInterface conn) throws Exception {
	update(dto, conn);
    }
    public void update(final Dto dto, DataBaseTXInterface conn) throws Exception {

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(DataBaseTXInterface conn) throws Exception {
		executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoUpdate(), conn);
	    }
	};

	executeSql(dtoConfiguration.getUpdateScript(), setTypes, conn);
    }

    public void update(final Dto dto, final Dto primaryKeyForWhereClause) throws Exception {

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(DataBaseTXInterface conn) throws Exception {

		int index = 1;

		// ---------------------
		for (ColunaDaTabela coluna : dtoConfiguration.getColunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set()) {
		    conn.setType("P", coluna.getTipo().getCodigoPara_SetType(), index++, coluna.getValorDoObjeto(dto));
		}

		// ---------------------
		for (ColunaDaTabela coluna : dtoConfiguration.getColunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where()) {
		    conn.setType("P", coluna.getTipo().getCodigoPara_SetType(), index++, coluna.getValorDoObjeto(primaryKeyForWhereClause));
		}
	    }
	};

	executeSql(dtoConfiguration.getUpdateScriptComPrimaryKeyAlterada(), setTypes);
    }
    /**
     * Este m�todo tem como proposito executar um comando update customizado de forma a n\u00E3o atualizar campos espec\u00EDficados na chamada do m�todo,passando uma conex�o como par\u00E2metro
     *
     *
     * @author Lucas Prestes
     * @param dto - classe representando a tabela a ser atualizada.
     * @param conn - conex�o do banco de dados
     * @param camposNaoAtualizaveis - lista de campos que devem ser exclu\u00EDdos do update no padr\u00E3o java.<br/>Ex: codIdeCli,codIns.
     * @throws Exception
     *
     */
    public void update(final Dto dto,DataBaseTXInterface conn,String...camposNaoAtualizaveis) throws Exception{
		String updateScript = dtoConfiguration.createDynamicUpdate(camposNaoAtualizaveis);


    	SetTypes setTypes = new SetTypes(){

			@Override
			public void setTypes(DataBaseTXInterface conn) throws Exception {
				executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoUpdateDinamico(), conn);

			}

    	};
    	executeSql(updateScript, setTypes, conn);

    }
    /**
     * Este m�todo tem como proposito executar um comando update customizado de forma a n\u00E3o atualizar campos espec\u00EDficados na chamada do m�todo
     *
     * @see #update(dto,conn,camposNaoAtualizaveis)
     * @param dto - classe representando a tabela a ser atualizada.
     * @param camposNaoAtualizaveis  - lista de campos que devem ser exclu\u00EDdos do update no padr\u00E3o java.<br/>Ex: codIdeCli,codIns.
     * @throws Exception
     */
    public void update(final Dto dto,String...camposNaoAtualizaveis) throws Exception{

    		String updateScript = dtoConfiguration.createDynamicUpdate(camposNaoAtualizaveis);
    		SetTypes setTypes = new SetTypes(){

    			@Override
    			public void setTypes(DataBaseTXInterface conn) throws Exception {
    				executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoUpdateDinamico(), conn);

    			}

        	};
        	executeSql(updateScript,setTypes);

    }


    public void update(final Dto dto, final Dto primaryKeyForWhereClause, DataBaseTXInterface conn) throws Exception {


	SetTypes setTypes = new SetTypes() {
	    public void setTypes(DataBaseTXInterface conn) throws Exception {

		int index = 1;

		// ---------------------
		for (ColunaDaTabela coluna : dtoConfiguration.getColunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Set()) {
		    conn.setType("P", coluna.getTipo().getCodigoPara_SetType(), index++, coluna.getValorDoObjeto(dto));
		}

		// ---------------------
		for (ColunaDaTabela coluna : dtoConfiguration.getColunasParaSeremPreenchidasNoUpdateScriptComPrimaryKeyAlterada_Where()) {
		    conn.setType("P", coluna.getTipo().getCodigoPara_SetType(), index++, coluna.getValorDoObjeto(primaryKeyForWhereClause));
		}
	    }
	};

	executeSql(dtoConfiguration.getUpdateScriptComPrimaryKeyAlterada(), setTypes, conn);
    }

    public void insert(final Dto dto) throws Exception {
	
		inserirSequenceParaCamposCadastrados(dto, null);
		
		SetTypes setTypes = new SetTypes() {
		    public void setTypes(final DataBaseTXInterface conn) throws Exception {
		    	executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoInsert(), conn);
		    }
		};
		
		executeSql(dtoConfiguration.getInsertScript(), setTypes);
    }

    public void insert(final List<Dto> listDto, DataBaseTXInterface conn) throws Exception {
	for (Dto dto : listDto) {
	    insert(dto, conn);
	}
    }

    public void insertSemTB(final Dto dto) throws Exception {

		inserirSequenceParaCamposCadastrados(dto, null);

		SetTypes setTypes = new SetTypes() {
			public void setTypes(final DataBaseTXInterface conn) throws Exception {
				executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoInsert(), conn);
			}
		};

		executeSql(dtoConfiguration.getInsertScript().replace("TB_", ""), setTypes);
	}

    public void create(final Dto dto) throws Exception {
	create(dto, null);
    }
    public void create(final Dto dto, DataBaseTXInterface conn) throws Exception {
	insert(dto, conn);
    }
    public void insert(final Dto dto, DataBaseTXInterface conn) throws Exception {

	inserirSequenceParaCamposCadastrados(dto, conn);

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(final DataBaseTXInterface conn) throws Exception {
		executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoInsert(), conn);
	    }
	};

	executeSql(dtoConfiguration.getInsertScript(), setTypes, conn);
    }

    private void inserirSequenceParaCamposCadastrados(Dto dto, DataBaseTXInterface conn) throws Exception {

	for (ColunaDaTabela colunaComSequence : dtoConfiguration.getColunasComSequenceCadastradas()) {

	    String valorAtualDoCampo = (String) colunaComSequence.getValorDoObjeto(dto);
	    if (valorAtualDoCampo == null || valorAtualDoCampo.trim().equals("")) {
		String nextValueForSequence = DAOGenerico.getNextValueForSequence(colunaComSequence.getSequenceAssociada(), conn);
		colunaComSequence.setValorNoObjeto(dto, nextValueForSequence);
	    }
	}
    }

    public void delete(final Dto dto, DataBaseTXInterface conn) throws Exception {

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(final DataBaseTXInterface conn) throws Exception {
		executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoDelete(), conn);
	    }
	};

	executeSql(dtoConfiguration.getDeleteScript(), setTypes, conn);
    }

    public void delete(final Dto dto) throws Exception {

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(final DataBaseTXInterface conn) throws Exception {
		executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoDelete(), conn);
	    }
	};
	executeSql(dtoConfiguration.getDeleteScript(), setTypes);
    }

    public void deleteSemTB(final Dto dto) throws Exception {

		SetTypes setTypes = new SetTypes() {
			public void setTypes(final DataBaseTXInterface conn) throws Exception {
				executeSetType(dto, dtoConfiguration.getColunasParaSeremPreenchidasNoDelete(), conn);
			}
		};

		executeSql(dtoConfiguration.getDeleteScript().replace("TB_", ""), setTypes);
	}
    public List<Dto> findAll() throws Exception {
	return find(dtoConfiguration.getSelectScript());
    }

    public List<Dto> findAll(DataBaseTXInterface conn) throws Exception {
	return find(dtoConfiguration.getSelectScript(), conn);
    }

    public List<Dto>findAllSemTB() throws Exception{
		return find(dtoConfiguration.getSelectScript().replace("TB_", ""));
	}

    public Dto findUniqueValue(String query) throws Exception {
	return findUniqueValue(query, new EmptySetTypes());
    }

    public Dto findUniqueValue(String query, SetTypes setTypes) throws Exception {
	return findUniqueValue(query, setTypes, null);
    }

    public List<Dto> find(String query, SetTypes setTypes) throws Exception {
	return find(query, setTypes, getResultTransformer());
    }

    public Dto findUniqueValue(String query, DataBaseTXInterface conn) throws Exception {
	return findUniqueValue(query, new EmptySetTypes(), conn);
    }

    public Dto findUniqueValue(String query, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	List<Dto> list = find(query, setTypes, getResultTransformer(), conn);
	return list.isEmpty() ? null : list.get(0);
    }

    public Dto findUnique(String query, DataBaseTXInterface conn) throws Exception {
	return findUniqueValue(query, new EmptySetTypes(), conn);
    }

    public Dto findUnique(String query) throws Exception {
	return findUniqueValue(query, new EmptySetTypes(), null);
    }

    public List<Dto> find(Dto dto, String[] camposParaFiltrar) throws Exception {
	return find(dto, camposParaFiltrar, null);
    }
    public List<Dto> find(Dto dto, String[] camposParaFiltrar, DataBaseTXInterface conn) throws Exception {
	Class<Dto> klass = getClasseDoDto();

	Dto example = klass.newInstance();
	for (String campo : camposParaFiltrar) {
	    Field field = klass.getDeclaredField(campo);
	    field.setAccessible(true);

	    String valorCampo = (String) field.get(dto);
	    field.set(example, valorCampo);
	}

	return findByExample(example, conn);
    }

    public static <T> List<T> findByExample(T dto) {
	return (List<T>) findByExampleReturnObjectList(dto, null, null);
    }

    public static <T> List<T> findByExample(T dto, ResultTransformer<T> rt) {
	return (List<T>) findByExampleReturnObjectList(dto, rt, null);
    }

    public static <T> List<T> findByExample(T dto, DataBaseTXInterface conn) {
	return (List<T>) findByExampleReturnObjectList(dto, null, conn);
    }

    public static <T> T findByExampleForUniqueValue(T dto) {
	return findByExampleForUniqueValue(dto, null, null);
    }

    public static <T> T findByExampleForUniqueValue(T dto, DataBaseTXInterface conn) {
	List<T> list = (List<T>) findByExampleReturnObjectList(dto, null, conn);
	return list.isEmpty() ? null : list.get(0);
    }

    public static <T> List<T> findByExample(T dto, ResultTransformer<T> rt, DataBaseTXInterface conn) {
	return (List<T>) findByExampleReturnObjectList(dto, rt, conn);
    }

    public static <T> T findByExampleForUniqueValue(T dto, ResultTransformer<T> rt){
	return (T) findByExampleForUniqueValue(dto, rt, null);
    }

    public static <T> T findByExampleForUniqueValue(T dto, ResultTransformer<T> rt, DataBaseTXInterface conn) {
	List<T> list = (List<T>) findByExampleReturnObjectList(dto, rt, conn);
	return list.isEmpty() ? null : list.get(0);
    }

    private static List<Object> findByExampleReturnObjectList(final Object dto, ResultTransformer rt, DataBaseTXInterface conn) {

	try {

	    // ------------------
	    Class<? extends Object> klassDto = dto.getClass();

	    // ------------------
	    final DtoConfiguration dc;
	    if (dtoConfigurationsCache.get(klassDto) != null) {
		dc = dtoConfigurationsCache.get(klassDto);
	    } else {
		dc = new DtoConfiguration(klassDto);
		dtoConfigurationsCache.put(klassDto, dc);
	    }

	    // ------------------
	    final List<ColunaDaTabela> colunasForSelectByExample = dc.getColunasForSelectByExample(dto);
	    if (colunasForSelectByExample.isEmpty()) {
		return new ArrayList<>();
	    }

	    // ------------------
	    SetTypes setTypes = new SetTypes() {
		public void setTypes(final DataBaseTXInterface conn) throws Exception {
		    int index = 1;
		    for (ColunaDaTabela colunaDaTabela : colunasForSelectByExample) {
			conn.setType("P", colunaDaTabela.getTipo().getCodigoPara_SetType(), index++, colunaDaTabela.getValorDoObjeto(dto));
		    }
		}
	    };

	    // ------------------
	    String query = dc.getScriptForSelectByExample(dto);

	    // ------------------
	    if (rt == null) {
		rt = new ResultTransformerForDtoResult(klassDto, true);
	    }

	    // ------------------
	    if (conn == null) {
		return DAOGenerico.executeQuery(query, setTypes, rt);
	    } else {
		return DAOGenerico.executeQuery(query, setTypes, rt, conn);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public static void main(String[] args) {
	    /*
		IndiceJudicial dto = new IndiceJudicial();
		dto.setAnoMesInicio("anon");
		dto.setCodIns("1");
		dto.setDatUltAtu("dsdw");
		DAOGenerico.findByExample(dto);
	
		PessoaFisica pf = new PessoaFisica();
		pf.setCodIns("1");
		pf.setNumCpf("3395");
		DAOGenerico.findByExample(pf);
		*/
    }

    public static <T> List<T> executeQuery(StringBuilder query, SetTypes setTypes, ResultTransformer<T> resultTransformer) throws Exception {
	return executeQuery(query.toString(), setTypes, resultTransformer);
    }

    public static <T> List<T> executeQuery(String query, SetTypes setTypes, ResultTransformer<T> resultTransformer) throws Exception {

	DataBaseTXInterface conn = null;
	Log log = new Log(getClasseChamadora().getSimpleName());

	try {
	    conn = new DataBaseTX(DAOGenerico.class.getSimpleName());
	    if (!conn.connect()) {
		log.log("Erro na conex\u00E3o...");
		throw new MsgExcept(1000);
	    }

	    return executeQuery(query, setTypes, resultTransformer, conn);

	} catch (Exception e) {
	    if (conn != null) {
		conn.rollback();
	    }
	    throw e;
	}
	finally {
	    if (conn != null) {
		conn.disconnect();
		log.log("Desconectei...");
	    }
	}
    }

    private static Class getClasseChamadora() {

	// ---------------------
	StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	for (int i = 1; i < stackTrace.length; i++) {
	    StackTraceElement s = stackTrace[i];

	    // ---------------------
	    boolean naoEhChamadaDestaClasse = s.getClassName().equals(DAOGenerico.class.getName()) == false;
	    if (naoEhChamadaDestaClasse) {
		try {
		    return Class.forName(s.getClassName());
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
	    }
	}

	// ---------------------
	return DAOGenerico.class;
    }

    public static <T> List<T> executeQuery(String query, SetTypes setTypes, ResultTransformer<T> resultTransformer, DataBaseTXInterface conn) throws Exception {

	if (conn == null) {
	    return executeQuery(query, setTypes, resultTransformer);
	}

	Log log = new Log(getClasseChamadora().getSimpleName());

	conn.prepareStatement(query);

	if (setTypes != null) {
	    setTypes.setTypes(conn);
	}

	if (conn.execute() < 0) {
	    log.log("Erro na execu\u00E7\u00E3o do SELECT...");
	    throw new MsgExcept(1002);
	}

	String[] aliases = getAliases(conn);

	List<T> v = new ArrayList<T>();
	while (conn.next()) {
	    v.add(resultTransformer.createObject(conn, aliases));
	}

	return v;
    }

    public static String[] getAliases(DataBaseTXInterface conn) {

	try {

	    // ---------------------
	    Field field = conn.getClass().getDeclaredField("resultSet");
	    field.setAccessible(true);
	    ResultSet resultSet = (ResultSet) field.get(conn);
	    ResultSetMetaData metaData = resultSet.getMetaData();

	    // ---------------------
	    String[] alias = new String[metaData.getColumnCount()];
	    for (int index = 0; index < alias.length; index++) {
		int columnIndex = index + 1;
		alias[index] = metaData.getColumnLabel(columnIndex);
	    }

	    return alias;

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public Dto findByPrimaryKey(final Dto primaryKey) throws Exception {
	
		SetTypes setTypes = new SetTypes() {
		    public void setTypes(final DataBaseTXInterface conn) throws Exception {
			executeSetType(primaryKey, dtoConfiguration.getColunasParaSeremPreenchidasNoSelectByPrimaryKey(), conn);
		    }
		};
		
		return findUniqueValue(dtoConfiguration.getSelectByPrimaryKeyScript(), setTypes);

    }

    public Dto findByPrimaryKey(final Dto primaryKey, DataBaseTXInterface conn) throws Exception {

	SetTypes setTypes = new SetTypes() {
	    public void setTypes(final DataBaseTXInterface conn) throws Exception {
		executeSetType(primaryKey, dtoConfiguration.getColunasParaSeremPreenchidasNoSelectByPrimaryKey(), conn);
	    }
	};

	return findUniqueValue(dtoConfiguration.getSelectByPrimaryKeyScript(), setTypes, conn);
    }

    public void insertOrUpdate(Dto dto) throws Exception {
	insertOrUpdate(dto, null);
    }

    public void insertOrUpdate(Dto dto, DataBaseTXInterface conn) throws Exception {

	Dto findByPrimaryKey = null;
	try {
	    findByPrimaryKey = findByPrimaryKey(dto, conn);
	} catch (Exception e) {
	    // ------------------
	}

	if (findByPrimaryKey == null) {
	    insert(dto, conn);
	} else {
	    update(dto, conn);
	}
    }

    public List<Dto> find(String query, SetTypes setTypes, ResultTransformer<Dto> resultTransformer) throws Exception {
	return executeQuery(query, setTypes, resultTransformer);
    }

    public List<Dto> find(String query, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	return executeQuery(query, setTypes, getResultTransformer(), conn);
    }

    public List<Dto> find(String query, ResultTransformer<Dto> resultTransformer, DataBaseTXInterface conn) throws Exception {
	return executeQuery(query, new EmptySetTypes(), getResultTransformer(), conn);
    }

    public List<Dto> find(String query, SetTypes setTypes, ResultTransformer<Dto> resultTransformer, DataBaseTXInterface conn) throws Exception {
	return executeQuery(query, setTypes, resultTransformer, conn);
    }

    public List<Dto> find(String query) throws Exception {
	return executeQuery(query, null, getResultTransformer());
    }

    public List<Dto> find(String query, DataBaseTXInterface conn) throws Exception {
	return executeQuery(query, null, getResultTransformer(), conn);
    }

    public static String converterPadraoJavaParaPadraoDoBanco(String nomeDoCampoNaClasse) {
	StringBuilder builder = new StringBuilder();

	for (int i = 0; i < nomeDoCampoNaClasse.length(); i++) {
	    char c = nomeDoCampoNaClasse.charAt(i);

	    boolean primeiraLetra = i == 0;
	    if (primeiraLetra) {
		builder.append(Character.toUpperCase(c));
	    } else {

		if (Character.isUpperCase(c)) {
		    builder.append("_");
		    builder.append(c);
		} else {
		    builder.append(Character.toUpperCase(c));
		}
	    }
	}

	return builder.toString();
    }

    private Class<Dto> getClasseDoDto() {

	// ---------------------
	Class directSubclass = getClass();
	while (directSubclass.getSuperclass().equals(DAOGenerico.class) == false) {
	    directSubclass = directSubclass.getSuperclass();
	}

	// ---------------------
	return (Class<Dto>) ((ParameterizedType) directSubclass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public DtoConfiguration<Dto> getDtoConfiguration() {
	return dtoConfiguration;
    }

    public void setDtoConfiguration(DtoConfiguration<Dto> dtoConfiguration) {
	this.dtoConfiguration = dtoConfiguration;
    }

    private void executeSetType(final Dto dto, List<ColunaDaTabela> colunasParaUsarSetType, DataBaseTXInterface conn) throws SQLException {
	int index = 1;
	for (ColunaDaTabela colunaDaTabela : colunasParaUsarSetType) {
	    conn.setType("P", colunaDaTabela.getTipo().getCodigoPara_SetType(), index++, colunaDaTabela.getValorDoObjeto(dto));
	}
    }

    public static void executeSql(String sql, SetTypes setTypes) throws Exception {
	DataBaseTXInterface conn = null;
	Log log = new Log(getClasseChamadora().getSimpleName());

	try {
	    conn = new DataBaseTX(DAOGenerico.class.getSimpleName());
	    if (!conn.connect()) {
		log.log("Erro na conex\u00E3o...");
		throw new MsgExcept(1000);
	    }

	    executeSql(sql, setTypes, conn);

	    conn.commit();

	} catch (Exception e) {
	    if (conn != null)
		conn.rollback();
	    log.log("Erro na execu\u00E7\u00E3o do Sql..." + e.getMessage());
	    throw e;
	} finally {
	    if (conn != null) {
		conn.disconnect();
		log.log("Desconectei...");
	    }
	}
    }

    /**
     *
     * @param codigoParaExecutar
     * @return
     * @throws Exception
     *
     * @deprecated Utilizar chamada com clousure DAOGenerico.executarTransacao(conn -> ....)
     */
    @Deprecated
    public static Object executarCodigoComControleDeTransacao(ControleTransacaoExecute codigoParaExecutar) throws Exception {
	return executarTransacaoComRetorno(conn -> {
	    codigoParaExecutar.execute(conn);
	    return codigoParaExecutar.executeComRetorno(conn);
	});
	/*DataBaseTXInterface conn = null;
	Log log = new Log(getClasseChamadora().getSimpleName());

	try {
	    conn = new DataBaseTX(DAOGenerico.class.getSimpleName());
	    if (!conn.connect()) {
		log.log("Erro na conex\u00E3o...");
		throw new MsgExcept(1000);
	    }

	    codigoParaExecutar.execute(conn);
	    Object retorno = codigoParaExecutar.executeComRetorno(conn);

	    conn.commit();
	    return retorno;

	} catch (Exception e) {
	    if (conn != null)
		conn.rollback();
	    log.log("Erro na execu\u00E7\u00E3o do Sql..." + e.getMessage());
	    throw e;
	} finally {
	    if (conn != null) {
		conn.disconnect();
		log.log("Desconectei...");
	    }
	}*/
    }

    public static void executarTransacao(CodigoTransacional codigoTransacional) throws Exception {
	executarTransacaoComRetorno(conn -> {
	    codigoTransacional.execute(conn);
	    return null;
	});
    }
    public static <X> X executarTransacaoComRetorno(CodigoTransacionalComRetorno<X> codigoTransacional) throws Exception {

	DataBaseTXInterface conn = null;
	Log log = new Log(getClasseChamadora().getSimpleName());

	try {
	    conn = new DataBaseTX(DAOGenerico.class.getSimpleName());
	    if (!conn.connect()) {
		log.log("Erro na conex\u00E3o...");
		throw new MsgExcept(1000);
	    }

	    X result = codigoTransacional.execute(conn);

	    conn.commit();
	    return result;

	} catch (Exception e) {
	    if (conn != null)
		conn.rollback();
	    log.log("Erro na execu\u00E7\u00E3o do Sql..." + e.getMessage());
	    throw e;
	} finally {
	    if (conn != null) {
		conn.disconnect();
		log.log("Desconectei...");
	    }
	}
    }

    public static void executeSql(String sql, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {

	if (conn == null) {
	    executeSql(sql, setTypes);
	    return;
	}

	conn.prepareStatement(sql);

	if (setTypes != null) {
	    setTypes.setTypes(conn);
	}

	if (conn.execute() < 0) {
	    throw new MsgExcept(1002);
	}
    }

    public String getCodIns() {
        return codIns;
    }

    public void setCodIns(String codIns) {
        this.codIns = codIns;
    }

    public void popularDTO(Dto dto, DataBaseTXInterface conn) {
	dtoConfiguration.popularDTO(dto, conn);
    }

    public ResultTransformer<Dto> getResultTransformer() {
	return dtoConfiguration.getResultTransformer();
    }

    public static <T> T executeQueryForUniqueValue(String query, ResultTransformer<T> resultTransformer) throws Exception {
	List<T> list = executeQuery(query, resultTransformer);
	return list.isEmpty() ? null : list.get(0);
    }

    public static <T> List<T> executeQuery(String query, ResultTransformer<T> resultTransformer) throws Exception {
	return executeQuery(query, null, resultTransformer);
    }

    public static <T> List<Map<String, String>> executeQuery(String query, SetTypes setTypes) throws Exception {
	return executeQuery(query, setTypes, new ResultTransformerForMapResult());
    }

    public static Map<String, String> executeQueryForUniqueValue(String query, SetTypes setTypes) throws Exception {
	return executeQueryForUniqueValue(query, setTypes, new ResultTransformerForMapResult());
    }

    public static Map<String, String> executeQueryForUniqueValue(String query, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	return executeQueryForUniqueValue(query, setTypes, new ResultTransformerForMapResult(), conn);
    }

    public static <T> T executeQueryForUniqueValue(String query, SetTypes setTypes, ResultTransformer<T> resultTransformer) throws Exception {
	List<T> list = executeQuery(query, setTypes, resultTransformer);
	return list.isEmpty() ? null : list.get(0);
    }

    public static <T> T executeQueryForUniqueValue(String query, SetTypes setTypes, ResultTransformer<T> resultTransformer, DataBaseTXInterface conn) throws Exception {
	List<T> list = executeQuery(query, setTypes, resultTransformer, conn);
	return list.isEmpty() ? null : list.get(0);
    }

    public static <T> T executeQueryForUniqueValue(String query, ResultTransformer<T> resultTransformer, DataBaseTXInterface conn) throws Exception {
	List<T> list = executeQuery(query, resultTransformer, conn);
	return list.isEmpty() ? null : list.get(0);
    }

    public static List<Map<String, String>> executeQuery(String query, DataBaseTXInterface conn) throws Exception {
	return executeQuery(query, new ResultTransformerForMapResult(), conn);
    }

    public static List<Map<String, String>> executeQuery(String query) throws Exception {
	return executeQuery(query, new ResultTransformerForMapResult());
    }

    public static <T> List<T> executeQuery(String query, ResultTransformer<T> resultTransformer, DataBaseTXInterface conn) throws Exception {
	return executeQuery(query, null, resultTransformer, conn);
    }

    public static Map<String, String> executeQueryForUniqueValue(String query) throws Exception {
	return executeQueryForUniqueValue(query, new ResultTransformerForMapResult());
    }

    public static Map<String, String> executeQueryForUniqueValue(String query, DataBaseTXInterface conn) throws Exception {
	return executeQueryForUniqueValue(query, new ResultTransformerForMapResult(), conn);
    }

    public static String getNextValueForSequence(String sequenceName) throws Exception {
	return getNextValueForSequence(sequenceName, null);
    }

    public static String getNextValueForSequence(String sequenceName, DataBaseTXInterface conn) throws Exception {
	if (conn != null) {
	    return DatabaseUtils.getSequence(sequenceName, conn);
	} else {
	    return DatabaseUtils.getSequence(sequenceName);
	}
    }

    /**
     * Preenche os metodos setDatIng, setDatUltAtu, setNomUsuUltAtu, setNomProUltAtu
     *
     * @param objetoParaPreencher
     * @param nomUsuUltAtu
     */
    public static void preencherCamposDeAuditoria(Object objetoParaPreencher, String nomUsuUltAtu) {
	try {
	    Method getDatIng 		= objetoParaPreencher.getClass().getMethod("getDatIng");
	    Method setDatIng 		= objetoParaPreencher.getClass().getMethod("setDatIng", String.class);
	    Method setDatUltAtu 	= objetoParaPreencher.getClass().getMethod("setDatUltAtu", String.class);
	    Method setNomUsuUltAtu 	= objetoParaPreencher.getClass().getMethod("setNomUsuUltAtu", String.class);
	    Method setNomProUltAtu 	= objetoParaPreencher.getClass().getMethod("setNomProUltAtu", String.class);

	    String dataIng = (String) getDatIng.invoke(objetoParaPreencher);
	    if (dataIng == null || dataIng.trim().equals("")) {
		setDatIng.invoke(objetoParaPreencher, Utils.getDateTime());
	    }

	    setDatUltAtu.invoke(objetoParaPreencher, Utils.getDateTime());
	    setNomUsuUltAtu.invoke(objetoParaPreencher, nomUsuUltAtu);

	    Class classeChamadora = getClasseChamadora();
	    setNomProUltAtu.invoke(objetoParaPreencher, classeChamadora.getName().substring(classeChamadora.getName().lastIndexOf('.') + 1));

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public static Map<Class, DtoConfiguration> getDtoConfigurationsCache() {
        return dtoConfigurationsCache;
    }

    public static void preencherCamposDeAuditoria(Object object, Contexto contexto) {
	preencherCamposDeAuditoria(object, contexto.getLogin());
    }

    public static List<Map<String, String>> executeQuery(String string, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	return executeQuery(string, setTypes, new ResultTransformerForMapResult(), conn);
    }

    /**
     * @deprecated Este metodo n\u00E3o garante q a conexao ser\u00E1 fechada (disconnected).
     * Usar DAOGenerico.executarCodigoComControleDeTransacao(new ControleTransacaoExecute...)
     */
    public static DataBaseTXInterface createConnection() throws MsgExcept, Exception {
	DataBaseTXInterface conn = new DataBaseTX(DAOGenerico.class.getSimpleName());
	if (!conn.connect()) {
	    throw new MsgExcept(1000);
	}
	return conn;
    }

    public static int count(String query) throws Exception {
	return count(query, null, null);
    }
    public static int count(String query, DataBaseTXInterface conn) throws Exception {
	return count(query, null, conn);
    }
    public static int count(String query, SetTypes setTypes) throws Exception {
	return count(query, setTypes, null);
    }
    public static int count(String query, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	String valor = executeQueryForUniqueValue(query, setTypes, ResultTransformerForUniqueAlias.RESULT_TRANSFORMER, conn);
	return Integer.parseInt(valor);
    }

    public static boolean exists(String query) throws Exception {
	return exists(query, null, null);
    }
    public static boolean exists(String query, DataBaseTXInterface conn) throws Exception {
	return exists(query, null, conn);
    }
    public static boolean exists(String query, SetTypes setTypes) throws Exception {
	return exists(query, setTypes, null);
    }
    public static boolean exists(String query, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	List<Map<String, String>> list = executeQuery(query, setTypes, new ResultTransformerForMapResult(), conn);
	return list.size() > 0;
    }

    public static boolean existsByCount(String query) throws Exception {
	return existsByCount(query, null, null);
    }
    public static boolean existsByCount(String query, DataBaseTXInterface conn) throws Exception {
	return existsByCount(query, null, conn);
    }
    public static boolean existsByCount(String query, SetTypes setTypes) throws Exception {
	return existsByCount(query, setTypes, null);
    }
    public static boolean existsByCount(String query, SetTypes setTypes, DataBaseTXInterface conn) throws Exception {
	return count(query, setTypes, conn) > 0;
    }
    
	/**
	 * Preenche os metodos setDatIng, setDatUltAtu, setNomUsuUltAtu, setNomProUltAtu
	 * 
	 * @param objetoParaPreencher
	 * @param contexto
	 */
	public static void preencherInstitutoECamposDeAuditoria(Object objetoParaPreencher, Contexto contexto) {
		try {
			
			Method getDatIng 		= objetoParaPreencher.getClass().getMethod("getDatIng");
			
			Method setCodIns 		= objetoParaPreencher.getClass().getMethod("setCodIns", String.class);
			Method setDatIng 		= objetoParaPreencher.getClass().getMethod("setDatIng", String.class);
			Method setDatUltAtu 	= objetoParaPreencher.getClass().getMethod("setDatUltAtu", String.class);
			Method setNomUsuUltAtu 	= objetoParaPreencher.getClass().getMethod("setNomUsuUltAtu", String.class);
			Method setNomProUltAtu 	= objetoParaPreencher.getClass().getMethod("setNomProUltAtu", String.class);

			setCodIns.invoke(objetoParaPreencher, contexto.getCodIns());
			
			String dataIng = (String) getDatIng.invoke(objetoParaPreencher);
			if (dataIng == null || dataIng.trim().equals("")) {
				setDatIng.invoke(objetoParaPreencher, Utils.getDateTime());
			}

			setDatUltAtu.invoke(objetoParaPreencher, Utils.getDateTime());
			setNomUsuUltAtu.invoke(objetoParaPreencher, contexto.getLogin());

			Class classeChamadora = getClasseChamadora();
			setNomProUltAtu.invoke(objetoParaPreencher, classeChamadora.getName().substring(classeChamadora.getName().lastIndexOf('.') + 1));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e); 
		} 
	}	    

}

