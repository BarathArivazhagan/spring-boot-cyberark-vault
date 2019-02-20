package com.barath.cyberark.vault.authentication;

import com.barath.cyberark.vault.Token;
import com.barath.cyberark.vault.exception.TokenException;

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
