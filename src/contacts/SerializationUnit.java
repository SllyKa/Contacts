package contacts;

import java.io.*;

public class SerializationUnit {
	    public static void serialize(Object obj, File file) throws IOException {
        if (file == null) {
            return ;
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }
 
    public static Object deserialize(File file) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
