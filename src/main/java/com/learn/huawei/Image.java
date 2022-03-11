package com.learn.huawei;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.Map;
import java.util.Random;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 2021-03-01
 * Time: 19:04
 * Description:
 */
public class Image {

    public static void main(String[] args) throws FileNotFoundException {
        /**
         * 1.
         */
        Integer width = 100;
        Integer height = 100;

        BufferedImage bufferedImage = new BufferedImage(width, height, TYPE_INT_RGB);
        Color color;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col ++) {
                int r = ran();
                int g = ran();
                int b = ran();
                color = new Color(r,g,b);
                bufferedImage.setRGB(row, col, color.getRGB());
            }
        }
        File file1 = new File("/Users/cheng/Desktop/1.jpg");
        try {
            ImageIO.write(bufferedImage, "jpg", file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 2.
         */
        File fileTemp = new File("/Users/cheng/Desktop/1.jpg");
        try {
            BufferedImage bufferedImage2 = ImageIO.read(fileTemp);
            int temp = 500;
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < height; col++) {
                    if (check()) {
                        bufferedImage2.setRGB(row, col, new Color(0,0,0).getRGB());
                        temp --;
                    }
                }
            }
            File file2 = new File("/Users/cheng/Desktop/2.jpg");
            try {
                ImageIO.write(bufferedImage2, "jpg", file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }

        /**
         * 3.
         */
        try {
            File file2Temp = new File("/Users/cheng/Desktop/2.jpg");
            BufferedImage bufferedImage3 = ImageIO.read(file2Temp);
            ImageIO.write(bufferedImage3, "jpg", file2Temp);
            Color colr = new Color(0,0,0);
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < height; col++) {

                    if (bufferedImage3.getRGB(row, col) == colr.getRGB()) {
                        // 还原,todo 越界
                        bufferedImage3.setRGB(row, col, bufferedImage3.getRGB(row-1, col));
                    }

                }
            }
            File file3 = new File("/Users/cheng/Desktop/3.jpg");
            ImageIO.write(bufferedImage3, "jpg", file3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean check() {
        Random random = new Random();
        int temp = random.nextInt(100);
        if (temp >= 5) {
            return false;
        } else {
            return true;
        }

    }

    private static int ran() {
        Random random = new Random();
        return random.nextInt(256);
    }

}
