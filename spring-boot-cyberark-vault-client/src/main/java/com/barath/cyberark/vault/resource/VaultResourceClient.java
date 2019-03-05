package com.barath.cyberark.vault.resource;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.barath.cyberark.vault.Token;
import com.barath.cyberark.vault.VaultCredential;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class VaultResourceClient {
	
	public VaultCredential getVaultCredential() {
		return vaultCredential;
	}



	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();
	private final VaultCredential vaultCredential;
	private static final String SECRETS_URI="/secrets";
	private static final String TOKEN_URI="/authn/{account}/{user}/authenticate";
	
	
	public VaultResourceClient(VaultCredential vaultCredential) {
		super();
		this.vaultCredential = vaultCredential;
	}




	public Token getToken() {
		
		Map<String,String> uriVariables= new HashMap<>();
		uriVariables.put("account", vaultCredential.getAccount());
		uriVariables.put("user", vaultCredential.getUsername());
		ResponseEntity<String> responseEntity=this.restTemplate.postForEntity(getTokenUri(vaultCredential.getUrl()), vaultCredential.getPassword(), String.class, uriVariables);
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			logger.info("successful in retriveing the cyberark conjur token {}",responseEntity.getStatusCodeValue());
		}else {
			logger.error("failure in retriveing the cyberark conjur token");
			return null;
		}		
		return new Token(responseEntity.getBody());
		
	}
	

	public String getTokenUri(String vaultUri) {
		
		UriComponentsBuilder builder = UriComponentsBuilder
										.fromHttpUrl(vaultUri)
										.path(TOKEN_URI);
		
		if(logger.isInfoEnabled()) {
			logger.info("token uri formed {}",builder.toString());
		}
		return builder.encode().toUriString();						
	}
	
	
	public Map<String,Object> getSecrets(String appName){
		
		HttpHeaders headers= new HttpHeaders();
		Token token =this.getToken();
		headers.add("Authorization", token.toHeader());
		HttpEntity<Object> httpEntity= new HttpEntity<>(headers);
		String url =this.getSecretUri(vaultCredential.getUrl(),vaultCredential.getAccount(),vaultCredential.getVariableIds());
		logger.info("SECRET URI = {}",url);
		ResponseEntity<String> responseEntity=restTemplate.exchange(url,
				HttpMethod.GET,
				httpEntity,
				String.class);
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			logger.info("secrets success");
			try {
				return mapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, String>>(){});
			} catch (IOException e) {
				logger.error("exception in retrieving secrets {}",e.getMessage());
			}
		}else {
			logger.error("secrets failed");
			return null;
		}
		return null;
		
	}
	
	

	public String getSecretUri(String vaultUri, String account,String variableIds) {
		
		if(logger.isInfoEnabled()) {
			logger.info("variables to be retrieved {}",variableIds);
		}
		StringBuilder idBuilder = new StringBuilder();
		for(String variable : variableIds.split(",")) {
			idBuilder.append(account+":variable:"+variable+",");
		}
		UriComponentsBuilder builder = UriComponentsBuilder
										.fromHttpUrl(vaultUri)
										.path(SECRETS_URI)
										.queryParam("variable_ids", idBuilder.toString());
		
		if(logger.isInfoEnabled()) {
			logger.info("secret uri formed {}",builder.toUriString());
		}
		return builder.encode().toUriString();						
	}
	
	
	
	

}
