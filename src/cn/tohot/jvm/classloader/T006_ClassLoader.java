package cn.tohot.jvm.classloader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class T006_ClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = null;
        try {
            classData = getClassData(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return defineClass(name, classData, 0, classData.length);
    }

    protected byte[] getClassData(String name) throws IOException {
        File f = new File("/home/allan/test", name.replace(".", "/").concat(".class"));
        try (FileInputStream fis = new FileInputStream(f); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            int b = 0;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            byte[] classData = baos.toByteArray();
            return classData;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ClassLoader l = new T006_ClassLoader();
        Class<?> clazz = l.loadClass("cn.tohot.jvm.classloader.Hello");
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("m");
        method.invoke(instance);
        System.out.println(clazz.getClassLoader());
        System.out.println(l.getClass().getClassLoader());
        System.out.println(l.getParent());
    }
}
