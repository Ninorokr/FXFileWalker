package com.silverlink.fxfilewalker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    TreeItem<String> folder;
    TreeItem<String> item;

    int level = -1;

    Image folderIcon = new Image(getClass().getResourceAsStream("img/folderIcon64.png"), 20, 20, false, false);
    Image fileIcon = new Image(getClass().getResourceAsStream("img/fileIcon128.png"), 20, 20, false, false);

    SimpleFileVisitor<Path> simp = new SimpleFileVisitor<>(){
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            folder = new TreeItem<>(getName(dir), new ImageView(folderIcon));
            level++;
            for(int i = 0; i <= level; i++) System.out.print("\t");
            System.out.println("\uD83D\uDCC1 " + getName(dir));

            switch(level){
                case 0: treeViewFolders.setRoot(folder); break;
                case 1: lvl0folder = folder; treeViewFolders.getRoot().getChildren().add(lvl0folder); break;
                case 2: lvl1folder = folder; lvl0folder.getChildren().add(lvl1folder); break;
            }

//            switch(level){
//                case 0: treeViewFolders.setRoot(new TreeItem<>(getName(dir), new ImageView(folderIcon))); break;
//                case 1: lvl0folder = new TreeItem<>(getName(dir), new ImageView(folderIcon)); treeViewFolders.getRoot().getChildren().add(lvl0folder); break;
//                case 2: lvl1folder = new TreeItem<>(getName(dir), new ImageView(folderIcon)); lvl0folder.getChildren().add(lvl1folder); break;
//            }


            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            item = new TreeItem<>(getName(file), new ImageView(fileIcon));

            for(int i = 0; i <= level; i++) System.out.print("\t");
            System.out.println("\uD83D\uDE0A " + getName(file));

            switch(level){
                case 0: treeViewFolders.getRoot().getChildren().add(item); break;
                case 1: lvl0folder.getChildren().add(item); break;
                case 2: lvl1folder.getChildren().add(item); break;
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
