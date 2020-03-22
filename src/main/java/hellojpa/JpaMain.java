package hellojpa;

import hellojpa.jpabook.jpashop.domain.Order;
import hellojpa.jpabook.jpashop.domain.OrderItem;
import hellojpa.jpabook.jpashop.domain.inheritstant.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // EntityManagerFactory는 한번만 만든다.
        EntityManager em = emf.createEntityManager(); // 트랜잭션 단위에 행위를 할때는 EntityManager를 만들어야 한다. JPA에서 모든 작업은 트랜잭션안에서 이루어져야 한다.
        EntityTransaction tx = em.getTransaction(); // 트랜잭션을 받아온다.
        tx.begin(); // 트랜잭션 시작

        try{

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");
            book.setCreateDate(LocalDateTime.now());

            em.persist(book);

            tx.commit(); // 작업 끝났으니 커밋
        }catch(Exception e){
            tx.rollback(); // 에러 발생하면 롤백
        }finally {
            em.close(); // 작업 후 EntityManager 종료, jdbc 커넥션을 물고 동작하기 때문에 커넥션을 반드시 닫아야함.
        }

        emf.close();
    }
}


/* CRUD

    * JPA를 위한 준비
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();


    * create
    Member member = new Member;
    member.setId(1L); // 타입이 롱이여서 L을 붙임
    member.setName("HelloA");
    em.persist(member);


    * read
    Member findMember = em.find(Member.class, 1L); // find메소드에 첫번째 파라미터로 Entity Class를 넘겨야 하기 때문에 Member.class를 넘기는 것이다, 두번째 파라미터는 primary 키
    System.out.println("findMember.id = " + findMember.getId());
    System.out.println("findMember.name = " + findMember.getName());


    * update
    Member findMember = em.find(Member.class, 1L);
    findMember.setName("name change"); // find로 찾고 set으로 변경만하면 다시 persist로 추가하지않아도 알아서 적용이 되어있다. 마치 자바 컬렉션을 다루도록 설계되어 있기 때문이다. 트랜잭션을 commit하는 시점에 변동사항이 있는지 JPA가 관리해서 변경사항을 update 쿼리를 날린다.


    * remove
    Member findMember = em.find(Member.class, 1L);
    em.remove(findMember);
*/

/*
    주의사항
    1. 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
    2. 엔티티 매니저는 쓰레드간에 공유 X (작업이 완료되면 close해야 한다.)
    3. JPA의 모든 데이터 변경을 트랙잭션 안에서 실행
*/

