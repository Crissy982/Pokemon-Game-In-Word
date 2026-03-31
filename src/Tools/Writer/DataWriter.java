package Tools.Writer;

import java.io.IOException;

public interface DataWriter<T> {
    public void write(String path, T data) throws IOException;
}
