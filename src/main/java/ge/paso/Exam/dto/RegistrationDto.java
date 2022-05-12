package ge.paso.Exam.dto;


import ge.paso.Exam.users.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationDto {

    @NotNull(message = "first name ველი არ უნდა იყოს ცარიელი")
    private String firstName;

    @NotNull(message = "last name ველი არ უნდა იყოს ცარიელი")
    private String lastName;

    @NotNull(message = "იუზერის ველი არ უნდა იყოს ცარიელი!")
    @Size(min = 6, message = "იუზერის სახელი უნდა შედგებოდეს მინიმუმ 6 სიმბოლოსგან!")
    private String userName;

    @NotNull(message = "ასაკის ველი არუნდა იყოს ცარიელი!")
    @Min(message = "მინიმალური ასაკი რეგისტრაციისათვის 18 წელია!", value = 18)
    private int age;

    @NotNull(message = "დაბადების თარიღის ველი არ უნდა იყოს ცარიელი!")
    private String birthDate;

    @NotNull(message = "პაროლის ველი არ უნდა იყოს ცარიელი!")
    @Size(min = 7, message =  "პაროლი უნდა შედგებოდეს მინიმუმ 7 სიმბოლოსგან!")
    private String password;

    @NotNull(message = "მეილის ველი არ უნდა იყოს ცარიელი!")
    private String email;

    @NotNull(message = "როლი არუნდა იყოს ცარიელი!")
    private String role;

}
