package com.projectnoly.Model.MongoDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Document(collection = "tables")
public class Tables {
    @Id
    private int id;
    private String name;
    private List<Product> productList;
}
