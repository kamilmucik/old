package pl.estrix.app.backend.send.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.estrix.app.backend.send.model.SendProductImage;

import java.util.List;

@Repository
public interface SendProductImageRepository extends JpaRepository<SendProductImage, Long> {

    SendProductImage findById(Long id);

    List<SendProductImage> findBySendProductId(Long id);

}
