package com.yyxnb.arch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Target指定Annotation用于修饰哪些程序元素。
@Target也包含一个名为”value“的成员变量，该value成员变量类型为ElementType[ ]，ElementType为枚举类型，值有如下几个：

ElementType.TYPE：能修饰类、接口或枚举类型
ElementType.FIELD：能修饰成员变量
ElementType.METHOD：能修饰方法
ElementType.PARAMETER：能修饰参数
ElementType.CONSTRUCTOR：能修饰构造器
ElementType.LOCAL_VARIABLE：能修饰局部变量
ElementType.ANNOTATION_TYPE：能修饰注解
ElementType.PACKAGE：能修饰包


RetentionPolicy.SOURCE	注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃
RetentionPolicy.CLASS	注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期
RetentionPolicy.RUNTIME	注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在

 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindFragment {

    int layoutRes() default 0;

    boolean subPage() default false;

    boolean fitsSystemWindows() default false;

    boolean statusBarTranslucent() default true;

    @BarStyle int statusBarStyle() default 0;

    int statusBarColor() default 0;

    @SwipeStyle int swipeBack() default 0;

    boolean needLogin() default false;

    int group() default -1;

}
