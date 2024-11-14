package cn.tohot.jvm.classloader;

import java.io.*;

public class T012_ClassReloading2 {
    private static class MyClassLoader extends ClassLoader {
        public MyClassLoader() {
            super(null);
        }
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            File f = new File("/home/allan/test", name.replace(".", "/").concat(".class"));
            if (!f.exists()) {
                return super.loadClass(name);
            }
            try(InputStream is = new FileInputStream(f)) {
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.loadClass("cn.tohot.jvm.classloader.Hello");

        myClassLoader = new MyClassLoader();
        Class<?> clazzNew = myClassLoader.loadClass("cn.tohot.jvm.classloader.Hello");

        System.out.println(clazz == clazzNew);
    }
}
