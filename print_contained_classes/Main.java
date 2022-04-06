import proguard.classfile.ClassPool;
import proguard.classfile.visitor.ClassPoolFiller;
import proguard.io.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    /**
     * Prints all the classes contained within the given jar file to the standard output.
     */
    public static void main(String[] args) throws IOException {

        String fn = args[0];

        ClassPool cp = collectClasses(fn);

        for (Iterator<String> it = cp.classNames(); it.hasNext(); ) {
            String cn = it.next();
            System.out.println(cn);
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

}
