package ge.paso.Exam.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "servers")
@Getter
@Setter
public class Server {

    @Column(name = "id")
    @SequenceGenerator(name = "servers_seq", sequenceName = "servers_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "servers_seq")
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "delete_date")
    private String deleteDate;

    @Column(name = "status")
    private String status;

    @Column(name = "user_id")
    private Integer userId;

    public Server(String name, int capacity, String deleteDate, String status, Integer userId) {
        this.name = name;
        this.capacity = capacity;
        this.deleteDate = deleteDate;
        this.status = status;
        this.userId = userId;
    }

    public Server() {

    }
}
