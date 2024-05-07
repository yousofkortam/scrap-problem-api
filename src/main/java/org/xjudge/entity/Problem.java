package org.xjudge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.xjudge.enums.OnlineJudge;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private OnlineJudge onlineJudge;
    private String title;
    private String problemLink;
    private String contestLink;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Section> sections;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Property> properties;
}
