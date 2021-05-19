package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Integer fileId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "file_size")
    private String fileSize;
    @Column(name = "user_id")
    private long userId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "file_data")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] fileData;
}
