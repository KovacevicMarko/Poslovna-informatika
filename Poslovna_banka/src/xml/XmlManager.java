package xml;

import java.io.File;

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
	
	
	public static void generateDocument(NalogZaPlacanje nalog){
		
		 try {
	            JAXBContext context = JAXBContext.newInstance(NalogZaPlacanje.class);
	            Marshaller m = context.createMarshaller();
	            //for pretty-print XML in JAXB
	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            
	            // Write to System.out for debugging
	            // m.marshal(emp, System.out);

	            // Write to File
	         // postavljanje validacije
	    		// W3C sema
	    		SchemaFactory schemaFactory = SchemaFactory
	    				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    		// lokacija seme
	    		Schema schema = schemaFactory.newSchema(new File("./data/nalog.xsd"));
	            
	    		m.setSchema(schema);
	    		
	            m.marshal(nalog, new File("./data/nalozi/nalog"+nalog.getId()+".xml"));
	            
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        } catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
	}
	
	public static NalogZaPlacanje generateBean(File file) throws JAXBException, SAXException{
		
		// definisemo kontekst, tj. paket u kome se nalaze bean-ovi
				JAXBContext context = JAXBContext.newInstance("modelFromXsd");
				Unmarshaller unmarshaller = context.createUnmarshaller();

				// postavljanje validacije
				// W3C sema
				SchemaFactory schemaFactory = SchemaFactory
						.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				// lokacija seme
				Schema schema = schemaFactory.newSchema(new File("./data/nalog.xsd"));
				// setuje se sema
				unmarshaller.setSchema(schema);

				// parsira se XML i kreira objektni model
				NalogZaPlacanje nalog = (NalogZaPlacanje) unmarshaller.unmarshal(file);

				// obradi ucitane naloge
				//processPayment(nalog);
				return nalog;
		
	}
	
	
}
