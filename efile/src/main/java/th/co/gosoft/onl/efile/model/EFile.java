/**
 * 
 */
package th.co.gosoft.onl.efile.model;

/**
 * @author tanaponjit
 *
 */
import org.hibernate.annotations.GenericGenerator;
import th.co.gosoft.onl.efile.model.audit.DateAudit;

import java.util.HashMap;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "efiles")
public class EFile extends DateAudit{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
   
    private String id;
    private String fileName;
    
    @Size(max = 4)
    private String date;
    
    @Size(max = 5)
    private String storeId;
    
    @Size(max = 10)
    private String invoiceNo;
    
    private String venderCode;
    private String fileType; 
    @Lob
    private byte[] data;
    
    public EFile() {

    }
    public EFile(String fileName, String fileType) {
    	String[] fileNameParts = fileName.split(".r");
    	this.fileName = fileName;
        this.fileType = fileType;
        this.date = fileNameParts[0].substring(0, 4);
        this.storeId = fileNameParts[0].substring(4, 9);
        this.invoiceNo = fileNameParts[0].substring(9, fileNameParts[0].length());
        this.venderCode = fileNameParts[1];
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getVenderCode() {
		return venderCode;
	}

	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode;
	}
 
	
}

