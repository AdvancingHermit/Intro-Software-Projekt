package com.snake.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//Made by Oliver
//Picks a random fruit, from the given fruits that are enabled, defaults to apple

public class FruitPicker {
    //20% chance for default
    static double defaultFruitTypeChance = 0.2;

    public static FruitType pickFruitType(FruitType defaultFruitType, FruitType... fruitTypes
    ) {
        if (new Random().nextDouble(1)<defaultFruitTypeChance){
            return defaultFruitType;
        }

        int sum=0;
        List<FruitType> fruitTypeList = Arrays.stream(fruitTypes).filter(fruitType -> fruitType.getChance()>0).collect(Collectors.toList());
        fruitTypeList = fruitTypeList.stream().filter(FruitType::isEnabled).collect(Collectors.toList());
        int totalWeight = fruitTypeList.stream().map(FruitType::getChance).reduce(0, Integer::sum);
        if (totalWeight <= 0){
            return defaultFruitType;
        }
        int random = new Random().nextInt(0, totalWeight) + 1;
        for (FruitType fruitType:fruitTypeList) {
            if (random>sum && random<(sum+fruitType.getChance())) {
                return fruitType;
            } else {
                sum+=fruitType.getChance();
            }
        }
        return defaultFruitType;
    }

}
