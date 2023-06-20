package com.hieunt.ebookapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

}
