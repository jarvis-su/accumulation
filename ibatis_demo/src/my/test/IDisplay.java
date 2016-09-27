package my.test;

public interface IDisplay extends IBase {

	@Text(text = "hello world")
	String get();
}
