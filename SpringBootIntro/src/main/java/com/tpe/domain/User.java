package com.tpe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//Burada tablo ismini user olarak bıraktığımızda SQL hata veriyor, o yüzden değiştirmek zorundayız.
@Table(name="t_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25,nullable = false)
    private String firstName;

    @Column(length = 25,nullable = false)
    private String lastName;

    @Column(length = 25,nullable = false,unique = true)
    private String userName;

    @Column(length = 255,nullable = false)//password DB ye kaydedilmeden önce şifreleneceği için
    private String password;//karmaşık ve uzun olacak

    //role

    @ManyToMany
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="role_id"),
                                inverseJoinColumns =@JoinColumn(name="user_id"))//opsiyonel
    private Set<Role> roles=new HashSet<>();


}
