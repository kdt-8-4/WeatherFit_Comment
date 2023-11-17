package com.weatherfit.comment_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nickname;

    @Column(columnDefinition = "VARCHAR(500) NOT NULL")
    private String content;

    @Column(nullable = true, columnDefinition = "TINYINT(1)")
    @ColumnDefault("1")
    private int status;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false, insertable = false, updatable = false)
    private Comment comment;

    @Column(name = "comment_id")
    private int commentId;
    @PrePersist
    public void prePersist() {
        this.status = this.status == 0 ? 1 : this.status;
    }
}
