package xml;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import modelFromXsd.NalogZaPlacanje;

public class XmlManager {

	public static void generateDocument(NalogZaPlacanje nalog) {

		try {
			
			JAXBContext context = JAXBContext.newInstance(NalogZaPlacanje.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("./data/nalog.xsd"));
			m.setSchema(schema);
			m.marshal(nalog, new File("./data/nalozi/nalog" + nalog.getId() + ".xml"));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static NalogZaPlacanje generateBean(File file) {
		NalogZaPlacanje nalog = new NalogZaPlacanje();
		
		try {

			JAXBContext context = JAXBContext.newInstance("modelFromXsd");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("./data/nalog.xsd"));
			unmarshaller.setSchema(schema);
			// parsira se XML i kreira objektni model
			nalog = (NalogZaPlacanje) unmarshaller.unmarshal(file);
			
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SAXException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} finally {
			return nalog;
		}
	}

}