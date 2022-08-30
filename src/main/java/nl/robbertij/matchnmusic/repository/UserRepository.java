package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
