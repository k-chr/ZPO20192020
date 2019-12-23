package Kamil215691.ZPO.LAB10.Zad1;

import javassist.*;
import javassist.bytecode.*;
import javassist.expr.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

public class PointExtension {
    public static void main(String[] var0) throws Exception{
//        BufferedInputStream fin
//                = new BufferedInputStream(new FileInputStream("Point.class"));
//        ClassFile cf = new ClassFile(new DataInputStream(fin));
        ClassPool pool = new ClassPool(true);
        pool.insertClassPath("D:\\Projects\\ZPO20192020");
        CtClass ctClass = pool.getCtClass("Point");
        CtMethod method = ctClass.getDeclaredMethod("move");
        method.insertAt(0, "{ int var = $1; $1 = $2; $2 = var;}");
        ctClass.writeFile(".");
        File f = new File("D:\\Projects\\ZPO20192020");
        ClassLoader loader = new URLClassLoader(new java.net.URL[]{f.toURI().toURL()});
        Class clazz = loader.loadClass("Point");
        Method m = clazz.getDeclaredMethod("main", String[].class);
        m.invoke(null, (Object) new String[]{"23", "234"});

    }
}