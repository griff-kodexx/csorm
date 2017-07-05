package com.kodexx.csorm.backend.data;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("files")

public class File implements Serializable {

    @Id
    @NotNull
    private int id = -1;  //when scaling this will need to be of type long or ObjectId. It's -1 to show that it's a new file.
    @NotNull
    private String code = "";
    @NotNull
    @Size(min = 6, message = "Title must be more than 6 characters")
    private String title = "";
    @NotNull
    private String uploadedBy = "";
    @NotNull
    private String uploadDate = "";
    @NotNull
    private String mime = "";
    @NotNull
    private String size = "";
    @NotNull
    private String path = "";
    @NotNull
    private String description = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
