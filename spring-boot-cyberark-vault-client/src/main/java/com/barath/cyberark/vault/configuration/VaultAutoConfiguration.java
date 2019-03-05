package com.barath.cyberark.vault.configuration;

import java.net.URI;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import com.barath.cyberark.vault.VaultCredential;
import com.barath.cyberark.vault.VaultEndpoint;
import com.barath.cyberark.vault.authentication.ClientAuthentication;
import com.barath.cyberark.vault.authentication.VaultClientAuthentication;
import com.barath.cyberark.vault.resource.VaultResourceClient;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE+50)
@AutoConfigureBefore({DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@Import(VaultConfiguration.class)
public class VaultAutoConfiguration {
	
	
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

}
