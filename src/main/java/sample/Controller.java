package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import sample.file.ExportPng;
import sample.file.ExportSvg;
import sample.file.Reader;
import sample.graph.Graph;
import sample.graph.Search;

public class Controller {
    @FXML
    public ImageView myImageView;
    public TextField startText;
    public TextField endText;
    public Button findBtn;
    public Button exportPngBtn;
    public Button exportSvgBtn;
    public Button previousBtn;
    public Button nextBtn;
    public Label totalCaseLabel;
    public Label orderLabel;
    public Label pathLabel;

    public Reader reader;
    public Graph graph;
    public Map<String, LinkedHashSet<String>> map;
    public ArrayList<LinkedList<String>> ways;
    public Search search;
    public int orderNum;

    public void initialize(){
        findBtn.setDisable(true);
        exportPngBtn.setDisable(true);
        exportSvgBtn.setDisable(true);
        previousBtn.setVisible(false);
        nextBtn.setVisible(false);
    }

    public void handleChooseFile(ActionEvent event) throws IOException{
        System.out.println("Choosing file!");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File Dialog");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            reader = new Reader();
            graph = reader.readFile(file.getAbsolutePath());
            map = graph.getMap();
            ExportPng.export(map);

            findBtn.setDisable(false);
            exportPngBtn.setDisable(false);
            exportSvgBtn.setDisable(false);

            File file_img = new File("C:/Users/Duy/Desktop/OOP_GK/src/main/resources/export/graph.png");
            String localUrl = file_img.toURI().toString();
            Image image = new Image(localUrl);
            myImageView.setImage(image);
            startText.setText(graph.getStart());
            endText.setText(graph.getEnd());
        }
    }

    public void findWays(ActionEvent event) {
        search = new Search(startText.getText(), endText.getText());
        ways = search.search(graph);
        int totalCase = ways.size();
        if (totalCase > 0) {
            ExportSvg.export(map, ways);
            totalCaseLabel.setText("Total case: "+ totalCase);
            previousBtn.setVisible(true);
            nextBtn.setVisible(true);
            orderLabel.setText("1");
            File file_img_way = new File("C:/Users/Duy/Desktop/OOP_GK/src/main/resources/ways/way_1.png");
            String localUrl_way = file_img_way.toURI().toString();
            Image image_way = new Image(localUrl_way);
            myImageView.setImage(image_way);
            pathLabel.setText("PATH: " + ways.get(0));
        }
        else totalCaseLabel.setText("Can't find the way");
    }

    public void findNextWays(ActionEvent event) {
        orderNum = Integer.parseInt(orderLabel.getText());
        if (orderNum + 1 <= ways.size()){
            orderNum++;
            File file_img_way = new File("C:/Users/Duy/Desktop/OOP_GK/src/main/resources/ways/way_" + orderNum +".png");
            String localUrl_way = file_img_way.toURI().toString();
            Image image_way = new Image(localUrl_way);
            myImageView.setImage(image_way);
            pathLabel.setText("PATH: " + ways.get(orderNum - 1));
            orderLabel.setText(String.valueOf(orderNum));
        }
    }
    public void findPreviousWays(ActionEvent event) {
        orderNum = Integer.parseInt(orderLabel.getText());
        if (orderNum - 1 > 0){
            orderNum--;
            File file_img_way = new File("C:/Users/Duy/Desktop/OOP_GK/src/main/resources/ways/way_" + orderNum +".png");
            String localUrl_way = file_img_way.toURI().toString();
            Image image_way = new Image(localUrl_way);
            myImageView.setImage(image_way);
            pathLabel.setText("PATH: " + ways.get(orderNum - 1));
            orderLabel.setText(String.valueOf(orderNum));
        }
    }

    public void handleExit(ActionEvent event) {
        Platform.exit();
    }
}
