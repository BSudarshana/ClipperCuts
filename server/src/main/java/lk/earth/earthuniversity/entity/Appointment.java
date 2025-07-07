package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Appointment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "appointment_date")
    private Date appointmentDate;
    @Basic
    @Column(name = "appointment_time")
    private Time appointmentTime;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "appointmentstatus_id", referencedColumnName = "id", nullable = false)
    private Appointmentstatus appointmentstatus;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @OneToMany(mappedBy = "appointment")
    private Collection<Customerfeedback> customerfeedbacks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) && Objects.equals(appointmentDate, that.appointmentDate) && Objects.equals(appointmentTime, that.appointmentTime) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentDate, appointmentTime, description);
    }

    public Appointmentstatus getAppointmentstatus() {
        return appointmentstatus;
    }

    public void setAppointmentstatus(Appointmentstatus appointmentstatus) {
        this.appointmentstatus = appointmentstatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<Customerfeedback> getCustomerfeedbacks() {
        return customerfeedbacks;
    }

    public void setCustomerfeedbacks(Collection<Customerfeedback> customerfeedbacks) {
        this.customerfeedbacks = customerfeedbacks;
    }
}
