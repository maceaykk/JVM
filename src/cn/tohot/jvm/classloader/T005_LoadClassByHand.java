package cn.tohot.jvm.classloader;

public class T005_LoadClassByHand {
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz = T005_LoadClassByHand.class.getClassLoader().loadClass("cn.tohot.jvm.classloader.T002_ClassLoaderLevel");
        System.out.println(clazz.getName());
    }
}
