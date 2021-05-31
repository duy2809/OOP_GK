package sample.graph;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    private String start = "1";
    private String end = "1";
    private Map<String, LinkedHashSet<String>> map = new HashMap();

    public void addEdge(String node1, String node2) {
        int node1Number = Integer.parseInt(node1);
        int node2Number = Integer.parseInt(node2);
        int startNumber = Integer.parseInt(start);
        int endNumber = Integer.parseInt(end);

        if (node1Number < startNumber) start = node1;
        if (node2Number < startNumber) start = node2;
        if (node1Number > endNumber) end = node1;
        if (node2Number > endNumber) end = node2;

        LinkedHashSet<String> adjacent = map.get(node1);
        if (adjacent == null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    public LinkedList<String> adjacentNodes(String last) {
        LinkedHashSet<String> adjacent = map.get(last);
        if (adjacent == null) {
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);
    }

    public Map<String, LinkedHashSet<String>> getMap() {
        return map;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}