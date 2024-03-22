package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private EntityManager em;

    @Test
    public void findById_test(){
        // given
        int id = 1;
        // when
        System.out.println("start -1");
        Board board = em.find(Board.class, id);
        System.out.println("start -2");
        System.out.println(board.getId());
        // then
        System.out.println("start -3");
        System.out.println(board.getUser().getUsername());
    }
}
