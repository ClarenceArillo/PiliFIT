package com.example.pilifitproject.controller;
import com.example.pilifitproject.model.ClothingItem;
import com.example.pilifitproject.utils.RandomFitGenerator;

import java.util.List;


public class GenerateController {
    List<ClothingItem> randomFit = RandomFitGenerator.generateRandomFit();
    /*
    for(ClothingItem item :randomFit){
        System.out.println(item.getName() + " - Category ID: " + item.getCategoryId());
        // Display image, name, etc. in your UI
    }

     */

}
