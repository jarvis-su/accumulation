package sonar.response;

public class ClassB {
    private ClassA classA = new ClassA();

    public void doSomethingBasedOneClassA(){
        System.out.println (classA.toString());
    }

    public String toString(){
        return "classB";
    }
}
