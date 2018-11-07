package com.sebastian_daschner.tasks.tasklist.tasks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String description;

    private Instant created;
    private Instant assigned;

    @PrePersist
    private void updateCreated() {
        created = Instant.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getAssigned() {
        return assigned;
    }

    public void setAssigned(Instant assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", assigned=" + assigned +
                '}';
    }
}
