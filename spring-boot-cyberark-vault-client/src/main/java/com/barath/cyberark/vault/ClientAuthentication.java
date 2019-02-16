package com.barath.cyberark.vault;



@FunctionalInterface
public interface ClientAuthentication {

	/**
	 * Return a {@link VaultToken}. This method can optionally log into Vault to obtain a
	 * {@link VaultToken token}.
	 *
	 * @return a {@link VaultToken}.
	 */
	Token login() throws TokenException;
}
