package com.chang;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * 此类不用提交，用于生成实体类，sql语句使用
 * @author Administrator
 *
 */
public class TestDemo {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//连接数据库url
			String url = "jdbc:mysql://10.1.32.69:3306/chang";
			String user = "root";
			String pass = "1qaz@WSX";
			conn = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	//生成实体类
	public void userModel(String table){
		Connection conn = getConnection();
		String sql = "select * from "+table;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取tableName表列信息
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", table, "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					// 备注
					String columnComment = columnSet.getString("REMARKS");
					System.out.println("/**"+columnComment+"**/");
					// 列名
					String columnName = columnSet.getString("COLUMN_NAME");
					String str = "private String  ";
					String[] strs = columnName.split("_");
					for (int k = 0; k < strs.length; k++) {
						if (k == 0) {
							str = str + strs[k].toLowerCase();
						} else {
							str = str + strs[k].substring(0, 1).toUpperCase() + strs[k].substring(1, strs[k].length()).toLowerCase();
						}
					}
					System.out.println(str+";");
					System.out.println();

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//生成mapper配置文件
	public void getMapper(String table){
		Connection conn = getConnection();
		String sql = "select * from "+table;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取tableName表列信息
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", table, "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					// 列名
					String columnName = columnSet.getString("COLUMN_NAME");
					String str = "<result column=\""+columnName+"\" property=\"";
					String[] strs = columnName.split("_");
					for (int k = 0; k < strs.length; k++) {
						if (k == 0) {
							str = str + strs[k].toLowerCase();
						} else {
							str = str + strs[k].substring(0, 1).toUpperCase() + strs[k].substring(1, strs[k].length()).toLowerCase();
						}
					}
					str= str+"\" />";
					System.out.println(str);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//生成动态的更新
	public void update(String  table){
		Connection conn = getConnection();
		String sql = "select * from "+table;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取tableName表列信息
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", table, "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					// 列名
					String columnName = columnSet.getString("COLUMN_NAME");
					String str = "";
					String[] strs = columnName.split("_");
					for (int k = 0; k < strs.length; k++) {
						if (k == 0) {
							str = strs[k].toLowerCase();
						} else {
							str = str+strs[k].substring(0, 1).toUpperCase() + strs[k].substring(1, strs[k].length()).toLowerCase();
						}
					}


//					System.out.println("<if test=\""+str+" != null and "+str+" != ''\">");
					System.out.println("<if test=\""+str+" != null\">");
					System.out.println(columnName+" = #{"+str+"},");
					System.out.println("</if>");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//自动生成插入
	public void insert(String tableName){
		String str = "#{";
		Connection conn = getConnection();
		String sql = "select * from "+tableName;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取tableName表列信息
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					// 列名
					String columnName = columnSet.getString("COLUMN_NAME");
					String[] strs = columnName.split("_");
					for (int k = 0; k < strs.length; k++) {
						if (k == 0) {
							str = str+strs[k].toLowerCase();
						} else {
							str = str+strs[k].substring(0, 1).toUpperCase() + strs[k].substring(1, strs[k].length()).toLowerCase();
						}
					}
					str=str+ "},#{";

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(str.substring(0,str.length()-3));
	}

	//查询字段
	public void selectColumn(String tableName){
		String str = "";
		Connection conn = getConnection();
		String sql = "select * from "+tableName;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取tableName表列信息
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					// 列名
					String columnName = columnSet.getString("COLUMN_NAME");
					str = str + ", " + columnName;

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(str.substring(1));
	}


	public void where(String  table){
		Connection conn = getConnection();
		String sql = "select * from "+table;
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取tableName表列信息
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", table, "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					// 列名
					String columnName = columnSet.getString("COLUMN_NAME");
					String str = "";
					String[] strs = columnName.split("_");
					for (int k = 0; k < strs.length; k++) {
						if (k == 0) {
							str = strs[k].toLowerCase();
						} else {
							str = str+strs[k].substring(0, 1).toUpperCase() + strs[k].substring(1, strs[k].length()).toLowerCase();
						}
					}
					System.out.println("<if test=\""+str+" != null and "+str+" != ''\">");
					System.out.println("    AND " + columnName+" = #{"+str+"}");
					System.out.println("</if>");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestDemo  dome = new TestDemo();
//		dome.userModel("timber_self_record");
//		dome.getMapper("timber_self_record");
//		dome.selectColumn("timber_self_record");
//		dome.where("timber_self_record");
//		dome.insert("timber_self_record");
		dome.update("timber_self_record");
	}

}