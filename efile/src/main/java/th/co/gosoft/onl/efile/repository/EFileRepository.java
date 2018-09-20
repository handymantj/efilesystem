/**
 * 
 */
package th.co.gosoft.onl.efile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.gosoft.onl.efile.model.EFile;

/**
 * @author tanaponjit
 *
 */
@Repository
public interface EFileRepository extends JpaRepository<EFile, String>{

}
