package com.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:camel*.xml" })
public class Test {
	@org.junit.Test
	public  void compare() {
		System.out.println("test");
		int i=0;
		if(i>0){
			System.out.println(i);
		}

	}
}
