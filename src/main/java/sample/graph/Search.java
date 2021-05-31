package sample.graph;

import java.util.ArrayList;
import java.util.LinkedList;

public class Search {
    private final String START;
    private final String END;
    private final LinkedList<String> visited = new LinkedList();
    private final ArrayList<LinkedList<String>> ways = new ArrayList<>();

    public Search(String START, String END) {
        this.START = START;
        this.END = END;
    }

    public ArrayList<LinkedList<String>> search(Graph graph) {
        visited.add(START);
        dfs(graph, visited);
        return ways;
    }

    public void dfs(Graph graph, LinkedList<String> visited) {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());

        for (String node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(END)) {
                visited.add(node);
                LinkedList<String> way = (LinkedList<String>) visited.clone();
                ways.add(way);
                visited.removeLast();
                break;
            }
        }
        for (String node : nodes) {
            if (visited.contains(node) || node.equals(END)) {
                continue;
            }
            visited.addLast(node);
            dfs(graph, visited);
            visited.removeLast();
        }
    }
}