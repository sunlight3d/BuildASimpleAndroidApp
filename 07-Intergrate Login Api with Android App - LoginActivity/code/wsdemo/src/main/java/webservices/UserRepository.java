package webservices;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

}
