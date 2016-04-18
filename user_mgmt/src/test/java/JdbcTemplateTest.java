import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by Jarvis on 4/16/16.
 */
public class JdbcTemplateTest {
    private ApplicationContext applicationContext;

	@Test

    // 使用配置文件 测试

    public void demo2() {

        applicationContext = new ClassPathXmlApplicationContext(

                "applicationContext.xml");

        JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext

                .getBean("jdbcTemplate");


       // jdbcTemplate.execute("create table abcd(id int , name varchar(20))");
       List<?> list = jdbcTemplate.queryForList("SELECT * from users ");

    }
}
