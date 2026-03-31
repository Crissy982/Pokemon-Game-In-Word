package Tools.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONDataWriter<T> implements DataWriter<T> {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void write(String path, T data) throws IOException {
        String className = data.getClass().getSimpleName();
        mapper.writeValue(new File(path), data);
    }


}
