package com.miller.o2o.dto;

import com.miller.o2o.exceptions.ProductOperationException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by miller on 2019/3/19
 *  TODO 是否要用MultipartFile 做替换
 * @author Miller
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageHolder {

    private String imageName;

    private InputStream image;

    public static ImageHolder of(MultipartFile file) {
        if (file == null || file.isEmpty()){ return null;}
        try {
            return new ImageHolder(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            throw new ProductOperationException("转换ImageHolder出现异常" + e.getMessage());
        }
    }
}
