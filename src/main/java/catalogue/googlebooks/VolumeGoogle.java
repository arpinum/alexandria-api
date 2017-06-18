package catalogue.googlebooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeGoogle {

    public String title;
    public List<Identifieur> industryIdentifiers;
    public String description;
    public ImagesGoogle imageLinks;
}
