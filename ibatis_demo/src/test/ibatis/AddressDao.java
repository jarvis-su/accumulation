package test.ibatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class AddressDao {

	public List<Address> list() {
		SqlSession sqlSession = SessionFactory.getSqlSession();
		List<Address> list = sqlSession.selectList("address.selectAddressList");
		sqlSession.close();
		return list;
	}

	public Address selectOne(int i) {
		SqlSession session = SessionFactory.getSqlSession();
		Address address = (Address) session.selectOne("address.selectAddress",
				i);
		session.close();
		return address;
	}
}
