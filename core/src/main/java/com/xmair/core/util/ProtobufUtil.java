package com.xmair.core.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProtobufUtil{
    static final byte[] EMPTY_ARRAY = new byte[0];

    /**
     * 序列化对象
     *
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            return EMPTY_ARRAY;
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        return ProtobufIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate());
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param targetClz
     * @return
     */
    public static <T> T deserialize(byte[] bytes, Class<T> targetClz) {
        if (isEmpty(bytes)) {
            return null;
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClz);
        T message = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, message, schema);
        return message;
    }

    /**
     * 序列化列表
     *
     * @param list
     * @return
     */
    public static <T> byte[] serializeList(List<T> list) {

        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
        byte[] protostuff = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ProtostuffIOUtil.writeListTo(bos, list, schema, LinkedBuffer.allocate());
            protostuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象列表(" + list + ")发生异常!", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return protostuff;
    }

    /**
     * 反序列化列表
     *
     * @param byteArr
     * @param targetClass
     * @return
     * @throws IOException
     */
    public static <T> List<T> deserializeList(byte[] byteArr, Class<T> targetClass) {

        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        List<T> result = null;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(byteArr), schema);
        } catch (IOException e) {
            throw new RuntimeException("反序列化{" + targetClass + "}列表发生异常!");
        }
        return result;
    }

    private static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }


}
