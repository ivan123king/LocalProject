package com.lw.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class PersonDao {

	private JdbcTemplate template;

	public void query() throws SQLException {
		String sql = "select * from t_person";
		List<String> names = new ArrayList<String>();
		//此处第二个参数是  RowCallbackHandler类
		template.query(sql,(resultSet)->{
			String name = resultSet.getString("p_name");
			names.add(name);
		});
		names.parallelStream().forEach(System.out::println);
	}
	
	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	
	
}
