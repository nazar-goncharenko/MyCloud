package project.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.Models.Track;


@Repository
public interface TrackRepo extends CrudRepository<Track,Long> {


    public Iterable<Track> findTop20ByOrderByRatingDesc();


}
