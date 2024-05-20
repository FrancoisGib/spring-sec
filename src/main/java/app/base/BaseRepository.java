package app.base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository extends JpaRepository<BaseObject, Void> {
	@Query(value = """
	SELECT count(*) from user_app
	""", nativeQuery = true)
	Integer getCount();
}