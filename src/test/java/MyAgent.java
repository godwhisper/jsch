import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * @author little whisper
 * @date 2021/1/4 17:52
 */
public class MyAgent {
    public MyAgent() {}
    public void agentmain(String args, Instrumentation inst) {
        File file = new File(args);
        byte[] targetBytes = new byte[(int) file.length()];
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            dataInputStream.readFully(targetBytes);
            dataInputStream.close();
            MyClassLoader classLoader = new MyClassLoader();
            Class targetClass = classLoader.findClass(targetBytes);
            Class oldClass = Class.forName(targetClass.getName());
            ClassDefinition classDefinition = new ClassDefinition(oldClass, targetBytes);
            inst.redefineClasses(classDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
