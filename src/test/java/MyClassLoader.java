/**
 * @author little whisper
 * @date 2021/1/4 17:53
 */
public class MyClassLoader extends ClassLoader {
    public Class<?> findClass(byte[] b) {
        return this.defineClass(null, b, 0, b.length);
    }
}
