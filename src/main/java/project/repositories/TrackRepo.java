package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.Track;


@Repository
public interface TrackRepo extends CrudRepository<Track,Long> {


    public Iterable<Track> findTop20ByOrderByRatingDesc();



}
