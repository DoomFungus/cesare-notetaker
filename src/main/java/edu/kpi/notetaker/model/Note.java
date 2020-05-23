package edu.kpi.notetaker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;
    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Attachment> attachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notebook_id", nullable = false)
    private Notebook notebook;
}
