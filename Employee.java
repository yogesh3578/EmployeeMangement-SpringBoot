package com.csi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Employee {

    @Id
    @GeneratedValue

    private int empId;

    @Size(min = 2, message = "First Name Should Be Two Char")
    private String empFirstName;

    @Pattern(regexp = "[A-Za-z]*", message = "Last Name Must Be Valid..")
    private String empLastName;

    private String empAddress;

    @Column(unique = true)
    @Range(min = 1000000000, max = 9999999999L, message = "Contact Number Must Be Valid")
    private long empContactNumber;

    private double empSalary;

    @Column(unique = true)
    private long empUid;

    @Column(unique = true)
    private String empPanCard;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date empDob;


    @Email(message = "Email Must Be Valid..")
    @Column(unique = true)
    private String empEmailId;

    private String empPassword;


}
