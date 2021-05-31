package sample.file;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class ExportGraph {
    public static void export(Map<String, LinkedHashSet<String>> map, String formatOption) {
        MutableGraph g = mutGraph("graph png").setDirected(true);

        for (Map.Entry<String, LinkedHashSet<String>> set : map.entrySet()) {
            MutableNode mutableNode = mutNode(set.getKey());
            LinkedHashSet<String> nodes = set.getValue();
            for (String node : nodes) {
                mutableNode.addLink(mutNode(node));
            }
            g.add(mutableNode);
        }

        try {
            String path = "src/main/resources/export/";
            DeleteFolder.delete(path);
            if (formatOption.equals("png"))
                Graphviz.fromGraph(g).height(250 * map.size()).render(Format.PNG).toFile(new File(path + "graph.png"));
            else Graphviz.fromGraph(g).height(250 * map.size()).render(Format.PNG).toFile(new File(path + "graph.svg"));
        } catch (Exception e) {
            System.out.println("Can't export png!");
        }
    }
}
