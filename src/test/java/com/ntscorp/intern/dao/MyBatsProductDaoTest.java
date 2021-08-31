package com.ntscorp.intern.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ntscorp.intern.config.ApplicationConfig;
import com.ntscorp.intern.config.DBConfig;
import com.ntscorp.intern.config.WebMvcContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcContextConfiguration.class, DBConfig.class, ApplicationConfig.class})
@WebAppConfiguration
public class MyBatsProductDaoTest {

	@Test
	public void crud() {
	}

}
