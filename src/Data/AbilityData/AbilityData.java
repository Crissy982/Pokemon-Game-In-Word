package Data.AbilityData;

import Entity.Pokemon;

import java.util.HashMap;
public record AbilityData(
        int id,
        String name,
        String shortEffect,
        String description,
        String effect
) {
}

