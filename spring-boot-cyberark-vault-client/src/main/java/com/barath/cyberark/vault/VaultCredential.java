package com.barath.cyberark.vault;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="spring.vault.credential")
public class VaultCredential implements Serializable {

	private static final long serialVersionUID = 2081795482457612775L;
	
	@NotNull
	private String url;
	
	@NotNull
	private String account;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	private String application;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	
	
	

}
