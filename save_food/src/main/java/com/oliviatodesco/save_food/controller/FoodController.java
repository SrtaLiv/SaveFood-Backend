package com.oliviatodesco.save_food.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliviatodesco.save_food.model.Food;
import com.oliviatodesco.save_food.model.Image;
import com.oliviatodesco.save_food.model.UserSec;
import com.oliviatodesco.save_food.service.FoodService;
import com.oliviatodesco.save_food.service.IUserSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
@CrossOrigin(origins = "http://localhost:4200")
public class FoodController  {
    @Autowired
    FoodService foodService;

    @Autowired
    private IUserSecService userSecService;

   /* @PostMapping
    public ResponseEntity<Food> saveFood(@RequestPart("food") Food food, @RequestPart("file")MultipartFile file) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UserSec> userOpt = userSecService.findByEmail(username);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build();
            }

            Food saved = foodService.save(food, file);
            return new ResponseEntity<>(saved, HttpStatus.OK);
            //return ResponseEntity.status(HttpStatus.CREATED).body(savedFood);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

    /*
   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> saveFood(@RequestPart("food") String foodJson,
                                     @RequestPart("file") MultipartFile file) {
       System.out.println("Food JSON recibido: " + foodJson);
       return ResponseEntity.ok("Recibido");
   }
*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveFood(@RequestPart("food") String foodJson,
                                      @RequestPart("file") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Food food = objectMapper.readValue(foodJson, Food.class);

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UserSec> userOpt = userSecService.findByEmail(username);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Asociar el usuario manualmente al objeto Food
            food.setUsuario(userOpt.get());

            Food saved = foodService.save(food, file);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // te da más detalles en consola
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la comida");
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