package Tools.Writer;
import Data.AbilityData;
import Entity.Ability.Ability;
import Tools.Parser.AbilityParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONDataWriter<T> implements DataWriter<T> {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void write(String path, T data) throws IOException {
        String className = data.getClass().getSimpleName();
        mapper.writeValue(new File(path), data);
    }

    public static void main(String[] args) throws IOException {

    }
}
