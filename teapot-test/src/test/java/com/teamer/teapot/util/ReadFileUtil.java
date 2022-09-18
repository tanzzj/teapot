package com.teamer.teapot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author tanzj
 * @date 2022/9/17
 */
public class ReadFileUtil {

    /**
     * 用于从指定路径中读取资源，并转换为特定对象
     *
     * @param path          路径
     * @param typeReference 类型
     * @param <T>           类型
     * @return 指定类型的对象
     */
    public static <T> T readJsonFromResource(String path, TypeReference<T> typeReference) {

        try {
            String jsonString = StreamUtils.copyToString(
                new ClassPathResource(path).getInputStream(), Charset.defaultCharset()
            );
            return JSON.parseObject(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readStringFromResource(String fileName) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(fileName).getInputStream(), Charset.defaultCharset());
    }

}
