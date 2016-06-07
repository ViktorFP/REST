package by.epamlab.beans.ifaces;

import java.io.IOException;

import org.xml.sax.SAXException;

import by.epamlab.beans.reservations.Reservation;

public interface IRepositoryDAO {
	Reservation getReservation(String fileName) throws IOException, SAXException;
}
