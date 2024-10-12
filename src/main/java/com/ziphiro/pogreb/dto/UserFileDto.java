package com.ziphiro.pogreb.dto;

import com.ziphiro.pogreb.entityes.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserFileDto {

    private Long id;
    private String fileName;
    private String filePath;
    private String creator;
    private User user;

}
