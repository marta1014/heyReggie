package com.simple.reggie.common;

/**
 * 封装工具类 保存用户、获取用户
 *
 * 基于 ThreadLocal线程局部变量
 *
 * 同一个调用链上使用的是同一个线程 ThreadLocal 为每个线程提供独一份空间且具有隔离效果
 *
 * ThreadLocal 的set get方法
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
