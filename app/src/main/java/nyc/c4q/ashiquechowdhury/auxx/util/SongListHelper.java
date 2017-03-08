package nyc.c4q.ashiquechowdhury.auxx.util;

import java.util.ArrayList;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

/**
 * Created by shawnspeaks on 3/7/17.
 */

public class SongListHelper {

    public static ArrayList<Item> searchFragmentSongItemList = new ArrayList<>();

    public static ArrayList<Item> getSearchFragmentSongItemList() {
        return searchFragmentSongItemList;
    }

    public static void addToList(Item item){
        searchFragmentSongItemList.add(item);
    }
}
