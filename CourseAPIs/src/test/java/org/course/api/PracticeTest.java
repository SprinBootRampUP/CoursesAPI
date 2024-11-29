//package org.course.api;
//
//import lombok.Data;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ParameterResolver;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.junit.runner.RunWith;
//import org.mockito.*;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.*;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//
//
////@RunWith(TestRunner.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest
//public class PracticeTest  {
//
//    //@RegisterExtension
//   // static LoggingExtension loggingExtension = new LoggingExtension();
//
//    private  int a =5;
//
//    @Test
//    void test(){
//        System.out.println("ji");
//    }
//
//@RepeatedTest(value = 3 ,name = "demo" )
//    void repeatedTest(RepetitionInfo repetitionInfo){
//    System.out.println("current repeat number"+repetitionInfo.getCurrentRepetition());
//
//    a=a+1;
//    System.out.println(a);
//    }
//
//    @Disabled
//    @TestFactory
//    Collection<DynamicTest> dynamicTestsWithCollection() {
//        return Arrays.asList(
//                DynamicTest.dynamicTest("Add test",
//                        () -> assertEquals(2, Math.addExact(1, 1))),
//                DynamicTest.dynamicTest("Multiply Test",
//                        () -> assertEquals(4, Math.multiplyExact(2, 2))));
//    }
//
//
//
//    @TestFactory
//    Stream<DynamicTest> streamOfDynamicTests() {
//
//        int[] v = {3, 4};
//        return Arrays.stream(v)
//                .mapToObj(n ->
//                        DynamicTest.dynamicTest(
//                                "Test ",
//                                () -> assertEquals(n * 2, n + n)
//                        )
//                );
//    }
//
//
//
//    @Mock
//    private List<String> mockList;
//
//    @Captor
//    private ArgumentCaptor<String> captor;
//
//    @Test
//    void mockitoTest(){
//
//        mockList.add("sharans");
//    //    verify(mockList).add("sharan");
//
//        Mockito.verify(mockList).add(captor.capture());
//        assertEquals("sharan", captor.getValue());
//    }
//
//    @Spy
//    private List<String> spyList = new ArrayList<>();
//
//    @Test
//    public void testSpy() {
//        spyList.add("Sharan");
//        Mockito.verify(spyList).add("Sharan");
//        assertEquals(1, spyList.size());
//     //   System.out.println(spyList.size());
//        Mockito.when(spyList.size()).thenReturn(10);
//        assertEquals(10, spyList.size());
//    }
//}
//
