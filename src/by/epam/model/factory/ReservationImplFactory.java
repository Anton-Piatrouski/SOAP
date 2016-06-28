package by.epam.model.factory;

import by.epam.model.iface.ReservationDAO;
import by.epam.model.impl.ReservationImplXml;

public class ReservationImplFactory {
	public static ReservationDAO getImplementation() {
		return new ReservationImplXml();
	}
}
