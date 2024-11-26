package io.usnack.simplechat.util.binary;

import java.io.InputStream;
import java.util.UUID;

public interface BinaryStorage {
    UUID saveBinary(UUID key, byte[] bytes);
    String resolvePath(UUID key);
    InputStream loadBinary(String path);
}
