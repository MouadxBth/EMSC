package me.espada.system.utils;

import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Utils {


    public static Set<Class<?>> getClasses(File jarFile, String packageName, Class<?> type) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile file = new JarFile(jarFile);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements(); ) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if (name.startsWith(packageName) && name.endsWith(".class")) {
                    Class<?> c = Class.forName(name.substring(0, name.length() - 6));
                    if(type.isAssignableFrom(c) && !c.equals(type))
                    classes.add(c);
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<File> getFilesFrom(String path) {
        List<File> files = new ArrayList<>();
        try {
             files = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        SecureRandom random = new SecureRandom();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static Object getField(Object object, String field) {
        Class<?> clazz = object.getClass();
        Field objectField = null;
        try {
            objectField = clazz.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        objectField.setAccessible(true);
        Object result = null;
        try {
            result = objectField.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        objectField.setAccessible(false);
        return result;
    }

    public static Predicate<Collection<?>> isEmpty = Collection::isEmpty;


}
