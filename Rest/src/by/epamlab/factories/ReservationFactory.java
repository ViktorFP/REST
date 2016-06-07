package by.epamlab.factories;

import by.epamlab.beans.ifaces.IReservationDAO;
import by.epamlab.impl.ReservationImplXml;

public class ReservationFactory {
	public static IReservationDAO getClassFromFactory() {
		return new ReservationImplXml();
	}
}
