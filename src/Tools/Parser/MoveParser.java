package Tools.Parser;

import Data.AbilityData;
import Data.MoveData;
import Data.StatChange;
import Entity.Move.DamageClass;
import Entity.Move.Move;
import Entity.Move.Stat;
import Entity.Move.Target;
import Entity.Type.Type;
import Tools.Writer.JSONDataWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MoveParser {
    private  static final int RETRY_TIME = 3;
    private static final String CLIENT_ERROR_MSG = "CLIENT ERROR";

    // Get all Moves available to a pokemon species
    public static List<MoveData> pokemonMovesParser(String url)
            throws InterruptedException, IOException {
        HttpResponse<String> httpResponse = APILoader.loadAPI(url);
        JSONObject root = new JSONObject(httpResponse.body());
        JSONArray movesArr = root.getJSONArray("moves");
        List<MoveData> moveAvailableList = new ArrayList<>();
        for (int i = 0; i < movesArr.length(); i ++) {
            JSONObject moveObj = movesArr.getJSONObject(i);
            moveAvailableList.add(moveParser(moveObj.getString("url")));
        }
        return moveAvailableList;
    }

    // Get information of a single Move
    public static MoveData moveParser(String url)
            throws IOException {
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
                MoveData result = getMoveData(httpResponse);
                if (result == null) {
                    break;
                }
                return result;
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

    public static MoveData getMoveData(HttpResponse<String> httpResponse) {
        JSONObject root = new JSONObject(httpResponse.body());
        System.out.print(root.optInt("accuracy") + "\n");
        String flavorText = null;
        JSONArray flavorTextArr = root.getJSONArray("flavor_text_entries");
        for (int i = 0; i < flavorTextArr.length(); i++) {
            JSONObject flavorTextObj = flavorTextArr.getJSONObject(i);
            if (flavorTextObj
                    .getJSONObject("language")
                    .getString("name")
                    .equals("en")) {
                if (flavorTextObj
                        .getJSONObject("version_group")
                        .getString("name")
                        .equals("sword-shield"))
                    flavorText = flavorTextObj
                            .getString("flavor_text");
            }
        }
        if (flavorText == null)
            return null;
        Integer accuracy = root.optInt("accuracy");
        DamageClass damageClass = DamageClass
                .valueOf(root
                        .getJSONObject("damage_class")
                        .getString("name")
                        .toUpperCase());
        Integer statusChance = root.optInt("effect_chance");
        JSONObject effect_Text = root.getJSONArray("effect_entries").optJSONObject(1);
        String effect;
        String shortEffect;
        if (effect_Text != null) {
            effect = effect_Text.optString("effect");
            shortEffect = effect_Text.optString("short_effect");
        }
        else {
            effect = null;
            shortEffect = null;
        }

        int id = root.getInt("id");
        String name = root.getString("name");
        int power = root.optInt("power");
        int pp = root.getInt("pp");
        int priority = root.getInt("priority");
        JSONArray statChanges = root.getJSONArray("stat_changes");
        ArrayList<StatChange> statChangeArrayList = new ArrayList<StatChange>();
        for (int i = 0; i < statChanges.length(); i++) {
            JSONObject statChangeObj = statChanges.getJSONObject(i);
            int change = statChangeObj.getInt("change");
            Stat statName = Stat.valueOf(statChangeObj
                    .getJSONObject("stat")
                    .getString("name")
                    .toUpperCase()
                    .replace("-","_"));
            statChangeArrayList.add(new StatChange(statName, change));
        }
        Target target = Target.valueOf(root
                .getJSONObject("target")
                .getString("name").replace("-","_")
                .toUpperCase());
        Type type = Type.valueOf(root
                .getJSONObject("type")
                .getString("name")
                .toUpperCase());
        MoveData data = new MoveData(
                accuracy,
                damageClass,
                statusChance,
                effect,
                shortEffect,
                flavorText,
                id,
                name,
                power,
                pp,
                priority,
                statChangeArrayList,
                target,
                type);
        return data;
    }

    public static List<MoveData> getAllMoves() {
        List<MoveData> data = new ArrayList<>();
        // System.out.print("Step 1 run\n");
        for (int i = 1; ;i++) {
            // System.out.print("Step 2 run\n");
            try {
                MoveData moveData = moveParser("https://pokeapi.co/api/v2/move/" + i);
                // System.out.print("Step 3 run\n");
                if (moveData == null)
                    break;
                data.add(moveData);
            } catch (IOException e) {
                continue;
            }
        }
        return data;
    }

    public static void main(String[] args)
            throws IOException, InterruptedException {
        JSONDataWriter<List<MoveData>> writer = new JSONDataWriter<>();
        writer.write("resources/moves.json", getAllMoves());
    }
}
