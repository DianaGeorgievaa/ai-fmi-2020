package bg.sofia.uni.fmi.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private List<Entity> data;
    private int attributeIndex;
    private String value;
    private boolean isLeaf;
    private Map<String, Node> children;

    public Node() {
        this.data = new ArrayList<>();
        this.children = new HashMap<>();
    }

    public List<Entity> getData() {
        return data;
    }

    public void setData(List<Entity> data) {
        this.data = data;
    }

    public int getAttributeIndex() {
        return attributeIndex;
    }

    public void setAttribute(int attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public Map<String, Node> getChildren() {
        return children;
    }

    public void setChildren(Map<String, Node> children) {
        this.children = children;
    }
}
