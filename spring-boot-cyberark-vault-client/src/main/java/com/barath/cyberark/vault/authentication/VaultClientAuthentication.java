package com.barath.cyberark.vault.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import com.barath.cyberark.vault.Token;
import com.barath.cyberark.vault.exception.TokenException;
import com.barath.cyberark.vault.resource.VaultResourceClient;


public class VaultClientAuthentication implements ClientAuthentication {
	
	@Autowired
	private VaultResourceClient vaultResourceClient;	
	

	public VaultClientAuthentication() {
		super();
		
	}

	@Override
	public Token login() throws TokenException {
		
		Token token = this.vaultResourceClient.getToken();		
		return token;
	}

}
