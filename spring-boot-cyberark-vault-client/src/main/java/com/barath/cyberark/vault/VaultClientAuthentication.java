package com.barath.cyberark.vault;

import org.springframework.beans.factory.annotation.Autowired;


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
