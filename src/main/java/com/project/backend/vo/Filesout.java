package com.project.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Filesout {
	String id;
    Long size;
    String url;
    String contentType;
}
