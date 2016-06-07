package application.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<PaceUser, Long> {
    PaceUser findByFacebookId(String facebookId);
}
