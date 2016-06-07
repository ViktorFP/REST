package by.epamlab.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.epamlab.beans.reservations.AncillaryAirComponent;
import by.epamlab.beans.reservations.FareFamily;
import by.epamlab.beans.reservations.ResComponent;
import by.epamlab.beans.reservations.Reservation;
import by.epamlab.beans.reservations.customer.Customer;
import by.epamlab.beans.reservations.customer.Email;
import by.epamlab.beans.reservations.customer.Payment;
import by.epamlab.beans.reservations.customer.Phone;
import by.epamlab.beans.reservations.customer.Status;

public class DataHandler extends DefaultHandler {
	private Reservation reservation;
	private List<ResComponent> resComponents;
	private Customer customer;
	private FareFamily fareFamily;

	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		String key = qName.toUpperCase();

		if (DataEnum.names().contains(key)) {
			switch (DataEnum.valueOf(key)) {
			case RESERVATION:
				resComponents = new ArrayList<ResComponent>();
				final int CODE_IDX = 0,
				DESCRIPTION_IDX = 1;
				reservation = new Reservation(attrs.getValue(CODE_IDX),
						attrs.getValue(DESCRIPTION_IDX), resComponents);
				break;
			case RESCOMPONENT:
				resComponents
						.add(new ResComponent(attrs
								.getValue("ComponentTypeCode"), attrs
								.getValue("CreateDateTime"), attrs
								.getValue("InternalStatus"), attrs
								.getValue("Sequence")));
				break;
			case CUSTOMER:
				customer = new Customer(attrs.getValue("CustomerDocID"),
						attrs.getValue("FirstName"),
						attrs.getValue("LastName"), attrs.getValue("Sequence"));
				reservation.setCustomer(customer);
				break;
			case PAYMENT:
				customer.getPayments().add(
						new Payment(attrs.getValue("AmountPaid"), attrs
								.getValue("FormOfPaymentTypeCode"), attrs
								.getValue("CurrencyCode")));
				break;
			case EMAIL:
				customer.setEmail(new Email(attrs.getValue("Default"), attrs
						.getValue("EmailAddress"), attrs.getValue("EmailType"),
						attrs.getValue("Sequence"), Enum.valueOf(Status.class,
								attrs.getValue("SyncStatus"))));
				break;
			case PHONE:
				customer.setPhone(new Phone(attrs.getValue("Default"), attrs
						.getValue("PhoneNumber"), attrs.getValue("PhoneType"),
						attrs.getValue("Sequence"), attrs
								.getValue("SyncStatus")));
				break;
			case FAREFAMILY:
				fareFamily = new FareFamily(attrs.getValue("FareFamilyCode"),
						new ArrayList<AncillaryAirComponent>());
				reservation.setFareFamily(fareFamily);
				break;
			case ANCILLARYAIRCOMPONENT:
				fareFamily.getAncillaryAirComponent().add(
						new AncillaryAirComponent(attrs
								.getValue("AncillaryAirComponentCode")));
				break;
			}
		}
	}

	private static enum DataEnum {
		RESERVATION, RESCOMPONENT, CUSTOMER, FAREFAMILY, ANCILLARYAIRCOMPONENT, EMAIL, PHONE, PAYMENT;

		private static List<String> names = null;

		public static List<String> names() {
			if (names == null) {
				names = new ArrayList<String>();
				for (DataEnum data : DataEnum.values()) {
					names.add(data.name());
				}
			}
			return names;
		}
	}

	public Reservation getReservation() {
		return reservation;
	}

}
