package org.course.api;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(TestRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PracticeTest  {

    @RegisterExtension
    static LoggingExtension loggingExtension = new LoggingExtension();

    private  int a =5;

@RepeatedTest(value = 3 ,name = "demo" )
    void repeatedTest(RepetitionInfo repetitionInfo){
    System.out.println("current repeat number"+repetitionInfo.getCurrentRepetition());

    a=a+1;
    System.out.println(a);
    }

    @Disabled
    @TestFactory
    Collection<DynamicTest> dynamicTestsWithCollection() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Add test",
                        () -> assertEquals(2, Math.addExact(1, 1))),
                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(4, Math.multiplyExact(2, 2))));
    }



}
