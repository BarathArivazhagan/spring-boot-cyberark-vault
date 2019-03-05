package com.barath.cyberark.vault.configuration;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import com.barath.cyberark.vault.resource.VaultResourceClient;

@Configuration
public class VaultConfiguration{

	private final ConfigurableApplicationContext context;
	private VaultResourceClient vaultResourceClient;
	
	

	public VaultConfiguration(VaultResourceClient vaultResourceClient,ConfigurableApplicationContext context) {
		super();
		this.context=context;
		this.vaultResourceClient=vaultResourceClient;
		init();
	}
	

    
    public void init() {    	
     Map<String,Object> vaultMap=	this.vaultResourceClient.getSecrets(this.vaultResourceClient.getVaultCredential().getVariableIds());
     ConfigurableEnvironment env = context.getEnvironment();
     MutablePropertySources sources = env.getPropertySources(); 
     // remove account name & variable keywords from the properties
     vaultMap=vaultMap.entrySet()
		.stream()
		.collect(Collectors.toMap( e -> {			
			int length = e.getKey().split(":").length;
			return e.getKey().split(":")[length-1];
		},Map.Entry::getValue));
     MapPropertySource vaultPropertySource= new MapPropertySource("vaultClient",vaultMap);
     sources.addFirst(vaultPropertySource);
    
    }

	

}
