import org.hibernate.mapping.List;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data
@AllArgsConstructor
@Builder

public class Brand {

    @Id
    private char brandID;

    private string brangName;

    private List<Product> products; // This is a list of products that are associated with this brand
}
