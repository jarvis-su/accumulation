package test.ibatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionFactory {

	private static SqlSessionFactory sqlMapper;
	private static SqlSession sqlSession;
	static {
		try {
			String resource = "test/ibatis/dao-configuration.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SqlSession getSqlSession() {
		sqlSession = sqlMapper.openSession();
		return sqlSession;
	}

	public static void closeSqlSession() {
		if (sqlSession != null) {
			sqlSession.close();
		}
	}
}
