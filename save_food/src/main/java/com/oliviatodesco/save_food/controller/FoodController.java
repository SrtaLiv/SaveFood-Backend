package com.oliviatodesco.save_food.controller;


import com.oliviatodesco.save_food.model.Food;
import com.oliviatodesco.save_food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
@CrossOrigin("http://localhost:4200/")
public class FoodController {
    @Autowired
    FoodService foodService;

    @PostMapping
    public ResponseEntity<Food> saveFood(@RequestPart("food") Food food, @RequestPart("file") MultipartFile file) {
        try {
            Food savedFood = foodService.save(food, file);
            return new ResponseEntity<>(savedFood, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<List<Food>> getFoods() {
        return new ResponseEntity<>(foodService.getFoods(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getById(@PathVariable Long id) {
        Optional<Food> foodOptional = foodService.getById(id);
        return foodOptional.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) throws IOException {
        Optional<Food> food = foodService.getById(id);
        if (food.isPresent()){
            foodService.delete(food.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Food> updateFood(@RequestBody Food food){
        try {
            Food savedFood = foodService.updateFood(food);
            return new ResponseEntity<>(savedFood, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Food> updateFoodImage(@PathVariable Long id,
                                                @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Food> food = foodService.getById(id);
        if (food.isPresent()) { //si existe
            Food updateFood = foodService.updateFoodImage(food.get(), file);
            return new ResponseEntity<>(updateFood, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}