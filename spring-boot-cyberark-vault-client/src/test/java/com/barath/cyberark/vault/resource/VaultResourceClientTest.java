package com.barath.cyberark.vault.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class VaultResourceClientTest {

	@Test
	public void testMap() {
		
		Map<String,Object> vaultMap = new HashMap<>();
		vaultMap.put("quick-start:variable:username", "root");
		vaultMap.put("quick-start:variable:password", "root");
		
		Map<String,Object> testMap= vaultMap.entrySet()
			.stream()
			.collect(Collectors.toMap( e -> {
				
				int length = e.getKey().split(":").length;
				return e.getKey().split(":")[length-1];
			},Map.Entry::getValue));
		System.out.println("vault "+testMap);
	
	}

}

