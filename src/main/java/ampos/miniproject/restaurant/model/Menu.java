package ampos.miniproject.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Menu entity
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Menu.TABLE_NAME)
public class Menu implements DomainEntity<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    protected static final String TABLE_NAME = "menu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "details")
    private String details;

    /**
     *
     * @param id
     * @param name
     * @param description
     * @param imageUrl
     * @param price
     * @param details
     */
    public Menu(long id, @NotNull String name, @NotNull String description, String imageUrl, @NotNull BigDecimal price,
            String details) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.details = details;
    }
}