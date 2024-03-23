package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void updateById_test(){
        // given
        int id = 1;
        BoardRequest.UpdateDTO reqDTO = new BoardRequest.UpdateDTO();
        reqDTO.setTitle("안녕?");
        reqDTO.setContent("헬로우!");
        // when
        Board board = em.find(Board.class, id);
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());

        // then
        Assertions.assertThat(board.getTitle().equals(reqDTO.getTitle()));
    }

    @Test
    public void deleteById_test(){
        // given
        int id = 1;
        // when
        String q = """
                delete from Board b where b.id = :id
                """;
        Query query = em.createQuery(q);
        query.setParameter("id", id);
        query.executeUpdate();
        // then
        System.out.println(boardRepository.findAll().size());

    }

    @Test
    public void randomQuery_test() {
        String q1 = "select b from Board b order by b.id desc";
        Query query = em.createQuery(q1, Board.class);
        List<Board> boardList = query.getResultList();

        // 이 쿼리문에서 게시글을 작성한 사용자만 중복없이 담김
        int[] ids = boardList.stream().mapToInt(value -> value.getUser().getId()).distinct().toArray();
        // select u from User u where b.id in (?, ?); --> 동적 in 쿼리
        String q2 = "select u from User u where u.id in (";
        for (int i = 0; i < ids.length; i++) {
            if (i == ids.length - 1) {
                q2 = q2 + ids[i] + ")";
            } else {
                q2 = q2 + ids[i] + ",";
            }
        }
        // 여기서 동적 인쿼리가 완성되어 발송, 그 객체들을 userList로 받는다.
        List<User> userList = em.createQuery(q2, User.class).getResultList();
        for (Board board : boardList){
            for (User user : userList){
                if (board.getUser().getId() == user.getId()){
                    board.setUser(user);
                }
            }
        }
    }


    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardRepository.findAll();

        boardList.forEach(board -> {
            System.out.println(board.getUser().getUsername());
        });

        // then

    }

    @Test
    public void findById_test() {
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
