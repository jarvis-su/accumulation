

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.Test;

/**
 * 获取*.properties配置文件中的内容 ，常见的两种方法：
 * 
 * @author 冰雨凌風
 * 
 */
public class ReadProperties {
	// 方法一
	@Test
	public void One() {
		// 获得资源包
		ResourceBundle bundle = ResourceBundle.getBundle("test");
		// 通过资源包拿到所有的名称
		Enumeration<String> allName = bundle.getKeys();
		// 遍历
		while (allName.hasMoreElements()) {
			// 获取每一个名称
			String name = allName.nextElement();
			// 利用已得到的名称通过资源包获得相应的值
			String value = bundle.getString(name);
			System.out.println(name + "=" + value);
		}
	}

	// 方法二
	@Test
	public void Two() throws Exception {
		// 获得类加载器，然后把文件作为一个流获取
		InputStream in = ReadProperties.class.getClassLoader()
				.getResourceAsStream("test.properties");
		// 创建Properties实例
		Properties prop = new Properties();
		// 将Properties和流关联
		prop.load(in);
		// 获取所有的名称
		Enumeration<?> allName = prop.propertyNames();
		// 遍历
		while (allName.hasMoreElements()) {
			// 获得每一个名称
			String name = (String) allName.nextElement();
			// 利用已得到的名称通过Properties对象获得相应的值
			String value = (String) prop.get(name);
			System.out.println(name + "=" + value);
		}
		// 关闭资源
		in.close();
	}
}
