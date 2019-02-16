package com.barath.cyberark.vault;

import org.springframework.core.NestedRuntimeException;

/**
 * The Vault specific {@link NestedRuntimeException} implementation.
 *
 * @author barath.arivazhagan
 */
@SuppressWarnings("serial")
public class TokenException extends NestedRuntimeException {

	/**
	 * Create a {@code TokenException} with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public TokenException(String msg) {
		super(msg);
	}

	/**
	 * Create a {@code TokenException} with the specified detail message and nested
	 * exception.
	 *
	 * @param msg the detail message.
	 * @param cause the nested exception.
	 */
	public TokenException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
