package com.miller.o2o.util;

import com.miller.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public class ImageUtil {
    private ImageUtil() {
    }

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final Random random = new Random();



    public static String generateThumbnail(ImageHolder imageHolder, String targetAddr) throws IOException {
        // 1.获取新文件名
        String realFileName = getRandomFileName();
        // 2.获取后缀名
        String extension = getFileExtension(imageHolder.getImageName());
        // 3.如果文件夹不存在，创建所有文件夹
        makeDirPath(targetAddr);
        // 4.实际存储路径
        String relativeAddr = targetAddr + realFileName + extension;

        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder imageHolder, String targetAddr) throws IOException {
        // 1.获取新文件名
        String realFileName = getRandomFileName();
        // 2.获取后缀名
        String extension = getFileExtension(imageHolder.getImageName());
        // 3.如果文件夹不存在，创建所有文件夹
        makeDirPath(targetAddr);
        // 4.实际存储路径
        String relativeAddr = targetAddr + realFileName + extension;

        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return relativeAddr;
    }


    /**
     * 创建目标路径所涉及到的目录，即/home/work/miller/xx.jpg
     * 那么 home work milller 这三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }



    /**
     * 获取输入文件扩展名
     * @param fileName 原文件名
     * @return 扩展名
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取一个文件随即名
     * @return 文件随即名
     */
    public static String getRandomFileName() {
        // 获取随机的五位数
        int rannum = random.nextInt(89999) + 10000;
        String nowTimeStr = format.format(new Date());
        return nowTimeStr + rannum;
    }


    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C:\\Users\\Miller\\Desktop\\2k壁纸\\wallhaven-716116.jpg"))
                .size(1000, 1000).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath + "/watermark.jpg")), 0.5f).outputQuality(0.8f)
                .toFile("C:\\Users\\Miller\\Desktop\\2k壁纸\\1.jpg");
    }

    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.deleteOnExit();
        }
    }

}
