package org.course.api.Entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Builder
@ToString(includeFieldNames = false)
//@Value
//@AllArgsConstructor
//@Data
//@NoArgsConstructor
public class Demo {
    String firstName;
    String lastName;

 //   @With
    int age;

    @Getter(lazy = true)
    private final String fullName= firstName +lastName;

    @Singular
    List<String> hobbies;

    public void setAge(){
        this.age=23;
    }

    Sample s;

//    @Override
//    public String toString(){
//
//        return "hi";
//    }

    @SneakyThrows
    public void readFile() {
            FileInputStream file = new FileInputStream("file.txt");
    }



    public static void main(String[] args) {

        Demo person = Demo.builder()
                .firstName("sharan")
                .lastName("V")
                .age(30)
                .hobby("Reading")
                .hobby("Swimming")
                .build();
        System.out.println(person.toString());
        System.out.println(person);


    }

}

//@FieldDefaults(makeFinal = true)
class Sample{

   //@NonFinal
   int a;
    //Demo d;
}