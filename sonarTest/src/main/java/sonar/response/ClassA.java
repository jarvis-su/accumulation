package sonar.response;

public class ClassA {
    private ClassB classB = new ClassB();

    public void doSomething(){
        System.out.println ( "doSomething");
    }

    public void doSomethingBasedOnClassB(){
        System.out.println (classB.toString());
    }
}
