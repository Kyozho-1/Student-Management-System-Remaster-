package com.kyozho.backend;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty nickname = new SimpleStringProperty();
    private StringProperty homeAddress = new SimpleStringProperty();
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty classes = new SimpleStringProperty();

    private final int MAX_NUM_OF_PERIODS = 6;
    private final int MIN_NUM_OF_PERIODS = 1;
    private final int DIGITS_IN_PHONE_NUMBER = 10;

    /**
     * Creates a student object. <br>
     * Note: This constructor is chosen if the student
     * <strong><em>does</em></strong> have a nickname associated with them.
     * 
     * @param name        The name of the student (Last, First) or (Last, Middle,
     *                    First).
     * @param nickname    The nickname of the student.
     * @param homeAddress The home address of the student.
     * @param phoneNumber The phone number of the student.
     * @param email       The E-mail that the student has with the school.
     * @param classes     The classes the student is taking. They should at least be
     *                    taking 1-6 classes.
     * @throws NullPointerException      Thrown if any of the parameters is
     *                                   <code>null</code>.
     * @throws Error                     Thrown if the phone number provided is not
     *                                   a 10-digit number.
     * @throws IndexOutOfBoundsException Thrown if the amount of classes the student
     *                                   is taking is not in the range of 1-6
     *                                   classes.
     */
    public Student(String id, String name, String nickname, String homeAddress,
            String phoneNumber, String email, String classes) throws NullPointerException,
            IllegalArgumentException {
        /* Checking for null values. */
        Objects.requireNonNull(id, "Error! Please enter an I.D. for the student.");
        Objects.requireNonNull(name, "Error! Please enter a name for the student.\n" +
                "Enter it either as: (Last, First) or (Last, Middle, First)");
        Objects.requireNonNull(homeAddress, "Error! Please enter the home address for the student.");
        Objects.requireNonNull(phoneNumber, "Error! Please enter a phone number for the student.");
        Objects.requireNonNull(email, "Error! Please enter an E-mail for the student.");
        Objects.requireNonNull(classes, "Error! Please enter classes that the student is taking.");

        this.id.set(id);
        this.name.set(name);
        this.nickname.set(nickname);
        this.homeAddress.set(homeAddress);

        if (phoneNumber.replace("-", "").length() == DIGITS_IN_PHONE_NUMBER) {
            this.phoneNumber.set(phoneNumber);
        } else {
            throw new IllegalArgumentException(
                    "Please ensure that the phone number is a 10-digit number.");
        }

        this.email.set(email);

        if (classes.split(",").length >= MIN_NUM_OF_PERIODS
                &&
                classes.split(",").length <= MAX_NUM_OF_PERIODS) {
            this.classes.set(classes);
        } else {
            throw new IndexOutOfBoundsException("A student can only have 1-6 classes.");
        }
    }

    /* This is only here so Jackson can work properly. */
    public Student() {};

    /* Start of Normal Strings */
    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getNickname() {
        return nickname.get();
    }

    public String getHomeAddress() {
        return homeAddress.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getClasses() {
        return classes.get();
    }
    /* End of Normal Strings */

    /* Start of StringProperty Strings */
    @JsonIgnore
    /**
     * Gets the I.D. of the student.
     * 
     * @return A <code>StringProperty</code> of the student's I.D.
     */
    public StringProperty getIdProperty() {
        return id;
    }

    @JsonIgnore
    /**
     * Gets the name of the student.
     * 
     * @return A <code>StringProperty</code> of the student's name.
     */
    public StringProperty getNameProperty() {
        return name;
    }

    @JsonIgnore
    /**
     * Gets the nickname of the student. <br>
     * <em>Note: <code>"[Not Given]"</code> may be present if the student does not
     * have a nickname associated with them.</em>
     * 
     * @return A <code>StringProperty</code> of the student's nickname.
     */
    public StringProperty getNicknameProperty() {
        return nickname;
    }

    @JsonIgnore
    /**
     * Gets the student's home address.
     * 
     * @return A <code>StringProperty</code> of the student's home address.
     */
    public StringProperty getHomeAddressProperty() {
        return homeAddress;
    }

    @JsonIgnore
    /**
     * Gets the student's phone number.
     * 
     * @return A <code>StringProperty</code> of the student's phone number.
     */
    public StringProperty getPhoneNumberProperty() {
        return phoneNumber;
    }

    @JsonIgnore
    /**
     * Gets the student's school E-mail.
     * 
     * @return A <code>StringProperty</code> of the student's school E-mail.
     */
    public StringProperty getEmailProperty() {
        return email;
    }

    @JsonIgnore
    /**
     * Gets the student's classes that they are enrolled in.
     * 
     * @return A <code>StringProperty</code> of the student's classes that they are
     *         enrolled in.
     */
    public StringProperty getClassesProperty() {
        return classes;
    }
    /* End of StringProperty Strings */

    /**
     * Sets the student's I.D.
     * 
     * @param id The student's I.D.
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Sets the student's name.
     * 
     * @param name The student's name.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Sets the student's nickname.
     * 
     * @param nickname The student's nickname.
     */
    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    /**
     * Sets the student's home address.
     * 
     * @param homeAddress The student's home address.
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress.set(homeAddress);
    }

    /**
     * Sets the student's phone number.
     * 
     * @param phoneNumber The student's phone number.
     */
    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber.replace("-", "").length() == DIGITS_IN_PHONE_NUMBER) {
            this.phoneNumber.set(phoneNumber);
        } else {
            throw new IllegalArgumentException(
                    "Please ensure that the phone number is a 10-digit number.");
        }
    }

    /**
     * Sets the student's school E-mail.
     * 
     * @param email The student's E-mail.
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Sets the student's classes that they are enrolled in. <br>
     * If a student has more than six classes, an
     * <code>IndexOutOfBoundsException</code>
     * will be thrown. The same error will be thrown if they have less than one
     * class.
     * 
     * @param classes The classes the student's enrolled in.
     */
    public void setClasses(String classes) throws IndexOutOfBoundsException {
        if (classes.split(",").length >= MIN_NUM_OF_PERIODS
                &&
                classes.split(",").length <= MAX_NUM_OF_PERIODS) {
            this.classes.set(classes);
        } else {
            throw new IndexOutOfBoundsException("A student can only have 1-6 classes.");
        }
    }

    /**
     * (non-Javadoc)
     * Gets all of the student's information.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "I.D.: %s\nName: %s\nNickname: %s\nHome Address: %s\nPhoneNumber: %s\nE-mail: %s\nClasses: %s",
                id.get(), name.get(), nickname.get(), homeAddress.get(),
                phoneNumber.get(), email.get(), classes.get());
    }
}
