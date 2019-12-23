package Kamil215691.ZPO.LAB10.Zad2;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class FactorialExtensions {
    public static void main(String[] var0) throws Exception {
        ClassPool pool = new ClassPool(true);
        pool.insertClassPath("D:\\Projects\\ZPO20192020");
        CtClass ctClass = pool.getCtClass("Factorial");
        CtMethod[] methods = ctClass.getDeclaredMethods();
        CtMethod[] ctMethods = Arrays.stream(methods).filter(ctMethod -> ctMethod.getName().contains("fact")).toArray(CtMethod[]::new);
        for(CtMethod variable : ctMethods){
            final int[] counter = {0};
            variable.instrument(
                    new ExprEditor(){
                        @Override
                        public void edit(MethodCall m) throws CannotCompileException {
                            try {
                                CtClass[] arr1 = m.getMethod().getParameterTypes();
                                CtClass[] arr2 = variable.getParameterTypes();
                                boolean paramTypesEquality = Arrays.equals(arr1, arr2, Comparator.comparing(CtClass::getName));
                                if(m.getMethod().getName().equals(variable.getName()) && paramTypesEquality){
                                    ++counter[0];
                                }
                            } catch (NotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            CtClass exception = pool.getCtClass("java.lang.Exception");
            variable.setExceptionTypes(new CtClass[]{exception});
            variable.insertBefore("{ if( $1 < 0 ) throw new java.lang.IllegalArgumentException(); else if( $1 == 0 ) return 1; }");
            System.out.println("Method: " + variable.getName() + ( counter[0] > 0 ? " is recursive" : " is iterative" ));
        }

        ctClass.writeFile(".");
        File f = new File("D:\\Projects\\ZPO20192020");
        ClassLoader loader = new URLClassLoader(new java.net.URL[]{f.toURI().toURL()});

        Class clazz = loader.loadClass("Factorial");
        Method m = clazz.getDeclaredMethod("main", String[].class);
        m.invoke(null, (Object) new String[]{"23", "234"});
    }
}
