package com.barath.cyberark.vault;



@FunctionalInterface
public interface ClientAuthentication {

	/**
	 * Return a {@link Token}. This method can optionally log into Vault to obtain a
	 * {@link Token token}.
	 *
	 * @return a {@link Token}.
	 */
	Token login() throws TokenException;
}
