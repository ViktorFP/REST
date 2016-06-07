package by.epamlab.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.SAXException;

import by.epamlab.beans.ifaces.IRepositoryDAO;
import by.epamlab.beans.ifaces.IReservationDAO;
import by.epamlab.beans.reservations.Reservation;
import by.epamlab.factories.ReservationFactory;

public class RepositoryImplHard implements IRepositoryDAO {
	private static Map<String, Reservation> repository = new HashMap<>();

	@Override
	public Reservation getReservation(String fileName) throws IOException, SAXException {
		Reservation reservation = null;
		if (repository.containsKey(fileName)) {
			reservation = repository.get(fileName);
		} else {
			IReservationDAO reservationDAO = ReservationFactory.getClassFromFactory();
			reservation = reservationDAO.getReservation(fileName);
			repository.put(fileName, reservation);
		}
		return reservation;
	}
}
