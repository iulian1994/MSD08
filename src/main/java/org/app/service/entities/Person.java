package org.app.service.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person implements Serializable {
private static final long serialVersionUID = -3196208012963783251L;
@Id
@GeneratedValue
private int id;
protected String name;



}