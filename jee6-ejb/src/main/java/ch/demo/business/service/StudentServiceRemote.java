package ch.demo.business.service;

import java.io.Serializable;

import javax.ejb.Remote;

import ch.demo.dom.Student;

/**
 * Defines the remote contract of the student service.
 * This service can be used outside of the application EAR
 * @author hostettler
 * 
 */
@Remote
public interface StudentServiceRemote extends Serializable {

    /**
     * @param lastname
     *            of the student
     * @return the student with the given lastname.
     */
    Student getStudentByLastName(String lastname);


}