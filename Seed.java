package game;

import java.util.HashMap;
import java.util.Map;

public enum Seed {
      WHITE(1), 
      BLACK(2),
      INVALID(3),
      EMPTY(0);


    private final int code;

    Seed(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    private static final Map<Integer, Seed> typesByValue = new HashMap<Integer, Seed>();

    static {
        for (Seed type : Seed.values()) {
            typesByValue.put(type.code, type);
        }
    }
    public static Seed getType(int value) {
        return typesByValue.get(value);
    }
}