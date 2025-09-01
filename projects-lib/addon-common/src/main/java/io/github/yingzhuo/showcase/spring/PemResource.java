/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.spring;

import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("unchecked")
public class PemResource extends AbstractResource {

	private final @NonNull Resource delegatingResource;
	private final @NonNull PemContent pemContent;
	private final @Nullable String keypass;

	/**
	 * 构造方法
	 *
	 * @param delegatingResource 代理的资源
	 */
	public PemResource(Resource delegatingResource) {
		this(delegatingResource, null);
	}

	/**
	 * 构造方法
	 *
	 * @param delegatingResource 代理的资源
	 * @param keypass            私钥密码
	 */
	public PemResource(Resource delegatingResource, @Nullable String keypass) {
		this.delegatingResource = delegatingResource;
		this.keypass = keypass;

		try {
			this.pemContent = PemContent.of(delegatingResource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public String getDescription() {
		return this.delegatingResource.getDescription();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.delegatingResource.getInputStream();
	}

	@Nullable
	public String getKeypass() {
		return keypass;
	}

	public PemContent getPemContent() {
		return pemContent;
	}

	public List<X509Certificate> getCertificates() {
		return pemContent.getCertificates();
	}

	public X509Certificate getCertificate() {
		var certs = pemContent.getCertificates();
		if (certs.size() != 1) {
			throw new IllegalArgumentException("not unique certificate");
		}
		return certs.get(0);
	}

	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	public <T extends PrivateKey> T getPrivateKey() {
		return (T) pemContent.getPrivateKey(keypass);
	}

	public KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}
}
