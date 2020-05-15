package project.Iservise;

import org.springframework.web.multipart.MultipartFile;
import project.models.Track;

public interface ITrackService {


    void addTrack(MultipartFile file, String name);

    void like(Long ID);

    void unlike(Long ID);

    Iterable<Track> getTop20();

    void configToUser(Iterable <Track> tracks, String Name);

}
