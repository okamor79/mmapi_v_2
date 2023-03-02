package com.mm.beauty.api.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EmailModel {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
