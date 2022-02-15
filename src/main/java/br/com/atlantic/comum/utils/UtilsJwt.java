package br.com.atlantic.comum.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import br.com.esocial.comum.dao.daogenerico.ValoresParametrosSistema;
import br.com.esocial.comum.dao.daogenerico.ValoresParametrosSistemaDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe para tratar da integracao JWT entre sistemas
 * @author Tony Caravana Campos
 * @version 1.00
 */

public class UtilsJwt {
	
    private static Log log = new Log("UtilsJwt");
    
    private final static String COD_NUM_INT_JWT_RECAD_BENF              = "1"; 
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_ISSUER     = "INT_JWT_AUTH_ISSUER";
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_SUBJECT    = "INT_JWT_AUTH_SUBJECT";
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_EXPIRATION = "INT_JWT_AUTH_EXPIRATION";
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_ALGORITHM  = "INT_JWT_AUTH_ALGORITHM";
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_SECRET_KEY = "INT_JWT_AUTH_SECRET_KEY";
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_URL        = "INT_JWT_AUTH_URL";
    private final static String COD_NUM_INT_JWT_COD_PAR_AUTH_REDIRECT   = "INT_JWT_AUTH_ALLOW_REDIRECT";

    private static Map<String, String> getJWTParams() throws Exception {
    	return getJWTParams(COD_NUM_INT_JWT_RECAD_BENF);
    }
    
    private static Map<String, String> getJWTParams(String _COD_NUM) throws Exception {

		ValoresParametrosSistemaDAO dao = new ValoresParametrosSistemaDAO();
    	ValoresParametrosSistema    param;
    	Map<String, String>         map = new HashMap<String, String>();
    	
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_ISSUER);
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_ISSUER, param != null ? param.getValPar() : "");
    	
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_SUBJECT);
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_SUBJECT, param != null ? param.getValPar() : "");
		
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_EXPIRATION);
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_EXPIRATION, param != null ? param.getValPar() : "");
		
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_ALGORITHM);
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_ALGORITHM, param != null ? param.getValPar() : "");
		
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_SECRET_KEY);
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_SECRET_KEY, param != null ? param.getValPar() : "");
		
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_URL);
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_URL, param != null ? param.getValPar() : "");
		
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_REDIRECT);		
		map.put(COD_NUM_INT_JWT_COD_PAR_AUTH_REDIRECT, param != null ? param.getValPar() : "");		
    	
    	return map;
    }   
    
    private static String getJWTParamValue(String _COD_NUM, String _COD_PARAM) throws Exception {
		ValoresParametrosSistemaDAO    dao = new ValoresParametrosSistemaDAO();
    	ValoresParametrosSistema     param;
    	String                     retorno = "";
		param = dao.findByCodNumAndCodPar(_COD_NUM, _COD_PARAM);
    	return param != null ? param.getValPar() : "";
    }
    
    public static String generateJWTToken(String userId) throws Exception {

    	Map<String, String> configMap = getJWTParams();    	
    	
		//The JWT signature algorithm we will be using to sign the token
    	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(configMap.get(COD_NUM_INT_JWT_COD_PAR_AUTH_SECRET_KEY));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		 String data =  ("{ \"codIdeCli\":  \""+userId+"\" }");

		JwtBuilder builder = Jwts.builder().setId("1L")
				.setIssuedAt(now)
				.setSubject (configMap.get(COD_NUM_INT_JWT_COD_PAR_AUTH_SUBJECT))
				.setIssuer  (configMap.get(COD_NUM_INT_JWT_COD_PAR_AUTH_ISSUER))
				.claim      ("data", data)
				.signWith   (signatureAlgorithm, signingKey);
		
		
		String ttlMillisStr = configMap.get(COD_NUM_INT_JWT_COD_PAR_AUTH_EXPIRATION); 
		long ttlMillis = (!Utils.trim(ttlMillisStr).replaceAll("^[0-9]", "").equals("") ? new Long(Utils.trim(ttlMillisStr).replaceAll("^[0-9]", "")) : -1);
		
		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
		
		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}   

    
    public static Claims decodeJWTToken(String token) throws MsgExcept, Exception {
    	Map<String, String> configMap = getJWTParams();
    	
    	Claims claims = null;
    	try {
        	claims = Jwts.parser().setSigningKey(configMap.get(COD_NUM_INT_JWT_COD_PAR_AUTH_SECRET_KEY)).parseClaimsJws(token).getBody();    	
    	
    	} catch (ExpiredJwtException e) {
    		e.printStackTrace();
    		throw new MsgExcept("Ocorreu um erro na tentativa de recuperar informa��es ao sistema. Contate o Administrador (JWT Expired Error).");
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new MsgExcept("Ocorreu um erro na tentativa de recuperar informa��es ao sistema. Contate o Administrador (JWT Error).");
    	}
    	return claims;
	}
    
    public static String getDataFromDecodeJWTToken(String token) throws MsgExcept, Exception {
    	Claims claims = decodeJWTToken(token);
    	String data   = "";
    	if (claims != null) {
    		data          = (String) claims.get("data");
    	}
    	return data;    	
    }
    
    public static String getURLToRecadastramentoWithToken(String userId) throws Exception {
    	String urlWithToken = getURL(COD_NUM_INT_JWT_RECAD_BENF) + "?token="+generateJWTToken(userId); //http://187.110.41.2:7001/recadastramento/jwt/index?token="+generateJWTToken(contexto.getCpfUsuario());
    	return urlWithToken;
    }    
    
    public static String getURLToRecadastramento() throws Exception {
    	return getURL(COD_NUM_INT_JWT_RECAD_BENF);			
    }
    
    public static boolean isPermissionedToRedirectByJWT() throws Exception {
    	return getJWTParamValue(COD_NUM_INT_JWT_RECAD_BENF, COD_NUM_INT_JWT_COD_PAR_AUTH_REDIRECT).equals("YES") ? true : false;			
    }    
    
    private static String getURL(String _COD_NUM) throws Exception {
		ValoresParametrosSistemaDAO dao = new ValoresParametrosSistemaDAO();
    	ValoresParametrosSistema    param;
		param = dao.findByCodNumAndCodPar(_COD_NUM, COD_NUM_INT_JWT_COD_PAR_AUTH_URL);		
    	return (param != null ? param.getValPar() : "");
	}   
    
}