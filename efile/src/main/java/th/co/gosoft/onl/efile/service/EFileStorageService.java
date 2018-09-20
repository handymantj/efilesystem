/**
 * 
 */
package th.co.gosoft.onl.efile.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author tanaponjit
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import th.co.gosoft.onl.efile.exception.FileStorageException;
import th.co.gosoft.onl.efile.exception.MyFileNotFoundException;
import th.co.gosoft.onl.efile.model.EFile;
import th.co.gosoft.onl.efile.property.FileStorageProperties;
import th.co.gosoft.onl.efile.repository.EFileRepository;

@Service
public class EFileStorageService {
	
    private final Path fileStorageLocation;

    @Autowired
    private EFileRepository eFileRepository;
    
    @Autowired
    public EFileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public EFile storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        validateFileName(fileName);

		EFile eFile = new EFile(fileName, file.getContentType());
		
		Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
		return eFileRepository.save(eFile);
    }
       
    public EFile getFile(String fileId) {
        return eFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
    
    private void validateFileName(String fileName) {
    	if(fileName.contains("..")) {
		    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
		}
    	if(!fileName.contains(".r")) {
		    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
		}
    }
}
