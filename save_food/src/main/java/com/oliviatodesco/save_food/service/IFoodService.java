package com.oliviatodesco.save_food.service;

import com.oliviatodesco.save_food.model.Food;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public interface IFoodService {
    Food save(Food food, MultipartFile file) throws IOException;
    ArrayList<Food> getFoods();
    Optional<Food> getById(Long id);
    void delete(Food food) throws IOException;
    Food updateFood(Food food);
    Food updateFoodImage(Food food, MultipartFile file) throws IOException;
}