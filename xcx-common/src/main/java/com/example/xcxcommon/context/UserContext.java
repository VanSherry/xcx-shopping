package com.example.xcxcommon.context;

public class UserContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setUser(Long userId){
        threadLocal.set(userId);
    }

    public static Long getUser(){
        return threadLocal.get();
    }

    public static void removerUser(){
        threadLocal.remove();
    }
}
