package like.ibatis;

public interface SqlSession {

	<T> T getMapper(Class<T> clz);
}
