package com.barath.cyberark.vault;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Token class containing the fields and payload as part of authentication 
 * and having its own validitiy period.
 * 
 * @author barath.arivazhagan
 *
 */
public class Token {
	
	private static final ObjectMapper mapper = new ObjectMapper();
    private static final int DEFAULT_LIFESPAN_SECONDS = 8 * 60;
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            // tokens use dates like 2013-10-01 18:48:32 UTC
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZZ");
  
    private static class Fields {
      
        private String protectedText;
        private String payload;
        private String signature;
        private String expiration;
    }

    private static class Payload {
        
        private String data;      
        private String timestamp;
    }

    private static class ProtectedText {
        
        public String key;
    }

    private Fields fields;
    private Payload payload;
    private ProtectedText protectedText;
    private final String json;
    private DateTime timestamp;
    private DateTime expiration;

    public Token(String json){
        this.json = json;
    }

    private Fields fields(){
        if(fields == null){
            try {
				fields = mapper.readValue(json, Fields.class);
			} catch (IOException e) {				
				e.printStackTrace();
			}
        }
        return fields;
    }

    private Payload payload(){
        if(payload == null){
            try {
				payload = mapper.readValue(fromBase64(fields().payload), Payload.class);
			} catch (IOException e) {				
				e.printStackTrace();
			}
        }
        return payload;
    }

    private ProtectedText protectedText(){
        if(protectedText == null){
            try {
				protectedText =  mapper.readValue(fromBase64(fields().protectedText), ProtectedText.class);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
        return protectedText;
    }

    public String getData() {
		return payload().data;
	}
	
	public String getSignature() {
		return fields().signature;
	}

	public String getKey() {
		return protectedText().key;
	}

    public DateTime getTimestamp(){
        if(timestamp == null){
            timestamp = new DateTime((Long.parseLong(payload().timestamp) - 37) * 1000L);
        }
        return timestamp;
    }

    public DateTime getExpiration(){
        if(expiration == null){
            if(fields().expiration == null){
                expiration = getTimestamp().plusSeconds(DEFAULT_LIFESPAN_SECONDS);
            }else{
                expiration = DATE_TIME_FORMATTER.parseDateTime(fields().expiration);
            }
        }
        return expiration;
    }

    public boolean willExpireWithin(int seconds){
        return DateTime.now().plusSeconds(seconds).isAfter(getExpiration());
    }

    public boolean isExpired(){
        return willExpireWithin(0);
    }


    public String toString(){
        return toJson();
    }

	private String toJson(){
		return json;
	}

    public static Token fromJson(String json){
        return new Token(json);
    }

  


    private String fromBase64(String base64){
        return new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
    }

    private String toBase64(){
        // NB url safe mode *does not* work
		return Base64.getEncoder().encodeToString(toJson().getBytes());
	}

	public String toHeader(){
        String header = new StringBuilder()
                .append("Token token=\"")
                .append(toBase64())
                .append("\"").toString();       
        return header;
	}
}
