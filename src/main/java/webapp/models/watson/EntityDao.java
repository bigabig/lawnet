package webapp.models.watson;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by tim on 28.02.2017.
 */
public interface EntityDao extends CrudRepository<Entity, Long> {

    public Entity findById(long id);

}
