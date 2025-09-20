/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.websecurity.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAlgorithm extends Algorithm {

	protected static final Map<String, String> ALG_NAME_MAPPING;

	static {
		Map<String, String> algNameMapping = new HashMap<>();
		algNameMapping.put("SHA256withRSA", "RS256");
		algNameMapping.put("SHA385withRSA", "RS384");
		algNameMapping.put("SHA512withRSA", "RS512");
		algNameMapping.put("SHA256withECDSA", "ES256");
		algNameMapping.put("SHA384withECDSA", "ES384");
		algNameMapping.put("SHA512withECDSA", "ES512");
		ALG_NAME_MAPPING = Collections.unmodifiableMap(algNameMapping);
	}

	/**
	 * 构造方法
	 *
	 * @param name 算法名称
	 */
	public AbstractAlgorithm(String name) {
		this(name, "<no description>");
	}

	/**
	 * 构造方法
	 *
	 * @param name        算法名称
	 * @param description 算法描述
	 */
	public AbstractAlgorithm(String name, String description) {
		super(name, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void verify(DecodedJWT jwt) throws SignatureVerificationException {

		// header + '.' + payload
		var data = String.join(".", jwt.getHeader(), jwt.getPayload())
			.getBytes(StandardCharsets.UTF_8);

		// 签名
		var signature = Base64.getUrlDecoder().decode(jwt.getSignature());

		try {
			doVerify(data, signature);
		} catch (SignatureVerificationException e) {
			throw e;
		} catch (Exception e) {
			throw new SignatureVerificationException(this, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final byte[] sign(byte[] bytes) throws SignatureGenerationException {
		try {
			return doSign(bytes);
		} catch (SignatureGenerationException e) {
			throw e;
		} catch (Exception e) {
			throw new SignatureGenerationException(this, e);
		}
	}

	/**
	 * 签名
	 *
	 * @param data 需要签名的数据
	 * @return 签名后的数据
	 */
	protected abstract byte[] doSign(byte[] data) throws Exception;

	/**
	 * 验证
	 *
	 * @param data      需要验证的部分
	 * @param signature 签名部分
	 */
	protected abstract void doVerify(byte[] data, byte[] signature) throws Exception;

	/**
	 * 获取证书
	 *
	 * @return 证书
	 */
	public abstract X509Certificate getCertificate();

	/**
	 * 获取公钥
	 *
	 * @return 公钥
	 */
	public abstract PublicKey getPublicKey();

	/**
	 * 获取私钥
	 *
	 * @return 私钥
	 */
	public abstract PrivateKey getPrivateKey();

	/**
	 * 获取密钥对
	 *
	 * @return 密钥对
	 */
	public final KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

}
