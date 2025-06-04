package com.example.pilifitproject.utils;

import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

    public class ImageUtil {

            // Base directory for images
            private static final Path BASE_IMAGE_DIR = Paths.get("src", "main", "resources", "com", "example", "pilifitproject", "images");

            /**
             * Loads a JavaFX Image from a given path.
             */
            public static Image loadImage(String path) {
                try {
                    if (path != null && !path.isEmpty()) {
                        File file = new File(path);
                        if (file.exists()) {
                            return new Image(file.toURI().toString());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error loading image: " + e.getMessage());
                }
                return null;
            }

            /**
             * Saves an image file to the specified category folder.
             *
             * @param sourceFile The image file to save.
             * @param category   The clothing category ("tops", "bottom", "footwear").
             * @return The relative path to the saved image.
             * @throws IOException If saving fails.
             */
            public static String saveImage(File sourceFile, String category) throws IOException {
                if (sourceFile == null || !sourceFile.exists()) {
                    throw new IllegalArgumentException("Source file is null or does not exist.");
                }

                if (category == null || category.isBlank()) {
                    throw new IllegalArgumentException("Category cannot be null or blank.");
                }

                // Create directory path: images/category/
                Path categoryDir = BASE_IMAGE_DIR.resolve(category);
                if (!Files.exists(categoryDir)) {
                    Files.createDirectories(categoryDir);
                }

                // Generate unique name to avoid overwriting
                String fileName = System.currentTimeMillis() + "_" + sourceFile.getName();
                Path targetPath = categoryDir.resolve(fileName);

                Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                // Return relative path to be stored in the database
                return BASE_IMAGE_DIR.relativize(targetPath).toString().replace("\\", "/");
            }

            /**
             * Deletes an image file given its relative path from the image base directory.
             *
             * @param relativePath The relative path to the image (e.g., "tops/T1.png")
             * @return true if deleted, false otherwise.
             */
            public static boolean deleteImage(String relativePath) {
                if (relativePath == null || relativePath.trim().isEmpty()) {
                    return false;
                }

                try {
                    Path fullPath = BASE_IMAGE_DIR.resolve(relativePath);
                    return Files.deleteIfExists(fullPath);
                } catch (IOException e) {
                    System.err.println("Error deleting image: " + e.getMessage());
                    return false;
                }
            }
        }




