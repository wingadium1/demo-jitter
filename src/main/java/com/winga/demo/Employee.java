package com.winga.demo;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

class Employee {

  private String id;
  private String name;
  private String role;

  Employee() {
	  UUID uuid = UUID.randomUUID();
	  id = uuid.toString();
	  Date now = new Date();
	  name = now.toString();
	  role = now.toString();
  }

  Employee(String name, String role) {

    this.name = name;
    this.role = role;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getRole() {
    return this.role;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Employee))
      return false;
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
        && Objects.equals(this.role, employee.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.role);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
  }
}
