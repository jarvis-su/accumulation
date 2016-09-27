package like.ibatis;

import java.io.File;
import java.net.URL;

public class SqlSessionFactory {
	
	private Configuration configuration = new Configuration();

	public SqlSessionFactory(String path) {
		URL url = this.getClass().getClassLoader().getResource(path);
		File file = new File(url.getFile());
		File[] files = file.listFiles();
		for (File f : files) {
			
		}
	}

	public SqlSession getSession() {
		return new DefaultSqlSession(configuration);
	}
}
