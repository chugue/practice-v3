package shop.mtcoding.blog.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void updateById_test(){
        // given
        int id = 1;
        UserRequest.UpdateDTO reqDTO = new UserRequest.UpdateDTO();
        reqDTO.setUsername("안녕");
        reqDTO.setPassword("안녕");
        reqDTO.setUsername("안녕");
        // when
        User user = em.find(User.class, id);
        user.setUsername(reqDTO.getUsername());
        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());
        // then
        Assertions.assertThat(user.getUsername().equals(reqDTO.getUsername()));

    }

    @Test
    public void findById_test(){
        // given
        int id = 1;
        // when
        String q = """
                select u from User u where u.id = :id
                """;
        Query query = em.createQuery(q, User.class);
        query.setParameter("id", id);
        query.getSingleResult();
        // then

    }

    @Test
    public void findByUsernameAndPassword_test(){
        // given
        String username = "ssar";
        String password = "1234";
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();
        reqDTO.setUsername(username);
        reqDTO.setPassword(password);
        // when
        User user = userRepository.findByUsernameAndPassword(reqDTO);
        // then
        Assertions.assertThat(user.getUsername()).isEqualTo("ssar");
    }
}
