package com.oliviatodesco.save_food.service;

import com.oliviatodesco.save_food.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {
    Image uploadImagen(MultipartFile file) throws IOException;
    void deleteImagen(Image image) throws IOException;
}
