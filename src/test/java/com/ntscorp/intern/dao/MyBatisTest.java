package com.ntscorp.intern.dao;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ntscorp.intern.product.dto.ProductDto;

public class MyBatisTest {
	
	private static final Logger log = LoggerFactory.getLogger(MyBatisTest.class);
	
	@Test
	public void gettingStarted() throws IOException {
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			  List<ProductDto> productDtoList = session.selectList("ProductMapper.selectByCategoryId", 1);
			  log.info("DEBUG : {}", productDtoList);
		}
	}
}
