package com.lw.kettle;

import java.util.HashMap;
import java.util.Map;

public class ExtractBean {

	/**
	 * 源表数据库连接
	 */
	private DatabaseConn srcDB;

	/**
	 * 源表表名
	 */
	private String[] srcTable = new String[0];

	/**
	 * 源表交换字段类型
	 */
	private String[] srcFields;

	/**
	 * 源表主键
	 */
	private String[] srcPk;

	/**
	 * 目标表的数据库配置
	 */
	private DatabaseConn destDB;

	/**
	 * 目标表
	 */
	private String destTable;

	/**
	 * 目标表字段
	 */
	private String[] destFields;

	/**
	 * 目标表主键
	 */
	private String[] destPk;
	
	/**
	 * 数据转换
	 */
	private FieldTransfer[] fieldTransfers;

	public FieldTransfer[] getFieldTransfers() {
		return fieldTransfers;
	}

	public void setFieldTransfers(FieldTransfer[] fieldTransfers) {
		this.fieldTransfers = fieldTransfers;
	}

	public String[] getSrcTable() {
		return srcTable;
	}

	public void setSrcTable(String[] srcTable) {
		this.srcTable = srcTable;
	}

	public String[] getSrcFields() {
		return srcFields;
	}

	public void setSrcFields(String[] srcFields) {
		this.srcFields = srcFields;
	}

	public String[] getSrcPk() {
		return srcPk;
	}

	public void setSrcPk(String[] srcPk) {
		this.srcPk = srcPk;
	}

	public String getDestTable() {
		return destTable;
	}

	public void setDestTable(String destTable) {
		this.destTable = destTable;
	}

	public String[] getDestFields() {
		return destFields;
	}

	public void setDestFields(String[] destFields) {
		this.destFields = destFields;
	}

	public String[] getDestPk() {
		return destPk;
	}

	public void setDestPk(String[] destPk) {
		this.destPk = destPk;
	}

	public DatabaseConn getSrcDB() {
		return srcDB;
	}

	public void setSrcDB(DatabaseConn srcDB) {
		this.srcDB = srcDB;
	}

	public DatabaseConn getDestDB() {
		return destDB;
	}

	public void setDestDB(DatabaseConn destDB) {
		this.destDB = destDB;
	}
}
