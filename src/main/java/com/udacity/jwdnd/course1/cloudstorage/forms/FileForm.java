package com.udacity.jwdnd.course1.cloudstorage.forms;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileForm {
    private Integer fileId;
    private String fileName;
    private byte[] fileData;
}
