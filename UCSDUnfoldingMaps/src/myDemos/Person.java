package myDemos;

class Student extends Person{
	int id;
	public Student(String _name, int _id) {
		super(_name);
		this.id = _id;
	}
	
	@Override
	public String toString() {
		return super.toString() + ": " + this.id;
	}
}

class Faculty extends Person{
	String id;
	public Faculty(String _name, String _id) {
		super(_name);
		this.id = _id;
	}
	
	@Override
	public String toString() {
		return this.getName() + ": " + this.id;
	}
}

public class Person {
	private String name;
	public String getName() {
		return this.name;
	}
	public Person() {
		this("Default name");
	}
	
	public Person(String _name) {
		name = _name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static void main(String[] args) {
		Person[] p = new Person[3];
		p[0] = new Person("Tim");
		p[1] = new Student("Cara", 1234);
		p[2] = new Faculty("Mia", "ABCD");

		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i]);
		}
		Person p5 = new Student("ahmed", 123);
	}
}
class program{
	public static void main(String[] args) {
		Person[] p = new Person[3];
		p[0] = new Person("Tim");
		p[1] = new Student("Cara", 1234);
		p[2] = new Faculty("Mia", "ABCD");

		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i]);
		}
	}
}