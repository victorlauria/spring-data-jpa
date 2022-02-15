package br.com.atlantic.comum.utils;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author mfsixel
 *
 */
public class AppDataSource {

	private static final String _CNST_CTX_JNDI_NAME="java:comp/env";
	private static DataSource ds = null;

	/**
	 * @param jndiName
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static Connection getConnection(String jndiName) throws NamingException, SQLException{
		Connection conn;
		if (ds == null) {
			ds = (DataSource)((Context)new InitialContext().lookup(_CNST_CTX_JNDI_NAME)).lookup(jndiName);
		}
		conn = ds.getConnection();
		//conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		return (conn);
	}
}
