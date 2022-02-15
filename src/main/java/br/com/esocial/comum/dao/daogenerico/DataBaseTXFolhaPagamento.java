package br.com.esocial.comum.dao.daogenerico;

import java.io.File;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.atlantic.comum.utils.Contexto;
import br.com.atlantic.comum.utils.Crypt;
import br.com.atlantic.comum.utils.IniFile;
import br.com.atlantic.comum.utils.Log;
import br.com.atlantic.comum.utils.MsgExcept;
import br.com.atlantic.comum.utils.Utils;
//import oracle.jdbc.pool.OracleConnectionCacheImpl;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.CLOB;

/**
 * Classe que prov� a conexão com o banco de dados ORACLE
 * 
 * @author Raphael de Oliveira Silva
 * @version 1.00
 */

public final class DataBaseTXFolhaPagamento implements DataBaseTXInterface {

	private static OracleDataSource odts = null; // No caso � utilizado para
												// conexão sem fazer
												// cacheamento
	// Nome da classe.
	private final String CLASS_NAME = getClass().getName().replaceFirst(getClass().getPackage().getName() + ".", "");

	private static OracleConnectionPoolDataSource ods = null;

	private Connection connection = null; // Classe de conexão
	private PreparedStatement preparedStatement = null;

	// Classe de execu\u00E7\u00E3o de comandos
	private CallableStatement callableStatement = null;
	
	// Classe de execu\u00E7\u00E3o de Procedimentos Armazenados
	private ResultSet resultSet = null; // Classe de resultado

	// private String DB_DRIVER = ""; // name's path to jdbc directory (ORACLE)
	protected String DB_URL_FULL    = ""; // name's path to jndi	
	protected String DB_PATH_JNDI   = ""; // name's path to jndi
	protected String DO_LOOKUP      = ""; // path to lookup
	protected String MAX_CONNECTION = ""; // max limit of conection

	protected String DB_PATH = ""; // IP da conexão
	protected String DB_SERVER = "";
	
	// name of ORACLE service to establish a connection with database
	// private boolean readyToGo;
	// true if DB_SERVER was read and one connection was made with success
	private String acessClass = null;
	// name of class that is trying use DataBaseTX
	private Log log = null; // Log class
	private String sqlCommand = ""; // Sql preparada para ser executada
	private StringBuffer sqlCommandToLog = new StringBuffer("");
	// Sql completa para ser logada nos arquivos de log
	private String nameStoredProcedure = ""; // Sql preparada para ser executada

	protected static String DB_USER_NAME = null;
	protected static String DB_USER_PASS = null;

	private String versao = "Versao - 1.09";

	// Commands
	private final String DB_BEGIN_TRAN = " begin tran ";
	private final String DB_END_TRAN = " end tran ";
	private final String DB_COMMIT = " commit work ";
	private final String DB_ROLLBACK = " rollback ";
	private final String DB_LOG_ERROR_FILE = "SQLErrors";
	private final String DB_LOG_COMMAND = "SQLCommands";

	// Error messages
	private final String DB_ERROR_COMMAND = "Error in execute command.";
	private final String DB_ERROR_COMMAND_PROCEDURE = "Error in Stored Procedure execute.";
	private final String DB_ERROR_NO_CONNECT = "There is no connection opened with DataBase.";
	private final String DB_ERROR_NO_RESULTSET = "There is no ResultSet opened with DataBase for this object.";
	
	private String usuarioAplicacao = null;

	/**
	 * Construtor default
	 */
	public DataBaseTXFolhaPagamento() {
		// readyToGo = false;
		acessClass = "NoIdentified";
		log = new Log("DataBaseTX");
		log.log(versao);

		sysLog("");
	}

	/**
	 * Construtor que recebe a identificação do objeto que solicita conexão
	 * 
	 * @param str
	 *            � o nome da classe que está criando a instancia do objeto
	 *            DataBaseTX
	 */
	public DataBaseTXFolhaPagamento(String str) {
		// readyToGo = false;
		if (str == null)
			acessClass = "NoIdentified";
		else
			acessClass = str;
		log = new Log("DataBaseTX");
		log.log(versao);

		sysLog("");

	}	
	
