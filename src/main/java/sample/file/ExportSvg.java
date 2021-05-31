package sample.file;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.*;

public class ExportSvg {
    public static void export(Map<String, LinkedHashSet<String>> map, ArrayList<LinkedList<String>> ways) {
        String path = "src/main/resources/ways/";
        DeleteFolder.delete(path);

        int count = 1;
        for (LinkedList<String> way : ways) {
            MutableGraph g = mutGraph("way_" + count).setDirected(true);
            for (Map.Entry<String, LinkedHashSet<String>> set : map.entrySet()) {
                String key = set.getKey();
                MutableNode mutableNode = mutNode(set.getKey());
                LinkedHashSet<String> nodes = set.getValue();

                for (String node : nodes) {
                    MutableNode child = mutNode(node);
                    if (way.indexOf(node) - way.indexOf(key) == 1 && way.contains(node) && way.contains(key)) {
                        mutableNode.add(Color.RED);
                        child.add(Color.RED);
                        mutableNode.addLink(to(child).with(Color.RED));
                    } else mutableNode.addLink(to(child));
                }
                g.add(mutableNode);
            }

            try {
                Graphviz.fromGraph(g).height(250 * map.size()).render(Format.PNG).toFile(new File(path + "way_" + count + ".png"));
            } catch (Exception e) {
                System.out.println("Can't export way!");
            }

            count++;
        }
    }
}
