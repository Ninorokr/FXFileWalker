package com.silverlink.fxfilewalker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;

public class FileWalkerController implements Initializable {
    @FXML
    TreeView<String> treeViewFolders;
    TreeItem<String> lvl0folder;
    TreeItem<String> lvl1folder;
    TreeItem<String> lvl2folder;

    int level = -1;

//    Image folderIcon = new Image(getClass().getResourceAsStream("img/folderIcon64.png"), 10, 10, false, false);

    SimpleFileVisitor<Path> simp = new SimpleFileVisitor<>(){
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            level++;
            switch(level){
                case 0: treeViewFolders.setRoot(new TreeItem<>(getName(dir)));break;
                case 1: lvl0folder = new TreeItem<>(getName(dir)); treeViewFolders.getRoot().getChildren().add(lvl0folder); break;
                case 2: lvl1folder = new TreeItem<>(getName(dir)); lvl0folder.getChildren().add(lvl1folder); break;
            }

            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            TreeItem<String> item = new TreeItem<>(getName(file));
            switch(level){
                case 0:
            }
            return super.visitFile(file, attrs);
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return super.visitFileFailed(file, exc);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level--;
            return super.postVisitDirectory(dir, exc);
        }

        private String getName(Path path){
            return path.toString().substring(path.toString().lastIndexOf('\\')+1);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Files.walkFileTree(Path.of("D:\\root"), simp);
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
