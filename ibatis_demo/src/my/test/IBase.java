package my.test;

import java.util.List;

public interface IBase {
	@Text(text = "well~")
	String say(List<?> o);
}
