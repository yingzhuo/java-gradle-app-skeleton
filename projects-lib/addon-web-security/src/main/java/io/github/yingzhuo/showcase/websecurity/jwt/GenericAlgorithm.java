/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.X509Certificate;

public class GenericAlgorithm extends AbstractAlgorithm {

	private final X509Certificate certificate;
	private final PublicKey publicKey;
	private final PrivateKey privateKey;
	private final String sigAlgName;

	/**
	 * 构造方法
	 *
	 * @param pemContent pem文件内容
	 */
	public GenericAlgorithm(String pemContent) {
		this(pemContent, null);
	}

	/**
	 * 构造方法
	 *
	 * @param pemContent pem文件内容
	 * @param password   秘钥密码
	 */
	public GenericAlgorithm(String pemContent, @Nullable String password) {
		super("<no name>", "<no description>");
		var pc = PemContent.of(pemContent);
		this.certificate = pc.getCertificates().get(0);
		this.sigAlgName = this.certificate.getSigAlgName();
		this.publicKey = this.certificate.getPublicKey();
		this.privateKey = pc.getPrivateKey(password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		var newName = ALG_NAME_MAPPING.get(sigAlgName);
		return newName != null ? newName : sigAlgName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected byte[] doSign(byte[] data) throws Exception {
		var s = Signature.getInstance(sigAlgName);
		s.initSign(privateKey, new SecureRandom());
		s.update(data);
		return s.sign();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doVerify(byte[] data, byte[] signature) throws Exception {
		var s = Signature.getInstance(sigAlgName);
		s.initVerify(publicKey);
		s.update(data);
		if (!s.verify(signature)) {
			throw new SignatureVerificationException(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public X509Certificate getCertificate() {
		return this.certificate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

}
