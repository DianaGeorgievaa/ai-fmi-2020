package bg.sofia.uni.fmi.ai;

import java.util.*;

public class Entity {
    private List<String> values;

    public static Entity parseEntity(String line) {
        String[] currentLine = line.split(",");
        Entity entity = new Entity();
        List<String> values = new ArrayList<>();
        for (String s : currentLine) {
            if (s.equals("?")) {
               return null;
            }
            values.add(s);
        }
        entity.setValues(values);
        return entity;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
