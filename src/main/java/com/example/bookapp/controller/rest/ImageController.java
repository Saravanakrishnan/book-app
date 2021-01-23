package com.example.bookapp.controller.rest;

import com.example.bookapp.repo.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rest/image")
public class ImageController {
    
    public static final Logger logger = Logger.getLogger(ImageController.class.getName());
    
    @Autowired
    ImageRepository imageRepository;
    
    /**
     * @return - random image url
     */
    @GetMapping(value = {"", "/"}, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getRandomImage() throws IOException {
        String result = (String) imageRepository.findImage();
        
        
        try {
            InputStream inputStream = new URL(result).openStream();
            return IOUtils.toByteArray(inputStream);
//            File f = new File("src/main/resources/static/images/" + result);
//            if (f.exists()) {
//                InputStream in = getClass().getResourceAsStream(f.getAbsolutePath());
//                return IOUtils.toByteArray(in);
//            }
////            InputStream in = getClass().getResourceAsStream("src/main/resources/static/images/" + result);
//            InputStream in = getClass().getResourceAsStream("resources/static/images/a.jpg");
//            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            // Image is not found
            result = "";
        }
        return result.getBytes();
    }
}
