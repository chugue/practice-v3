package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

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

    public void findAll() {

    }
}
