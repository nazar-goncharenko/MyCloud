package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.User;

import java.util.Optional;

@Repository
public interface UsersRepo extends CrudRepository<User,Long> {

    public User findByLoginIs(String Login);

    public Optional <User> findByLogin(String Login);

}
