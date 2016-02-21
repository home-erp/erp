package org.homecloud.erp.authentication.facade;

import org.homecloud.erp.authentication.dto.TokenDTO;
import org.homecloud.erp.authentication.entity.User;
import org.homecloud.erp.authentication.service.AuthenticationService;
import org.homecloud.erp.authentication.service.KeyService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rene on 20.02.16.
 */
@Stateless
@Path("/oauth")
public class TokenFacade {

  @EJB
  private AuthenticationService authenticationService;

  @EJB
  private KeyService keyService;


  @POST
  @Path("/token")
  @Consumes(value = {"application/x-www-form-urlencoded"})
  @Produces(MediaType.APPLICATION_JSON)
  public TokenDTO getToken(@FormParam("email") String email, @FormParam("password") String password) {


    System.out.println("lookup user "+email+" with password: "+password);
    User user = authenticationService.authenticate(email,password);
    if (user == null) {
      System.out.println("User not found.");
      throw new NotFoundException();
    }

    return new TokenDTO(keyService.createJWT(user),"bearer");
  }

  @GET
  @Path("/public_key")
  public String  getKey(){
    return this.keyService.getVerificationKey();
  }
}
