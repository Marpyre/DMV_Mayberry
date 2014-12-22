package smarple1Mayberry.integration;

import java.io.IOException;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicCallbackHandler implements CallbackHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(BasicCallbackHandler.class);

	private String name;
	private char[] password;
	private String realm = "ApplicationRealm";

	private static CallbackHandler login;

	public BasicCallbackHandler() {
		logger.info("BasicCallbackHandler:constructor");
	}

	public BasicCallbackHandler(String name, String password) {
		this.name = name;
		this.password = password == null ? null : password.toCharArray();
	}

	public void handle(Callback[] callbacks)
			throws UnsupportedCallbackException, IOException {

		if (login != null && login != this) {
			login.handle(callbacks);
			return;
		}

		for (Callback cb : callbacks) {
			if (cb instanceof NameCallback) {
				((NameCallback) cb).setName(name);
			} else if (cb instanceof PasswordCallback) {
				((PasswordCallback) cb).setPassword(password);
			} else if (cb instanceof RealmCallback) {
				((RealmCallback) cb).setText(realm);
			} else {
				throw new UnsupportedCallbackException(cb);
			}
		}
	}

	public static void setLogin(CallbackHandler login) {
		BasicCallbackHandler.login = login;
	}

	public static CallbackHandler getLogin() {
		return login;
	}
}