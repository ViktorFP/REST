package by.epamlab.factories;

import by.epamlab.beans.ifaces.IRepositoryDAO;
import by.epamlab.impl.RepositoryImplHard;

public class RepositoryFactory {
	public static IRepositoryDAO getRepository() {
		return new RepositoryImplHard();
	}
}
