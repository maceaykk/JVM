package cn.tohot.jvm.classloader;

public class T011_ClassReloading1 {
    public static void main(String[] args) throws Exception {
        T006_ClassLoader classLoader = new T006_ClassLoader();
        Class<?> clazz = classLoader.loadClass("cn.tohot.jvm.classloader.Hello");
        System.out.println(clazz.hashCode());

        Class<?> clazzNew = classLoader.loadClass("cn.tohot.jvm.classloader.Hello");
        System.out.println(clazzNew.hashCode());

        System.out.println(clazz == clazzNew);

        classLoader = null;

        classLoader = new T006_ClassLoader();
        clazzNew = classLoader.loadClass("cn.tohot.jvm.classloader.Hello");
        System.out.println(clazzNew.hashCode());

        System.out.println(clazz == clazzNew);

        classLoader = null;
        classLoader = new T006_ClassLoader();
        Class<?> clazz1 = classLoader.loadClass("cn.tohot.jvm.classloader.T002_ClassLoaderLevel");
        System.out.println(clazz1.hashCode());

        classLoader = null;
        classLoader = new T006_ClassLoader();
        Class<?> clazzNew1 = classLoader.loadClass("cn.tohot.jvm.classloader.T002_ClassLoaderLevel");
        System.out.println(clazzNew1.hashCode());

        System.out.println(clazz1 == clazzNew1);
    }
}
