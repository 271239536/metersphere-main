package io.metersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileVersionDTO {
    public String id;
    public String commitId;
    public String commitMessage;
    public String operator;
    public long operatorTime;
}
