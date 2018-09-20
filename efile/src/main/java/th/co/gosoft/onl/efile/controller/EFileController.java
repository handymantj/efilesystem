/**
 * 
 */
package th.co.gosoft.onl.efile.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanaponjit
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import th.co.gosoft.onl.efile.model.EFile;
import th.co.gosoft.onl.efile.payload.UploadFileResponse;
import th.co.gosoft.onl.efile.service.EFileStorageService;

@RestController
public class EFileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private EFileStorageService EFileStorageService;

    @PostMapping("/uploadEFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        EFile eFile = null;
        String fileDownloadUri="";
		try {
			eFile = EFileStorageService.storeFile(file);

			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadEFile/")
                .path(eFile.getFileName())
                .toUriString();
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  return new UploadFileResponse(eFile.getFileName(), fileDownloadUri,
	         file.getContentType(), file.getSize());
		
    }
    
    
    @PostMapping("/uploadMultipleEFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
	
    
    @GetMapping("/downloadEFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        EFile eFile = EFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(eFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + eFile.getFileName() + "\"")
                .body(new ByteArrayResource(eFile.getData()));
    }

}
