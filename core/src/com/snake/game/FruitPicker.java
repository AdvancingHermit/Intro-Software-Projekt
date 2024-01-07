package com.snake.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


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
        int totalWeight = fruitTypeList.stream().map(FruitType::getChance).reduce(0, Integer::sum);
        int random = new Random().nextInt(0, totalWeight) + 1;
        for (FruitType fruitType:fruitTypes) {
            if (random>sum && random<(sum+fruitType.getChance())) {
                return fruitType;
            } else {
                sum+=fruitType.getChance();
            }
        }
        return defaultFruitType;
    }

}
