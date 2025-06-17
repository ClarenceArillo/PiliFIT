package com.example.pilifitproject.utils;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

    public class ImageUtil {
        public static byte[] fileToBytes(File file) throws IOException {
            return Files.readAllBytes(file.toPath());
        }

        public static Image bytesToImage(byte[] imageData) {
            if (imageData == null || imageData.length == 0) {
                return null;
            }
            return new Image(new ByteArrayInputStream(imageData));
        }

        public static byte[] imageToBytes(Image image) throws IOException {
            throw new UnsupportedOperationException("Convert File to bytes first");
        }
    }





