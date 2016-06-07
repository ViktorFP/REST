package by.epamlab.impl;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.epamlab.beans.ifaces.IReservationDAO;
import by.epamlab.beans.reservations.Reservation;
import by.epamlab.sax.DataHandler;

public class ReservationImplXml implements IReservationDAO{

	@Override
	public Reservation getReservation(String fileName) throws IOException, SAXException {
		XMLReader reader = XMLReaderFactory.createXMLReader();		
			DataHandler dataHandler = new DataHandler();
		reader.setContentHandler(dataHandler);
		reader.parse(this.getClass().getClassLoader().getResource("").getPath()+fileName+".xml");	
		return dataHandler.getReservation();
	}
	
}
