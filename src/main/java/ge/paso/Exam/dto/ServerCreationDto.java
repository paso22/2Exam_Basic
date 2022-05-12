package ge.paso.Exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ServerCreationDto {

    @NotNull(message = "სერვერის სახელი არ უნდა იყოს ცარიელი!")
    private String name;

    @NotNull(message = "სერვერის მოცულობა არუნდა იყოს ცარიელი!")
    @Min(message = "სერვერის მოცულობა უნდა იყოს დადებითი!", value = 1)
    private int capacity;

    @NotNull(message = "გაუქმების თარიღი არ უნდა იყოს ცარიელი!")
    private String deleteDate;

}