	public DataBaseTXFolhaPagamento(String str, Contexto contexto) {

		if (str == null)
			acessClass = "NoIdentified";
		else
			acessClass = str;
		
		log = new Log("DataBaseTX");
		log.log(versao);

		sysLog("");
		
		if (contexto != null && !contexto.getLogin().equals("")) {
			usuarioAplicacao = contexto.getLogin();
		}

	}
	
	/**
	 * Construtor que recebe a identificação do objeto que solicita conexão
	 * 
	 * @param str
	 *            � o nome da classe que está criando a instancia do objeto
	 *            DataBaseTX
	 */
	public DataBaseTXFolhaPagamento(String str, boolean flgCache) {

		flgCache = false;

		// readyToGo = false;
		if (str == null)
			acessClass = "NoIdentified";
		else
			acessClass = str;
		log = new Log("DataBaseTX");
		log.log(versao);

		if (str.equals("ProcessamentoFolhaDAO")) {
			ods = null;
			odts = null;
		}

		if (str.equals("DecisaoService")) {
			ods = null;
			odts = null;
		}

		sysLog("");

	}
	

	/**
	 * Construtor que recebe a identificação do objeto que solicita conexão
	 * 
	 * @param obj
	 *            � o nome do objeto que está criando a instancia do objeto
	 *            DataBaseTX
	 */
	public DataBaseTXFolhaPagamento(Object obj) {
		// readyToGo = false;
		if (obj == null)
			acessClass = "NoIdentified";
		else
			acessClass = obj.getClass().getName();

		log = new Log("DataBaseTX");
		log.log(versao);
	}

	/**
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	private boolean readFile() throws SQLException, NamingException {
		IniFile ini = new IniFile("conf" + File.separator
				+ "dbconfig.prevweb.par");

		// read key the Service in IniFile
		sysLog("loading dbconfig.par...");
		
		DB_URL_FULL    = ini.readString("Url"     , "Path_Full_Folha"     , "notfound");
		DB_PATH_JNDI   = ini.readString("Jndi"    , "Path_Folha"          , "notfound");
		DO_LOOKUP      = ini.readString("Jndi"    , "Lookup_Folha"        , "notfound");
		MAX_CONNECTION = ini.readString("DataBase", "Max_connection_Folha", "notfound");

		if (DB_PATH_JNDI.equals("notfound")) {
			File file = new File(System.getProperty("java.io.tmpdir"));
			if (!file.exists())
				try {
					file.mkdir();
				} catch (Exception e) {
				}
			DB_PATH_JNDI = "file://" + System.getProperty("java.io.tmpdir");
		}

		if (DO_LOOKUP.equals("notfound")) {
			sysLog("section [Jndi] or key [Lookup] not found in [dbconfig.par]!");
			return false;
		}

		String servidor = System.getProperty("user.dir");
		if (servidor.toUpperCase().contains("TOMCAT") || servidor.toUpperCase().contains("ECLIPSE")) {
			DO_LOOKUP += "/" + "/" + CLASS_NAME;
		}

		// key Max_connection not found in IniFile
		if (MAX_CONNECTION.equals("notfound")) {
			sysLog("section [Database] or key [Max_connection] not found in [dbconfig.par]!");
			return false;
		}

		String dbUserCrypt = ini.readString("Login", "User_Folha", "");
		String dbPassCrypt = ini.readString("Login", "Pass_Folha", "");

		// decriptografando senha
		Crypt crypt = new Crypt();
		DB_USER_NAME = crypt.deCrypt(dbUserCrypt, "atlantic");
		DB_USER_PASS = crypt.deCrypt(dbPassCrypt, "atlantic");

		return true;
	}

	protected void sysLog(String str) {
		log.log(str);
	}

	// /**
	// * M�todo respons�vel pela disconexão com o Banco de Dados ORACLE
	// * @exception Levanta uma SQLException caso algum erro aconte�a
	// */
	// public void _disconnect() throws SQLException
	// {
	// if (readyToGo)
	// {
	// readyToGo = false;
	// if (resultSet != null) resultSet.close();
	// if (preparedStatement != null) preparedStatement.close();
	// if (callableStatement != null) callableStatement.close();
	// if (connection != null)
	// {
	// connection.setAutoCommit(true);
	// connection.close();
	// }
	//
	// resultSet = null;
	// preparedStatement = null;
	// callableStatement = null;
	// connection = null;
	// sysLog("class ["+acessClass+"] disconnected!");
	// sysLog(DB_END_TRAN);
	//
	// }
	// else
	// sysLog("class ["+acessClass+"] was already disconnected!");
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#disconnect()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#disconnect()
	 */
	public final void disconnect() throws SQLException {
		if (connection != null) {

			if (resultSet != null)
				resultSet.close();
			if (preparedStatement != null)
				preparedStatement.close();
			if (callableStatement != null)
				callableStatement.close();

			if (connection != null) {
				connection.setAutoCommit(true);
				connection.close();
			}

			resultSet = null;
			preparedStatement = null;
			callableStatement = null;

			sysLog("class [" + acessClass + "] disconnected!");
			sysLog(DB_END_TRAN);

		} else
			sysLog("class [" + acessClass + "] was already disconnected!");
	}

