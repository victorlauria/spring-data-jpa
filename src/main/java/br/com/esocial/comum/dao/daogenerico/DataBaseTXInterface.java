package br.com.esocial.comum.dao.daogenerico;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.CLOB;

public interface DataBaseTXInterface {

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#disconnect()
	 */
	public abstract void disconnect() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#connect()
	 */
	public abstract boolean connect() throws Exception;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#commit()
	 */
	public abstract void commit() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#rollback()
	 */
	public abstract void rollback() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#prepareStatement(java.lang.Object)
	 */
	public abstract void prepareStatement(Object oCommand) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#setType(java.lang.String, java.lang.String, int, java.lang.String)
	 */
	public abstract void setType(String typeCommand, String type, int pos,
			String param) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#setType(java.lang.String, java.lang.String, int, java.lang.Object)
	 */
	public abstract void setType(String typeCommand, String type, int pos,
			Object param) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#execute()
	 */
	public abstract int execute() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#prepareCall(java.lang.String)
	 */
	public abstract void prepareCall(String sNameStoredProcedure)
			throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#executeStoredProcedure()
	 */
	public abstract int executeStoredProcedure();

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#registerOutParameter(int, int)
	 */
	public abstract void registerOutParameter(int pos, int type)
			throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#registerOutParameter(int, int, java.lang.String)
	 */
	public abstract void registerOutParameter(int paramIndex, int sqlType,
			String typeName) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getOutAsString(int)
	 */
	public abstract String getOutAsString(int pos) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getOutAsDate(int)
	 */
	public abstract java.util.Date getOutAsDate(int pos) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getOutAsObject(int)
	 */
	public abstract void getOutAsObject(int pos) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#next()
	 */
	public abstract boolean next() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getColumn(java.lang.String)
	 */
	public abstract String getColumn(String sColumnName) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getColumn(int)
	 */
	public abstract String getColumn(int sColumnNumber) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getColumnDate(java.lang.String)
	 */
	public abstract java.util.Date getColumnDate(String sColumnName)
			throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getColumnDate(int)
	 */
	public abstract java.util.Date getColumnDate(int sColumnNumber)
			throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getColumnClob(java.lang.String)
	 */
	public abstract CLOB getColumnClob(String sColumnName) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getColumnClob(int)
	 */
	public abstract CLOB getColumnClob(int sColumnNumber) throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#existsRow()
	 */
	public abstract boolean existsRow() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getRow()
	 */
	public abstract int getRow() throws SQLException;

	/* (non-Javadoc)
	 * @see lib.DataBaseTXInterface#getConnection()
	 */
	public abstract Connection getConnection();

}