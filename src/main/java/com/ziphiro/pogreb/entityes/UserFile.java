package com.ziphiro.pogreb.entityes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_files")
public class UserFile {

    @Id
    @SequenceGenerator(name = "user_files_sequence",sequenceName = "user_files_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_files_sequence")
    @Column(name = "file_id")
    private Long id;

    @Column(unique = true)
    private String fileName;
    private String filePath;
    private String creator;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
