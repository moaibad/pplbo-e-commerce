// import org.hibernate.mapping.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Brand {

    @Id
    private char brandID;

    private String brandName;

    private List<Product> products; // This is a list of products that are associated with this brand
}
