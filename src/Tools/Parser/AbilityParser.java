package Tools.Parser;

import Data.AbilityData;
import Entity.Ability.Ability;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AbilityParser {
    private static final int RETRY_TIME = 3;
    private static final String CLIENT_ERROR_MSG = "CLIENT ERROR";
    //Get all information of all possible abilities for a pokemon species
    public static ArrayList<Ability> getPokemonAbilities (String url)
            throws IOException, InterruptedException {
        HttpResponse<String> httpResponse = APILoader.loadAPI(url);
        ArrayList<Ability> abilityList = new ArrayList<>();
        JSONObject root = new JSONObject(httpResponse.body());
        JSONArray abilities = root.getJSONArray("abilities");
        for (int i = 0; i < abilities.length(); i++) {
            JSONObject abilityObj = abilities.getJSONObject(i);
            abilityList.add(getSingleAbility(abilityObj.
                    getJSONObject("ability")
                    .getString("url")));
        }
        return abilityList;
    }

    public static List<Ability> getAllAbility() {
        List<Ability> allAbility = new ArrayList<>();
        for (int i = 1; ;i++) {
            try {
                Ability ability = getSingleAbility("https://pokeapi.co/api/v2/ability/" + i);
                if (ability == null)
                    break;
                allAbility.add(ability);
            }
            catch (IOException e) {
                continue;
            }
        }
        return allAbility;
    }

    // Get all information of an ability
    public static Ability getSingleAbility(String url) throws IOException {
        int retry = RETRY_TIME;
        // Load API and assign the return to httpResponse
        while (retry-- > 0) {
            try {
                HttpResponse<String> httpResponse = APILoader.loadAPI(url);
                int status = httpResponse.statusCode();
                // 404 NOT FOUND: null -> END
                if (status == 404)
                    return null;
                // REQUEST NOT SUCCESSFUL
                if (status != 200) {
                    if (status >= 400 && status <= 499)
                        throw new IOException(CLIENT_ERROR_MSG);
                    throw new IOException("HTTP ERROR: " + status);
                }
                return abilityParser(httpResponse);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("THREAD INTERRUPTED");
            }
            catch (IOException e) {
                if (e.getMessage().contains(CLIENT_ERROR_MSG)) {
                    System.out.print(CLIENT_ERROR_MSG);
                    throw e;
                    }
                if (retry == 0)
                    throw e;
                continue;
            }
        }
        throw new IOException("FAILED TO FETCH ABILITY AFTER RETRIES");
    }

    private static Ability abilityParser
            (HttpResponse<String> httpResponse) {
        // Declare a new JSONObject with httpResponse.body() as input
        JSONObject root = new JSONObject(httpResponse.body());
        int id = root.getInt("id");
        // Get String information of ability
        String abilityName = root.getString("name");
        String[] stringEffect = effectParser(root);
        String effect = stringEffect[0];
        String shortEffect = stringEffect[1];
        String description = descriptionParser(root);
        // Declare new AbilityData Object and return it
        AbilityData data = new AbilityData(id,
                abilityName,
                shortEffect,
                description,
                effect);
        return new Ability(data);
    }

    // Get String information of effects of an ability
    private static String[] effectParser(JSONObject root) {
        String[] stringsEffect = new String[2];
        JSONArray effectArr = root.getJSONArray("effect_entries");
        for(int i = 0; i < effectArr.length(); i++) {
            JSONObject effectObj = effectArr.getJSONObject(i);
            if (effectObj.getJSONObject("language")
                    .getString("name")
                    .equals("en")) {
                stringsEffect[0] = effectObj.getString("effect");
                stringsEffect[1] = effectObj.getString("short_effect");
                return stringsEffect;
            }
        }
        return new String[]{"",""};
    }

    // Get String information of description of an ability
    private static String descriptionParser(JSONObject root) {
        JSONArray flavorTextArr = root.getJSONArray("flavor_text_entries");
        for(int i = 0; i < flavorTextArr.length(); i++) {
            JSONObject flavorTextObj = flavorTextArr.getJSONObject(i);
            if (flavorTextObj
                    .getJSONObject("language")
                    .getString("name")
                    .equals("en")) {
                return flavorTextObj.getString("flavor_text");
            }
        }
        return "";
    }

    // Test the AbilityParser.class and all its methods
    public static void main(String[] args)
            throws IOException, InterruptedException {
        ArrayList<Ability> list =
                getPokemonAbilities("https://pokeapi.co/api/v2/pokemon/1");
    }
}
