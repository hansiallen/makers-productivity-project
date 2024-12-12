package com.example.productivity.service;

import com.example.productivity.Config.CloudinaryConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryService {

    private final String cloudName;
    private final String apiKey;
    private final String apiSecret;

    private static final String CLOUDINARY_UPLOAD_URL = "https://api.cloudinary.com/v1_1/{cloudName}/image/upload";

    @Autowired
    public CloudinaryService(CloudinaryConfig cloudinaryConfig) {
        this.cloudName = cloudinaryConfig.getCloudName();
        this.apiKey = cloudinaryConfig.getApiKey();
        this.apiSecret = cloudinaryConfig.getApiSecret();
    }

    public String uploadImage(MultipartFile photo) {
        try {
            byte[] imageBytes = photo.getBytes();

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new org.springframework.core.io.ByteArrayResource(imageBytes) {
                @Override
                public String getFilename() {
                    return photo.getOriginalFilename();
                }
            });
            body.add("api_key", apiKey);
            body.add("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            body.add("signature", generateSignature());

            // Send POST request to Cloudinary API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    CLOUDINARY_UPLOAD_URL.replace("{cloudName}", cloudName),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            JSONObject jsonResponse = new JSONObject(response.getBody());

            if (jsonResponse.has("url") || jsonResponse.has("secure_url")) {
                return jsonResponse.getString("secure_url");
            } else {
                throw new RuntimeException("Image upload failed, no URL found in response.");
            }

        } catch (IOException e) {
            System.out.println("Error uploading image: " + e.getMessage());
            return null;
        }
    }


    private String generateSignature() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signatureString = "timestamp=" + timestamp;
        String stringToSign = signatureString + apiSecret;
        return DigestUtils.sha1Hex(stringToSign);
    }

}
