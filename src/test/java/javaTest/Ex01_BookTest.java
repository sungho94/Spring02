package javaTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// ** Book class
// => 맴버필드 3개 정의, 맴버필드 3개를 초기화하는 생성자를 만드세요 ~
// => 접근범위는 default

class Book {
	String author;
	String title;
	int price;
	
	Book(String author, String title, int price){ 
		this.author=author;
		this.title=title;
		this.price=price;
	}
	public boolean isBook(boolean b) {
		return b;
	}
} //Book

//** @ 종류
//=> @Before - @Test - @After
//=> @ 적용 메서드 : non static, void 로 정의 해야 함.

//** org.junit.Assert 가 지원하는 다양한 Test용 Method 
//1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
//2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
//3) assertTrue(a) : a가 참인지 확인
//4) assertNotNull(a) : a객체가 Null 이 아님을 확인
//5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인

public class Ex01_BookTest {
	
	//1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
	public void equalsTest() {
		Book b1 = new Book("엄미현","Java 정복",9900); 
		//assertEquals(b1.author,"엄미현"); // true : 그린라인
		assertEquals(b1.author,"홍길동"); // flase : 레드라인
	}
	
	//2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
	public void sameTest() {
		Book b1 = new Book("엄미현","Java 정복",9900); 
		Book b2 = new Book("엄미현","Java 정복",9900); 
		Book b3 = new Book("톨스토이","죄와벌",15000);
		b3=b1; // true
		assertSame(b1, b3);
	}
	
	//3) assertTrue(a) : a가 참인지 확인
	public void trueTest() {
		Book b1 = new Book("톨스토이","죄와벌",15000);
		assertTrue(b1.isBook(false)); // 레드라인
	}
	
	@Test
	//4) assertNotNull(a) : a객체가 Null 이 아님을 확인
	public void notNullTest() {
		Book b1 = new Book("톨스토이","죄와벌",15000); 
		System.out.println("**** b1 =>"+b1);
		assertNotNull(b1); // 그린라인
	}
	
	//5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인 (배열의 동일성)
	public void arrayEqualsTest() {
		String[] a1 = new String[] {"가","나","다"};
		String[] a2 = new String[] {"가","나","다"};
		String[] a3 = new String[] {"가","다","나"};
		String[] a4 = new String[] {"가","다","라"};
		// 5.1) 두배열의 순서, 값 모두 동일 (a1, a2)
		//assertArrayEquals(a1, a2);
		// 5.2) 두배열의 순서는 다르고, 값 모두 동일 (a1, a3)
		//assertArrayEquals(a1, a3); // 레드라인
		// 5.3) 모두 다른경우 (a1,a4)
		assertArrayEquals(a1, a4); // 레드라인
		
	}
	
} //class
