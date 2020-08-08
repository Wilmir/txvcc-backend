package com.wilmir.txvcc.security;

import java.io.InputStream;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.wilmir.txvcc.exception.TxVccException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;



@Service
public class JWTProvider {

	private KeyStore keyStore;

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/txvcc.jks");
			keyStore.load(resourceAsStream,"password".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e)
		{
			throw new TxVccException("Exception occured while loading keystore");

		}
	}

	public String generateToken(Authentication authentication) {
		User principal = (User)authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.compact();
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey)keyStore.getKey("txvcc", "password".toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e)
		{
			throw new TxVccException("Exception occured while retrieving private key from keystore");

		}
	}

	public boolean validateToken(String jwt){
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}

	private PublicKey getPublicKey() {
		try {
			return (PublicKey)keyStore.getCertificate("txvcc").getPublicKey();
		} catch (KeyStoreException e)
		{
			throw new TxVccException("Exception occured while retrieving public key from keystore");

		}
	}

	public String getUsernamefromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}
}
