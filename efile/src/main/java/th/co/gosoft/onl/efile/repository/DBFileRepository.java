/**
 * 
 */
package th.co.gosoft.onl.efile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.gosoft.onl.efile.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
