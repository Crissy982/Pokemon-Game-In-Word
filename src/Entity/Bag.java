package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bag {
    private List<Item> itemList;
    private Map<Item, Integer> numItemMap;
    private int countItem;

    public Bag(){
        itemList = new ArrayList<>();
        countItem = 0;
    }

    public List<Item> getItemList() {
        return new ArrayList<>(getItemList());
    }

    public Integer getNumItemMap(Item item) {
        return numItemMap.get(item);
    }

    public void addItem(Item item, Integer num) {
        // If the item is already in the itemList, just add the num to the
        // current num in numItemMap
        if (itemList.contains(item)) {
            numItemMap.put(item, numItemMap.get(item) + num);
        } else {
            itemList.add(item);
            numItemMap.put(item, num);
            countItem++;
        }
    }

    public Item getItem(int index) {
        return itemList.get(index);
    }

    public int getCountItem() {
        return countItem;
    }
    // TODO: Implement getter methods and refactor the itemList data structure
}
