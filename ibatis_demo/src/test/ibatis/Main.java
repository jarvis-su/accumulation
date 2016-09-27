package test.ibatis;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		AddressDaoImpl addressDao = new AddressDaoImpl();
		List<Address> list = addressDao.list();
		System.out.println(list.get(0).getDescr());
	}
}
