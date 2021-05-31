package sample.file;

import sample.graph.Graph;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    private final Graph graph = new Graph();

    public Graph readFile(String filePath) {
        try (FileReader fin = new FileReader(filePath)) {
            int data = fin.read();
            StringBuilder line = new StringBuilder();
            while (data != -1) {
                if (((char) data == '\n') || ((char) data == '\r')) {
                    insert(line.toString());
                    line.delete(0, line.length());
                    data = fin.read();
                    continue;
                }
                line.append((char) data);
                data = fin.read();
            }
            insert(line.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.graph;
    }

    private void insert(String lineData) {
        List<String> nodeNameStrings = Arrays.asList(lineData.split(" "));
        nodeNameStrings = nodeNameStrings.stream()
                .filter(nodeNameString -> !nodeNameString.equals(""))
                .collect(Collectors.toList());

        for (int i = 1; i < nodeNameStrings.size(); i++) {
            graph.addEdge(nodeNameStrings.get(0), nodeNameStrings.get(i));
        }
    }
}