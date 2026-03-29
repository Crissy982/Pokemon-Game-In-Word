package Data;

import Entity.Ability.Ability;
import Entity.EXPGroup;
import Entity.Item;
import Entity.Move.Move;
import Entity.Type.Type;

import java.util.ArrayList;
import java.util.List;

public record SpeciesData(int id,
                          String speciesName,
                          Type type1,
                          Type type2,
                          int baseHP,
                          int baseAttack,
                          int baseDefense,
                          int baseSA,
                          int baseSD,
                          int baseSpeed,
                          int baseTotal,
                          int[] evDefeat,
                          int baseEXP,
                          EXPGroup expGroup,
                          List<Ability> abilityList,
                          boolean hasHidden,
                          Ability hiddenAbility,
                          List<Item> itemList,
                          GenderData genderData,
                          int baseFriendness,
                          boolean canEvolve,
                          int evolveLevel,
                          SpeciesData evolveToData,
                          List<Move> moveList
) {
}
