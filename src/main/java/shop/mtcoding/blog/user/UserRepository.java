package shop.mtcoding.blog.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    @Transactional
    public User join(User user){
        em.persist(user);
        return user;
    }

    public User findByUsernameAndPassword(UserRequest.LoginDTO reqDTO) {
        String q = """
                select u from User u where u.username = :username and u.password = :password
                """;
        Query query = em.createQuery(q, User.class);
        query.setParameter("username", reqDTO.getUsername());
        query.setParameter("password", reqDTO.getPassword());
        return (User) query.getSingleResult();
    }
}


