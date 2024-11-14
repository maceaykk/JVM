package cn.tohot.jvm.classloader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class T007_ClassLoaderWithEncryption extends T006_ClassLoader {
    public static int seed = 0B10110110;

    @Override
    protected byte[] getClassData(String name) throws IOException {
        {
            File f = new File("/home/allan/test", name.replace(".", "/").concat(".tohotclass"));
            try (FileInputStream fis = new FileInputStream(f);ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
                int b = 0;
                while ((b = fis.read()) != -1) {
                    baos.write(b ^ seed);
                }
                byte[] classData = baos.toByteArray();
                return classData;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void encFile(String name) {
        File f = new File("/home/allan/test", name.replace(".", "/").concat(".class"));
        File of = new File("/home/allan/test", name.replace(".", "/").concat(".tohotclass"));
        try (FileInputStream fis = new FileInputStream(f); FileOutputStream fos = new FileOutputStream(of)) {
            int b = 0;
            while ((b = fis.read())!= -1) {
                fos.write(b ^ seed);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String name = "cn.tohot.jvm.classloader.Hello";
        encFile(name);
        ClassLoader l = new T007_ClassLoaderWithEncryption();
        Class<?> clazz = l.loadClass(name);
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("m");
        method.invoke(instance);
        System.out.println(clazz.getClassLoader());
        System.out.println(l.getClass().getClassLoader());
        System.out.println(l.getParent());
        System.out.println(T007_ClassLoaderWithEncryption.getSystemClassLoader());
    }
}
