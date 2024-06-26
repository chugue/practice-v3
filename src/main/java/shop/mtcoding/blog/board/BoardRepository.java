package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    @Transactional
    public void updateById (int id, BoardRequest.UpdateDTO reqDTO){
        Board board = em.find(Board.class, id);
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
    }

    @Transactional
    public void deleteById(int id){
        String q = """
                delete from Board b where b.id = :id
                """;
        Query query = em.createQuery(q);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void save (Board board){
        em.persist(board);
    }

    public List<Board> findAllV2 () {
        Query q1 = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = q1.getResultList();
        List<Integer> userIds = boardList.stream().mapToInt(value -> value.getUser().getId()).distinct().boxed().toList();

        Query q2 = em.createQuery("select u from User u where u.id in :userIds", User.class);
        q2.setParameter("userIds", userIds);
        List<User> userList = q2.getResultList();

        boardList.stream().forEach(board -> {
            User user = userList.stream().filter(u -> u.getId() == board.getUser().getId()).findFirst().get();
            board.setUser(user);
        });
        return boardList;
    }

    public Board findByIdJoinUser(int id){
        String q = """
                select b from Board b join fetch b.user u where b.id = :id
                """;
        Query query = em.createQuery(q, Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    public Board findById (int id){
        Board board = em.find(Board.class, id);
        return board;
    }

    public List<Board> findAll() {
        String q = """
                select b from Board b order by b.id desc
                """;
        Query query = em.createQuery(q, Board.class);
        return query.getResultList();

    }
}
