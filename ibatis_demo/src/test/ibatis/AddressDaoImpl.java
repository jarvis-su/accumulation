package test.ibatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class AddressDaoImpl {
	public List<Address> list() {
		SqlSession session = SessionFactory.getSqlSession();
		AddressMapper addressMapper = session.getMapper(AddressMapper.class);
		List<Address> list = addressMapper.list();
		session.close();
		return list;
	}
}
