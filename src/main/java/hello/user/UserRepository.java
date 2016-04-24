package hello.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<PaceUser, Long> {

    List<PaceUser> findByFacebookId(String lastName);
}
