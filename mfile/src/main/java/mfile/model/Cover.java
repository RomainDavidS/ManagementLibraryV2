package mfile.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public @Data class Cover {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NonNull
    private String fileName;

    @NonNull
    private String fileType;

    @NonNull
    private Long fileSize;

    @NonNull
    @Lob
    private byte[] data;

    @NonNull
    private String use;

}