	// /**
	// * M�todo respons�vel pela conexão com o Banco de Dados ORACLE
	// * @exception Levanta uma Exception caso algum erro aconte�a
	// */
	// public boolean _connect() throws Exception
	// {
	// try
	// {
	// sysLog(System.getProperty("user.dir"));
	//
	// ini = new IniFile("conf/dbconfig.par");
	//
	// // read key the Service in IniFile
	// if (!readyToGo)
	// {
	// sysLog("loading dbconfig.par...");
	// DB_DRIVER = ini.readString("Url","Driver","notfound");
	// DB_PATH = ini.readString("Url","Path","notfound");
	// DB_SERVER = ini.readString("DataBase","Service","notfound");
	//
	// String dbUserCrypt = ini.readString("Login","User","");
	//
	// String dbPassCrypt = ini.readString("Login","Pass","");
	//
	// String test = ini.readString("Login","test","");
	// sysLog("test["+test+"]");
	//
	// // decriptografando senha
	// Crypt crypt = new Crypt();
	// DB_USER_NAME = crypt.deCrypt(dbUserCrypt,"sonda");
	// DB_USER_PASS = crypt.deCrypt(dbPassCrypt,"sonda");
	// }
	// else
	// {
	// sysLog("ORACLE service already set");
	// sysLog("class ["+acessClass+"] connected");
	// return true;
	// }
	//
	//
	// // key Driver not found in IniFile
	// if (DB_DRIVER.equals("notfound"))
	// {
	// sysLog("section [Database] or key [Driver] not found in [dbconfig.par]!");
	// return false;
	// }
	//
	// // key Path not found in IniFile
	// if (DB_PATH.equals("notfound"))
	// {
	// sysLog("section [Database] or key [Path] not found in [dbconfig.par]!");
	// return false;
	// }
	//
	// // key Service not found in IniFile
	// if (DB_SERVER.equals("notfound"))
	// {
	// sysLog("section [Database] or key [Service] not found in [dbconfig.par]!");
	// return false;
	// }
	//
	// sysLog("Registering ORACLE driver ["+DB_DRIVER+"] ...");
	//
	// Class.forName(DB_DRIVER).newInstance();
	//
	// sysLog("");
	// sysLog("ORACLE driver ["+DB_DRIVER+"] Registered...");
	//
	// sysLog("trying ORACLE service ["+DB_SERVER+"] ...");
	//
	//
	// // trying connect to database...
	//
	// connection = DriverManager.getConnection(DB_PATH+":"+DB_SERVER,
	// DB_USER_NAME, DB_USER_PASS);
	//
	// // connection ok
	// if (connection!=null)
	// {
	// // not necessary read DB_SERVER again
	// sysLog("connection OK locking in service ["+DB_SERVER+"]...");
	// sysLog("class ["+acessClass+"] connected");
	// sysLog(DB_BEGIN_TRAN);
	// connection.setAutoCommit(false);
	// readyToGo = true;
	// return true;
	// }
	// else
	// sysLog("service ["+DB_SERVER+"] failed, trying another...");
	//
	// // Service not valid trying another...
	//
	// // reading total number of avaliable services
	//
	// int n = Utils.strToInt(ini.readString("DataBase","total","0"));
	// sysLog("number of services found = ["+n+"]");
	//
	//
	// // total number of avaliable services not found!
	// if (n==0)
	// {
	// sysLog("section [DataBase] or key [total] not found in [dbconfig.par]!");
	// return false;
	// }
	//
	// for (int i=1;i<=n;i++)
	// {
	// // reading next avaliable Service...
	// DB_SERVER = ini.readString("DataBase","Service"+i,"notfound");
	//
	// if (DB_SERVER.equals("notfound"))
	// {
	// sysLog("section [Database] or key [Service"+i+"] not found in [dbconfig.par]!");
	// sysLog("service"+i+" ["+DB_SERVER+"] failed, trying another...");
	// continue;
	// }
	//
	// // trying connect to database...
	// connection = DriverManager.getConnection(DB_PATH+":"+DB_SERVER,
	// DB_USER_NAME, DB_USER_PASS);
	//
	// sysLog("trying ORACLE service"+i+" ["+DB_SERVER+"] ...");
	//
	//
	// // connection ok
	// if (connection!=null)
	// {
	// // not necessary read DB_SERVER again
	// sysLog("connection OK locking in service["+i+"] ["+DB_SERVER+"]...");
	// sysLog("class ["+acessClass+"] connected");
	// sysLog(DB_BEGIN_TRAN);
	// readyToGo = true;
	// break;
	// }
	// else
	// sysLog("service"+i+" ["+DB_SERVER+"] failed, trying another...");
	//
	// }
	//
	// connection.setAutoCommit(false);
	//
	// sysLog("DB_SERVER = ["+DB_SERVER+"]");
	//
	// sysLog("return [true]");
	// return true;
	// }
	// catch(Exception e)
	// {
	// sysLog("Entrei no Exception: "+e);
	// return false;
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#connect()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#connect()
	 */
	public final boolean connect() throws Exception {
		try {
			// sysLog(System.getProperty("user.dir"));

			String servidor = System.getProperty("user.dir");

			if (servidor.toUpperCase().contains("TOMCAT") || servidor.toUpperCase().contains("ECLIPSE")) {

				//if (odsc == null || odsc.getCacheSize() == 0) {
					// reading the parameters file
					if (!readFile())
						return false;

					Context context = null;
					Hashtable env = new Hashtable(5);

					env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
					env.put(Context.PROVIDER_URL, DB_PATH_JNDI);

					context = new InitialContext(env);

					do_bind(context, DO_LOOKUP);
					ods = do_lookup(context, DO_LOOKUP);

					// Retrieve a connection
					connection = ods.getConnection();

			} else {

				if (!readFile())
					return false;

				InitialContext context = new InitialContext();
				DataSource ds = (DataSource) context.lookup(DO_LOOKUP);
				connection = ds.getConnection();
			}

			// connection ok
			if (connection != null) {
				sysLog("connection OK ...");
				sysLog("class [" + acessClass + "] connected...");
				sysLog(DB_BEGIN_TRAN);
				connection.setAutoCommit(false);
				
				try {
					if (usuarioAplicacao != null) {
						//#TODO Guardando o usu�rio da aplica��o no Banco para Triggers de Auditoria (Verificar performance)
						CallableStatement pstmt = connection.prepareCall("{call set_usuario_ctx_pkg.set_usuario(?)}");
						pstmt.setString(1, usuarioAplicacao);
						pstmt.execute();												
						pstmt.close();
						connection.commit(); //Verificar necessidade
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}				
				
				return true;
			} else {
				sysLog("service failed, try another...");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			sysLog("Entrei no Exception: " + e);
			return false;
		}
	}

	private void do_bind(Context ctx, String ln) throws SQLException, NamingException, MsgExcept {
		
		// Create a OracleDataSource instance explicitly
		ods = new OracleConnectionPoolDataSource();

		// Set the user name, password, driver type and network protocol
		ods.setUser(DB_USER_NAME);
		ods.setPassword(DB_USER_PASS);
		ods.setDriverType("thin");
		ods.setNetworkProtocol("ipc");
		
        if (!DB_URL_FULL.equals("") && !DB_URL_FULL.equalsIgnoreCase("notfound")) {
        	ods.setURL            (DB_URL_FULL);
        	log.log("do_bind - DB_URL_FULL: " + DB_URL_FULL);
        } else {
        	throw new MsgExcept(1000);
        }

		// Bind it
		sysLog("Doing a bind with the logical name : " + ln);
		ctx.rebind(ln, ods);
	}

	private OracleConnectionPoolDataSource do_lookup(Context ctx, String ln)
			throws SQLException, NamingException {

		sysLog("Doing a lookup with the logical name : " + ln);
		return (OracleConnectionPoolDataSource) ctx.lookup(ln);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#commit()
	 */
	public final void commit() throws SQLException {
		this.connectionClosed();
		sysLog("O banco está auto commit? " + connection.getAutoCommit());
		connection.commit();
		sysLog(DB_COMMIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#rollback()
	 */
	public final void rollback() throws SQLException {
		this.connectionClosed();
		sysLog("O banco está auto commit? " + connection.getAutoCommit());
		connection.rollback();
		sysLog(DB_ROLLBACK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#prepareStatement(java.lang.Object)
	 */
	public final void prepareStatement(Object oCommand) throws SQLException {
		sqlCommand = oCommand.toString().trim();
		// usada para ser logada
		sqlCommandToLog = new StringBuffer(sqlCommand);

		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}
		if (preparedStatement != null) {
			preparedStatement.close();
			preparedStatement = null;
		}

		// Cria a vari\u00E1vel de execu\u00E7\u00E3o
		preparedStatement = connection.prepareStatement(sqlCommand,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#setType(java.lang.String, java.lang.String,
	 * int, java.lang.String)
	 */
	public final void setType(String typeCommand, String type, int pos,
			String param) throws SQLException {
		setType(typeCommand, type, pos, ((Object) param));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#setType(java.lang.String, java.lang.String,
	 * int, java.lang.Object)
	 */
	public final void setType(String typeCommand, String type, int pos,
			Object param) throws SQLException {

		type = type.trim();
		typeCommand = typeCommand.trim();

		String parameter = null;
		if (param == null) {
			parameter = null;
		} else {
			parameter = param.toString();
		}

		// preenche o comando sql com os parametros setados
		// usados para log apenas - ajuda na hora do desenvolvimento
		int indice = sqlCommandToLog.toString().indexOf("?");
		if (indice != -1) {
			if (type.equalsIgnoreCase("%s")) {
				sqlCommandToLog.replace(indice, indice + 1, "'" + parameter
						+ "'");
			} else {
				sqlCommandToLog.replace(indice, indice + 1,
						String.valueOf(parameter));
			}
		}

		if (typeCommand.equalsIgnoreCase("P")) {
			// Verifica que tipo de parametro ir� ser passado para
			// prepareStatement
			if (type.equalsIgnoreCase("%s")) {
				preparedStatement.setString(pos, parameter);
			} else if (type.equalsIgnoreCase("%d")) {
				preparedStatement.setBigDecimal(pos, new BigDecimal(parameter));
			} else if (type.equalsIgnoreCase("%dh")) {
				if (param instanceof Timestamp) {
					Timestamp ts = (Timestamp) param;
					preparedStatement.setTimestamp(pos, ts);
				} else if (param instanceof java.util.Date) {

					java.util.Date date = (java.util.Date) param;
					preparedStatement.setTimestamp(pos,
							new Timestamp(date.getTime()));
				} else if (param instanceof Time) {
					Time time = (Time) param;
					preparedStatement.setTime(pos, time);
				} else if (param instanceof String) {
					String str = (String) param;
					try {
						preparedStatement.setTimestamp(pos,
								Utils.strToTimestamp(str));
					} catch (Exception e) {
						throw new SQLException("Invalid parameter");
					}
				} else {
					preparedStatement.setTimestamp(pos, null);
				}
			} else if (type.equalsIgnoreCase("%f")) {
				preparedStatement.setBigDecimal(pos, Utils.trim(parameter)
						.equals("") ? null : new BigDecimal(parameter));
			}
		} else if (typeCommand.equalsIgnoreCase("S")) {
			// Verifica que tipo de parametro ir� ser passado para
			// callableStatement
			if (type.equalsIgnoreCase("%s")) {
				callableStatement.setString(pos, parameter);
			} else if (type.equalsIgnoreCase("%d")) {
			    //callableStatement.setInt(pos, Utils.strToInt(parameter));
			    if (parameter == null) {
				callableStatement.setString(pos, null);
			    } else {
				callableStatement.setInt(pos, Utils.strToInt(parameter));
			    }
			} else if (type.equalsIgnoreCase("%dh")) {
				if (param instanceof Timestamp) {
					Timestamp ts = (Timestamp) param;
					callableStatement.setTimestamp(pos, ts);
				} else if (param instanceof java.util.Date) {

					java.util.Date date = (java.util.Date) param;
					callableStatement.setTimestamp(pos,
							new Timestamp(date.getTime()));
				} else if (param instanceof Time) {
					Time time = (Time) param;
					callableStatement.setTime(pos, time);
				} else {
					callableStatement.setTimestamp(pos, null);
				}
			} else if (type.equalsIgnoreCase("%f")) {
				callableStatement.setDouble(pos, Utils.strToDouble(parameter));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#execute()
	 */
	public final int execute() throws SQLException {
		// Chama a execu\u00E7\u00E3o interna
		return this.execute(sqlCommand);
	}

	/**
	 * M�todo que executa comandos no Banco de Dados, estes podem ser INSERT,
	 * DELETE, UPDATE, SELECT, CREATE e DROP
	 * 
	 * @param cCommand
	 *            � o sql que ser\u00E1 executado
	 * @return -1 caso aconte�a algum erro na execu\u00E7\u00E3o do comando, 0 (zero) caso
	 *         o comando executado seja do tipo SELECT ou o Número de linhas
	 *         modificadas pelo comando executado que tamb�m poder� ser 0 (zero)
	 * @exception SQLException
	 *                caso algum erro aconte�a
	 */
	private int execute(Object oCommand) {
		int retorno = 0;
		String sql = oCommand.toString();
		String sqlAux = oCommand.toString().toUpperCase().trim();
		long inicio, fim;

		try {
			this.connectionClosed();

			if (sqlAux.startsWith("C") || sqlAux.startsWith("DR")
					|| sqlAux.startsWith("DE") || sqlAux.startsWith("I")
					|| sqlAux.startsWith("U")) {
				// Executa comandos diferentes de Select
				inicio = System.currentTimeMillis();
				retorno = preparedStatement.executeUpdate();
				fim = System.currentTimeMillis();
			} else {
				// Executa comandos de Select
				if (resultSet != null) {
					resultSet.close();
					resultSet = null;
				}
				inicio = System.currentTimeMillis();
				resultSet = preparedStatement.executeQuery();
				fim = System.currentTimeMillis();
			}

			// Limpa os par\u00E2metros do sql
			preparedStatement.clearParameters();

			Log sqlLog = new Log(DB_LOG_COMMAND + "_"
					+ Calendar.getInstance().get(Calendar.HOUR));
			sqlLog.log(sql);
			sqlLog.log("");
			sqlLog.log("SQL executada[" + (fim - inicio) + "ms.]: "
					+ sqlCommandToLog.toString());
			sqlLog.log("");

			Log sqlLogUser = new Log(DB_LOG_COMMAND + "_" + acessClass);
			sqlLogUser.log(sql);
			sqlLogUser.log("");
			sqlLogUser.log("SQL executada[" + (fim - inicio) + "ms.]: "
					+ sqlCommandToLog.toString());
			sqlLogUser.log("");

			return retorno;
		} catch (Exception e) {
			Log erroLog = new Log(DB_LOG_ERROR_FILE);
			erroLog.log(sql);
			erroLog.log("SQL executada: " + sqlCommandToLog.toString());
			erroLog.log(e.getMessage());
			erroLog.log("");
			erroLog.log(DB_ERROR_COMMAND);

			Log erroLogUser = new Log(DB_LOG_ERROR_FILE + "_" + acessClass);
			erroLogUser.log(sql);
			erroLogUser.log("SQL executada: " + sqlCommandToLog.toString());
			erroLogUser.log(e.getMessage());
			erroLogUser.log("");
			erroLogUser.log(DB_ERROR_COMMAND);

			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#prepareCall(java.lang.String)
	 */
	public final void prepareCall(String sNameStoredProcedure)
			throws SQLException {
		this.nameStoredProcedure = sNameStoredProcedure.trim();

		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}
		if (callableStatement != null) {
			callableStatement.close();
			callableStatement = null;
		}
		callableStatement = connection.prepareCall(sNameStoredProcedure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#executeStoredProcedure()
	 */
	public final int executeStoredProcedure() {
		int retorno = 0;
		long inicio, fim;

		try {
			inicio = System.currentTimeMillis();
			callableStatement.execute();
			fim = System.currentTimeMillis();

			Log sqlLog = new Log(DB_LOG_COMMAND + "_"
					+ Calendar.getInstance().get(Calendar.HOUR));
			sqlLog.log("");
			sqlLog.log("Stored Procedure executada[" + (fim - inicio)
					+ "ms.]: " + nameStoredProcedure);
			sqlLog.log("");

			Log sqlLogUser = new Log(DB_LOG_COMMAND + "_" + acessClass);
			sqlLogUser.log("");
			sqlLogUser.log("Stored Procedure executada[" + (fim - inicio)
					+ "ms.]: " + nameStoredProcedure);
			sqlLogUser.log("");

			return retorno;

		} catch (Exception e) {
			Log erroLog = new Log(DB_LOG_ERROR_FILE);
			erroLog.log(nameStoredProcedure);
			erroLog.log(e.getMessage());
			erroLog.log("");
			erroLog.log(DB_ERROR_COMMAND_PROCEDURE);
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#registerOutParameter(int, int)
	 */
	public final void registerOutParameter(int pos, int type)
			throws SQLException {
		callableStatement.registerOutParameter(pos, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#registerOutParameter(int, int,
	 * java.lang.String)
	 */
	public final void registerOutParameter(int paramIndex, int sqlType,
			String typeName) throws SQLException {
		callableStatement.registerOutParameter(paramIndex, sqlType, typeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getOutAsString(int)
	 */
	public final String getOutAsString(int pos) throws SQLException {
		return callableStatement.getString(pos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getOutAsDate(int)
	 */
	public final java.util.Date getOutAsDate(int pos) throws SQLException {

		if (getOutAsString(pos).equals("")) {
			return null;
		} else {
			return callableStatement.getDate(pos);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getOutAsObject(int)
	 */
	public final void getOutAsObject(int pos) throws SQLException {
		resultSet = (ResultSet) callableStatement.getObject(pos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#next()
	 */
	public final boolean next() throws SQLException {
		this.resultSetClosed();

		return resultSet.next();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getColumn(java.lang.String)
	 */
	public final String getColumn(String sColumnName) throws SQLException {
		this.resultSetClosed();

		return Utils.trim(resultSet.getString(Utils.trim(sColumnName)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getColumn(int)
	 */
	public final String getColumn(int sColumnNumber) throws SQLException {
		this.resultSetClosed();

		return Utils.trim(resultSet.getString(sColumnNumber));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getColumnDate(java.lang.String)
	 */
	public final java.util.Date getColumnDate(String sColumnName)
			throws SQLException {
		this.resultSetClosed();

		if (getColumn(sColumnName).equals("")) {
			return null;
		} else {
			return resultSet.getTimestamp(sColumnName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getColumnDate(int)
	 */
	public final java.util.Date getColumnDate(int sColumnNumber)
			throws SQLException {
		this.resultSetClosed();
		if (getColumn(sColumnNumber).equals("")) {
			return null;
		} else {
			return resultSet.getTimestamp(sColumnNumber);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getColumnClob(java.lang.String)
	 */
	public final CLOB getColumnClob(String sColumnName) throws SQLException {
		this.resultSetClosed();
		return (CLOB) resultSet.getClob(sColumnName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getColumnClob(int)
	 */
	public final CLOB getColumnClob(int sColumnNumber) throws SQLException {
		this.resultSetClosed();
		return (CLOB) resultSet.getClob(sColumnNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#existsRow()
	 */
	public final boolean existsRow() throws SQLException {
		this.resultSetClosed();
		boolean retorno = false;
		resultSet.last();
		log.log("Número de registros retornados = " + resultSet.getRow());

		retorno = (resultSet.getRow() > 0);
		resultSet.beforeFirst();

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getRow()
	 */
	public final int getRow() throws SQLException {
		this.resultSetClosed();
		resultSet.last();
		int retorno = resultSet.getRow();
		resultSet.beforeFirst();
		return retorno;
	}

	// Verifica se a conexão está aberta
	private void connectionClosed() throws SQLException {
		if (connection == null || connection.isClosed()) {
			throw new SQLException(DB_ERROR_NO_CONNECT);
		}
	}

	// Verifica se existe resultSet aberto
	private void resultSetClosed() throws SQLException {
		if (resultSet == null) {
			throw new SQLException(DB_ERROR_NO_RESULTSET);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.DataBaseTXInterface#getConnection()
	 */
	public Connection getConnection() {
		return connection;
	}

}