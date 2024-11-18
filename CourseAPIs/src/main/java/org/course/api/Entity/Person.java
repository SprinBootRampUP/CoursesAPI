package org.course.api.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({Extension.class})
public class Person  {
   // @With
    private final String name;
   // @With
    private final int age;

    @Getter(onMethod_={@Id, @Column(name="unique-id")})
    int id;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        Person person1 = new Person("John", 30);
        System.out.println(person1); // Outputs: Person(name=John, age=30)


        Person s = new Person("John", 54);
        System.out.println(s);


        System.out.println( "sharan".hi() );

//        // Create a new person with a different age
//        Person person2 = person1.withAge(31);
//        System.out.println(person2); // Outputs: Person(name=John, age=31)
    }

    @Override
    public String toString() {
        return "Person(name=" + name + ", age=" + age + ")";
    }
}

class Extension{

    public static String hi(String str){
        return  "hi " +str;
    }

}