package com.example.jakartaee.mapper;

import com.example.jakartaee.dto.FoodDto;
import com.example.jakartaee.entity.Food;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class Mapper {

    public List<FoodDto> map(List<Food> all) {  //lista av objekt
        return all.stream().map(FoodDto::new).toList();
    }

    public Food map(FoodDto food) {
        var f = new Food();
        f.setId( food.getId());
        f.setName(food.getName());
        f.setCategory(food.getCategory());
        f.setPrice(food.getPrice());
        return f;
    }

    public FoodDto map(Food food) {
        return new FoodDto(food);
    } //ett objekt


//    public Object map(Optional<Food> foodAsEntityObjekt) {
//    }
}


//beroende på input så overloadar den. Kompilatorn väljer metod beroende på data.