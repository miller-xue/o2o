package com.miller.o2o.util;

/**
 * Created by miller on 2019/2/17
 *
 * @author Miller
 */
public class PathUtil {
    private PathUtil() {
    }

    private static String separator = System.getProperty("file.separator");

    /**
     * 获取图片存储的基础路径
     * @return
     */
    public static String getImgBasePath() {
        // 1.获取操作系统名称
        String os = System.getProperty("os.name");
        String basePath;
        // 2.根据操作系统设置基础路径
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/projectdev/image/";
        }else {
            basePath = "/home/miller/image/";
        }
        return basePath.replace("/", separator);
    }


    /**
     * 获取店铺图片存储路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }
}
