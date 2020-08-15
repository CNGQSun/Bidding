package com.dsmpharm.bidding.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/08/15
 */
@Entity
@Table(name="bidding_file_addcontent")
public class BiddingFileAddcontent implements Serializable {
    private static final long serialVersionUID = -8406592838827480056L;

    @Id
    private Integer id;

    private String filePath;

    public Integer getId() {
		return id;
	}
    public void setId(Integer id) {
		this.id = id;
	}
    public String getFilePath() {
		return filePath;
	}
    public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
