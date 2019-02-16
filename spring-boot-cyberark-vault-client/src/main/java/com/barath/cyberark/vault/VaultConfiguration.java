package com.barath.cyberark.vault;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaultConfiguration{

	private final ConfigurableApplicationContext context;

	

	public VaultConfiguration(ConfigurableApplicationContext context) {
		super();
		this.context=context;

	}
	
	@Bean
	public VaultCredential credential() {
		return new VaultCredential();
	}

    @Bean
	public VaultEndpoint vaultEndpoint(VaultCredential vaultCredential) {		
		return VaultEndpoint.from(URI.create(vaultCredential.getUrl()));
	}
    
    @Bean
	public ClientAuthentication clientAuthentication() {		
		return new VaultClientAuthentication();
	}
    
    @Bean
    public VaultResourceClient vaultResourceClient(VaultCredential vaultCredential) {
    	return new VaultResourceClient(vaultCredential);
    }
    
    @PostConstruct
    public void init() {
    	System.out.println("initializing secrets ");
    	this.vaultResourceClient(credential()).getSecrets(credential().getApplication());
    }

	

}
