package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import bg.sofia.uni.fmi.ai.naive.bayes.classifier.emum.ClassType;

public class Entity {
    public static final int ATTRIBUTES_SIZE = 16;

    private Boolean[] attributes;
    private ClassType classType;

    public static Entity parseEntity(String line) {
        String[] currentLine = line.split(",");
        Entity entity = new Entity();
        entity.setClassType(ClassType.valueOf(currentLine[0].toUpperCase()));
        entity.setAttributes(new Boolean[ATTRIBUTES_SIZE]);
        int index = 0;
        for (int i = 1; i < currentLine.length; i++) {
            if (currentLine[i].equals("n")) {
                entity.getAttributes()[index] = false;
            } else if (currentLine[i].equals("y")) {
                entity.getAttributes()[index] = true;
            } else {
                entity.getAttributes()[index] = null;
            }

            index++;
        }
        return entity;
    }

    public Boolean[] getAttributes() {
        return attributes;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public void setAttributes(Boolean[] attributes) {
        this.attributes = attributes;
    }
}