package org.homecloud.erp.authentication.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.homecloud.erp.authentication.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.security.KeyPair;
import java.util.Base64;


/**
 * Created by rene on 20.02.16.
 */
@Singleton
@Startup
public class KeyService {

  private KeyPair keyPair;

  @PostConstruct
  public void init(){
    if(keyPair == null)
      this.keyPair = RsaProvider.generateKeyPair(2048);
    System.out.println("private key is:\n"+Base64.getEncoder().encodeToString(this.keyPair.getPrivate().getEncoded()));
  }

  public String createJWT(User user) {
    return Jwts.builder()
        .setSubject(user.getId())
        .claim("email",user.getEmail())
        .claim("roles",user.getRoles())
        .claim("id",user.getId())
        .signWith(SignatureAlgorithm.RS256,keyPair.getPrivate())
        .compact();
  }

  public String getVerificationKey(){
    return Base64.getEncoder().encodeToString(this.keyPair.getPublic().getEncoded());
  }
}
