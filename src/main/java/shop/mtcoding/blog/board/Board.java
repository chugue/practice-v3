package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreationTimestamp // pc -> db (날짜주입)
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, String username, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.user = user;
        this.createdAt = createdAt;
    }
}
