package bg.sofia.uni.fmi.ai.naive.bayes.classifier.emum;

public enum ClassType {
    DEMOCRAT("democrat"),
    REPUBLICAN("republican");

    private final String typeName;

    ClassType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
