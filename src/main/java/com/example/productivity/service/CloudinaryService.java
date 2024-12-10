package com.example.productivity.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.productivity.Config.CloudinaryConfig;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(CloudinaryConfig cloudinaryConfig) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryConfig.getCloudName(),
                "api_key", cloudinaryConfig.getApiKey(),
                "api_secret", cloudinaryConfig.getApiSecret()
        ));
    }

    public String uploadImage(MultipartFile photo) {
        try {
            BufferedImage originalImage = ImageIO.read(photo.getInputStream());
            BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, 800, 800);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            baos.flush();
            byte[] compressedImageBytes = baos.toByteArray();
            baos.close();

            Map<String, Object> uploadResult = cloudinary.uploader().upload(compressedImageBytes, ObjectUtils.emptyMap());

            return (String) uploadResult.get("url");
        } catch (Exception e) {
            System.out.println("Error uploading image: " + e.getMessage());
            return null;
        }
    }
}
