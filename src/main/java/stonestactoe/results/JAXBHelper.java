package stonestactoe.results;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;

public class JAXBHelper {

    /**
     * Serializes an object to XML. The output document is written in UTF-8 encoding.
     *
     * @param o the object to serialize
     * @param os the {@code OutputStream} to write to
     * @throws jakarta.xml.bind.JAXBException if any problem occurs during serialization
     */
    public static void toXML(Object o, OutputStream os) throws JAXBException {
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(o, os);
        } catch(JAXBException e) {
            throw e;
        }
    }

    /**
     * @param clazz the class of the object
     * @param is the {@code InputStream} to read from
     * @param <T> Type parameter accepts a generic type
     * @return the resulting object
     * @throws jakarta.xml.bind.JAXBException if any problem occurs during deserialization
     */
    public static <T> T fromXML(Class<T> clazz, InputStream is) throws JAXBException {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(is);
        } catch(JAXBException e) {
            try {
                is.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            throw e;
        }
    }
}
