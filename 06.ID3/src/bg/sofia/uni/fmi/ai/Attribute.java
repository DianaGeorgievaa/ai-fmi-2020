package bg.sofia.uni.fmi.ai;

public enum Attribute {
    CLASS_NAME("class"),
    AGE("age"),
    MENOPAUSE("menopause"),
    TUMOR_SIZE("tumor-size"),
    INV_NODES("inv-nodes"),
    NODE_CAPS("node-caps"),
    DEG_MALIG("deg-malig"),
    BREAST("breast"),
    BREAST_QUAD("breast-quad"),
    IRRADIAT("irradiat");

    private final String name;

    Attribute(String name) {
        this.name = name;
    }
}
