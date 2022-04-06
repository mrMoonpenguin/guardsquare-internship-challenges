import proguard.classfile.ClassPool;
import proguard.classfile.Clazz;
import proguard.classfile.Method;
import proguard.classfile.visitor.ClassPoolFiller;
import proguard.classfile.visitor.ClassPrinter;
import proguard.classfile.visitor.MethodCollector;
import proguard.io.*;
import proguard.util.ExtensionMatcher;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        String fn = args[0];

        ClassPool cp = collectClasses(fn);

        for (Clazz clazz : cp.classes()) {
            System.out.println(clazz.getName());

            List<String> methods = collectMethods(clazz);
            for (String s : methods) {
                System.out.printf("\t- %s\n", s);
            }
        }
    }

    /**
     *
     * @param fn filename of the jar to analyse
     * @return a `ClassPool` of all the classes in the given jar
     * @throws IOException
     */
    private static ClassPool collectClasses(String fn) throws IOException {

        ClassPool cp = new ClassPool();

        DataEntrySource source = new FileSource(
                new File(fn)
        );

        source.pumpDataEntries(
            new JarReader(true,
            new ClassFilter(
            new ClassReader(true, false, false, false, null,
            new ClassPoolFiller(cp)))));

        return cp;
    }

    /**
     *
     * @param clazz class to extract all methods from
     * @return a sorted list of Strings with all the method handles
     */
    private static List<String> collectMethods(Clazz clazz) {

        ArrayList<Method> methods = new ArrayList<>();

        clazz.methodsAccept(
                new MethodCollector(methods)
        );

        return methods.stream().map(o -> o.getName(clazz)).sorted().collect(Collectors.toList());
    }

}
