package az.ingress.connectionpoolsstudy.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class StudentResponseDto {
    Long id;
    String name;
}
